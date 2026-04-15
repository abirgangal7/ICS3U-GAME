public class Item {
    private String id;
    private String name;
    private String description;
    private String use;

    public Item(String id, String name, String description, String use) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.use = use;
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
}
