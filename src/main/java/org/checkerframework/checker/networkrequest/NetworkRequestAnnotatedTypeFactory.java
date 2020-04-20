package org.checkerframework.checker.networkrequest;

import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import org.checkerframework.checker.networkrequest.qual.NetworkRequest;
import org.checkerframework.checker.networkrequest.qual.NetworkRequestDummy;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.type.QualifierHierarchy;
import org.checkerframework.framework.util.MultiGraphQualifierHierarchy;
import org.checkerframework.framework.util.MultiGraphQualifierHierarchy.MultiGraphFactory;
import org.checkerframework.javacutil.AnnotationUtils;

public class NetworkRequestAnnotatedTypeFactory extends BaseAnnotatedTypeFactory {

    public NetworkRequestAnnotatedTypeFactory(BaseTypeChecker checker) {
        super(checker);
        this.postInit();
    }

    @Override
    public QualifierHierarchy createQualifierHierarchy(MultiGraphFactory factory) {
        return new NetworkRequestQualifierHierarchy(factory);
    }

    protected static class NetworkRequestQualifierHierarchy extends MultiGraphQualifierHierarchy {
        public NetworkRequestQualifierHierarchy(MultiGraphFactory f) {
            super(f);
        }

        @Override
        public boolean isSubtype(AnnotationMirror subAnno, AnnotationMirror superAnno) {
            if (AnnotationUtils.areSameByClass(subAnno, NetworkRequestDummy.class)
                    && AnnotationUtils.areSameByClass(superAnno, NetworkRequestDummy.class)) {
                return true;
            } else if (AnnotationUtils.areSameByClass(subAnno, NetworkRequest.class)
                    && AnnotationUtils.areSameByClass(superAnno, NetworkRequest.class)) {
                return compareNetworkRequestTypes(subAnno, superAnno);
            } else {
                return false;
            }
        }

        private boolean compareNetworkRequestTypes(
                AnnotationMirror subAnno, AnnotationMirror superAnno) {
            List<String> subTypeValueList =
                    AnnotationUtils.getElementValueArray(subAnno, "value", String.class, true);
            List<String> superTypeValueList =
                    AnnotationUtils.getElementValueArray(superAnno, "value", String.class, true);
            return superTypeValueList.containsAll(subTypeValueList);
        }
    }
}
