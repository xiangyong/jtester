package org.jtester.tutorial.spring;

import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.annotations.SpringBeanByType;
import org.jtester.testng.JTester;
import org.jtester.tutorial.biz.service.CustomerService;
import org.testng.annotations.Test;

@Test(description = "基本spring加载演示")
@SpringApplicationContext({ "file:./src/main/resources/spring/data-source.xml",
		"file:./src/main/resources/spring/biz-service.xml" })
public class SpringUsageDemo_FilePath extends JTester {
	/**
	 * 没有显式指定bean的名称，按照字段的名称寻找spring bean注入
	 */
	@SpringBeanByName
	CustomerService customerService;
	/**
	 * 显式的指定bean的名称
	 */
	@SpringBeanByName("customerService")
	CustomerService customerServiceByName;

	/**
	 * 按类型方式注入
	 */
	@SpringBeanByType
	CustomerService customerServiceByType;

	@Test(description = "演示spring容器正常启动，其bean被注入到测试类中")
	public void testSpringBeanByName() {
		want.object(customerService).notNull();
		String result = this.customerService.doNothing();
		want.string(result).start("this is a service");
	}

	@Test(description = "演示@SpringBeanByName @SpringBeanByType注入的功能")
	public void testSpringInject() {
		want.object(customerService).notNull();
		want.object(customerServiceByName).notNull();
		want.object(customerServiceByType).notNull().same(customerServiceByName).same(customerService);
	}

	@Test(description = "演示在程序中使用spring容器")
	public void testSpringContext() {
		Object bean = spring.getBean("customerService");
		want.object(bean).notNull().same(customerService);
	}
}
