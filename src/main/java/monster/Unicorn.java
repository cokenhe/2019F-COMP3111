package monster;


    public class Unicorn extends Monster {
                
        public Unicorn(int no_of_frame){
            icon="unicorn.png";
            reward = 1+no_of_frame/10; 
            hp = 13 + no_of_frame/3;
            speed  = 1 + no_of_frame/10; //every 10 frame speed++
        }

    }