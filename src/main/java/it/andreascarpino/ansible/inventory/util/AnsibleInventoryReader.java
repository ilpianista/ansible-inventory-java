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

import java.util.StringTokenizer;

import it.andreascarpino.ansible.inventory.type.AnsibleGroup;
import it.andreascarpino.ansible.inventory.type.AnsibleHost;
import it.andreascarpino.ansible.inventory.type.AnsibleInventory;
import it.andreascarpino.ansible.inventory.type.AnsibleVariable;

/**
 * @author Andrea Scarpino
 */
public class AnsibleInventoryReader {

	private AnsibleInventoryReader() {
	}

	public static AnsibleInventory read(String text) {
		final AnsibleInventory inventory = new AnsibleInventory();

		final StringTokenizer tokenizer = new StringTokenizer(text, " \t\n\r\f", true);

		AnsibleGroup group = null;
		AnsibleHost host = null;
		boolean skipComment = false;
		boolean isVarsBlock = false;
		boolean isChildrenBlock = false;
		while (tokenizer.hasMoreTokens()) {
			final String token = tokenizer.nextToken();

			// New line, reset the comment flag
			if ("\n".equals(token)) {
				skipComment = false;
				continue;
			}

			// We are still reading a comment line
			if (skipComment) {
				continue;
			}

			// Ignore separators
			if (" ".equals(token) || "\t".equals(token) || "\r".equals(token) || "\f".equals(token)) {
				continue;
			}

			// We are reading a comment
			if (token.startsWith(";") || token.startsWith("#")) {
				skipComment = true;
				continue;
			}

			if (token.startsWith("[")) {
				host = null;
				isChildrenBlock = false;
				isVarsBlock = false;

				String groupName = token.replaceAll("^\\[", "").replaceAll("]$", "");

				if (groupName.contains(":")) {
					final String[] g = groupName.split(":");

					groupName = g[0];

					if ("vars".equals(g[1])) {
						isVarsBlock = true;
						group = inventory.getGroup(groupName);
					} else if ("children".equals(g[1])) {
						isChildrenBlock = true;
						group = new AnsibleGroup(groupName);
						inventory.addGroup(group);
					}
				} else {
					group = new AnsibleGroup(groupName);
					inventory.addGroup(group);
				}
			} else if (token.contains("=")) {
				final String[] v = token.split("=");
				// Replace YAML backslashes escapes
				final AnsibleVariable variable = new AnsibleVariable(v[0], v[1].replace("\\\\", "\\"));

				if (host != null) {
					host.addVariable(variable);
				} else if (isVarsBlock && group != null) {
					for (AnsibleGroup s : group.getSubgroups()) {
						for (AnsibleHost h : s.getHosts()) {
							h.addVariable(variable);
						}
					}
					for (AnsibleHost h : group.getHosts()) {
						h.addVariable(variable);
					}
				}
			} else {
				if (group == null) {
					host = new AnsibleHost(token);
					inventory.addHost(host);
				} else if (isChildrenBlock) {
					final AnsibleGroup g = inventory.getGroup(token);
					if (g != null) {
						group.addSubgroup(g);
					} else {
						group.addSubgroup(new AnsibleGroup(token));
					}
				} else {
					host = new AnsibleHost(token);
					group.addHost(host);
				}
			}
		}

		return inventory;
	}

}
