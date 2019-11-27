package tower;

import helper.Location;
import monster.Monster;


    abstract public class CatapultTower extends Tower{
        
        public static final int BUILDCOST = 0;
        private int CoolDownTime;

        CatapultTower(int x, int y){
            attackPower = 1;
            range = 65;
            upgradeCost = 1;   
            loc = new Location(x,y);
        }

        public void attack(Monster monsters){

        }

        public void upgrade(){

        }

    }

  