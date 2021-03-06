package org.jtester.module.dbfit;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.DbFit.AUTO;
import org.jtester.testng.JTester;
import org.testng.annotations.Test;

/**
 * 非测试类，仅用于协助AutoFindDbFitTest测试类
 * 
 * @author darui.wudr
 * 
 */
@DbFit(when = "data/clazz.wiki", auto = AUTO.AUTO)
@Test(groups = "for-test")
public class ForAutoFindDbFit extends JTester {

	@Test(groups = "jtester")
	@DbFit(then = "data/ForAutoFindDbFit/test_classwiki自动加载.wiki")
	public void test_classwiki自动加载() {

	}

	@DbFit
	public void test1() {

	}

	public void test2() {

	}

	@DbFit
	public void test3() {

	}

	public void test4() {

	}

	@DbFit(when = { "1.when.wiki", "2.when.wiki" }, then = { "1.then.wiki" })
	public void test5() {

	}
}
