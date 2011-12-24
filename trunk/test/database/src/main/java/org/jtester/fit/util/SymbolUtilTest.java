package org.jtester.fit.util;

import java.util.HashMap;
import java.util.Map;

import org.jtester.IAssertion;
import org.jtester.IDatabase;
import org.jtester.IReflector;
import org.jtester.junit.DataFrom;
import org.junit.Test;

public class SymbolUtilTest implements IAssertion, IDatabase, IReflector {

	@Test
	public void testReplacedBySymbols() {
		SymbolUtil.setSymbol(new HashMap<String, String>() {
			private static final long serialVersionUID = -3309427544195335641L;
			{
				put("first", "myName");
				put("second", "myAddress");
			}
		});
		String text = SymbolUtil.replacedBySymbols("ddd @{first} ddd @{first} dddd@{second}");
		want.string(text).isEqualTo("ddd myName ddd myName ddddmyAddress");
	}

	@Test(expected = RuntimeException.class)
	public void testValidateSymbol2() {
		reflector.invokeStatic(SymbolUtil.class, "validateSymbol", "[", true);
	}

	@Test
	@DataFrom("validateSymbols")
	public void testValidateSymbol(String symbol, boolean valid) {
		boolean matched = (Boolean) reflector.invokeStatic(SymbolUtil.class, "validateSymbol", symbol, false);
		want.bool(matched).isEqualTo(valid);
	}

	public static Object[][] validateSymbols() {
		return new Object[][] { { "[", false },// <br>
				{ "adfd[dfdf]", true }, /** <br> **/
				{ "adfd", true }, /** <br> **/
				{ "[adfd]", false }, /** <br> **/
				{ "", false }, /** <br> **/
				{ "adfd[dafdas", false }, /** <br> **/
				{ "adfd[]", false }, /** <br> **/
				{ "[]", false }, /** <br> **/
				{ "_dfdf[#$]", false }
		/** <br> **/
		};
	}

	@Test
	@DataFrom("symbol_values")
	public void testGetSymbol(String symbol, Object value, String variable, Object actual) {
		SymbolUtil.cleanSymbols();
		SymbolUtil.setSymbol(symbol, value);
		Object o = SymbolUtil.getSymbol(variable);
		want.object(o).isEqualTo(actual);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object[][] symbol_values() {
		Map map = new HashMap() {
			private static final long serialVersionUID = -4509693391990485039L;
			{
				put("name", "darui.wu");
			}
		};
		PoJoExample pojo = new PoJoExample("darui.wu", null);
		return new Object[][] { { "myName", "darui.wu", "myName", "darui.wu" },// <br>
				{ "myName", "darui.wu", "other", null }, /** <br> **/
				{ "myMap", map, "myMap[name]", "darui.wu" }, /** <br> **/
				{ "myMap", map, "myMap[other]", null }, /** <br> **/
				{ "mypojo", pojo, "mypojo[name]", "darui.wu" }, /** <br> **/
				{ "mypojo", pojo, "mypojo[code]", null }, /** <br> **/
				{ "mypojo", pojo, "mypojo[nothing]", null }
		/** <br> **/
		};
	}

	public static class PoJoExample {
		String name;
		String code;

		public PoJoExample(String name, String code) {
			this.name = name;
			this.code = code;
		}
	}

	/**
	 * 当存入的变量为primitive类型时，要做相应的类型转换
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testReplacedBySymbols_PrimitiveType() {

		fit.setSymbols(new HashMap() {
			private static final long serialVersionUID = -1102901909374818760L;

			{
				put("myid", 123434L);
				put("double", 3434.334d);
			}
		});
		String text = SymbolUtil.replacedBySymbols("@{myid} @{double}");
		want.string(text).isEqualTo("123434 3434.334");
	}

	@Test
	@DataFrom("symboldata")
	public void testHasSymbol(String var, boolean isExisted) {
		SymbolUtil.cleanSymbols();
		SymbolUtil.setSymbol(new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;
			{
				this.put("mykey", "myvalue");
			}
		});
		boolean result = SymbolUtil.hasSymbol(var);
		want.bool(result).isEqualTo(isExisted);
	}

	public static Object[][] symboldata() {
		return new Object[][] { { "mykey", true },// <br>
				{ "date", true }, // <br>
				{ "unexistedvar", false } // <br>
		};
	}
}
