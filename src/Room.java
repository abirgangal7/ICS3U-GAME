import java.util.List;
import java.util.Map;

public class Room {
    private String id;
    private String name;
    private String description;
    private Map<String, String> exits; // direction → roomId
    private List<Item> items;

    public Room(String id, String name, String description, Map<String, String> exits, List<Item> items) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.exits = exits;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortDescription() {
        return description;
    }

    public Map<String, String> getExits() {
        return exits;
    }

    public List<Item> getItems() {
        return items;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public String getLongDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        sb.append(description).append("\n");

        if (!items.isEmpty()) {
            sb.append("You see ");
            for (Item item : items) {
                if (items.size() == 1) {
                    sb.append(item.getDescription().toLowerCase()).append(", ");
                } else if (item == items.get(items.size() - 2)) {
                    sb.append(item.getDescription().toLowerCase()).append(", and ");
                } else {
                    sb.append(item.getDescription().toLowerCase()).append(", ");
                    
                }
            }
            // Remove trailing comma and space
            sb.setLength(sb.length() - 2);
            sb.append(".\n");
        }

        if (!exits.isEmpty()) {
            sb.append("Exits: ");
            for (String direction : exits.keySet()) {
                sb.append(direction).append(", ");
            }
            sb.setLength(sb.length() - 2);
            sb.append(".");
        }

        return sb.toString();
    }
}
