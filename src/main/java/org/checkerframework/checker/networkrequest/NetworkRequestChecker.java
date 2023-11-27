package org.checkerframework.checker.networkrequest;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.value.ValueChecker;

import java.util.Set;

/** This is the entry point for pluggable type-checking. */
public class NetworkRequestChecker extends BaseTypeChecker {

    @SuppressWarnings("NonApiType") // Until signature is adapted in the superclass
    @Override
    protected Set<Class<? extends BaseTypeChecker>> getImmediateSubcheckerClasses() {
        Set<Class<? extends BaseTypeChecker>> checkers = super.getImmediateSubcheckerClasses();
        checkers.add(ValueChecker.class);
        return checkers;
    }
}
