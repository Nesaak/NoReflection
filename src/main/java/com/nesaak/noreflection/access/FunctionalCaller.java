package com.nesaak.noreflection.access;

import java.util.function.Function;

public class FunctionalCaller implements DynamicCaller {

    private final Function function;

    public FunctionalCaller(Function function) {
        this.function = function;
    }

    @Override
    public Object call(Object... parameters) {
        return function.apply(parameters);
    }
}
