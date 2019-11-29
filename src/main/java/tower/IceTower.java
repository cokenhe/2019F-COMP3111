package tower;

import helper.Location;
import monster.Monster;

public class IceTower extends Tower{
    
    public static final int BUILDCOST = 0;
    public static int slowDuration = 2;

    public IceTower(int x, int y){
        attackPower = 1;
        range = 65;
        upgradeCost = 1;   
        loc = new Location(x,y);
    }

    @Override
    public void upgrade(){
        if (level >= 5) return;
            level++;
            slowDuration++; 
    }

    @Override
    public boolean isInRange(Location monsterLoc) {
        double distance = Math.sqrt((loc.x - monsterLoc.x) * (loc.x - monsterLoc.x) + (loc.y - monsterLoc.y) * (loc.y - monsterLoc.y));
        return (distance <= range);
    }

}

  