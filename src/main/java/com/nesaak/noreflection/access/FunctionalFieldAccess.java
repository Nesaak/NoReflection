package com.nesaak.noreflection.access;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class FunctionalFieldAccess implements FieldAccess {

    private final Function getter;
    private final BiConsumer setter;

    public FunctionalFieldAccess(Function getter, BiConsumer setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public Object get(Object obj) {
        return getter.apply(obj);
    }

    @Override
    public void set(Object obj, Object value) {
        setter.accept(obj, value);
    }
}
