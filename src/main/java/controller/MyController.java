package controller;

// MARK: javafx
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import helper.Location;
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

// MARK: my classes
import monster.Monster;

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

    private static final int ARENA_WIDTH = 480;
    private static final int ARENA_HEIGHT = 480;
    private static final int GRID_WIDTH = 40;
    private static final int GRID_HEIGHT = 40;
    private static final int MAX_H_NUM_GRID = 12;
    private static final int MAX_MONSTER_NUMBER = 999;
    private static final int MAX_V_NUM_GRID = 12;
    private static int number_of_frame = 0;
    
    private static Monster monsters[] = new Monster[MAX_MONSTER_NUMBER];

    private Label grids[][] = new Label[MAX_V_NUM_GRID][MAX_H_NUM_GRID]; //the grids on arena
    private int x = -1, y = 0;

    private enum Direction {
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
        for (int i = 0; i < MAX_V_NUM_GRID; i++)
            for (int j = 0; j < MAX_H_NUM_GRID; j++) {
                Label newLabel = new Label();
                if (j % 2 == 0 || i == ((j + 1) / 2 % 2) * (MAX_V_NUM_GRID - 1))
                    newLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                else
                    newLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                newLabel.setLayoutX(j * GRID_WIDTH);
                newLabel.setLayoutY(i * GRID_HEIGHT);
                newLabel.setMinWidth(GRID_WIDTH);
                newLabel.setMaxWidth(GRID_WIDTH);
                newLabel.setMinHeight(GRID_HEIGHT);
                newLabel.setMaxHeight(GRID_HEIGHT);
                newLabel.setStyle("-fx-border-color: black;");
                grids[i][j] = newLabel;
                paneArena.getChildren().addAll(newLabel);
            }

        setDragAndDrop();
    }

    @FXML
    private void nextFrame() {
        offerResources();

        if(number_of_frame++ % 5 == 0){
            generateMonster();
        }

        // Initial position
        if (x == -1) {
            grids[0][0].setStyle("-fx-background-image: url(\"fox.png\"); -fx-background-size:40px 40px;");
            x = 0;
            return; 
        }

        grids[y][x].setStyle("-fx-background-image:none; -fx-border-color: black;");

        // When col is odd || moved to top / bottom, then move to right
        if (x % 2 == 1 || (monsterDirection == Direction.DOWNWARD && y == MAX_V_NUM_GRID - 1) || (monsterDirection == Direction.UPWARD && y == 0)){
            x += 1;
            if (y == 0)                  monsterDirection = Direction.DOWNWARD;
            if (y == MAX_V_NUM_GRID - 1) monsterDirection = Direction.UPWARD;
        } else // Moving up / down
            y += monsterDirection.getValue();

        grids[y][x].setStyle("-fx-background-image: url(\"fox.png\"); -fx-background-size:40px 40px;");
    }

    private void generateMonster() {
        
    }

    private void offerResources() {

    }

    /**
     * Setup the styles of UI elements
     */
    private void setStyle() {
        labelMoneyAmount.setTextFill(Color.YELLOW);
    }

    /**
     * A function that demo how drag and drop works
     */
    private void setDragAndDrop() {
        labelBasicTower.setOnDragDetected(new DragEventHandler(labelBasicTower));
        labelIceTower.setOnDragDetected(new DragEventHandler(labelIceTower));
        labelCatapult.setOnDragDetected(new DragEventHandler(labelCatapult));
        labelLaserTower.setOnDragDetected(new DragEventHandler(labelLaserTower));

        for (int i = 0; i < MAX_V_NUM_GRID; i++)
            for (int j = 0; j < MAX_H_NUM_GRID; j++) {
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
