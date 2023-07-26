package org.checkerframework.checker.networkrequest;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.value.ValueChecker;

import java.util.LinkedHashSet;

/** This is the entry point for pluggable type-checking. */
public class NetworkRequestChecker extends BaseTypeChecker {

    @SuppressWarnings("NonApiType") // Until signature is adapted in the superclass
    @Override
    protected LinkedHashSet<Class<? extends BaseTypeChecker>> getImmediateSubcheckerClasses() {
        LinkedHashSet<Class<? extends BaseTypeChecker>> checkers =
                super.getImmediateSubcheckerClasses();
        checkers.add(ValueChecker.class);
        return checkers;
    }
}
