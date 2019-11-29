package tower;

import helper.Location;
import monster.Monster;

public class IceTower extends Tower{
    
    public static final int BUILDCOST = 60;
    public static int[] slowDuration = {2, 2, 3, 3, 4};

    public IceTower(int x, int y){
        attackPower = new int[]{1, 1, 2, 2, 3};
        range = 65;
        upgradeCost = new int[]{20, 20, 30 ,30 ,30};   
        loc = new Location(x,y);
    }

    /**
     * @return the slowDuration
     */
    public static int[] getSlowDuration() {
        return slowDuration;
    }
    
    @Override
    public void upgrade(){
        if (level >= MAXLEVEL) return;
            level++;
    }

    @Override
    public boolean isInRange(Location monsterLoc) {
        double distance = Math.sqrt((loc.x - monsterLoc.x) * (loc.x - monsterLoc.x) + (loc.y - monsterLoc.y) * (loc.y - monsterLoc.y));
        return (distance <= range);
    }

}

  