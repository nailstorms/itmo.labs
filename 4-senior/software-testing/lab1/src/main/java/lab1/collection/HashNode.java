package lab1.collection;

class HashNode {
    private String key;
    private String value;

    public HashNode(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
