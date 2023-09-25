public abstract class Insect {
    private Tile pos;
    private int hp;
    public Insect(Tile p, int hitpoints){
        this.pos = p;
        this.hp = hitpoints;
        if(p.addInsect(this) == false){
            throw new IllegalArgumentException("Tile already full Failed on a " + this.getClass().toString()+".");
        }
        //else{
        //    System.out.println("Placed insect of type " + this.getClass().toString() + ".");
        //}
    }

    public void setPosition(Tile p){
        this.pos = p;
    }

    public final Tile getPosition() {
        return this.pos;
    }

    public final int getHealth() {
        return this.hp;
    }

    public void takeDamage(int dmg){
        if(this instanceof HoneyBee&&this.pos.isHive()){
            this.hp -= Math.floor(dmg*0.9);//Math.round(dmg*0.9);
            //System.out.println(Math.floor(dmg*0.9));
        }
        else
            this.hp -= dmg;

        if(hp<=0){
            pos.removeInsect(this);
        }
    }

    public abstract boolean takeAction();

    //public boolean equals(Object obj){
    //    return (obj.getClass() == this.getClass() && this.pos == (Insect)obj.getPos() && this.hp == (Insect)obj.getHP());
    //}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Insect insect = (Insect) o;
        return hp == insect.hp && pos.equals(insect.pos); //&& getClass() != insect.getClass();
    }



//    @Override
//    public int hashCode() {
//        return Objects.hash(pos, hp);
//    }
}
