import java.util.ArrayList;
import java.util.List;

public class Player {
    private String currentRoomId;
    private String prevRoomId;
    private int carry;
    private int carry_cap;
    private List<Item> inventory;
    private List<String> tags;

    public Player(String startingRoomId) {
        this.currentRoomId = startingRoomId;
        this.prevRoomId = startingRoomId;
        this.carry = 0;
        this.carry_cap = 60;
        this.inventory = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public String getCurrentRoomId() {
        return currentRoomId;
    }

    public String getPrevRoomId() {
        return prevRoomId;
    }

    public void setCurrentRoomId(String roomId) {
        this.currentRoomId = roomId;
    }

    public void setPrevRoomId(String roomId) {
        this.prevRoomId = roomId;
    }

    public int getCarry() {
        return carry;
    }

    public int getCarry_cap() {
        return carry_cap;
    }

    public void addItem(Item item) {
        inventory.add(item);
        carry += item.getWeight();
    }

    public void removeItem(Item item) {
        inventory.remove(item);
        carry -= item.getWeight();
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public List<String> getTags() {
        return tags;
    }
}
