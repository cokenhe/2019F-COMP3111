package tower;

import monster.Monster;


    abstract class Tower {
        
        protected int attackPower;
        protected int buildCost;
        protected int range;
        protected int upgradeCost;
        protected int location[];

        public int getAttackPower(){
            return this.attackPower;
        }

        public int getBuildCost(){
            return this.buildCost;
        }

        public int getRange(){
            return this.range;
        }

        public int getUpgradeCost(){
            return this.upgradeCost;
        }

        public int[] getLocation(){
            return this.location;
        }

        public abstract void attack(Monster m);

        public abstract void upgrade();

    }

  