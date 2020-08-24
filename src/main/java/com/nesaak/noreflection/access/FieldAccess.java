package com.nesaak.noreflection.access;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class FieldAccess {

    private Function getter;
    private BiConsumer setter;

    public FieldAccess(Function getter, BiConsumer setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public Object get(Object obj) {
        return getter.apply(obj);
    }

    public void set(Object obj, Object value) {
        setter.accept(obj, value);
    }

}
