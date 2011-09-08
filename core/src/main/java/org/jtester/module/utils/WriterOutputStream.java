/*
 * Copyright 2006-2008,  Unitils.org
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
 *
 * $Id$
 */
package org.jtester.module.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * @author Filip Neven
 * @author Tim Ducheyne
 */
public class WriterOutputStream extends OutputStream {

	private Writer writer;
	
	
	public WriterOutputStream(Writer writer) {
		this.writer = writer;
	}

	
	@Override
	public void write(int b) throws IOException {
		writer.write(b);
	}

}
