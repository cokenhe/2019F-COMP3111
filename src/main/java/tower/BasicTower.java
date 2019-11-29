package tower;

import helper.Location;
import monster.Monster;


    abstract public class BasicTower extends Tower{

        public static final int BUILDCOST = 0;

        BasicTower(int x, int y){
            attackPower = 1;
            range = 65;
            upgradeCost = 1;   
            loc = new Location(x,y);
        }
        public void upgrade(){
            if (level >= 5) return;
            level++;
            attackPower++; 
        }

        public boolean isInRange(Location monsterLoc){
            double distance = Math.sqrt((loc.x - monsterLoc.x) * (loc.x - monsterLoc.x) + (loc.y - monsterLoc.y) * (loc.y - monsterLoc.y));
            return (distance <= range);
        }
    }

  