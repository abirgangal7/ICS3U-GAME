import java.util.ArrayList;
import java.util.List;

public class Monster {
    private String name;
    private String description;
    private int hp;
    private int dmg;
    private List<Item> inventory;

    public Monster(String name, String description, int hp, int dmg, List<Item> inventory) {
        this.name = name;
        this.description = description;
        this.hp = hp;
        this.dmg = dmg;
        this.inventory = inventory;
    }

    public int getHP() {
        return hp;
    }

    public int getDMG() {
        return dmg;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void take_dmg(int dmg) {
        hp -= dmg;
    }

    public void die(Room room) {
        if (hp <= 0) {
            System.out.println("The " + name + " dies");

            for (Item item : inventory) {
                room.addItem(item);
            }

            room.removeMonster(this);
        }
    }
}
