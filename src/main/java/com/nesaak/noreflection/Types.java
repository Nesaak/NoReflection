package com.nesaak.noreflection;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;

public class Types {

    public static String getMethodDescriptor(Class returnType, Class[] parameters) {
        StringBuilder builder = new StringBuilder();
        builder.append("(");
        for (Class parameter : parameters) {
            builder.append(Type.getDescriptor(parameter));
        }
        builder.append(")");
        builder.append(Type.getDescriptor(returnType));
        return builder.toString();
    }

    public static Primitive getPrimitive(Class clz) {
        for (Primitive value : Primitive.values()) {
            if (value.getPrimitive() == clz || value.getWrapper() == clz) return value;
        }
        return null;
    }

    public enum Primitive {

        VOID(Void.class, "V"),
        BOOLEAN(Boolean.class, "Z"),
        CHARACTER(Character.class, "C"),
        BYTE(Byte.class, "B"),
        SHORT(Short.class, "S"),
        INTEGER(Integer.class, "I"),
        FLOAT(Float.class, "F"),
        LONG(Long.class, "J"),
        DOUBLE(Double.class, "D");

        private Class wrapper;
        private Class primitive;
        private String identifier;

        Primitive(Class wrapper, String identifier) {
            this.wrapper = wrapper;
            try {
                this.primitive = (Class) wrapper.getDeclaredField("TYPE").get(null);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
            this.identifier = identifier;
        }

        public Class getWrapper() {
            return wrapper;
        }

        public Class getPrimitive() {
            return primitive;
        }

        public String getIdentifier() {
            return identifier;
        }

        public void toPrimitive(MethodVisitor mv) {
            mv.visitMethodInsn(INVOKEVIRTUAL, Type.getInternalName(getWrapper()), getPrimitive().getName() + "Value", "()" + getIdentifier(), false);
        }

        public void toWrapper(MethodVisitor mv) {
            mv.visitMethodInsn(INVOKESTATIC, Type.getInternalName(getWrapper()), "valueOf", "(" + getIdentifier() + ")" + Type.getDescriptor(getWrapper()), false);
        }
    }
}
