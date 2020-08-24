package com.nesaak.noreflection;

import com.nesaak.noreflection.access.DynamicCaller;
import com.nesaak.noreflection.access.FieldAccess;
import com.nesaak.noreflection.access.FunctionCaller;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NoReflection {

    private static final NoReflection SHARED_INSTANCE = new NoReflection();

    public static NoReflection shared() {
        return SHARED_INSTANCE;
    }

    private AccessManager manager = new AccessManager();

    public AccessManager getManager() {
        return manager;
    }

    public FieldAccess get(Field field) {
        return new FieldAccess(manager.getGetter(field), manager.getSetter(field));
    }

    public DynamicCaller get(Constructor constructor) {
        return new FunctionCaller(manager.getConstructor(constructor));
    }

    public DynamicCaller get(Method method) {
        return new FunctionCaller(manager.getMethod(method));
    }
}
