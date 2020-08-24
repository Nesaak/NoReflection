package com.nesaak.noreflection.writer;

import com.nesaak.noreflection.Types;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;

import static org.objectweb.asm.Opcodes.*;

public class ConstructorAccessWriter extends MemberAccessWriter {

    protected static final String OBJECT_ARRAY_DESCRIPTOR = Type.getDescriptor(Object[].class);
    protected static final String CALLER_TYPE = "(L" + OBJECT_TYPE + ";)L" + OBJECT_TYPE + ";";

    protected Class[] parameters;

    public ConstructorAccessWriter(Constructor constructor) {
        super(constructor);
        parameters = constructor.getParameterTypes();

        visit(V1_8, ACC_PUBLIC + ACC_FINAL + ACC_SYNTHETIC, generatedName, null, OBJECT_TYPE, new String[]{ FUNCTION_TYPE });
        generateCall();
        visitEnd();
    }

    protected void generateCall() {
        MethodVisitor mv = visitMethod(ACC_PUBLIC + ACC_FINAL, FUNCTION_METHOD_NAME, CALLER_TYPE, null, null);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitTypeInsn(CHECKCAST, OBJECT_ARRAY_DESCRIPTOR);
        mv.visitVarInsn(ASTORE, 1);
        mv.visitTypeInsn(NEW, Type.getInternalName(declaringClass));
        mv.visitInsn(DUP);
        castParams(mv);
        mv.visitMethodInsn(INVOKESPECIAL, declaringClassType, CTOR_NAME, Types.getMethodDescriptor(Void.TYPE, parameters), false);
        mv.visitInsn(ARETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    protected void castParams(MethodVisitor mv) {
        int var = 0;
        for (Class parameter : parameters) {
            mv.visitVarInsn(ALOAD, 1);
            mv.visitLdcInsn(var++);
            mv.visitInsn(AALOAD);
            if (parameter.isPrimitive()) {
                Types.Primitive primitive = Types.getPrimitive(parameter);
                mv.visitTypeInsn(CHECKCAST, Type.getInternalName(primitive.getWrapper()));
                primitive.toPrimitive(mv);
            } else {
                mv.visitTypeInsn(CHECKCAST, Type.getInternalName(parameter));
            }
        }
    }

}
