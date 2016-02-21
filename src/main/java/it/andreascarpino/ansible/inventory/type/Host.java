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
public class Host {

    private String name;

    private Map<String, Variable> variables;

    public Host(String name) {
        super();
        this.name = name;
        this.variables = new HashMap<String, Variable>();
    }

    public Host(String name, List<Variable> variables) {
        this(name);

        for (Variable v : variables) {
            this.variables.put(v.getName(), v);
        }
    }

    public String getName() {
        return name;
    }

    public Collection<Variable> getVariables() {
        return this.variables.values();
    }

    public void addVariable(Variable variable) {
        this.variables.put(variable.getName(), variable);
    }

    public void removeVariable(String variable) {
        this.variables.remove(variable);
    }

    public void clear() {
        this.variables.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Host host = (Host) o;

        return name.equals(host.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
