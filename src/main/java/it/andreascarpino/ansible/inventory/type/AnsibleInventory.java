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
public class AnsibleInventory {

	private Map<String, AnsibleHost> hosts;

	private Map<String, AnsibleGroup> groups;

	public AnsibleInventory() {
		super();
		this.hosts = new HashMap<>();
		this.groups = new HashMap<>();
	}

	public AnsibleInventory(List<AnsibleHost> hosts) {
		this();

		if (hosts != null) {
			for (AnsibleHost h : hosts) {
				this.hosts.put(h.getName(), h);
			}
		}
	}

	public AnsibleInventory(List<AnsibleHost> hosts, List<AnsibleGroup> groups) {
		this(hosts);

		if (groups != null) {
			for (AnsibleGroup g : groups) {
				this.groups.put(g.getName(), g);
			}
		}
	}

	public Collection<AnsibleHost> getHosts() {
		return this.hosts.values();
	}

	public Collection<AnsibleGroup> getGroups() {
		return this.groups.values();
	}

	public void addHost(AnsibleHost host) {
		this.hosts.put(host.getName(), host);
	}

	public void addGroup(AnsibleGroup group) {
		this.groups.put(group.getName(), group);
	}

	public AnsibleHost getHost(String host) {
		return this.hosts.get(host);
	}

	public AnsibleGroup getGroup(String group) {
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
