package tower;

import monster.Monster;

    abstract public class Tower {
        
        protected int attackPower;
        protected int range;
        protected int upgradeCost;
        protected int level = 1;
        protected Location loc;

        public int getAttackPower(){
            return this.attackPower;
        }

        public int getRange(){
            return this.range;
        }

        public int getUpgradeCost(){
            return this.upgradeCost;
        }

        public Location getLocation(){
            return loc;
        }

        public abstract void attack(Monster monsters);

        public abstract void upgrade();

    }

  