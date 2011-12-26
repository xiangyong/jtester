package org.jtester.json.decoder;

import java.util.HashMap;
import java.util.Map;

import org.jtester.IAssertion;
import org.jtester.beans.User;
import org.jtester.json.JSON;
import org.jtester.json.decoder.object.PoJoDecoder;
import org.jtester.json.helper.JSONArray;
import org.jtester.json.helper.JSONFeature;
import org.jtester.json.helper.JSONMap;
import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
public class ObjectDecoderTest implements IAssertion {

	@Test
	public void testDecode() {
		JSONMap json = new JSONMap() {
			{
				this.putJSON(JSONFeature.ClazzFlag, "org.jtester.beans.User@a123b");
				this.putJSON("name", "darui.wu");
			}
		};

		Map<String, Object> references = new HashMap<String, Object>();
		JSONDecoder decoder = new PoJoDecoder(User.class);
		User user = (User) decoder.decode(json, references);
		want.object(user).propertyEq("name", "darui.wu");
		want.map(references).hasEntry("@a123b", user);
	}

	/**
	 * json数组，数组的值指向同一个对象
	 */
	@Test
	public void testDecode_withRefObj() {
		User[] t = new User[2];
		System.out.println(t.getClass().getName());
		JSONMap json = new JSONMap() {
			{
				this.putJSON(JSONFeature.ClazzFlag, "[Lorg.jtester.beans.User;@01");
				this.putJSON(JSONFeature.ValueFlag, new JSONArray() {
					{
						this.add(new JSONMap() {
							{
								this.putJSON(JSONFeature.ClazzFlag, "org.jtester.beans.User@11");
								this.putJSON("name", "darui.wu");
							}
						});
						this.add(new JSONMap() {
							{
								this.putJSON(JSONFeature.ReferFlag, "@11");
							}
						});
					}
				});
			}
		};

		Map<String, Object> references = new HashMap<String, Object>();
		User[] users = JSON.toObject(json, references);
		want.array(users).sizeEq(2).propertyEq("name", new String[] { "darui.wu", "darui.wu" });
		want.object(users[0]).same(users[1]);
	}
}