public class Weapon extends Item {
    private int dmg;
    
    public Weapon(String id, String name, String description, String use, int weight, int dmg) {
        super(id, name, description, use, weight);
        this.dmg = dmg;
    }

    public int getDmg() {
        return dmg;
    }
}
