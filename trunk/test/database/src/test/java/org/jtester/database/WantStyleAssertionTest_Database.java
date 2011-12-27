package org.jtester.database;

import org.jtester.IAssertion;
import org.jtester.IDatabase;
import org.jtester.annotations.DbFit;
import org.jtester.beans.DataMap;
import org.jtester.helper.ListHelper;
import org.junit.Test;

@SuppressWarnings({ "serial", "unchecked" })
public class WantStyleAssertionTest_Database implements IAssertion, IDatabase {

	@DbFit(when = "WantStyleAssertionTest_Database.testDatabase.when.wiki")
	@Test
	public void testDatabase() {
		db.query("select id,first_name,last_name from tdd_user").reflectionEqMap(ListHelper.toList(new DataMap() {
			{
				this.put("id", 1);
				this.put("first_name", "darui");
			}
		}, new DataMap() {
			{
				this.put("id", 2);
				this.put("last_name", "he");
			}
		}));
	}
}