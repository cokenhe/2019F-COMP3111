package sample;


    public class Penguim extends Monster {
        
        Penguim(int no_of_frame){
            hp = 5*no_of_frame;
            speed  = speed + no_of_frame/10; //every 10 frame speed++ 
          }
        void replenish(){
            hp++;
        }

    }