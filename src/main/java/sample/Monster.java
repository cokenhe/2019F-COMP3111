package sample;


    public class Monster {
        
        private int hp;
        private int x;
        private int y;
        private int dir;
        
        
        Monster(){
            x=0;
            y=0;
            dir=1;
            hp=5;    
        }
        public int getHP(){
            return this.hp;
        }
        public void updateXY(int x, int y){
            this.x=x;
            this.y=y;
        }
        public void setDir(int d){
            this.dir=d;
        }
        
    }