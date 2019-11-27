package monster;


    public class Monster {
        
        protected int hp;
        protected int x;
        protected int y;
        protected int dir;
        protected int speed;

        Monster(){
            x=0;
            y=0;
            dir=1;
            hp=5;   
            speed =1; 
        }
        public void move(int MAX_V_NUM_GRID){ //need pass the MAX_V_NUM_GRID into this function
            if(dir==1){
                if (x == -1) {
                    return; 
                }
                if (x%2==1){
                    x++;
                    return;
                }
                if (y == MAX_V_NUM_GRID - 1){
                    x++;
                    dir=0;
                    return;
                }
                y++;
            }
            else{
                if (x%2==1){
                    x++;
                    return;
                }
                if (y == 0){
                    x++;
                    dir = 1;
                    return;
                }   
                y--;
            }
        }
        public int getHP(){
            return this.hp;
        }
        public void ReduceHP(){
            
        }
        public int getSpeed(){
            return this.speed;
        }
        public void updateXY(int x, int y){
            this.x=x;
            this.y=y;
        }
        public void setDir(int d){
            this.dir=d;
        }
        public int getX(){
            return this.x;
        }
        public int getY(){
            return this.y;
        }
    }

  