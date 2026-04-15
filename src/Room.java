import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private String id;
    private String name;
    private String description;
    private Map<String, String> exits; // direction → roomId
    private Map<String, String> lockedExits;
    private List<Item> items;
    private List<String> tags;

    public Room(String id, String name, String description, Map<String, String> exits, Map<String, String> lockedExits, List<Item> items, List<String> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.exits = exits;
        this.lockedExits = lockedExits != null ? lockedExits : new HashMap<>();
        this.items = items;
        this.tags = tags;
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

    public boolean isExitLocked(String direction) {
        return lockedExits.containsKey(direction);
    }

    public String getRequiredKeyId(String direction) {
        return lockedExits.get(direction);
    }

    public void unlockExit(String direction) {
        lockedExits.remove(direction);
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

    public String getLongDescription(Player player) {
        if ((tags.contains("light") && player.getTags().contains("light")) || !tags.contains("light")) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n" + name).append("\n");
            sb.append(description);

            if (!items.isEmpty()) {
                sb.append("\n").append("You see ");
                for (Item item : items) {
                    if (items.size() == 1) {
                        sb.append(item.getDescription().toLowerCase()).append(", ");
                    } else if (item == items.get(items.size() - 2)) {
                        sb.append(item.getDescription().toLowerCase()).append(", and ");
                    } else {
                        sb.append(item.getDescription().toLowerCase()).append(", ");
                    }
                }
                sb.setLength(sb.length() - 2);
                sb.append(".");
            }

            return sb.toString();
        } else {
            return "\nThe room is too dark to see in, perhaps you should find a light...";
        }
    }
}
