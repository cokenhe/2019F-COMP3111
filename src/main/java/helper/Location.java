package helper;

import java.awt.Point;
import javafx.scene.control.Label;
import helper.GameConfig;

/**
 * Save the x, y coordinate in px form
 */
public class Location extends Point {

    public Location(int x, int y) {
        super(x, y);
    }

    public double distance(Location l) {
        return this.distance(l);
    }

    /**
     * determin if this point is covered by the laser
     * @param tower location of tower
     * @param monster location of monster
     * @return true - in range, false - out of range
     */
    public boolean isInRange(Location tower, Location monster) {
        double A, C;
        A = (tower.y - monster.y) / (tower.x - monster.x);
        C = monster.y - A * monster.x;
        double distance = Math.abs(A * x - y + C) / Math.sqrt(A * A + 1);
        return distance <= 3;
    }

    /**
     * Convert pixel coordinate to grid label
     * @param grids the Label grids[][]
     * @return the Label in relative pixel coordinate
     */
    public Label getGridLabel(Label grids[][]) {
        return grids[y / GameConfig.GRID_HEIGHT][x / GameConfig.GRID_WIDTH];
    }

}