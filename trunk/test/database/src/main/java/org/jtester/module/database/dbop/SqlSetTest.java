package org.jtester.module.database.dbop;

import java.io.File;

import org.jtester.IAssertion;
import org.jtester.IDatabase;
import org.jtester.database.operator.SqlSet;
import org.junit.Test;

public class SqlSetTest implements IAssertion, IDatabase {

	@Test
	public void testReadFrom() {
		final String file = System.getProperty("user.dir")
				+ "/src/main/resources/org/jtester/module/database/dbop/sql-demo.sql";
		db.cleanTable("tdd_user").execute(new SqlSet() {
			{
				this.readFrom(new File(file));
			}
		});

		db.table("tdd_user").count().isEqualTo(2);
	}
}
