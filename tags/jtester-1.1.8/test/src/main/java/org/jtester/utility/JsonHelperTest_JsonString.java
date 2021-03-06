package org.jtester.utility;

import java.util.HashMap;
import java.util.Map;

import org.jtester.fortest.beans.Manager;
import org.jtester.json.JSON;
import org.jtester.json.helper.JSONFeature;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

@Test(groups = { "jtester", "json" })
public class JsonHelperTest_JsonString extends JTester {
	@Test
	public void fromJson() {
		String filename = "classpath:org/jtester/utility/manager.json";
		Manager manager = JsonHelper.fromJsonFile(Manager.class, filename);
		want.object(manager).propertyEq("name", "Tony Tester").propertyEq("phoneNumber.number", "0571-88886666");
		want.date(manager.getDate()).isYear(2009).isMonth("08").isHour(16);
	}

	@Test
	public void testFromJson() {
		String json = "{\"name\": \"Banana\",\"id\": 123,\"price\": 23.0}";
		Product product = JSON.toObject(json, Product.class);
		want.object(product).propertyEq("name", "Banana").propertyEq("id", 123);
	}

	public static class Product {
		String name;
		int id;
		double price;

		public Product() {
			this.name = "myname";
			this.id = 100;
			this.price = 1333.00d;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	@Test
	public void testJsonPojo() {
		Product p = new Product();
		String jsonString = JSON.toJSON(p, JSONFeature.UnMarkClassFlag);
		want.string(jsonString).isEqualTo("{name:\"myname\",id:100,price:1333}");
	}

	@Test
	public void testJsonMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 123);
		map.put("name", "张三");

		String text = JSON.toJSON(map, JSONFeature.UnMarkClassFlag);
		want.string(text).isEqualTo("{\"name\":\"张三\",\"id\":123}");
	}
}
