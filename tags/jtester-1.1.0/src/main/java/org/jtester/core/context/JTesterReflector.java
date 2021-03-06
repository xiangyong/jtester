package org.jtester.core.context;

import java.lang.reflect.Field;

import org.jtester.bytecode.imposteriser.JTesterProxy;
import org.jtester.bytecode.reflector.FieldAccessor;
import org.jtester.bytecode.reflector.MethodAccessor;
import org.jtester.bytecode.reflector.helper.FieldHelper;
import org.jtester.bytecode.reflector.helper.MethodHelper;
import org.jtester.bytecode.reflector.impl.FieldAccessorImpl;
import org.jtester.bytecode.reflector.impl.MethodAccessorImpl;
import org.jtester.bytecode.reflector.impl.PropertiesAccessorImpl;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class JTesterReflector {
	/**
	 * 调用类为clazz,名称为method的方法
	 * 
	 * @param methodName
	 * @param paras
	 * @return
	 */
	public <T> T invoke(Class clazz, Object target, String method, Object... paras) {
		if (target == null) {
			throw new RuntimeException("the target object can't be null!");
		}
		Object _target = PropertiesAccessorImpl.getProxiedObject(target);
		Class[] paraClazes = MethodHelper.getParameterClazz(paras);
		MethodAccessor methodAccessor = new MethodAccessorImpl(clazz, method, paraClazes);
		Object result = methodAccessor.invoke(_target, paras);
		return (T) result;
	}

	public <T> T invoke(Object target, String method, Object... paras) {
		if (target == null) {
			throw new RuntimeException("the target object can't be null!");
		}
		if (target instanceof Class) {
			return (T) invokeStatic((Class) target, method, paras);
		} else {
			Object _target = PropertiesAccessorImpl.getProxiedObject(target);
			return (T) invoke(_target.getClass(), _target, method, paras);
		}
	}

	public <T> T invokeStatic(Class target, String method, Object... paras) {
		return (T) MethodHelper.invokeStatic(target, method, paras);
	}

	public void setField(Object target, String field, Object value) {
		if (target == null) {
			throw new RuntimeException("the target object can't be null!");
		}
		Object _target = PropertiesAccessorImpl.getProxiedObject(target);
		setField(_target.getClass(), _target, field, value);
	}

	public void setField(Class clazz, Object target, String field, Object value) {
		if (target == null) {
			throw new RuntimeException("the target object can't be null!");
		}
		Object _target = PropertiesAccessorImpl.getProxiedObject(target);
		FieldAccessor accessor = new FieldAccessorImpl(clazz, field);
		accessor.set(_target, value);
	}

	/**
	 * 返回target对象中名称为field的字段
	 * 
	 * @param <T>
	 *            字段类型
	 * @param target
	 *            字段所有者
	 * @param field
	 *            字段名称
	 * @return 字段值
	 */
	public <T> T getField(Object target, String field) {
		if (target == null) {
			throw new RuntimeException("the target object can't be null!");
		}
		Object _target = PropertiesAccessorImpl.getProxiedObject(target);
		return (T) getField(_target.getClass(), _target, field);
	}

	/**
	 * 返回target对象中名称为field的字段
	 * 
	 * @param clazz
	 *            定义字段的类（可能是target对象的父类或target自身)
	 * @param target
	 *            字段所有者实例
	 * @param field
	 *            字段值
	 * @return
	 */
	public <T> T getField(Class clazz, Object target, String field) {
		if (target == null) {
			throw new RuntimeException("the target object can't be null!");
		}
		Object _target = PropertiesAccessorImpl.getProxiedObject(target);
		FieldAccessor accessor = new FieldAccessorImpl(clazz, field);
		return (T) accessor.get(_target);
	}

	/**
	 * get class's static value<br>
	 * 获得clazz的静态变量值
	 * 
	 * @param <T>
	 * @param clazz
	 * @param field
	 * @return
	 */
	public <T> T getStaticField(Class clazz, String field) {
		FieldAccessor accessor = new FieldAccessorImpl(clazz, field);
		Object o = accessor.getStatic();
		return (T) o;
	}

	/**
	 * set class's static value<br>
	 * 设置clazz的静态变量值
	 * 
	 * @param clazz
	 * @param field
	 * @param value
	 */
	public void setStaticField(Class clazz, String field, Object value) {
		FieldAccessor accessor = new FieldAccessorImpl(clazz, field);
		accessor.setStatic(value);
	}

	/**
	 * 返回spring代理的目标对象
	 * 
	 * @param <T>
	 * @param source
	 * @return
	 */
	public <T> T getSpringAdvisedTarget(Object source) {
		Object target = PropertiesAccessorImpl.getProxiedObject(source);
		return (T) target;
	}

	/**
	 * 创建target对象field字段的代理实例<br>
	 * 用于运行时转移代理操作到字段对象上
	 * 
	 * @param <T>
	 * @param target
	 * @param field
	 * @return
	 */
	public <T> T createFieldProxy(Object target, String fieldname) {
		if (target == null) {
			throw new RuntimeException("can't get a field from null object.");
		}
		Field field = FieldHelper.getField(target.getClass(), fieldname);
		Object proxy = JTesterProxy.proxy(target, field);
		return (T) proxy;
	}
}
