package org.jtester.module.spring;

import org.jtester.testng.JTester;
import org.jtester.unitils.spring.AutoBeanInject;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

@SpringApplicationContext
@AutoBeanInject
@Test(groups = "jtester")
public class SpringBeanRegisterTest_Constructor2 extends JTester {

	@SpringBeanByName
	OuterClass outer;

	public void test_Claz没有默认构造函数() {
		Object inner1 = outer.getInner();
		want.object(inner1).isNull();

		Object inner2 = outer.getInner2();
		want.object(inner2).notNull();
	}

	public static class OuterClass {
		InnerClazz inner;

		InnerClazz2 inner2;

		public void setInner(InnerClazz inner) {
			this.inner = inner;
		}

		public InnerClazz getInner() {
			return inner;
		}

		public InnerClazz2 getInner2() {
			return inner2;
		}

		public void setInner2(InnerClazz2 inner2) {
			this.inner2 = inner2;
		}
	}

	public static class InnerClazz {
		public InnerClazz(String value) {

		}
	}

	public static class InnerClazz2 {

	}
}
