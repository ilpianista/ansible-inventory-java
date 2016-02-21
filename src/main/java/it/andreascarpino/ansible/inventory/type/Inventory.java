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
package it.andreascarpino.ansible.inventory.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrea Scarpino
 */
public class Inventory {

    private Map<String, Host> hosts;

    private Map<String, Group> groups;

    public Inventory() {
        super();
        this.hosts = new HashMap<>();
        this.groups = new HashMap<>();
    }

    public Inventory(List<Group> groups) {
        this();

        for (Group g : groups) {
            this.groups.put(g.getName(), g);
        }
    }

    public Collection<Host> getHosts() {
        return this.hosts.values();
    }

    public Collection<Group> getGroups() {
        return this.groups.values();
    }

    public void addHost(Host host) {
        this.hosts.put(host.getName(), host);
    }

    public void addGroup(Group group) {
        this.groups.put(group.getName(), group);
    }

    public Host getHost(String host) {
        return this.hosts.get(host);
    }

    public Group getGroup(String group) {
        return this.groups.get(group);
    }

    public void removeHost(String host) {
        this.hosts.remove(host);
    }

    public void removeGroup(String group) {
        this.groups.remove(group);
    }

    public void clear() {
        this.hosts.clear();
        this.groups.clear();
    }
}
