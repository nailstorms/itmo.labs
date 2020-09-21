package lab1.collection;

import org.testng.Assert;
import org.testng.annotations.Test;


public class TestHashNode {

    @Test
    public void testHashNodeGetKey() {
        HashNode node = new HashNode("Test1_key", "Test1_val");
        Assert.assertEquals(node.getKey(), "Test1_key", "Не был получен корректный ключ.");
    }

    @Test
    public void testHashNodeGetValue() {
        HashNode node = new HashNode("Test1_key", "Test1_val");
        Assert.assertEquals(node.getValue(), "Test1_val", "Не было получено корректное значение.");
    }

}
