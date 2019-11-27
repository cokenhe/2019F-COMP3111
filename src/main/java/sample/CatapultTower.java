package sample;


    abstract class CatapultTower extends Tower{
        
        public static final int BUILDCOST = 0;
        private int CoolDownTime;

        CatapultTower(int x, int y){
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

  