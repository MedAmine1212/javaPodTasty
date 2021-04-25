/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Playlist;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Lwiss
 */
public class PlaylistZoneController implements Initializable {

    @FXML
    private Pane space;
    @FXML
    private Label a;
    @FXML
    private Label nameplaylist;
    @FXML
    private ImageView imageplaylist;
    @FXML
    private Label descriptionplaylist;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setView(Playlist pl) throws MalformedURLException, IOException{
                nameplaylist.setText(pl.getPlaylistName());
                descriptionplaylist.setText(pl.getPlaylistDescription());
                BufferedImage image;
                image = ImageIO.read(new URL("http://127.0.0.1:8000/images/playlist/"+pl.getImageName()));
                WritableImage img = SwingFXUtils.toFXImage(image, null);
                imageplaylist.setImage(img);
    }
    
    
}
