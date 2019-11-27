package monster;

import javafx.scene.control.Label;

public class Fox extends Monster {
        //public Label Label = new Label("Fox");
        Fox(int no_of_frame){
          hp = 10 + no_of_frame/5;
          speed  = 2 + no_of_frame/7; //every 7 frame speed++   
        }


    }