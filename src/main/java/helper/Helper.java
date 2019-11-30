package helper;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Helper
 */
public class Helper {

    public static final Helper instance = new Helper();

    public void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(content);
        alert.showAndWait().ifPresent((btnType) -> {});
    }
}