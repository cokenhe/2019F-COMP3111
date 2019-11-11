package sample;


    public class Fox {
        
        private String name;
        private int id;        
        
        Fox(){
            id=0;
            name = "MR. Fox";
        
        }
        Fox( String n, int iddd){
            name = n;   
            this.id = iddd;
            if(id>0){
                name = "asdasd";
            }
            if(id ==5){
                name = "ddd";
            }
        }
        public String getName(){
            return this.name;
        }
        public int getID(){
            return this.id;
        }
        
    }