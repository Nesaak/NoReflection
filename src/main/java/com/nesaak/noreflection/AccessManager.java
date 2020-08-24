package com.nesaak.noreflection;

import com.nesaak.noreflection.writer.ConstructorAccessWriter;
import com.nesaak.noreflection.writer.FieldAccessWriter;
import com.nesaak.noreflection.writer.MethodAccessWriter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class AccessManager {

    private Map<Field, Function> getterAccess = new HashMap<>();
    private Map<Field, BiConsumer> setterAccess = new HashMap<>();
    private Map<Constructor, Function> constructorAccess = new HashMap<>();
    private Map<Method, Function> methodAccess = new HashMap<>();

    public Function getGetter(Field field) {
        Function function = getterAccess.get(field);
        if (function == null) {
            function = UnsafeUtil.allocate((Class<? extends Function>) UnsafeUtil.defineAnonymous(field.getDeclaringClass(), new FieldAccessWriter(field, true).toByteArray()));
            getterAccess.put(field, function);
        }
        return function;
    }

    public BiConsumer getSetter(Field field) {
        BiConsumer biConsumer = setterAccess.get(field);
        if (biConsumer == null) {
            biConsumer = UnsafeUtil.allocate((Class<? extends BiConsumer>) UnsafeUtil.defineAnonymous(field.getDeclaringClass(), new FieldAccessWriter(field, false).toByteArray()));
            setterAccess.put(field, biConsumer);
        }
        return biConsumer;
    }

    public Function getConstructor(Constructor constructor) {
        Function function = constructorAccess.get(constructor);
        if (function == null) {
            function = UnsafeUtil.allocate((Class<? extends Function>) UnsafeUtil.defineAnonymous(constructor.getDeclaringClass(), new ConstructorAccessWriter(constructor).toByteArray()));
            constructorAccess.put(constructor, function);
        }
        return function;
    }

    public Function getMethod(Method method) {
        Function function = methodAccess.get(method);
        if (function == null) {
            function = UnsafeUtil.allocate((Class<? extends Function>) UnsafeUtil.defineAnonymous(method.getDeclaringClass(), new MethodAccessWriter(method).toByteArray()));
            methodAccess.put(method, function);
        }
        return function;
    }

}
