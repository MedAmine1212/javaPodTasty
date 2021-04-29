package podtasty;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import entities.Reclamation;
import entities.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.CRUDReport;
import services.CRUDUser;

public class PodcastReport extends Application implements Initializable
{
    @FXML
    ToggleGroup group;
    @FXML
    Label podid;
    @FXML
    TextArea description;
    entities.Podcast podcast;
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        // Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("podcastView.fxml"));
        // Path to the FXML File

        VBox root = (VBox) loader.load();

        // Create the Scene
        Scene scene = new Scene(root);
        // Set the Scene to the Stage
        stage.setScene(scene);
        // Set the Title to the Stage
        stage.setTitle("A simple FXML Example");
        // Display the Stage
        stage.show();
    }
    @FXML
    public void report () throws IOException {
        if (group.getSelectedToggle() != null) {
            RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
            String toggle = selectedRadioButton.getText();
            System.out.println(toggle);
            CRUDReport cr= new CRUDReport();
            Reclamation reclamation= new Reclamation();
            reclamation.setDescription(description.getText());
            reclamation.setType(toggle);
            reclamation.setDescription(description.getText());
            
            reclamation.setPodcastIdId(podcast.getId());
            //reclamation.setUserIdId();
            System.out.println("null");
            cr.addReclamation(reclamation);
            String urlParameters = "cause="+toggle+"&description="+description.getText().toString();
            URL url = new URL("http://localhost:8000/contact");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(urlParameters);
            writer.flush();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            writer.close();
            reader.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    void initData(entities.Podcast podcast)
    {
        this.podcast=podcast;
        podid.setText("#"+podcast.getId());
    }
}