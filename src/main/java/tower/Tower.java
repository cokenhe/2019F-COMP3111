package tower;

import helper.Location;
import monster.Monster;

    abstract public class Tower {
        
        protected int[] attackPower;
        protected int range;
        protected int[] upgradeCost;
        protected int level = 1;
        protected final int MAXLEVEL = 5;
        protected Location loc;

        /**
         * @return the attackPower
         */
        public int getAttackPower() {
            return attackPower[level-1];
        }

        /**
         * @return the range
         */
        public int getRange() {
            return range;
        }

        /**
         * @return the upgradeCost
         */
        public int getUpgradeCost() {
            return upgradeCost[level-1];
        }

        /**
         * @return the location
         */
        public Location getLocation() {
            return loc;
        }

        /**
         * Find the Monster nearest to End-Zone in attack range
         * @param monsters Array of the monster in Arena
         * @return 
         */
        public Monster findNearestMonster(Monster[] monsters, int size){

            int[] inRange = new int[monsters.length];
            int numOfInRange = 0;

            for (int i = 0; i < size; ++i){
                if (isInRange(monsters[i].getLocation()))
                    inRange[numOfInRange++] = i;
            }

            if (numOfInRange == 0) return null;
            
            if (numOfInRange > 1){
                int nearest = inRange[0];
                for (int j = 1; j < inRange.length; ++j){
                    double nearestDistance = Math.sqrt((440 - monsters[inRange[0]].getLocation().x) * (440 - monsters[inRange[0]].getLocation().x) + (0 - monsters[inRange[0]].getLocation().y) * (0 - monsters[inRange[0]].getLocation().y));
                    double newDistance = Math.sqrt((440 - monsters[inRange[j]].getLocation().x) * (440 - monsters[inRange[j]].getLocation().x) + (0 - monsters[inRange[j]].getLocation().y) * (0 - monsters[inRange[j]].getLocation().y));
                    if (nearestDistance > newDistance){
                        nearest = inRange[j];
                        nearestDistance = newDistance;
                    }
                }
                return monsters[nearest];
            } else
                return monsters[inRange[0]];
        }

        public void attack(Monster[] monsters, int size){
            Monster selectedMonster = findNearestMonster(monsters, size);
            if (selectedMonster != null)
                selectedMonster.reduceHP(attackPower[level-1]);
        }

        public abstract boolean isInRange(Location monsterLoc);
      
        public abstract void upgrade();

    }

  