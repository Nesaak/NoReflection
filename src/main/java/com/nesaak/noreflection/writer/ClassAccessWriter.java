package com.nesaak.noreflection.writer;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

public class ClassAccessWriter extends ClassWriter {

    protected static final String OBJECT_TYPE = Type.getInternalName(Object.class);
    protected static final String CTOR_NAME = "<init>";

    protected Class declaringClass;
    protected String declaringClassType;
    protected String generatedName;

    public ClassAccessWriter(Class declaringClass) {
        super(ClassWriter.COMPUTE_MAXS);
        this.declaringClass = declaringClass;
        this.declaringClassType = Type.getInternalName(declaringClass);
        this.generatedName = declaringClassType + "$Access";
    }

    protected void generateConstructor() {
        MethodVisitor ctor = visitMethod(ACC_PUBLIC, CTOR_NAME, "()V", null, null);
        ctor.visitCode();
        ctor.visitVarInsn(ALOAD, 0);
        ctor.visitMethodInsn(INVOKESPECIAL, OBJECT_TYPE, CTOR_NAME, "()V", false);
        ctor.visitInsn(RETURN);
        ctor.visitMaxs(0, 0);
        ctor.visitEnd();
    }
}
