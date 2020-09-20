package lab1.collection;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class TestHashTable {
    @BeforeClass
    public void setupData(ITestContext context) {
        context.setAttribute("bucketSize", 5);
    }

    @Test
    public void testHashTableInsert(ITestContext context) {
        HashTable hashTable = new HashTable((int) context.getAttribute("bucketSize"));
        HashNode node = new HashNode("Test1_key", "Test1_val");
        Assert.assertTrue(hashTable.hashInsert(node), "Пара ключ-значение не была добавлена в хеш-таблицу.");
    }

    @Test
    public void testHashTableSearch(ITestContext context) {
        HashTable hashTable = new HashTable((int) context.getAttribute("bucketSize"));
        HashNode node = new HashNode("Test2_key", "Test2_val");
        hashTable.hashInsert(node);
        Assert.assertEquals(hashTable.hashSearch(node.getKey()), node, "Пара ключ-значение не была найдена в хеш-таблице.");
    }

    @Test
    public void testHashTableSearchNegative(ITestContext context) {
        HashTable hashTable = new HashTable((int) context.getAttribute("bucketSize"));
        HashNode node = new HashNode("Test2_key", "Test2_val");
        Assert.assertNull(hashTable.hashSearch(node.getKey()), "Пара ключ-значение была найдена в хеш-таблице.");
    }

    @Test
    public void testHashTableDelete(ITestContext context) {
        HashTable hashTable = new HashTable((int) context.getAttribute("bucketSize"));
        HashNode node = new HashNode("Test3_key", "Test3_val");
        hashTable.hashInsert(node);
        Assert.assertTrue(hashTable.hashDelete(node), "Пара ключ-значение не была удалена из хеш-таблицы.");
    }

    @Test
    public void testHashTableDeleteNegative(ITestContext context) {
        HashTable hashTable = new HashTable((int) context.getAttribute("bucketSize"));
        HashNode node = new HashNode("Test3_key", "Test3_val");
        Assert.assertFalse(hashTable.hashDelete(node), "Пара ключ-значение была найдена в хеш-таблице.");
    }

    @Test
    public void testHashTableClear(ITestContext context) {
        HashTable hashTable = new HashTable((int) context.getAttribute("bucketSize"));
        HashNode node1 = new HashNode("Test4_key", "Test4_val");
        HashNode node2 = new HashNode("Test4_keyrfgf", "Test4_valdfdf");

        hashTable.hashInsert(node1);
        hashTable.hashInsert(node2);
        Assert.assertEquals(hashTable.hashSearch(node1.getKey()), node1);
        Assert.assertEquals(hashTable.hashSearch(node2.getKey()), node2);

        hashTable.hashClear();
        Assert.assertNull(hashTable.hashSearch(node1.getKey()), "Хеш-таблица не была очищена полностью.");
        Assert.assertNull(hashTable.hashSearch(node2.getKey()), "Хеш-таблица не была очищена полностью.");
    }
}
