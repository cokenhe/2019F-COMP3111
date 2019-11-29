package tower;

import helper.Location;
import monster.Monster;

    public class LaserTower extends Tower{
        
        public static final int BUILDCOST = 0;
        public int energy = 100;
        public boolean isRecharging = false;
        public int[] consumeRate = {100, 100, 100, 50 ,50};

        public LaserTower(int x, int y){
            attackPower = 1;
            upgradeCost = 1;   
            loc = new Location(x,y);
        }

        public void recharge(){
            if (energy == 0)
                isRecharging = true;
            if (isRecharging)
                energy += 20;
        }

        @Override
        public void upgrade(){
            if (level >= 5) return;
            level++;
            attackPower++; 
        }

        @Override
        public boolean isInRange(Location monsterLoc) {
            return true;
        }

        @Override
        public Monster findNearestMonster(Monster[] monsters) {
            if (!isRecharging){
                Monster selectedMonster = super.findNearestMonster(monsters);
                if (selectedMonster != null)
                    energy -= consumeRate[level-1];
                return selectedMonster;
            }else{
                recharge();
                return null;
            }

        }
    }

  