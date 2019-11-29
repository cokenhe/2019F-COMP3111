package controller;

// MARK: javafx
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
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

// MARK: java 
import java.util.Random;

// MARK: custom classes
import monster.*;
import tower.*;
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
    private Button buttonUpgrade;

    @FXML
    private Button buttonDestroy;

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

    private int selectedX, selectedY;
    private Tower selectedTower;

    private static int number_of_frame = 0;
    private static int number_of_monster = 0;
    private static int number_of_tower = 0;
    private static boolean isGameEnd = false;
    private static Random rand = new Random(System.currentTimeMillis());
    private static Monster monsters[] = new Monster[GameConfig.MAX_MONSTER_NUMBER];
    private static Tower towers[] = new Tower[GameConfig.MAX_TOWER_NUMBER];
    
    private static Alert endGameDialog = new Alert(AlertType.INFORMATION);
    private Label grids[][] = new Label[GameConfig.MAX_V_NUM_GRID][GameConfig.MAX_H_NUM_GRID]; //the grids on arena

    /**
     * A dummy function to show how button click works
     */
    
    @FXML
    private void play() {
        System.out.println("Play button clicked");
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
                
                newLabel.setId(String.format("%d,%d", i, j));
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
        cancelAction();               // de-select tower
        if(!isGameEnd){               // the game still not end
            updateResources(150 + number_of_frame * 30);       // give money to player every frame     
            cleanIcon();              // clear all monster's icon
            moveMonster();            // all existing monster move 
            fireAttack();             // tower fire to monster
            generateMonster();        // gernerate monster every 3 frames
            iconUpdate();             // update icon of monster  
            checkEndGame();
        }
        else
            endGameDialog.showAndWait().ifPresent((btnType) -> {});    //show dialog to say game over   
    }

    @FXML 
    private void upgradeTower() {
        int money = Integer.parseInt(labelMoneyAmount.getText());
        if (money < selectedTower.getUpgradeCost()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Not enough money");
            alert.showAndWait().ifPresent((btnType) -> {});
            return;
        }
        
        updateResources(-this.selectedTower.getUpgradeCost());
        this.selectedTower.upgrade();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Infomation");
        alert.setHeaderText("Tower upgrade successfully");
        alert.showAndWait().ifPresent((btnType) -> {});

        grids[selectedY][selectedX].setTooltip(new Tooltip(
            "Attack Power:\t" + selectedTower.getAttackPower() + "\n" +
            "Attack Range:\t" + selectedTower.getRange() + "\n" +
            "Upgrade Cost:\t$" + selectedTower.getUpgradeCost()
        ));
    }

    @FXML 
    private void destroyTower() {
        boolean found = false;
        for (int i = 0; i < number_of_tower; i++) {
            if (towers[i] == selectedTower) {
                number_of_tower -= 1;
                found = true;
            }

            if (found) towers[i] = towers[i + 1];
        }              
                
        grids[selectedY][selectedX].setGraphic(null);
        grids[selectedY][selectedX].setOnMouseClicked(null);
        setHandler(selectedX, selectedY);
        cancelAction();
    }

    @FXML
    private void cancelAction() {
        buttonUpgrade.setDisable(true);
        buttonDestroy.setDisable(true);
        grids[selectedY][selectedX].setStyle("-fx-border-color: black;");
        toggleFireRange(false);
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
            Location monster = monsters[i].getLocation();
            int x = monster.getGridX();
            int y = monster.getGridY();
            
            // grids[y][x].setStyle("-fx-background-image:none; -fx-border-color: black;");
            grids[y][x].setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            grids[y][x].setGraphic(null);
            grids[y][x].setTooltip(null);
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

    private void fireAttack() {
        for (int i = 0; i < number_of_tower; i++) {
            towers[i].attack(monsters);
        }
    }

    /**
     * update the icon of living monster in the whole arena
     */
    @FXML
    private void iconUpdate(){
        for(int i=0;i<number_of_monster;++i){
            Monster monster = monsters[i];
            int x = monster.getLocation().getGridX();
            int y = monster.getLocation().getGridY();

            if(monsters[i].isAlive()){
                // grids[y][x].setStyle("-fx-background-image: url("+monsters[i].getIcon()+"); -fx-background-size:40px 40px;");
                Image image = new Image(monsters[i].getIcon(), 40, 40, true, true);
                grids[y][x].setGraphic(new ImageView(image));
                grids[y][x].setMaxSize(40.0, 40.0);

                grids[y][x].setTooltip(new Tooltip(
                    "HP:\t" + monsters[i].getHP() + "\n" +
                    "Speed:\t" + monsters[i].getSpeed() + "\n" + 
                    "Reward:\t" + monsters[i].getReward()
                ));                      
            }       
            if(monsters[i].isDying()&&!monsters[i].isAlive()){
                // grids[y][x].setStyle("-fx-background-image: url("+monsters[i].getIcon()+"); -fx-background-size:40px 40px;");
                Image image = new Image(monsters[i].getIcon(), 40, 40, true, true);
                grids[y][x].setGraphic(new ImageView(image));
                grids[y][x].setMaxSize(40.0, 40.0);

                grids[y][x].setTooltip(new Tooltip(
                    "HP:\t" + monsters[i].getHP() + "\n" +
                    "Speed:\t" + monsters[i].getSpeed() + "\n" + 
                    "Reward:\t" + monsters[i].getReward()
                ));   
                monsters[i].dead();
                updateResources(monsters[i].getReward());
            }       
                            
        }
    }

    private void updateResources(int money) {
        int moneyBalance = Integer.parseInt(labelMoneyAmount.getText());
        moneyBalance += money;
        labelMoneyAmount.setText(Integer.toString(moneyBalance));
    }

    private boolean constructTower(String towerType, int x, int y) {
        int moneyBalance = Integer.parseInt(labelMoneyAmount.getText());
        int cost = 0;
        int px = x * GameConfig.GRID_HEIGHT + 20;
        int py = y * GameConfig.GRID_WIDTH + 20;

        if (towerType.compareTo("basicTower") == 0) {
            cost = BasicTower.BUILDCOST;
            if (cost > moneyBalance) return false;
            towers[number_of_tower++] = new BasicTower(px, py);
        }
        else if (towerType.compareTo("catapult") == 0) {
            cost = CatapultTower.BUILDCOST;
            if (cost > moneyBalance) return false;
            towers[number_of_tower++] = new CatapultTower(px, py);
        }
        else if (towerType.compareTo("iceTower") == 0) {
            cost = IceTower.BUILDCOST;
            if (cost > moneyBalance) return false;
            towers[number_of_tower++] = new IceTower(px, py);
        }
        else if (towerType.compareTo("laserTower") == 0) {
            cost = LaserTower.BUILDCOST;
            if (cost > moneyBalance) return false;
            towers[number_of_tower++] = new LaserTower(px, py);
        }
        else return false;

        grids[y][x].setTooltip(new Tooltip(
            "Attack Power: " + towers[number_of_tower - 1].getAttackPower() + "\n" +
            "Attack Range: " + towers[number_of_tower - 1].getRange() + "\n" +
            "Upgrade Cost: $" + towers[number_of_tower - 1].getUpgradeCost()
        ));
        updateResources(-cost);
        return true;
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


    public void searchTower() {
        for (int i = 0; i < number_of_tower; i++) {
            int x = towers[i].getLocation().getGridX();
            int y = towers[i].getLocation().getGridY();
            if (x == selectedX && y == selectedY) {
                selectedTower = towers[i];
                break;
            }
            selectedTower = null;
        }
    }

    public void toggleFireRange(boolean shouldOn) {
        Color toColor = shouldOn ? Color.YELLOW : Color.WHITE;
        Color fromColor = shouldOn ? Color.WHITE : Color.YELLOW;

        for (int i = 0; i < GameConfig.MAX_V_NUM_GRID; i++)
            for (int j = 0; j < GameConfig.MAX_H_NUM_GRID; j++) {
                Label target = grids[i][j];
                if (target != null 
                    && target.getBackground() != null 
                    && target.getBackground().getFills().size() > 0 
                    && target.getBackground().getFills().get(0).getFill() == fromColor) 
                    if (selectedTower.isInRange(new Location(i * GameConfig.GRID_HEIGHT + 20, j * GameConfig.GRID_WIDTH + 20))) {
                        target.setBackground(new Background(new BackgroundFill(toColor, CornerRadii.EMPTY, Insets.EMPTY)));

                        System.out.println(String.format("\nTower: (%d, %d) -> (%d, %d)\ttarget: (%d, %d)", selectedTower.getLocation().x, selectedTower.getLocation().y, selectedTower.getLocation().getGridX(), selectedTower.getLocation().getGridY(), j, i));
                    }
            }
    }

    /**
     * Setup the styles of UI elements
     */
    private void setStyle() {
        labelMoneyAmount.setTextFill(Color.ORANGE);
        buttonDestroy.setDisable(true);
        buttonUpgrade.setDisable(true);
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
            for (int j = 0; j < GameConfig.MAX_H_NUM_GRID; j++) 
                setHandler(j, i);
    }

    private void setHandler(int x, int y) {
        Label target = grids[y][x];
        if (target.getBackground().getFills().get(0).getFill() == Color.WHITE) return;

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
                target.setText("");
                
                String targetLocation = target.getId();
                int y = Integer.parseInt(targetLocation.split(",")[0]);
                int x = Integer.parseInt(targetLocation.split(",")[1]);
                if (!constructTower(db.getString(), x, y)) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Not enough money");
                    alert.showAndWait().ifPresent((btnType) -> {});
                    event.consume();
                    return;
                }

                Image image = new Image(db.getString() + ".png", 40, 40, true, true);
                target.setGraphic(new ImageView(image));
                target.setMaxSize(40.0, 40.0);
                target.setStyle("-fx-border-color: black;");

                // REMARK: should change to click event, for upgrade tower / destroy
                target.setOnDragDropped(null);
                target.setOnDragEntered(null);
                target.setOnDragOver(null);
                target.setOnDragExited(null);

                target.setOnMouseClicked(new ClickedEventHandler());

                success = true;
            }
            event.setDropCompleted(success);
            event.consume();

        }
    }

    class ClickedEventHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            Label target = (Label) event.getTarget();
            String targetLocation = target.getId();
            selectedY = Integer.parseInt(targetLocation.split(",")[0]);
            selectedX = Integer.parseInt(targetLocation.split(",")[1]);
            searchTower();
            toggleFireRange(true);

            target.setStyle("-fx-border-color: yellow;");

            buttonUpgrade.setDisable(false);
            buttonDestroy.setDisable(false);

            event.consume();
        }
    }

}