package com.nesaak.noreflection.writer;

import org.objectweb.asm.Type;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.function.Function;

public class MemberAccessWriter extends ClassAccessWriter {

    protected static final String FUNCTION_TYPE = Type.getInternalName(Function.class);
    protected static final String FUNCTION_METHOD_NAME = "apply";

    protected String name;
    protected boolean isStatic;

    public MemberAccessWriter(Member member) {
        super(member.getDeclaringClass());
        name = member.getName();
        isStatic = Modifier.isStatic(member.getModifiers());
    }
}
