public class Tile {
    private int food;
    private boolean hive;
    private boolean nest;
    private boolean onPath;
    private Tile next2h;
    private Tile next2n;//MUST BE NULL IF AT END OR NOT ON PATH
    private HoneyBee honeyBee;
    private SwarmOfHornets hSwarm;

    public Tile(){
        this.hive = false;
        this.nest = false;
        this.onPath = false;
        this.next2h = null;
        this.next2n = null;
        this.food = 0;
        this.honeyBee = null;
        this.hSwarm = new SwarmOfHornets();
    }

    public Tile(int f, boolean bh, boolean hn, boolean op, Tile nh, Tile nn,  HoneyBee hb, SwarmOfHornets hs){
        this.hive = bh;
        this.nest = hn;
        this.onPath = op;
        this.next2h = nh;
        this.next2n = nn;
        this.food = f;
        this.honeyBee = hb;
        this.hSwarm = hs;
    }

    public boolean isHive(){
        return this.hive;
    }

    public boolean isNest(){
        return this.nest;
    }

    public void buildHive(){
        this.hive = true;
    }

    public void buildNest(){
        this.nest = true;
    }

    public boolean isOnThePath() {
        return this.onPath;
    }

    public Tile towardTheHive(){
        return this.next2h;
    }

    public Tile towardTheNest(){
        return this.next2n;
    }

    public void createPath(Tile uno, Tile dos){
        this.next2h = uno;
        this.next2n = dos;
        this.onPath = true;
    }

    public int collectFood(){
        int presence = this.food;
        this.food = 0;
        return presence;
    }

    public void storeFood(int f){
        this.food += f;
    }

    public HoneyBee getBee() {
        return this.honeyBee;
    }

    public Hornet getHornet(){
        return this.hSwarm.getFirstHornet();
    }

    public int getNumOfHornets(){
        return this.hSwarm.sizeOfSwarm();
    }

    public boolean addInsect(Insect insect){
        if (insect instanceof HoneyBee){
            if (!this.nest && this.honeyBee == null) {
                honeyBee = (HoneyBee) insect;
                insect.setPosition(this);
                return true;
            }
            else
                return false;
        }
        else if (insect instanceof Hornet){
            if (this.nest||this.hive||this.onPath){
                if(hSwarm != null) {
                    hSwarm.addHornet((Hornet) insect);
                    insect.setPosition(this);
                    return true;
                }
                else {
                    //System.out.println("uh, no swarm");
                    return false;
                }
            }
            else
                return false;
        }
        return false;
    }

    public boolean removeInsect(Insect insect){
        if (insect instanceof HoneyBee){
            this.honeyBee = null;
            insect.setPosition(null);
            return true;
        }
        if (insect instanceof Hornet){
            this.hSwarm.removeHornet((Hornet) insect);
            insect.setPosition(null);
            return true;
        }
        return false;
    }
}
