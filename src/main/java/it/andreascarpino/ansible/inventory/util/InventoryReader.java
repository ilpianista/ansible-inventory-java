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
package it.andreascarpino.ansible.inventory.util;

import it.andreascarpino.ansible.inventory.type.Group;
import it.andreascarpino.ansible.inventory.type.Host;
import it.andreascarpino.ansible.inventory.type.Inventory;
import it.andreascarpino.ansible.inventory.type.Variable;

import java.util.StringTokenizer;

/**
 * @author Andrea Scarpino
 */
public class InventoryReader {

    private InventoryReader() {
    }

    public static Inventory read(String text) {
        final Inventory inventory = new Inventory();

        final StringTokenizer tokenizer = new StringTokenizer(text, " \t\n\r\f", true);

        Group group = null;
        Host host = null;
        boolean nextIsHost = false;
        while (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken();

            if (token.startsWith("[")) {
                group = new Group(token.replaceAll("^\\[", "").replaceAll("]$", ""));
                inventory.addGroup(group);
                nextIsHost = false;
            } else if ("\f".equals(token) || "\t".equals(token) || " ".equals(token) || "\r".equals(token)) {
                continue;
            } else if ("\n".equals(token)) {
                nextIsHost = true;
            } else if (!nextIsHost && token.contains("=")) {
                if (host != null) {
                    final String[] v = token.split("=");
                    host.addVariable(new Variable(v[0], v[1]));
                }
            } else if (nextIsHost && group != null) {
                host = new Host(token);
                group.addHost(host);
                nextIsHost = false;
            }
        }

        return inventory;
    }

}
