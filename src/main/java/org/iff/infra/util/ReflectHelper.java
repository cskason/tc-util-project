/*******************************************************************************
 * Copyright (c) 2013-2-14 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.infra.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * A Reflect helper provides a set of utility methods to process the java class.
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2013-2-14
 */
public class ReflectHelper {

	private static boolean CACHE = false;
	private static Multimap<Class<?>, Field> CACHE_FIELD = ArrayListMultimap.create();
	private static Multimap<Class<?>, Method> CACHE_METHOD = ArrayListMultimap.create();

	private ReflectHelper() {
	}

	public static boolean isCache() {
		return CACHE;
	}

	public static void setCache(boolean cache) {
		CACHE = cache;
	}

	public static Constructor<?> getConstructor(String className, String... parameterTypes) {
		try {
			Class<?> clazz = Class.forName(className);
			if (parameterTypes == null || parameterTypes.length < 1) {
				return clazz.getConstructor();
			}
			Constructor<?>[] constructors = clazz.getConstructors();
			for (Constructor<?> c : constructors) {
				Class<?>[] types = c.getParameterTypes();
				if (types.length != parameterTypes.length) {
					continue;
				}
				int i = 0;
				for (; i < types.length; i++) {
					if (!types[i].getName().equals(parameterTypes[i])) {
						break;
					}
				}
				if (i == types.length) {
					return c;
				}
			}
		} catch (Exception e) {
			Logger.error(FCS.get("[NoConstructorFound] className:{0}, parameterTypes:{1}", className, parameterTypes));
		}
		return null;
	}
}