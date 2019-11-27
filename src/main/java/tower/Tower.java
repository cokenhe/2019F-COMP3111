package tower;

import monster.Monster;


    abstract class Tower {
        
        protected int attackPower;
        protected int range;
        protected int upgradeCost;
        protected int x;
        protected int y;

        public int getAttackPower(){
            return this.attackPower;
        }

        public int getRange(){
            return this.range;
        }

        public int getUpgradeCost(){
            return this.upgradeCost;
        }

        public int[] getLocation(){
            return ;
        }

        public abstract void attack(Monster m);

        public abstract void upgrade();

    }

  