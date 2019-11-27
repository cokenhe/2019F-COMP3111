package monster;


    public class Penguim extends Monster {
        
        public Penguim(int no_of_frame){
            icon="penguin.png";
            hp = 5*no_of_frame;
            speed  = speed + no_of_frame/10; //every 10 frame speed++ 
          }
        void replenish(){
            hp++;
        }

    }