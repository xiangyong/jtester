/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.jtester.hamcrest.mockito;

import java.io.Serializable;

import org.hamcrest.Matcher;

@SuppressWarnings({ "rawtypes" })
public class AnyVararg extends ArgumentMatcher implements VarargMatcher, Serializable {

	private static final long serialVersionUID = 1700721373094731555L;
	public static final Matcher ANY_VARARG = new AnyVararg();

	public boolean matches(Object arg) {
		return true;
	}
}