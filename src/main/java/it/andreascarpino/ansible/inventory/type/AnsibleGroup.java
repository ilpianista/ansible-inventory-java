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
public class AnsibleGroup {

	private String name;

	private Map<String, AnsibleHost> hosts;

	private Map<String, AnsibleGroup> subgroups;

	private Map<String, AnsibleVariable> variables;

	public AnsibleGroup(String name) {
		super();
		this.name = name;
		this.hosts = new HashMap<>();
		this.subgroups = new HashMap<>();
		this.variables = new HashMap<>();
	}

	public AnsibleGroup(String name, List<AnsibleHost> hosts) {
		this(name);

		if (hosts != null) {
			for (AnsibleHost h : hosts) {
				this.hosts.put(h.getName(), h);
			}
		}
	}

	public AnsibleGroup(String name, List<AnsibleHost> hosts, List<AnsibleVariable> variables) {
		this(name, hosts);

		if (variables != null) {
			for (AnsibleVariable v : variables) {
				this.variables.put(v.getName(), v);
			}
		}
	}

	public String getName() {
		return name;
	}

	public Collection<AnsibleHost> getHosts() {
		return this.hosts.values();
	}

	public Collection<AnsibleGroup> getSubgroups() {
		return this.subgroups.values();
	}

	public Collection<AnsibleVariable> getVariables() {
		return this.variables.values();
	}

	public void addHost(AnsibleHost host) {
		this.hosts.put(host.getName(), host);
	}

	public void addHosts(List<AnsibleHost> hosts) {
		for (AnsibleHost h : hosts) {
			addHost(h);
		}
	}

	public void addSubgroup(AnsibleGroup subgroup) {
		this.subgroups.put(subgroup.getName(), subgroup);
	}

	public void addVariable(AnsibleVariable variable) {
		this.variables.put(variable.getName(), variable);
	}

	public void addVariables(List<AnsibleVariable> variables) {
		for (AnsibleVariable v : variables) {
			addVariable(v);
		}
	}

	public AnsibleHost getHost(String host) {
		return this.hosts.get(host);
	}

	public AnsibleVariable getVariable(String variable) {
		return this.variables.get(variable);
	}

	public void removeHost(String host) {
		this.hosts.remove(host);
	}

	public void removeSubgroup(String subgroup) {
		this.subgroups.remove(subgroup);
	}

	public void removeVariable(String variable) {
		this.variables.remove(variable);
	}

	public void clear() {
		this.hosts.clear();
		this.subgroups.clear();
		this.variables.clear();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || (getClass() != o.getClass()))
			return false;

		AnsibleGroup group = (AnsibleGroup) o;

		return name.equals(group.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
