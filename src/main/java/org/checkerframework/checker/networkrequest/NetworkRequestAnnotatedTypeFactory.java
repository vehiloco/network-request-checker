package org.checkerframework.checker.networkrequest;

import java.lang.annotation.Annotation;
import java.util.Collection;
import org.checkerframework.common.basetype.BaseAnnotatedTypeFactory;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.type.QualifierHierarchy;
import org.checkerframework.framework.type.SubtypeIsSubsetQualifierHierarchy;

public class NetworkRequestAnnotatedTypeFactory extends BaseAnnotatedTypeFactory {

    public NetworkRequestAnnotatedTypeFactory(BaseTypeChecker checker) {
        super(checker);
        this.postInit();
    }

    @Override
    public QualifierHierarchy createQualifierHierarchy() {
        return new NetworkRequestQualifierHierarchy(this.getSupportedTypeQualifiers());
    }

    private final class NetworkRequestQualifierHierarchy extends SubtypeIsSubsetQualifierHierarchy {
        /**
         * Creates a NetworkRequestQualifierHierarchy from the given classes.
         *
         * @param qualifierClasses classes of annotations that are the qualifiers for this hierarchy
         */
        public NetworkRequestQualifierHierarchy(
                Collection<Class<? extends Annotation>> qualifierClasses) {
            super(qualifierClasses, processingEnv);
        }
    }
}
