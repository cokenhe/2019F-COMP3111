package sample;


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
        public int getHP(){
            return this.hp;
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
        
    }

  