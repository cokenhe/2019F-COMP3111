package tower;

import helper.Location;
import monster.Monster;


    abstract public class CatapultTower extends Tower{
        
        public static final int BUILDCOST = 0;
        private int coolDownTime;
        private int coolDownCounter;
        private int minRange;
        private int maxRange;

        CatapultTower(int x, int y){
            attackPower = 1;
            minRange = 50;
            maxRange = 150;
            upgradeCost = 1;   
            loc = new Location(x,y);
        }

        public void coolDown(){
            coolDownCounter = coolDownTime;
        }

        /**
         * @return the minRange
         */
        public int getMinRange() {
            return minRange;
        }

        /**
         * @return the maxRange
         */
        public int getMaxRange() {
            return maxRange;
        }

        /**
         * @return the coolDownCounter
         */
        public int getCoolDownCounter() {
            return coolDownCounter;
        }

        /**
         * @return the coolDownTime
         */
        public int getCoolDownTime() {
            return coolDownTime;
        }
        
        @Override
        public void upgrade(){
            if (level >= 5) return;
            level++;
            coolDownTime--; 
        }

        @Override
        public boolean isInRange(Location monsterLoc) {
            double distance = Math.sqrt((loc.x - monsterLoc.x) * (loc.x - monsterLoc.x) + (loc.y - monsterLoc.y) * (loc.y - monsterLoc.y));
            return (distance < maxRange || distance > minRange);
        }

        @Override
        public Monster findNearestMonster(Monster[] monsters) {
            if (coolDownCounter == 0){
                Monster selectedMonster = super.findNearestMonster(monsters);
                if (selectedMonster != null)
                    coolDown();
                return selectedMonster;
            }
            else
                return null;
        }
    }

  