public abstract class HoneyBee extends Insect {
    private int foodCost;

    public HoneyBee(Tile p, int hitpoints, int cost) {
        super(p, hitpoints);
        this.foodCost = cost;
    }
    public int getCost(){
        return this.foodCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HoneyBee honeyBee = (HoneyBee) o;
        return foodCost == honeyBee.foodCost;
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(foodCost);
//    }
}
