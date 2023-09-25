public class SwarmOfHornets {
    private Hornet[] hornets;
    private int size;

    public SwarmOfHornets(){
        hornets = new Hornet[0];
        size = hornets.length;
    }

    public int sizeOfSwarm(){
        return size;
    }

    public Hornet[] getHornets(){
        return this.hornets;
    }

    public Hornet getFirstHornet(){
        if(hornets.length<1)
            return null;
        else
            return this.hornets[0];
    }

    public void addHornet(Hornet h){
        size++;
        Hornet[] tempHornets = new Hornet[hornets.length+1];
        for(int i=0; i<hornets.length;i++) {
            tempHornets[i] = hornets[i];
        }
        hornets = tempHornets;
        hornets[hornets.length-1] = h;
        tempHornets = new Hornet[0];
    }

    public boolean removeHornet(Hornet h){
        size = size -1;
        boolean found = false;
        int location = 0;
        Hornet[] tempHornets;
        for(int i=0; i<this.hornets.length;i++){
            if(this.hornets[i] == h) {
                this.hornets[i] = null;
                location = i;
                found = true;
                break;
            }
        }

        if (found){
            tempHornets = new Hornet[hornets.length-1];
            int j=0;
            while(j < hornets.length){
                if(j < location){
                    tempHornets[j] = hornets[j];
                }
                //else
                if(j == location){
                    j++;
                }

                if(j > location){
                    if(j<hornets.length) {
                        tempHornets[j - 1] = hornets[j];
                    }
                }
                j++;
            }
            hornets = tempHornets;
        }

        tempHornets = new Hornet[0];
        return found;
    }
}
