package com.nesaak.noreflection.access;

import com.nesaak.noreflection.UnsafeUtil;

import java.lang.reflect.Field;

public class FinalFieldAccess implements FieldAccess {

    private final long offset;

    public FinalFieldAccess(Field field) {
        offset = UnsafeUtil.getFieldOffset(field);
    }

    @Override
    public Object get(Object obj) {
        return UnsafeUtil.getField(obj, offset);
    }

    @Override
    public void set(Object obj, Object value) {
        UnsafeUtil.putField(obj, offset, value);
    }
}
