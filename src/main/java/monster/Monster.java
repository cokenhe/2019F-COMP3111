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
        protected enum Direction {
            DOWNWARD(1), UPWARD(-1);
            private int value;
            Direction(int i) {this.value = i; }
            public int getValue() { return this.value; }
        }
        protected Direction dir = Direction.DOWNWARD;
        Monster(){
            location = new Location(20, 20);
            dir=Direction.DOWNWARD;
            hp=5;   
            speed =1; 
            alive = true;
            dying = false;
            reachEndZone=false;
            icon="";
        }
        public void move(){ 
            // When col is odd, move to right
            if (location.getGridX() % 2 == 1 || (dir == Direction.DOWNWARD && location.getGridY() == GameConfig.MAX_V_NUM_GRID - 1) || (dir == Direction.UPWARD && location.getGridY() == 0)){
                if (location.getGridX() + 1 >= GameConfig.MAX_H_NUM_GRID) { //monster reach end-zone, end game
                    this.reachEndZone = true;
                    return;
                }
                location.x += GameConfig.GRID_WIDTH;
                if (location.getGridY() == 0) 
                    dir = Direction.DOWNWARD;
                if (location.getGridY() == GameConfig.MAX_V_NUM_GRID - 1)
                    dir = Direction.UPWARD;
                return;
            }
            // Moving up / down
            location.y += dir.getValue() * GameConfig.GRID_HEIGHT;
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
        
    }

  