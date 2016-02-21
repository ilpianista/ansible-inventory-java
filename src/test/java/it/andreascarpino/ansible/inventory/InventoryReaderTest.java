/*
 * The MIT License (MIT)
 * Copyright (c) 2016 Andrea Scarpino <me@andreascarpino.it>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package it.andreascarpino.ansible.inventory;

import it.andreascarpino.ansible.inventory.type.Group;
import it.andreascarpino.ansible.inventory.type.Host;
import it.andreascarpino.ansible.inventory.type.Inventory;
import it.andreascarpino.ansible.inventory.type.Variable;
import it.andreascarpino.ansible.inventory.util.InventoryReader;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrea Scarpino
 */
public class InventoryReaderTest {

    @Test
    public void testReadSimple() {
        String inventoryText = "[group1]\nhost1 var1=value1\n";

        Inventory inventory = InventoryReader.read(inventoryText);

        Assert.assertEquals(1, inventory.getGroups().size());
        Group group = inventory.getGroups().iterator().next();
        Assert.assertEquals("group1", group.getName());
        Assert.assertEquals(1, group.getHosts().size());

        Host host = group.getHosts().iterator().next();
        Assert.assertEquals("host1", host.getName());
        Assert.assertEquals(1, host.getVariables().size());

        Variable variable = host.getVariables().iterator().next();
        Assert.assertEquals("var1", variable.getName());
        Assert.assertEquals("value1", variable.getValue());

        inventoryText = "[group1]\nhost1 var1=value1 var2=value2 var3=value3\nhost2\nhost3 var1=value1";

        inventory = InventoryReader.read(inventoryText);
        group = inventory.getGroups().iterator().next();

        Assert.assertEquals(1, inventory.getGroups().size());
        Assert.assertEquals(3, group.getHosts().size());

        for (Host h : group.getHosts()) {
            if (h.getName().equals("host1")) {
                Assert.assertEquals(3, h.getVariables().size());
            } else if (h.getName().equals("host2")) {
                Assert.assertEquals(0, h.getVariables().size());
            } else if (h.getName().equals("host3")) {
                Assert.assertEquals(1, h.getVariables().size());
            }
        }
    }

    @Test
    public void testReadNoGroup() {
        final String inventoryText = "host1 var1=value1\n";

        Inventory inventory = InventoryReader.read(inventoryText);

        Assert.assertEquals(0, inventory.getGroups().size());
    }

    @Test
    public void testReadNoHosts() {
        final String inventoryText = "[group1]\n";

        Inventory inventory = InventoryReader.read(inventoryText);

        Assert.assertEquals(1, inventory.getGroups().size());
        Assert.assertEquals(0, inventory.getGroups().iterator().next().getHosts().size());
    }

}
