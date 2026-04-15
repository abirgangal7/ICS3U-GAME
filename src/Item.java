public class Item {
    private String id;
    private String name;
    private String description;
    private String use;
    private int weight;

    public Item(String id, String name, String description, String use, int weight) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.use = use;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUse() {
        return use;
    }

    public int getWeight() {
        return weight;
    }
}
