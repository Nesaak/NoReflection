package com.nesaak.noreflection.access;

import java.util.function.Function;

public class FunctionCaller implements DynamicCaller {

    private Function function;

    public FunctionCaller(Function function) {
        this.function = function;
    }

    @Override
    public Object call(Object... parameters) {
        return function.apply(parameters);
    }
}
