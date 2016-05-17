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

import it.andreascarpino.ansible.inventory.type.Group;
import it.andreascarpino.ansible.inventory.type.Host;
import it.andreascarpino.ansible.inventory.type.Inventory;
import it.andreascarpino.ansible.inventory.type.Variable;
import it.andreascarpino.ansible.inventory.util.InventoryWriter;

/**
 * @author Andrea Scarpino
 */
public class InventoryWriterTest {

	@Test
	public void testWriteHostAndGroups() {
		Inventory inventory = new Inventory();

		Host mail = new Host("mail.example.com");
		inventory.addHost(mail);

		Group webservers = new Group("webservers");
		inventory.addGroup(webservers);

		Host foo = new Host("foo.example.com");
		webservers.addHost(foo);

		Host bar = new Host("bar.example.com");
		webservers.addHost(bar);

		Group dbservers = new Group("dbservers");
		inventory.addGroup(dbservers);

		Host one = new Host("one.example.com");
		dbservers.addHost(one);

		Host two = new Host("two.example.com");
		dbservers.addHost(two);

		Host three = new Host("three.example.com");
		dbservers.addHost(three);

		String inventoryText = InventoryWriter.write(inventory);

		Assert.assertEquals(
				"mail.example.com\n[dbservers]\nthree.example.com\none.example.com\ntwo.example.com\n[webservers]\nfoo.example.com\nbar.example.com\n",
				inventoryText);
	}

	@Test
	public void testWriteHostVariables() {
		Inventory inventory = new Inventory();

		Group atlanta = new Group("atlanta");
		inventory.addGroup(atlanta);

		Host host1 = new Host("host1");
		atlanta.addHost(host1);

		Variable host1_http_port = new Variable("http_port", "80");
		host1.addVariable(host1_http_port);
		Variable host1_maxRequestsPerChild = new Variable("maxRequestsPerChild", "808");
		host1.addVariable(host1_maxRequestsPerChild);

		Host host2 = new Host("host2");
		atlanta.addHost(host2);

		Variable host2_http_port = new Variable("http_port", "303");
		host2.addVariable(host2_http_port);
		Variable host2_maxRequestsPerChild = new Variable("maxRequestsPerChild", "909");
		host2.addVariable(host2_maxRequestsPerChild);

		String inventoryText = InventoryWriter.write(inventory);

		Assert.assertEquals(
				"[atlanta]\nhost1 http_port=80 maxRequestsPerChild=808\nhost2 http_port=303 maxRequestsPerChild=909\n",
				inventoryText);
	}

	@Test
	public void testWriteGroupVariables() {
		Inventory inventory = new Inventory();

		Group atlanta = new Group("atlanta");
		inventory.addGroup(atlanta);

		Host host1 = new Host("host1");
		atlanta.addHost(host1);

		Host host2 = new Host("host2");
		atlanta.addHost(host2);

		Variable ntp_server = new Variable("ntp_server", "ntp.atlanta.example.com");
		atlanta.addVariable(ntp_server);
		Variable proxy = new Variable("proxy", "proxy.atlanta.example.com");
		atlanta.addVariable(proxy);

		String inventoryText = InventoryWriter.write(inventory);

		Assert.assertEquals(
				"[atlanta]\nhost1\nhost2\n[atlanta:vars]\nproxy=proxy.atlanta.example.com\nntp_server=ntp.atlanta.example.com\n",
				inventoryText);
	}

	@Test
	public void testWriteSubGroupsAndGroupsVars() {
		Inventory inventory = new Inventory();

		Group atlanta = new Group("atlanta");
		inventory.addGroup(atlanta);

		Host host1 = new Host("host1");
		atlanta.addHost(host1);

		Host host2 = new Host("host2");
		atlanta.addHost(host2);

		Group raleigh = new Group("raleigh");
		inventory.addGroup(raleigh);

		raleigh.addHost(host2);

		Host host3 = new Host("host3");
		raleigh.addHost(host3);

		Group southeast = new Group("southeast");
		southeast.addSubgroup(raleigh);
		southeast.addSubgroup(atlanta);
		inventory.addGroup(southeast);

		Variable some_server = new Variable("some_server", "foo.southeast.example.com");
		southeast.addVariable(some_server);
		Variable halon_system_timeout = new Variable("halon_system_timeout", "30");
		southeast.addVariable(halon_system_timeout);
		Variable self_destruct_countdown = new Variable("self_destruct_countdown", "60");
		southeast.addVariable(self_destruct_countdown);
		Variable escape_pods = new Variable("escape_pods", "2");
		southeast.addVariable(escape_pods);

		Group usa = new Group("usa");
		usa.addSubgroup(southeast);
		usa.addSubgroup(new Group("northeast"));
		usa.addSubgroup(new Group("southwest"));
		usa.addSubgroup(new Group("northwest"));
		inventory.addGroup(usa);

		String inventoryText = InventoryWriter.write(inventory);

		Assert.assertEquals(
				"[usa:children]\nsouthwest\nnorthwest\nnortheast\nsoutheast\n[atlanta]\nhost1\nhost2\n[southeast:children]\natlanta\nraleigh\n[southeast:vars]\nself_destruct_countdown=60\nhalon_system_timeout=30\nsome_server=foo.southeast.example.com\nescape_pods=2\n[raleigh]\nhost3\nhost2\n",
				inventoryText);
	}

}
