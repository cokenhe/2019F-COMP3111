package monster;


    public class Unicorn extends Monster {
                
        Unicorn(int no_of_frame){
            hp = 10 + no_of_frame/5;
            speed  = 1 + no_of_frame/10; //every 10 frame speed++
        }

    }