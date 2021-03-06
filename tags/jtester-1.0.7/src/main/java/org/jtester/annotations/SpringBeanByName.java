/*
 * Copyright 2008,  Unitils.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jtester.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation can be used on fields, in order to inject a bean from a
 * spring <code>ApplicationContext</code>.<br>
 * 
 * The id of the bean in the application context is automatically derived from
 * the name of the field or the name attribute. <br>
 * 
 * 
 * An <code>ApplicationContext</code> has to be configured for this test using
 * the {@link SpringApplicationContext} annotation.
 * 
 */
@Target({ FIELD, METHOD })
@Retention(RUNTIME)
@SuppressWarnings("rawtypes")
public @interface SpringBeanByName {
	/**
	 * spring bean name
	 * 
	 * @return
	 */
	String value() default "";

	/**
	 * spring bean implement class
	 * 
	 * @return
	 */
	Class claz() default SpringBeanByName.class;
}
