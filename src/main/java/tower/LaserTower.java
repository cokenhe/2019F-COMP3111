package tower;

import monster.Monster;

    abstract class LaserTower extends Tower{
        
        public static final int BUILDCOST = 0;

        LaserTower(int x, int y){
            attackPower = 1;
            range = 65;
            upgradeCost = 1;   
            this.x = x;
            this.y= y;
        }

        public void attack(Monster m){

        }

        public void upgrade(){

        }

    }

  