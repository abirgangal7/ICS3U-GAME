public class Healable extends Item {
    private int healAmt;
    
    public Healable(String id, String name, String description, String use, int weight, int healAmt) {
        super(id, name, description, use, weight);
        this.healAmt = healAmt;
    }

    public int getAmt() {
        return healAmt;
    }
}
