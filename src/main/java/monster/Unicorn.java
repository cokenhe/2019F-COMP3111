package monster;


    public class Unicorn extends Monster {
                
        public Unicorn(int no_of_frame){
            icon="unicorn.png";
            hp = 10 + no_of_frame/5;
            speed  = 1 + no_of_frame/10; //every 10 frame speed++
        }

    }