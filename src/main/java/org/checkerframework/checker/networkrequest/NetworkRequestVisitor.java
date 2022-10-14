package org.checkerframework.checker.networkrequest;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.NewClassTree;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import org.checkerframework.checker.networkrequest.qual.NetworkRequest;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;
import org.checkerframework.common.value.ValueAnnotatedTypeFactory;
import org.checkerframework.common.value.ValueChecker;
import org.checkerframework.common.value.qual.IntVal;
import org.checkerframework.common.value.qual.StringVal;
import org.checkerframework.framework.type.AnnotatedTypeMirror;
import org.checkerframework.framework.type.AnnotatedTypeMirror.AnnotatedDeclaredType;
import org.checkerframework.framework.type.AnnotatedTypeMirror.AnnotatedExecutableType;
import org.checkerframework.framework.util.AnnotatedTypes;
import org.checkerframework.javacutil.AnnotationUtils;
import org.checkerframework.javacutil.TreeUtils;

public class NetworkRequestVisitor extends BaseTypeVisitor<NetworkRequestAnnotatedTypeFactory> {

    protected final ProcessingEnvironment env;

    /** The {@link StringVal#value} element/argument. */
    protected final ExecutableElement stringValValueElement;

    /** The {@link IntVal#value} element/argument. */
    protected final ExecutableElement intValValueElement;

    /** The {@link NetworkRequest#value} element/argument. */
    protected final ExecutableElement networkRequestValueElement;

    enum ArgumentType {
        URL,
        URI,
        PORT,
        SKIP,
        HOST,
        FILE,
        BASEURL,
        PROTOCOL,
        RELATIVEURL
    }

    public NetworkRequestVisitor(final BaseTypeChecker checker) {
        super(checker);
        env = checker.getProcessingEnvironment();
        stringValValueElement = TreeUtils.getMethod(StringVal.class, "value", 0, env);
        intValValueElement = TreeUtils.getMethod(IntVal.class, "value", 0, env);
        networkRequestValueElement = TreeUtils.getMethod(NetworkRequest.class, "value", 0, env);
    }

    @Override
    public Void visitMethodInvocation(MethodInvocationTree node, Void p) {
        ExecutableElement method = TreeUtils.elementFromUse(node);
        ExecutableElement overriddenMethod = null;
        AnnotationMirror networkAnnoMirror =
                atypeFactory.getDeclAnnotation(method, NetworkRequest.class);

        if (networkAnnoMirror == null && method != null) {
            // Find all methods that are overridden by the called method
            Map<AnnotatedDeclaredType, ExecutableElement> overriddenMethods =
                    AnnotatedTypes.overriddenMethods(elements, atypeFactory, method);
            for (Map.Entry<AnnotatedDeclaredType, ExecutableElement> pair :
                    overriddenMethods.entrySet()) {
                // AnnotatedDeclaredType overriddenType = pair.getKey();
                ExecutableElement exe = pair.getValue();
                networkAnnoMirror = atypeFactory.getDeclAnnotation(method, NetworkRequest.class);
                if (networkAnnoMirror != null) {
                    overriddenMethod = exe;
                }
            }
        }
        if (networkAnnoMirror != null) {
            List<? extends ExpressionTree> trees = node.getArguments();
            List<String> res = performNetworkRequestChecking(networkAnnoMirror, trees);
            if (overriddenMethod != null) {
                checker.reportError(
                        node,
                        "network.request.found.in.method.invocation",
                        String.join(" | ", res),
                        overriddenMethod);
            } else {
                checker.reportError(
                        node,
                        "network.request.found.in.method.invocation",
                        String.join(" | ", res),
                        null);
            }
        }
        return super.visitMethodInvocation(node, p);
    }

    @Override
    protected void checkConstructorInvocation(
            AnnotatedDeclaredType invocation,
            AnnotatedExecutableType constructor,
            NewClassTree newClassTree) {
        Element ele = TreeUtils.elementFromUse(newClassTree);
        AnnotationMirror networkAnnoMirror =
                atypeFactory.getDeclAnnotation(ele, NetworkRequest.class);
        if (networkAnnoMirror != null) {
            List<? extends ExpressionTree> trees = newClassTree.getArguments();
            List<String> res = performNetworkRequestChecking(networkAnnoMirror, trees);
            checker.reportError(newClassTree, "network.request.found", String.join(" | ", res));
        }
        super.checkConstructorInvocation(invocation, constructor, newClassTree);
    }

    private ValueAnnotatedTypeFactory getValueAnnotatedTypeFactory() {
        return atypeFactory.getTypeFactoryOfSubchecker(ValueChecker.class);
    }

    private List<String> performNetworkRequestChecking(
            AnnotationMirror networkAnnoMirror, List<? extends ExpressionTree> trees) {
        ValueAnnotatedTypeFactory valueATF = getValueAnnotatedTypeFactory();
        List<String> argumentTypesList =
                AnnotationUtils.getElementValueArray(
                        networkAnnoMirror, networkRequestValueElement, String.class);
        int argumentListLength = argumentTypesList.size();
        assert argumentListLength == trees.size();
        List<String> res = new ArrayList<>();
        for (int i = 0; i < argumentListLength; i++) {
            String argType = argumentTypesList.get(i);
            ExpressionTree tree = trees.get(i);
            AnnotatedTypeMirror valueType = valueATF.getAnnotatedType(tree);
            ArgumentType argumentType = ArgumentType.valueOf(argType);
            AnnotationMirror stringValAnnoMirror = valueType.getAnnotation(StringVal.class);
            AnnotationMirror intValAnnoMirror = valueType.getAnnotation(IntVal.class);
            switch (argumentType) {
                case URL:
                case HOST:
                case FILE:
                case PROTOCOL:
                    if (stringValAnnoMirror != null) {
                        String stringValue =
                                AnnotationUtils.getElementValueArray(
                                                stringValAnnoMirror,
                                                stringValValueElement,
                                                String.class)
                                        .get(0);
                        res.add(String.format("%s: %s", argumentType, stringValue));
                    } else {
                        res.add(String.format("%s: %s", argumentType, "unrecognized"));
                    }
                    break;
                case PORT:
                    if (intValAnnoMirror != null) {
                        String intValue =
                                AnnotationUtils.getElementValueArray(
                                                intValAnnoMirror, intValValueElement, Long.class)
                                        .get(0)
                                        .toString();
                        res.add(String.format("%s: %s", argumentType, intValue));
                    } else {
                        res.add(String.format("%s: %s", argumentType, "unrecognized"));
                    }
                    break;
                case SKIP:
                    break;
                default:
                    res.add(String.format("%s: %s", argumentType, "unrecognized"));
            }
        }
        return res;
    }
}
