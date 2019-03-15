/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2018 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.pnc.bacon.pnc;

import org.aesh.command.CommandDefinition;
import org.aesh.command.GroupCommandDefinition;
import org.jboss.pnc.bacon.common.SubCommandHelper;

@GroupCommandDefinition(
        name = "product",
        description = "Product",
        groupCommands = {
                Product.Create.class,
                Product.Get.class,
                Product.List.class,
                Product.Update.class
        })
public class Product extends SubCommandHelper {


    @CommandDefinition(name = "create", description = "Create a product")
    public class Create extends SubCommandHelper {

    }

    @CommandDefinition(name = "get", description = "Get products")
    public class Get extends SubCommandHelper {
    }

    @CommandDefinition(name = "list", description = "List products")
    public class List extends SubCommandHelper {
    }

    @CommandDefinition(name = "update", description = "Update a product")
    public class Update extends SubCommandHelper {
    }


}