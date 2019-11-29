package tower;

import helper.Location;
import monster.Monster;


    public class CatapultTower extends Tower{
        
        public static final int BUILDCOST = 150;
        private int[] coolDownTime;
        private int coolDownCounter = 0;
        private int minRange;
        private int maxRange;

        public CatapultTower(int x, int y){
            attackPower = new int[]{8, 8, 9, 9, 10};
            upgradeCost = new int[]{50, 50, 70 ,70 ,100};   
            minRange = 50;
            maxRange = 150;
            coolDownTime = new int[]{3, 2, 2, 1, 0};
            loc = new Location(x,y);
        }

        public void coolDown(){
            coolDownCounter = coolDownTime[level-1];
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
            return coolDownTime[level-1];
        }

        @Override
        public void upgrade(){
            if (level >= 5) return;
            level++;
        }

        @Override
        public boolean isInRange(Location monsterLoc) {
            double distance = Math.sqrt((loc.x - monsterLoc.x) * (loc.x - monsterLoc.x) + (loc.y - monsterLoc.y) * (loc.y - monsterLoc.y));
            return (distance < maxRange || distance > minRange);
        }

        @Override
        public Monster findNearestMonster(Monster[] monsters, int size) {
            if (coolDownCounter == 0){
                Monster selectedMonster = super.findNearestMonster(monsters, size);
                if (selectedMonster != null)
                    coolDown();
                return selectedMonster;
            }
            else
                return null;
        }
    }

  