package org.jtester.reflector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtester.IAssertion;
import org.jtester.beans.Address;
import org.jtester.beans.Employee;
import org.jtester.beans.User;
import org.jtester.helper.FieldHelper;
import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FieldAccessorTest_FromReflectorUtil implements IAssertion {
	@Test
	public void setFieldValue() {
		Employee employee = new Employee();
		want.object(employee.getName()).isNull();
		FieldHelper.setFieldValue(employee, "name", "my name");
		want.object(employee).propertyEq("name", "my name");
	}

	@Test(expected =  Exception.class )
	public void setFieldValue_exception() {
		Employee employee = new Employee();
		want.object(employee.getName()).isNull();
		FieldHelper.setFieldValue(employee, "name1", "my name");
	}

	@Test(expected =  RuntimeException.class )
	public void setFieldValue_AssertError() {
		FieldHelper.setFieldValue(null, "name1", "my name");
	}

	@Test
	public void getFieldValue() {
		Employee employee = new Employee();
		employee.setName("test name");
		Object name = FieldHelper.getFieldValue(employee, "name");
		want.object(name).clazIs(String.class);
		want.string(name.toString()).isEqualTo("test name");
	}

	@Test
	public void getFieldValue_exception() {
		try {
			Employee employee = new Employee();
			employee.setName("test name");
			FieldHelper.getFieldValue(employee, "name1");
			want.fail();
		} catch (Throwable e) {
			String message = e.getMessage();
			want.string(message).start("No such field:");
		}
	}

	@Test(expected =  RuntimeException.class )
	public void getFieldValue_AssertError() {
		FieldHelper.getFieldValue(null, "name1");
	}

	@Test(expected = RuntimeException.class )
	public void getFieldValue_AssertError2() {
		FieldHelper.getStaticFieldValue(Employee.class, "name1");
	}

	@Test
	public void testGetArrayItemProperty() {
		List<?> values = PropertyAccessor.getArrayItemProperty(
				Arrays.asList(new User("ddd", "eeee"), new User("ccc", "dddd")), "first");
		want.collection(values).reflectionEq(new String[] { "ddd", "ccc" });
	}
	/**
	 * 数组类型
	 */
	@Test
	public void testGetArrayItemProperty_Array() {
		List<?> values = PropertyAccessor.getArrayItemProperty(new User[] { new User("ddd", "eeee"),
				new User("ccc", "dddd") }, "first");
		want.collection(values).reflectionEq(new String[] { "ddd", "ccc" });
	}
	/**
	 * 单值
	 */
	@Test
	public void testGetArrayItemProperty_SingleValue() {
		List<?> values = PropertyAccessor.getArrayItemProperty(new User("ddd", "eeee"), "first");
		want.collection(values).reflectionEq(new String[] { "ddd" });
	}
	/**
	 * Map类型
	 */
	@Test
	public void testGetArrayItemProperty_Map() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("first", "ddd");

		List<?> values = PropertyAccessor.getArrayItemProperty(map, "first");
		want.collection(values).reflectionEq(new String[] { "ddd" });
	}
	/**
	 * Map类型_集合
	 */
	@Test
	public void testGetArrayItemProperty_MapList() {
		List list = new ArrayList();
		for (int i = 0; i < 2; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("first", "ddd");
			list.add(map);
		}
		List<?> values = PropertyAccessor.getArrayItemProperty(list, "first");
		want.collection(values).reflectionEq(new String[] { "ddd", "ddd" });
	}

	@Test
	public void testGetArrayItemProperties() {
		Object[][] values = PropertyAccessor.getArrayItemProperties(
				Arrays.asList(new User("ddd", "eeee"), new User("ccc", "dddd")), new String[] { "first", "last" });
		want.array(values).reflectionEq(new String[][] { { "ddd", "eeee" }, { "ccc", "dddd" } });
	}
	/**
	 * 数组类型
	 */
	@Test
	public void testGetArrayItemProperties_Array() {
		Object[][] values = PropertyAccessor.getArrayItemProperties(new User[] { new User("ddd", "eeee"),
				new User("ccc", "dddd") }, new String[] { "first", "last" });
		want.array(values).reflectionEq(new String[][] { { "ddd", "eeee" }, { "ccc", "dddd" } });
	}

	/**
	 * 单值
	 */
	@Test
	public void testGetArrayItemProperties_SingleValue() {
		Object[][] values = PropertyAccessor.getArrayItemProperties(new User("ddd", "eeee"), new String[] { "first",
				"last" });
		want.array(values).reflectionEq(new String[][] { { "ddd", "eeee" } });
	}

	/**
	 * Map类型
	 */
	@Test
	public void testGetArrayItemProperties_Map() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("first", "ddd");
		map.put("last", "eeee");

		Object[][] values = PropertyAccessor.getArrayItemProperties(map, new String[] { "first", "last" });
		want.array(values).reflectionEq(new String[][] { { "ddd", "eeee" } });
	}

	/**
	 * Map类型集合
	 */
	@Test
	public void testGetArrayItemProperties_MapList() {
		List list = new ArrayList();
		for (int i = 0; i < 2; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("first", "ddd");
			map.put("last", "eeee");
			list.add(map);
		}
		Object[][] values = PropertyAccessor.getArrayItemProperties(list, new String[] { "first", "last" });
		want.array(values).reflectionEq(new String[][] { { "ddd", "eeee" }, { "ddd", "eeee" } });
	}

	@Test(expected = RuntimeException.class)
	public void testGetProperty() {
		PropertyAccessor.getProperty(null, "dd");
	}

	/**
	 * 对象是Map
	 */
	@Test
	public void testGetProperty_Map() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("wikiName", "eeee");
		String value = (String) PropertyAccessor.getProperty(map, "wikiName");
		want.string(value).isEqualTo("eeee");

		try {
			PropertyAccessor.getProperty(map, "kkkk");
			want.fail();
		} catch (Exception e) {
			want.string(e.getMessage()).contains("no key[kkkk]");
		}
	}

	/**
	 * 没有这个属性
	 */
	@Test
	public void testGetProperty_NoProp() {
		try {
			PropertyAccessor.getProperty(item, "dde");
			want.fail();
		} catch (Throwable e) {
			String error = e.getMessage();
			want.string(error).start("No such field:");
		}
	}

	ForReflectUtil item = new ForReflectUtil("first name", "last name");

	/**
	 * Get方法可以取到值
	 */
	@Test
	public void testGetProperty_GetMethod() {
		String first = (String) PropertyAccessor.getProperty(item, "first");
		want.string(first).isEqualTo("first name");
	}

	/**
	 * 方法可以取到值_但方法在父类
	 */
	@Test
	public void testGetProperty_GetMethodInParentClass() {
		SubForReflectUtil item1 = new SubForReflectUtil("first name", "last name");
		String field = (String) PropertyAccessor.getProperty(item1, "myName");
		want.string(field).isEqualTo("first name,last name");
	}

	@Test
	public void testGetProperty_IsMethod() {
		boolean isMan = (Boolean) PropertyAccessor.getProperty(item, "man");
		want.bool(isMan).is(true);
	}

	/**
	 * 只能通过直接取字段
	 */
	@Test
	public void testGetProperty_NotGetMethod() {
		String field = (String) PropertyAccessor.getProperty(item, "noGetMethod");
		want.string(field).isEqualTo("no get method field");
	}

	/**
	 * Get方法有逻辑
	 */
	@Test
	public void testGetProperty_GetMethodHasLogical() {
		String field = (String) PropertyAccessor.getProperty(item, "myName");
		want.string(field).isEqualTo("first name,last name");
	}

	/**
	 * 只能通过直接取字段_且字段在父类中
	 */
	@Test
	public void testGetProperty_FieldInParentClass() {
		SubForReflectUtil item1 = new SubForReflectUtil("first name", "last name");
		String field = (String) PropertyAccessor.getProperty(item1, "noGetMethod");
		want.string(field).isEqualTo("no get method field");
	}

	public static class SubForReflectUtil extends ForReflectUtil {
		public SubForReflectUtil(String first, String last) {
			super(first, last);
		}
	}

	/**
	 * 单值对象_且属性值非集合
	 */
	@Test
	public void testGetArrayOrItemProperty_SingleValue_And_PropNotList() {
		Collection values = PropertyAccessor.getArrayOrItemProperty(new User("ddd", "ddd"), "first");
		want.collection(values).sizeEq(1).reflectionEq(new String[] { "ddd" });
	}

	/**
	 * 单值对象_且属性值为集合
	 */
	@Test
	public void testGetArrayOrItemProperty_SingleValue_PropIsList() {
		User user = new User("ddd", "ddd");
		user.setAddresses(Arrays.asList(new Address("aaa"), new Address("bbb")));
		Collection values = PropertyAccessor.getArrayOrItemProperty(user, "addresses");
		want.collection(values).sizeEq(2).reflectionEq(new Address[] { new Address("aaa"), new Address("bbb") });
	}
}