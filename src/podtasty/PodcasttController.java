/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Podcast;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javax.sound.sampled.AudioFileFormat;
import javax.swing.JFileChooser;
import services.CRUDPodcast;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class PodcasttController implements Initializable {

    @FXML
    private Button Upaudio;
    @FXML
    private TextField podname;
    @FXML
    private TextArea poddesc;
    @FXML
    private Button upimg;
    @FXML
    private Button ajoutpodcast;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Upload files from Java client to a HTTP server
       // String url = "http://127.0.0.1:8000.upload";
        String charset = "UTF-8";
        String param = "value";
        File textFile = new File("/path/to/file.txt");
        File binaryFile = new File("/path/to/file.bin");
        String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
        String CRLF = "\r\n"; // Line separator required by multipart/form-data.
        
        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        
        try (
    OutputStream output = connection.getOutputStream();
    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
) {
    // Send normal param.
    writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"param\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
    writer.append(CRLF).append(param).append(CRLF).flush();

    // Send text file.
    writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"textFile\"; filename=\"" + textFile.getName() + "\"").append(CRLF);
    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
    writer.append(CRLF).flush();
    Files.copy(textFile.toPath(), output);
    output.flush(); // Important before continuing with writer!
    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

    // Send binary file.
    writer.append("--" + boundary).append(CRLF);
    writer.append("Content-Disposition: form-data; name=\"binaryFile\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
    writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
    writer.append("Content-Transfer-Encoding: binary").append(CRLF);
    writer.append(CRLF).flush();
    Files.copy(binaryFile.toPath(), output);
    output.flush(); // Important before continuing with writer!
    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.

    // End of multipart/form-data.
    writer.append("--" + boundary + "--").append(CRLF).flush();
}       catch (IOException ex) {
            Logger.getLogger(PodcasttController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int responseCode = 0;
        try {
            responseCode = ((HttpURLConnection) connection).getResponseCode();
        } catch (IOException ex) {
            Logger.getLogger(PodcasttController.class.getName()).log(Level.SEVERE, null, ex);
        }
System.out.println(responseCode); // Should be 200



        
    }    

    @FXML
    private void btnuploadaudio(ActionEvent event) {
        
        JFileChooser fchooser = new JFileChooser();
        fchooser.showOpenDialog(null);
        File selectedFile = fchooser.getSelectedFile();
        String filename = selectedFile.getAbsolutePath();
        AudioFileFormat getAbsolutepath = null ;
        System.out.println(filename);
        
        
    }

    @FXML
    private void btnuploadimg(ActionEvent event) {
        
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File selectedFile = chooser.getSelectedFile();
        String filename = selectedFile.getAbsolutePath();
        Image getAbsolutepath = null ;
       // AudioFileFormat getAbsolutpath = null;
        //ImageIcon icon = new Image
      System.out.println(filename);
    }

    @FXML
    private void btnajoutpodcast(ActionEvent event) {
        
        Podcast pod = new Podcast (podname.getText(),poddesc.getText());
        CRUDPodcast crpod = CRUDPodcast.getInstance();
        crpod.addPodcast(pod);
        
        Alert alert = new Alert (AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("podcast successfully added");
        alert.show();
    }
    
}
