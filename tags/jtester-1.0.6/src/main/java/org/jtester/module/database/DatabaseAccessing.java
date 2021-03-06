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
package org.jtester.module.database;

import java.util.Properties;

import org.jtester.module.database.support.SQLHandler;

/**
 * Task that can be performed on a database.
 *
 * @author Filip Neven
 * @author Tim Ducheyne
 */
public interface DatabaseAccessing {

    /**
     * Initializes the database operation class with the given {@link Properties}, {@link SQLHandler}.
     *
     * @param configuration The configuration, not null
     * @param sqlHandler    The sql handler, not null
     */
    public void init(Properties configuration, SQLHandler sqlHandler);

}
