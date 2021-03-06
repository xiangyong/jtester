/*
 * Copyright (c) 2006 Rick Mugridge, www.RimuResearch.com
 * Released under the terms of the GNU General Public License version 2 or later.
 * Written: 19/08/2006
 */

package fitlibrary.specify.exception;

public class ExceptionThrownByParse {
	public Value value() {
		return new Value();
	}

	public static class Value {
		public static Object parse(String s) {
			throw new ForcedException();
		}
	}
}
