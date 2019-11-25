package sample;


    public class Monster {
        
        protected int hp;
        protected int x;
        protected int y;
        protected int dir;
        protected int speed;
        protected int id;
        
        
        Monster(){
            id = 0;
            x=0;
            y=0;
            dir=1;
            hp=5;   
            speed =1; 
        }
        public void move(){
          
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
        
    }

  