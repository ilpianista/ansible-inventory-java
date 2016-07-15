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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Andrea Scarpino
 */
public class Host {

	private String name;

	private Set<Variable> variables;

	public Host(String name) {
		super();
		this.name = name;
		this.variables = new HashSet<>();
	}

	public Host(String name, List<Variable> variables) {
		this(name);

		if (variables != null) {
			for (Variable v : variables) {
				this.variables.add(v);
			}
		}
	}

	public String getName() {
		return name;
	}

	public Set<Variable> getVariables() {
		return this.variables;
	}

	public void addVariable(Variable variable) {
		this.variables.add(variable);
	}

	public void addVariables(List<Variable> variables) {
		for (Variable v : variables) {
			addVariable(v);
		}
	}

	public Variable getVariable(String variableName) {
		for (Variable v : variables) {
			if (v.getName().equals(variableName)) {
				return v;
			}
		}

		return null;
	}

	public void removeVariable(String variableName) {
		this.variables.remove(variableName);
	}

	public void clear() {
		this.variables.clear();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || (getClass() != o.getClass()))
			return false;

		Host host = (Host) o;

		return name.equals(host.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
