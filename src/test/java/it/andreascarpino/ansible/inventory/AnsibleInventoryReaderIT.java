package it.andreascarpino.ansible.inventory;

import it.andreascarpino.ansible.inventory.type.AnsibleInventory;
import it.andreascarpino.ansible.inventory.util.AnsibleInventoryReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class AnsibleInventoryReaderIT {

    public static final String VAGRANT_INVENTORY_PATH = "src/test/resources/inventories/vagrant-inventory";
    public static final String DIGITALOCEAN_INVENTORY_PATH = "src/test/resources/inventories/digitalocean-inventory";

    @Test
    public void readVagrantInventoryFile() throws IOException {
        AnsibleInventory ansibleInventory =
                AnsibleInventoryReader.read(Paths.get(VAGRANT_INVENTORY_PATH).toAbsolutePath());
        Assert.assertEquals(0, ansibleInventory.getHosts().size());
        Assert.assertEquals(2, ansibleInventory.getGroup("lamp_www").getHosts().size());
        Assert.assertEquals(1, ansibleInventory.getGroup("lamp_memcached").getHosts().size());
        Assert.assertEquals("192.168.2.2", ansibleInventory.getGroup("lamp_varnish").getHost("192.168.2.2").getName());
        Assert.assertEquals("slave",
                ansibleInventory
                        .getGroup("lamp_db")
                        .getHost("192.168.2.6")
                        .getVariable("mysql_replication_role").getValue());
    }

    @Test
    public void readDigitaloceanInventoryFile() throws IOException {
        AnsibleInventory ansibleInventory =
                AnsibleInventoryReader.read(Paths.get(DIGITALOCEAN_INVENTORY_PATH).toAbsolutePath());
        Assert.assertEquals(0, ansibleInventory.getHosts().size());
        Assert.assertEquals(2, ansibleInventory.getGroup("lamp_www").getSubgroups().size());
    }
}
