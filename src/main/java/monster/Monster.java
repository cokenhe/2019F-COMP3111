package monster;

import helper.Location;
import helper.GameConfig;

public class Monster {
        
        protected int hp;
        protected int speed;   //how many times need to call move() per 1 frames
        protected String icon; //the name of the image 
        protected boolean alive;
        protected boolean dying; //the situation which alive == false in the current round, but still need to display collision.png 
        protected Location location;
        protected int reward;
        protected boolean reachEndZone;
        protected String status;
        protected enum Direction {
            DOWNWARD(1), UPWARD(-1);
            private int value;
            Direction(int i) {this.value = i; }
            public int getValue() { return this.value; }
        }
        protected Direction dir = Direction.DOWNWARD;
        Monster(){
            location = new Location(0,0);
            dir=Direction.DOWNWARD;
            hp=5;   
            speed =1; 
            alive = true;
            dying = false;
            reachEndZone=false;
            icon="";
        }
        public void move(){ 
                
            // Initial position
            if (location.x == -1) {
                return; 
            }
            // When col is odd, move to right
            if (location.x % 2 == 1 || (dir == Direction.DOWNWARD && location.y == GameConfig.MAX_V_NUM_GRID - 1) || (dir == Direction.UPWARD && location.y == 0)){
                if(location.x+1>=GameConfig.MAX_H_NUM_GRID){ //monster reach end-zone, end game
                    this.reachEndZone = true;
                    return;
                }
                ++location.x;
                dir = (location.y == 0)? Direction.DOWNWARD : 
                    (location.y == GameConfig.MAX_V_NUM_GRID - 1) ? Direction.UPWARD   : dir;
                return;
            }
            // Moving up / down
            location.y += dir.getValue();
        }
        public int getHP(){
            return this.hp;
        }
        public void reduceHP(int dam){
            this.hp = this.hp-dam;
            if(this.hp<1){
                this.alive = false;
                this.icon  = "collision.png";
                this.dying = true;
                this.speed = 0;
            }
                         
        }
        public boolean isDying(){
            return this.dying;
        }
        public void dead(){
            this.dying = false;
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
            return this.location;
        }
        public int getReward() {
            return this.reward;
        }
        public boolean isReachEndZone(){
            return this.reachEndZone;
        }
        public String getStatus(){
            return status;
        }        
        public void setStatus(String status){
            this.status = status;
        }        
    }

  