package com.nesaak.noreflection;

import com.nesaak.noreflection.access.DynamicCaller;
import com.nesaak.noreflection.access.FieldAccess;
import com.nesaak.noreflection.access.FunctionalCaller;
import com.nesaak.noreflection.access.FunctionalFieldAccess;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NoReflection {

	private static final NoReflection SHARED_INSTANCE = new NoReflection();

	private final Map<Field, FieldAccess> CACHED_FIELD_ACCESSORS = new ConcurrentHashMap<>();
	private final Map<Constructor, DynamicCaller> CACHED_CONSTRUCTORS_CALLERS = new ConcurrentHashMap<>();
	private final Map<Method, DynamicCaller> CACHED_METHOD_CALLERS = new ConcurrentHashMap<>();

	public static NoReflection shared() {
		return SHARED_INSTANCE;
	}

	private final AccessManager manager = new AccessManager();

	public AccessManager getManager() {
		return manager;
	}

	public FieldAccess get(Field field) {
		FieldAccess ret = CACHED_FIELD_ACCESSORS.get(field);

		if (ret == null) {
			ret = new FunctionalFieldAccess(manager.getGetter(field), manager.getSetter(field));
			CACHED_FIELD_ACCESSORS.put(field, ret);
		}

		return ret;
	}

	public DynamicCaller get(Constructor constructor) {
		DynamicCaller ret = CACHED_CONSTRUCTORS_CALLERS.get(constructor);

		if (ret == null) {
			ret = new FunctionalCaller(manager.getConstructor(constructor));
			CACHED_CONSTRUCTORS_CALLERS.put(constructor, ret);
		}

		return ret;
	}

	public DynamicCaller get(Method method) {
		DynamicCaller ret = CACHED_METHOD_CALLERS.get(method);

		if (ret == null) {
			ret = new FunctionalCaller(manager.getMethod(method));
			CACHED_METHOD_CALLERS.put(method, ret);
		}

		return ret;
	}
}
