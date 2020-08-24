package com.nesaak.noreflection.writer;

import com.nesaak.noreflection.Types;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.util.function.BiConsumer;

import static org.objectweb.asm.Opcodes.*;

public class FieldAccessWriter extends MemberAccessWriter {

    protected static final String BICONSUMER_TYPE = Type.getInternalName(BiConsumer.class);
    protected static final String BICONSUMER_METHOD_NAME = "accept";

    protected static final String GETTER_TYPE = "(L" + OBJECT_TYPE + ";)L" + OBJECT_TYPE + ";";
    protected static final String SETTER_TYPE = "(L" + OBJECT_TYPE + ";L" + OBJECT_TYPE + ";)V";

    protected Class type;

    public FieldAccessWriter(Field field, boolean getter) {
        super(field);
        type = field.getType();

        visit(V1_8, ACC_PUBLIC + ACC_FINAL + ACC_SYNTHETIC, generatedName, null, OBJECT_TYPE, new String[]{ getter ? FUNCTION_TYPE : BICONSUMER_TYPE });
        if (getter) generateGetter(); else generateSetter();
        visitEnd();
    }

    protected void generateGetter() {
        MethodVisitor mv = visitMethod(ACC_PUBLIC + ACC_FINAL, FUNCTION_METHOD_NAME, GETTER_TYPE, null, null);
        if (!isStatic) {
            mv.visitVarInsn(ALOAD, 1);
            mv.visitTypeInsn(CHECKCAST, declaringClassType);
        }
        mv.visitFieldInsn(isStatic ? GETSTATIC : GETFIELD, declaringClassType, name, Type.getDescriptor(type));
        if (type.isPrimitive()) Types.getPrimitive(type).toWrapper(mv);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    protected void generateSetter() {
        MethodVisitor mv = visitMethod(ACC_PUBLIC + ACC_FINAL, BICONSUMER_METHOD_NAME, SETTER_TYPE, null, null);
        if (!isStatic) {
            mv.visitVarInsn(ALOAD, 1);
            mv.visitTypeInsn(CHECKCAST, declaringClassType);
        }
        mv.visitVarInsn(ALOAD, 2);
        if (type.isPrimitive()) {
            Types.Primitive primitive = Types.getPrimitive(type);
            mv.visitTypeInsn(CHECKCAST, Type.getInternalName(primitive.getWrapper()));
            primitive.toPrimitive(mv);
        } else {
            mv.visitTypeInsn(CHECKCAST, Type.getInternalName(type));
        }
        mv.visitFieldInsn(isStatic ? PUTSTATIC : PUTFIELD, declaringClassType, name, Type.getDescriptor(type));
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

}
