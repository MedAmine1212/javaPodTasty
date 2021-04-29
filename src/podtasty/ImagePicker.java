package podtasty;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ImagePicker extends Application {

    @Override
    public void start(Stage primaryStage) {
        FileChooser.ExtensionFilter imageFilter
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(imageFilter);

        Button btn = new Button();
        btn.setText("Open File");
        btn.setOnAction((ActionEvent event) -> {
            fc.showOpenDialog(primaryStage);
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("FileChooser Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}