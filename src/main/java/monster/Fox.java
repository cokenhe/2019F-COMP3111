package monster;


public class Fox extends Monster {
        //public Label Label = new Label("Fox");
        public Fox(int no_of_frame){
          reward = 1+no_of_frame/10;
          icon="fox.png";
          hp = 10 + no_of_frame/5;
          speed  = 2 + no_of_frame/7;    
        }


    }