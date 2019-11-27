package tower;

import monster.Monster;


    abstract class BasicTower extends Tower{

        public static final int BUILDCOST = 0;

        BasicTower(int x, int y){
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

  