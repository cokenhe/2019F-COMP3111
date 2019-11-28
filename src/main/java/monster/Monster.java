package monster;

import helper.Location;;

public class Monster {
        
        protected int hp;
        protected int x;
        protected int y;
        protected int speed;   //how many times need to call move() per 1 frames
        protected String icon; //the name of the image 
        protected boolean alive;
        
        protected enum Direction {
            DOWNWARD(1), UPWARD(-1);
            private int value;
            Direction(int i) {this.value = i; }
            public int getValue() { return this.value; }
        }
        protected Direction dir = Direction.DOWNWARD;
        Monster(){
            x=0;
            y=0;
            dir=Direction.DOWNWARD;
            hp=5;   
            speed =1; 
            alive = true;
            icon="";
        }
        public void move(){ 
                
            // Initial position
            if (x == -1) {
                return; 
            }
            // When col is odd, move to right
            if (x % 2 == 1 || (dir == Direction.DOWNWARD && y == GameConfig.MAX_V_NUM_GRID - 1) || (dir == Direction.UPWARD && y == 0)){
                ++x;
                dir = (y == 0)? Direction.DOWNWARD : 
                    (y == GameConfig.MAX_V_NUM_GRID - 1) ? Direction.UPWARD   : dir;
                return;
            }
            // Moving up / down
            y += dir.getValue();
        }
        public int getHP(){
            return this.hp;
        }
        public void reduceHP(int dam){
            this.hp = this.hp-dam;
            if(this.hp<1)
                this.alive = false;         
        }
        public int getSpeed(){
            return this.speed;
        }
        public boolean isAlive(){
            return this.alive;
        }
        public String getIcon(){
            return this.icon;
        }
        public Location getLocation() {
            return new Location(0,0);
        }
        
    }

  