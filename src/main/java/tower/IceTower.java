package tower;

import helper.Location;
import monster.Monster;

public class IceTower extends Tower{
    
    public static final int BUILDCOST = 0;

    public IceTower(int x, int y){
        attackPower = 1;
        range = 65;
        upgradeCost = 1;   
        loc = new Location(x,y);
    }

    public void attack(Monster monsters){

    }

    public void upgrade(){

    }

    @Override
    public boolean isInRange(Location monsterLoc) {
        // TODO Auto-generated method stub
        return false;
    }

}

  