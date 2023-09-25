public class Hornet extends Insect {
    private int atkDmg;

    public Hornet(Tile p, int hitpoints, int dmg) {
        super(p, hitpoints);
        this.atkDmg = dmg;
    }

    public boolean takeAction() {
        if(getPosition() != null) {
            if (getPosition().getBee() != null) {      //attack if possible
                getPosition().getBee().takeDamage(atkDmg);
                return true;
            } else if (getPosition().isHive()) {
                return false;
            } else {
                Tile sendhelp = getPosition();
                getPosition().removeInsect(this);
                setPosition(sendhelp.towardTheHive());
                getPosition().addInsect(this);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hornet)) return false;
        if (!super.equals(o)) return false;
        Hornet hornet = (Hornet) o;
        return atkDmg == hornet.atkDmg && hornet.getHealth() == this.getHealth();
    }

//    @Override
//    public int hashCode() {
//        return 0;
//    }
}
