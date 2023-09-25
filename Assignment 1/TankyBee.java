public class TankyBee extends HoneyBee{
    private int armor;
    private int atkDmg;

    public TankyBee(Tile p, int dmg, int def) {
        super(p, 30, 3);
        this.armor = def;
        this.atkDmg = dmg;
    }

    public boolean takeAction() {
        if (getPosition().getHornet() != null){
            getPosition().getHornet().takeDamage(this.atkDmg);
            return true;
        }
        return false;
    }

    public void takeDamage(int dmg){
        double multiplier = (100.0/(100.0+armor));
        int newdmg = (int) Math.floor(dmg * multiplier);
        //System.out.println((100/(100+armor)));
        super.takeDamage(newdmg);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TankyBee tankyBee = (TankyBee) o;
        return armor == tankyBee.armor &&
                atkDmg == tankyBee.atkDmg;
    }

//  @Override
//  public int hashCode() {
//      return Objects.hash(armor, atkDmg);
//  }
}
