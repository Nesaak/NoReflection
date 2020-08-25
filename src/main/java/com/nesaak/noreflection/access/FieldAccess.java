package com.nesaak.noreflection.access;

public interface FieldAccess {

    Object get(Object obj);

    void set(Object obj, Object value);

}
