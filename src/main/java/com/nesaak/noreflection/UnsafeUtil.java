package com.nesaak.noreflection;

import com.nesaak.noreflection.access.DynamicCaller;
import com.nesaak.noreflection.access.FunctionalCaller;
import com.nesaak.noreflection.writer.MethodAccessWriter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Function;

public class UnsafeUtil {

    private static final Class UNSAFE_CLASS;
    private static final Object UNSAFE;

    private static final DynamicCaller DEFINE_ANONYMOUS;
    private static final DynamicCaller ALLOCATE;
    private static final DynamicCaller FIELD_OFFSET;
    private static final DynamicCaller STATIC_FIELD_OFFSET;
    private static final DynamicCaller PUT_FIELD;
    private static final DynamicCaller GET_FIELD;

    static {
        try {
            Class unsafeClass = Class.forName("sun.misc.Unsafe");
            UNSAFE_CLASS = unsafeClass;

            Field unsafeField = UNSAFE_CLASS.getDeclaredField("theUnsafe");
            if (!unsafeField.isAccessible()) unsafeField.setAccessible(true);
            UNSAFE = unsafeField.get(null);

            final Method DEFINE_ANONYMOUS_METHOD = UNSAFE_CLASS.getDeclaredMethod("defineAnonymousClass", Class.class, byte[].class, Object[].class);
            final Method ALLOCATE_METHOD = UNSAFE_CLASS.getDeclaredMethod("allocateInstance", Class.class);

            DEFINE_ANONYMOUS = new FunctionalCaller((Function) ALLOCATE_METHOD.invoke(UNSAFE, DEFINE_ANONYMOUS_METHOD.invoke(UNSAFE, DEFINE_ANONYMOUS_METHOD.getDeclaringClass(), new MethodAccessWriter(DEFINE_ANONYMOUS_METHOD).toByteArray(), null)));
            ALLOCATE = new FunctionalCaller((Function) ALLOCATE_METHOD.invoke(UNSAFE, DEFINE_ANONYMOUS_METHOD.invoke(UNSAFE, ALLOCATE_METHOD.getDeclaringClass(), new MethodAccessWriter(ALLOCATE_METHOD).toByteArray(), null)));

            NoReflection noReflection = new NoReflection();
            FIELD_OFFSET = noReflection.get(UNSAFE_CLASS.getDeclaredMethod("objectFieldOffset", Field.class));
            STATIC_FIELD_OFFSET = noReflection.get(UNSAFE_CLASS.getDeclaredMethod("staticFieldOffset", Field.class));
            PUT_FIELD = noReflection.get(UNSAFE_CLASS.getDeclaredMethod("putObject", Object.class, long.class, Object.class));
            GET_FIELD = noReflection.get(UNSAFE_CLASS.getDeclaredMethod("getObject", Object.class, long.class));

        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static Class defineAnonymous(Class clz, byte[] bytes) {
        return (Class) DEFINE_ANONYMOUS.call(UNSAFE, clz, bytes, null);
    }

    public static <T> T allocate(Class<T> clz) {
        return (T) ALLOCATE.call(UNSAFE, clz);
    }

    public static void putField(Object object, long offset, Object value) {
        PUT_FIELD.call(UNSAFE, object, offset, value);
    }

    public static Object getField(Object object, long offset) {
        return GET_FIELD.call(UNSAFE, object, offset);
    }

    public static long getFieldOffset(Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            return (long) STATIC_FIELD_OFFSET.call(UNSAFE, field);
        }
        return (long) FIELD_OFFSET.call(UNSAFE, field);
    }

}
