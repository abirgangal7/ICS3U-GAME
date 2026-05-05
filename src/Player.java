import java.util.ArrayList;
import java.util.List;

public class Player {
    private String currentRoomId;
    private String prevRoomId;
    private int carry;
    private int carry_cap;
    private int dmg;
    private int hp;
    private int score;
    private int money;
    private boolean alive;
    private List<Item> inventory;
    private List<String> tags;

    public Player(String startingRoomId) {
        this.currentRoomId = startingRoomId;
        this.prevRoomId = startingRoomId;
        this.carry = 0;
        this.carry_cap = 60;
        this.dmg = 1;
        this.hp = 20;
        this.score = 0;
        this.money = 0;
        this.alive = true;
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
        currentRoomId = roomId;
    }

    public void setPrevRoomId(String roomId) {
        prevRoomId = roomId;
    }

    public int getCarry() {
        return carry;
    }

    public int getCarry_cap() {
        return carry_cap;
    }

    public int getScore() {
        return score;
    }

    public int getDmg() {
        return dmg;
    }

    public int getHp() {
        return hp;
    }

    public int getMoney() {
        return money;
    }

    public void addScore(int amt) {
        score += amt;
    }

    public void addMoney(int amt) {
        money += amt;
    }

    public String take_dmg(int dmg, boolean hit) {
        if (hit) {
            hp -= dmg;
            return "hit";
        } else {
            double dodge = Math.random();
        
            if (dodge < 0.8) {
                hp -= dmg;
                for (Item item : inventory) {
                    if (item instanceof Weapon w) {
                        if (w.getName().toLowerCase().contains("shield"))
                            hp += (int) dmg / 2;
                    }
                }
                return "hit";
            } else {
                return "dodge";
            }
        }
        
    }

    public boolean isAlive() {
        return alive;
    }

    public void die() {
        if (hp <= 0)
            alive = false;
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
