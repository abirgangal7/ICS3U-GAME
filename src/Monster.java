import java.util.ArrayList;
import java.util.List;

public class Monster {
    private String name;
    private String description;
    private int hp;
    private int mhp;
    private int dmg;
    private List<Item> inventory;

    public Monster(String name, String description, int hp, int dmg, List<Item> inventory) {
        this.name = name;
        this.description = description;
        this.hp = hp;
        this.mhp = hp;
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

    public void die(Room room, Player player) {
        if (hp <= 0) {
            System.out.println("The " + name + " dies.");
            if (room.getId().matches("cage"))
            {
               System.out.println("A pale pendant is on the floor. Pick it up and you are marked forever"); 
            }
            if (room.getId().matches("warden_office"))
            {
               System.out.println("The warden's key and a rusty sword has dropped from the Forgotten Warden"); 
            }
            if (room.getId().matches("octagon"))
            {
               System.out.println("The Pale Katana has dropped from the Pale Servant"); 
            }
            if (room.getId().matches("arena"))
            {
               System.out.println("The Greataxe of the tomb has dropped from the Juggernaut."); 
            }

            for (Item item : inventory) {
                room.addItem(item);
            }

            room.removeMonster(this);

            player.addScore(mhp);
            player.addMoney(2);
        }
    }
}
