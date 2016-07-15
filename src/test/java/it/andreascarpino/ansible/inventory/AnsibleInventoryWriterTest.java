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

import org.junit.Assert;
import org.junit.Test;

import it.andreascarpino.ansible.inventory.type.AnsibleGroup;
import it.andreascarpino.ansible.inventory.type.AnsibleHost;
import it.andreascarpino.ansible.inventory.type.AnsibleInventory;
import it.andreascarpino.ansible.inventory.type.AnsibleVariable;
import it.andreascarpino.ansible.inventory.util.AnsibleInventoryWriter;

/**
 * @author Andrea Scarpino
 */
public class AnsibleInventoryWriterTest {

	@Test
	public void testWriteHostAndGroups() {
		AnsibleInventory inventory = new AnsibleInventory();

		AnsibleHost mail = new AnsibleHost("mail.example.com");
		inventory.addHost(mail);

		AnsibleGroup webservers = new AnsibleGroup("webservers");
		inventory.addGroup(webservers);

		AnsibleHost foo = new AnsibleHost("foo.example.com");
		webservers.addHost(foo);

		AnsibleHost bar = new AnsibleHost("bar.example.com");
		webservers.addHost(bar);

		AnsibleGroup dbservers = new AnsibleGroup("dbservers");
		inventory.addGroup(dbservers);

		AnsibleHost one = new AnsibleHost("one.example.com");
		dbservers.addHost(one);

		AnsibleHost two = new AnsibleHost("two.example.com");
		dbservers.addHost(two);

		AnsibleHost three = new AnsibleHost("three.example.com");
		dbservers.addHost(three);

		String inventoryText = AnsibleInventoryWriter.write(inventory);

		Assert.assertEquals(
				"mail.example.com\n[dbservers]\nthree.example.com\none.example.com\ntwo.example.com\n[webservers]\nfoo.example.com\nbar.example.com\n",
				inventoryText);
	}

	@Test
	public void testWriteHostVariables() {
		AnsibleInventory inventory = new AnsibleInventory();

		AnsibleGroup atlanta = new AnsibleGroup("atlanta");
		inventory.addGroup(atlanta);

		AnsibleHost host1 = new AnsibleHost("host1");
		atlanta.addHost(host1);

		AnsibleVariable host1_http_port = new AnsibleVariable("http_port", "80");
		host1.addVariable(host1_http_port);
		AnsibleVariable host1_maxRequestsPerChild = new AnsibleVariable("maxRequestsPerChild", "808");
		host1.addVariable(host1_maxRequestsPerChild);

		AnsibleHost host2 = new AnsibleHost("host2");
		atlanta.addHost(host2);

		AnsibleVariable host2_http_port = new AnsibleVariable("http_port", "303");
		host2.addVariable(host2_http_port);
		AnsibleVariable host2_maxRequestsPerChild = new AnsibleVariable("maxRequestsPerChild", "909");
		host2.addVariable(host2_maxRequestsPerChild);

		String inventoryText = AnsibleInventoryWriter.write(inventory);

		Assert.assertEquals(
				"[atlanta]\nhost1 http_port=80 maxRequestsPerChild=808\nhost2 http_port=303 maxRequestsPerChild=909\n",
				inventoryText);
	}

	@Test
	public void testWriteGroupVariables() {
		AnsibleInventory inventory = new AnsibleInventory();

		AnsibleGroup atlanta = new AnsibleGroup("atlanta");
		inventory.addGroup(atlanta);

		AnsibleHost host1 = new AnsibleHost("host1");
		atlanta.addHost(host1);

		AnsibleHost host2 = new AnsibleHost("host2");
		atlanta.addHost(host2);

		AnsibleVariable ntp_server = new AnsibleVariable("ntp_server", "ntp.atlanta.example.com");
		atlanta.addVariable(ntp_server);
		AnsibleVariable proxy = new AnsibleVariable("proxy", "proxy.atlanta.example.com");
		atlanta.addVariable(proxy);

		String inventoryText = AnsibleInventoryWriter.write(inventory);

		Assert.assertEquals(
				"[atlanta]\nhost1\nhost2\n[atlanta:vars]\nproxy=proxy.atlanta.example.com\nntp_server=ntp.atlanta.example.com\n",
				inventoryText);
	}

	@Test
	public void testWriteSubGroupsAndGroupsVars() {
		AnsibleInventory inventory = new AnsibleInventory();

		AnsibleGroup atlanta = new AnsibleGroup("atlanta");
		inventory.addGroup(atlanta);

		AnsibleHost host1 = new AnsibleHost("host1");
		atlanta.addHost(host1);

		AnsibleHost host2 = new AnsibleHost("host2");
		atlanta.addHost(host2);

		AnsibleGroup raleigh = new AnsibleGroup("raleigh");
		inventory.addGroup(raleigh);

		raleigh.addHost(host2);

		AnsibleHost host3 = new AnsibleHost("host3");
		raleigh.addHost(host3);

		AnsibleGroup southeast = new AnsibleGroup("southeast");
		southeast.addSubgroup(raleigh);
		southeast.addSubgroup(atlanta);
		inventory.addGroup(southeast);

		AnsibleVariable some_server = new AnsibleVariable("some_server", "foo.southeast.example.com");
		southeast.addVariable(some_server);
		AnsibleVariable halon_system_timeout = new AnsibleVariable("halon_system_timeout", "30");
		southeast.addVariable(halon_system_timeout);
		AnsibleVariable self_destruct_countdown = new AnsibleVariable("self_destruct_countdown", "60");
		southeast.addVariable(self_destruct_countdown);
		AnsibleVariable escape_pods = new AnsibleVariable("escape_pods", "2");
		southeast.addVariable(escape_pods);

		AnsibleGroup usa = new AnsibleGroup("usa");
		usa.addSubgroup(southeast);
		usa.addSubgroup(new AnsibleGroup("northeast"));
		usa.addSubgroup(new AnsibleGroup("southwest"));
		usa.addSubgroup(new AnsibleGroup("northwest"));
		inventory.addGroup(usa);

		String inventoryText = AnsibleInventoryWriter.write(inventory);

		Assert.assertEquals(
				"[usa:children]\nsouthwest\nnorthwest\nnortheast\nsoutheast\n[atlanta]\nhost1\nhost2\n[southeast:children]\natlanta\nraleigh\n[southeast:vars]\nself_destruct_countdown=60\nhalon_system_timeout=30\nsome_server=foo.southeast.example.com\nescape_pods=2\n[raleigh]\nhost3\nhost2\n",
				inventoryText);
	}

}
