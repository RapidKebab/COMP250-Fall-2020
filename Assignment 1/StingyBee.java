public class StingyBee extends HoneyBee{
    private int atkDmg;

    public StingyBee(Tile p, int dmg) {
        super(p, 10, 1);
        this.atkDmg = dmg;
    }

    public boolean takeAction() {
        Tile t = this.getPosition();
        if(!this.getPosition().isOnThePath())//&&getPos().isNest())
            return false; //FIX
        while(!t.isNest()){
            if(t.getHornet()!=null) {
                t.getHornet().takeDamage(this.atkDmg);
                return true;
            }
            t = t.towardTheNest();
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StingyBee stingyBee = (StingyBee) o;
        return atkDmg == stingyBee.atkDmg;
    }

//   @Override
//   public int hashCode() {
//       return 0;
//   }
}
