package com.nesaak.noreflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnsafeUtil {

    private static final Class UNSAFE_CLASS;
    private static final Object UNSAFE;

    private static final Method DEFINE_ANONYMOUS;
    private static final Method ALLOCATE;
    private static final Method FIELD_OFFSET;
    private static final Method PUT_FIELD;
    private static final Method GET_FIELD;

    static {
        try {
            Class unsafeClass = Class.forName("sun.misc.Unsafe");
            UNSAFE_CLASS = unsafeClass;

            Field unsafeField = UNSAFE_CLASS.getDeclaredField("theUnsafe");
            if (!unsafeField.isAccessible()) unsafeField.setAccessible(true);
            UNSAFE = unsafeField.get(null);

            DEFINE_ANONYMOUS = UNSAFE_CLASS.getDeclaredMethod("defineAnonymousClass", Class.class, byte[].class, Object[].class);
            ALLOCATE = UNSAFE_CLASS.getDeclaredMethod("allocateInstance", Class.class);
            FIELD_OFFSET = UNSAFE_CLASS.getDeclaredMethod("objectFieldOffset", Field.class);
            PUT_FIELD = UNSAFE_CLASS.getDeclaredMethod("putObject", Object.class, long.class, Object.class);
            GET_FIELD = UNSAFE_CLASS.getDeclaredMethod("getObject", Object.class, long.class);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static Class defineAnonymous(Class clz, byte[] bytes) {
        try {
            return (Class) DEFINE_ANONYMOUS.invoke(UNSAFE, clz, bytes, null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void putField(Field field, Object object, Object value) {
        try {
            long offset = (long) FIELD_OFFSET.invoke(UNSAFE, field);
            PUT_FIELD.invoke(UNSAFE, object, offset, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static Object getField(Field field, Object object, Object value) {
        try {
            long offset = (long) FIELD_OFFSET.invoke(UNSAFE, field);
            return GET_FIELD.invoke(UNSAFE, object, offset);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static <T> T allocate(Class<T> clz) {
        try {
            return (T) ALLOCATE.invoke(UNSAFE, clz);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
