package controller;

// MARK: javafx
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.fxml.FXML;    
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Console;
// MARK: java 
import java.util.Random;

// MARK: custom classes
import monster.*;
import helper.Location;
import helper.GameConfig;

public class MyController {
    @FXML
    private Button buttonNextFrame;
    
    @FXML
    private Button buttonSimulate;

    @FXML
    private Button buttonPlay;

    @FXML
    private AnchorPane paneArena;

    @FXML
    private Label labelBasicTower;

    @FXML
    private Label labelIceTower;

    @FXML
    private Label labelCatapult;

    @FXML
    private Label labelLaserTower;

    @FXML
    private Label labelMoneyAmount;

    @FXML
    private Label labelEnergyAmount;

    private static int number_of_frame = 0;
    private static int number_of_monster = 0;
    private static boolean isGameEnd = false;
    private static Random rand = new Random(System.currentTimeMillis());
    private static Monster monsters[] = new Monster[GameConfig.MAX_MONSTER_NUMBER];
    private static Alert endGameDialog = new Alert(AlertType.INFORMATION);
    private Label grids[][] = new Label[GameConfig.MAX_V_NUM_GRID][GameConfig.MAX_H_NUM_GRID]; //the grids on arena
    private int x = -1, y = 0;
    
    protected enum Direction { //copied to monster
        DOWNWARD(1), UPWARD(-1);
        private int value;
        Direction(int i) {this.value = i; }
        public int getValue() { return this.value; }
    }

    private Direction monsterDirection = Direction.DOWNWARD;

    /**
     * A dummy function to show how button click works
     */
    
    @FXML
    private void play() {
        System.out.println("Play button clicked");

        // Label newLabel = new Label();
        // newLabel.setLayoutX(GRID_WIDTH / 4);
        // newLabel.setLayoutY(GRID_WIDTH / 4);
        // newLabel.setMinWidth(GRID_WIDTH / 2);
        // newLabel.setMaxWidth(GRID_WIDTH / 2);
        // newLabel.setMinHeight(GRID_WIDTH / 2);
        // newLabel.setMaxHeight(GRID_WIDTH / 2);
        // newLabel.setStyle("-fx-border-color: black;");
        // newLabel.setText("*");
        // newLabel.setBackground(new Background(new BackgroundFill(Color.YELLOW,
        // CornerRadii.EMPTY, Insets.EMPTY)));
        // paneArena.getChildren().addAll(newLabel);
    }

    /**
     * A function that create the Arena
     */
    @FXML
    public void createArena() {
        if (grids[0][0] != null)
            return; //created already
        for (int i = 0; i < GameConfig.MAX_V_NUM_GRID; i++)
            for (int j = 0; j < GameConfig.MAX_H_NUM_GRID; j++) {
                Label newLabel = new Label();
                if (j % 2 == 0 || i == ((j + 1) / 2 % 2) * (GameConfig.MAX_V_NUM_GRID - 1))
                    newLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                else
                    newLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                newLabel.setLayoutX(j * GameConfig.GRID_WIDTH);
                newLabel.setLayoutY(i * GameConfig.GRID_HEIGHT);
                newLabel.setMinWidth(GameConfig.GRID_WIDTH);
                newLabel.setMaxWidth(GameConfig.GRID_WIDTH);
                newLabel.setMinHeight(GameConfig.GRID_HEIGHT);
                newLabel.setMaxHeight(GameConfig.GRID_HEIGHT);
                newLabel.setStyle("-fx-border-color: black;");
                grids[i][j] = newLabel;
                paneArena.getChildren().addAll(newLabel);
            }
        setStyle();
        setDragAndDrop();
    }

    @FXML
    private void nextFrame() {
        if(!isGameEnd){               //the game still not end
            offerResources();         
            cleanIcon();              //clear all monster's icon
            moveMonster();            //all existing monster move 
            generateMonster();        //gernerate monster every 3 frames
            iconUpdate();             //update icon of monster  
            checkEndGame();
        }
        else{
            endGameDialog.showAndWait().ifPresent((btnType) -> {    //show dialog to say game over
            });
        }
    }
    @FXML
    private void generateMonster(){
        if(number_of_frame++ % 3 == 0 && number_of_monster<= GameConfig.MAX_MONSTER_NUMBER ){ 
            switch(rand.nextInt(GameConfig.NO_OF_MONSTER_TYPE)){
                case 0:
                    monsters[number_of_monster++] = new Fox(number_of_frame);
                    break;
                case 1:
                    monsters[number_of_monster++] = new Unicorn(number_of_frame);
                    break;
                case 2:
                    monsters[number_of_monster++] = new Penguim(number_of_frame);
                    break;
            }
            if(rand.nextInt(100)%5==0){ //20% chance to generate an extra monster
                switch(rand.nextInt(GameConfig.NO_OF_MONSTER_TYPE)){
                    case 0:
                        monsters[number_of_monster++] = new Fox(number_of_frame);
                        break;
                    case 1:
                        monsters[number_of_monster++] = new Unicorn(number_of_frame);
                        break;
                    case 2:
                        monsters[number_of_monster++] = new Penguim(number_of_frame);
                        break;
                }   
            }
        }
    }
    
    /**
     * clean the icon of monster in the whole arena
     */
    @FXML
    private void cleanIcon(){
        for(int i=0;i<number_of_monster;++i){
            grids[monsters[i].getLocation().y][monsters[i].getLocation().x].setStyle("-fx-background-image:none; -fx-border-color: black;");
            grids[monsters[i].getLocation().y][monsters[i].getLocation().x].setTooltip(null);
        }
    }
    /**
     * all monster still alive will call move() acoording to its'speed times, the icon will not change yet.
     */
    @FXML
    private void moveMonster(){
        for(int i=0;i<number_of_monster;++i){
            if(monsters[i].isAlive()){
                for(int j=0;j<monsters[i].getSpeed();++j)
                    monsters[i].move();
            }       
        }
    }
    /**
     * update the icon of living monster in the whole arena
     */
    @FXML
    private void iconUpdate(){
        for(int i=0;i<number_of_monster;++i){
            if(monsters[i].isAlive()){
                grids[monsters[i].getLocation().y][monsters[i].getLocation().x].setStyle("-fx-background-image: url("+monsters[i].getIcon()+"); -fx-background-size:40px 40px;");
                grids[monsters[i].getLocation().y][monsters[i].getLocation().x].setTooltip(new Tooltip("HP: "+monsters[i].getHP()));                      
            }       
            if(monsters[i].isDying()&&!monsters[i].isAlive()){
                grids[monsters[i].getLocation().y][monsters[i].getLocation().x].setStyle("-fx-background-image: url("+monsters[i].getIcon()+"); -fx-background-size:40px 40px;");
                grids[monsters[i].getLocation().y][monsters[i].getLocation().x].setTooltip(new Tooltip("HP: "+monsters[i].getHP()));
                monsters[i].dead();
            }       
                            
        }
    }

    /**
     * all monster still alive will call move() acoording to its'speed times, the icon will not change yet.
     */
    @FXML
    private void checkEndGame(){
        for(int i=0;i<number_of_monster;++i){
            if(monsters[i].isReachEndZone()){
                endGameDialog.setTitle("The game is over");
                endGameDialog.setHeaderText("A monster reached the end-zone");
                endGameDialog.showAndWait().ifPresent((btnType) -> {
                });
                isGameEnd = true;
                System.out.println("Gameover");
            }       
        }
    }
    private void offerResources() {

    }

    /**
     * Setup the styles of UI elements
     */
    private void setStyle() {
        labelMoneyAmount.setTextFill(Color.ORANGE);
    }

    /**
     * A function that demo how drag and drop works
     */
    private void setDragAndDrop() {
        labelBasicTower.setOnDragDetected(new DragEventHandler(labelBasicTower));
        labelIceTower.setOnDragDetected(new DragEventHandler(labelIceTower));
        labelCatapult.setOnDragDetected(new DragEventHandler(labelCatapult));
        labelLaserTower.setOnDragDetected(new DragEventHandler(labelLaserTower));

        for (int i = 0; i < GameConfig.MAX_V_NUM_GRID; i++)
            for (int j = 0; j < GameConfig.MAX_H_NUM_GRID; j++) {
                Label target = grids[i][j];
                if (target.getBackground().getFills().get(0).getFill() == Color.WHITE) continue;

                target.setOnDragEntered((event) -> {
                    if (event.getGestureSource() != target && event.getDragboard().hasString()) 
                        target.setStyle("-fx-border-color: red;");
                    event.consume();
                });

                target.setOnDragOver((event) -> {
                    if (event.getGestureSource() != target && event.getDragboard().hasString()) 
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    event.consume();
                });

                target.setOnDragDropped(new DragDroppedEventHandler());

                target.setOnDragExited((event) -> {
                    target.setStyle("-fx-border-color: black;");
                    event.consume();
                });
            }
    }
}

class DragEventHandler implements EventHandler<MouseEvent> {
    private Label source;
    public DragEventHandler(Label e) {
        source = e;
    }
    @Override
    public void handle (MouseEvent event) {
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        Image image = new Image(source.getId() + ".png");
        content.putImage(image);
        content.putString(source.getId());
        db.setContent(content);

        event.consume();
    }
}

class DragDroppedEventHandler implements EventHandler<DragEvent> {
    @Override
    public void handle(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString() && ((Label)event.getGestureTarget()).getGraphic() == null) {
            Label target = (Label) event.getGestureTarget();
            // String style = String.format("-fx-background-image: url(\"%s\"); -fx-background-size:40px 40px;", db.getString() + ".png");
            target.setText("");
            // target.setStyle(style);
            
            Image image = new Image(db.getString() + ".png", 40, 40, true, true);
            target.setGraphic(new ImageView(image));
            target.setMaxSize(40.0, 40.0);

            target.setStyle("-fx-border-color: black;");

            // REMARK: should change to click event, for upgrade tower / destroy
            target.setOnDragDropped(null);
            target.setOnDragEntered(null);
            target.setOnDragOver(null);
            target.setOnDragExited(null);

            success = true;
        }
        event.setDropCompleted(success);
        event.consume();

    }
}
