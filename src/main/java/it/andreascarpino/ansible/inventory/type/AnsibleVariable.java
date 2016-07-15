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

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ClassUtils;

/**
 * @author Andrea Scarpino
 * @see AnsibleConstants
 */
public class AnsibleVariable {

	private String name;

	private Object value;

	public AnsibleVariable(String name) {
		super();
		this.name = name;
	}

	public AnsibleVariable(String name, Object value) {
		this(name);
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnsibleVariable other = (AnsibleVariable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
    public String toString() {
        if (this.value == null) {
            return "";
        }

        return this.name + "=" + valueToString(this.value);
    }

    public String valueToString(Object value) {
        if (value == null) {
            return "";
        }

        final Class<?> vClass = value.getClass();

        String str;
        if (Collection.class.isAssignableFrom(vClass)) {
            str = listToString((Collection<?>) value);
        } else if (Map.class.isAssignableFrom(vClass)) {
            str = mapToString((Map<?, ?>) value);
        } else if (ClassUtils.isPrimitiveOrWrapper(vClass) || value instanceof String) {
            str = value.toString();
        } else {
            str = objToString(value);
        }

        // Use double backslash because of YAML syntax
        return str.replace("\\", "\\\\");
    }

    public String objToString(Object value) {
        final StringBuilder buf = new StringBuilder();

        for (Field f : value.getClass().getDeclaredFields()) {
            f.setAccessible(true);

            try {
                buf.append("'" + f.getName() + "': ");
                if (ClassUtils.isPrimitiveOrWrapper(value.getClass()) || value instanceof String) {
                    buf.append("'" + value + "'");
                } else {
                    buf.append(valueToString(f.get(value)));
                }
                buf.append(", ");
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // Silently ignore errors
                e.printStackTrace();
            }
        }
        buf.replace(buf.length() - 2, buf.length(), "");

        return buf.toString();
    }

    public String listToString(Collection<?> list) {
        final StringBuilder buf = new StringBuilder();
        buf.append("'[");

        if (!list.isEmpty()) {
            for (Object o : list) {
                if (ClassUtils.isPrimitiveOrWrapper(o.getClass()) || o instanceof String) {
                    buf.append("'" + o + "'");
                } else {
                    buf.append(valueToString(o));
                }
                buf.append(", ");
            }
            buf.replace(buf.length() - 2, buf.length(), "");
        }

        buf.append("]'");

        return buf.toString();
    }

    public String mapToString(Map<?, ?> map) {
        final StringBuilder buf = new StringBuilder();
        buf.append("{");

        if (!map.isEmpty()) {
            for (Entry<?, ?> o : map.entrySet()) {
                final Object v = o.getValue();

                if (v != null) {
                    buf.append("'" + o.getKey() + "': ");
                    if (ClassUtils.isPrimitiveOrWrapper(v.getClass()) || v instanceof String) {
                        buf.append("'" + v + "'");
                    } else {
                        buf.append(valueToString(v));
                    }
                    buf.append(", ");
                }
            }
            buf.replace(buf.length() - 2, buf.length(), "");
        }

        buf.append("}");

        return buf.toString();
    }

}
