package org.jtester.json.encoder.array;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.jtester.IAssertion;
import org.jtester.beans.User;
import org.jtester.json.encoder.JSONEncoder;
import org.jtester.json.helper.JSONFeature;
import org.junit.Test;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class CollectionEncoderTest implements IAssertion {
	@Test
	public void testEncode() throws Exception {
		List<User> users = new ArrayList<User>();
		users.add(User.newInstance(12, "darui.wu"));
		users.add(null);

		JSONEncoder encoder = JSONEncoder.get(users.getClass());
		want.object(encoder).clazIs(CollectionEncoder.class);

		encoder.setFeatures(JSONFeature.UseSingleQuote, JSONFeature.UnMarkClassFlag);
		StringWriter writer = new StringWriter();
		encoder.encode(users, writer, new ArrayList<String>());

		String json = writer.toString();
		String exp = "[{id:12,name:'darui.wu',first:null,last:null,age:0,salary:0,isFemale:false,address:null,addresses:null,phones:null,assistor:null},null]";
		want.string(json).eqIgnoreSpace(exp);
	}

	@Test
	public void testEncode_UserRef() throws Exception {
		List<User> users = new ArrayList<User>();
		User user = User.newInstance(12, "darui.wu");
		users.add(user);
		users.add(user);

		JSONEncoder encoder = JSONEncoder.get(users.getClass());
		want.object(encoder).clazIs(CollectionEncoder.class);

		encoder.setFeatures(JSONFeature.UseSingleQuote);
		StringWriter writer = new StringWriter();
		encoder.encode(users, writer, new ArrayList<String>());

		String json = writer.toString();
		want.string(json).contains("#class:'org.jtester.beans.User@").contains("#refer:@");
	}
}