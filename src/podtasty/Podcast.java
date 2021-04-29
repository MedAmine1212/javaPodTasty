package podtasty;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.fxml.Initializable;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;










import entities.Playlist;
import entities.Reclamation;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.http.HttpRequest;
import org.json.JSONObject;
import services.CRUDPodcast;
import services.CRUDReport;
import javafx.stage.FileChooser;
public class Podcast extends Application implements Initializable
{
    public String imagepath;
    public String audiopath;
    @FXML
    TextField name;
    @FXML
    TextArea description;
    @FXML Label result;
    @FXML
    CheckBox currentlylive;

    @FXML
    CheckBox block;

    @FXML
    CheckBox allow_comments;
    @FXML
    ChoiceBox playlistid;
    @FXML
    Button audiobtn;
    @FXML
    Button imgbtn;
    Stage imagestage;

    @FXML
    ImageView imgview;
    entities.Podcast podcast = null;

    public static void main(String[] args)
    {
        Application.launch(args);
    }
    void initData(entities.Podcast podcast)
    {
        this.podcast=podcast;
        name.setText(podcast.getPodcastName());
        description.setText(podcast.getPodcastDescription());

        System.out.println("CURRENTLY LIVE : "+podcast.getCurrentlyLive());
        if (podcast.getCurrentlyLive()==1)
            currentlylive.setSelected(true);
        else
            currentlylive.setSelected(false);

        if (podcast.getIsBlocked()==1)
            block.setSelected(true);
        else
            block.setSelected(false);

        if (podcast.getCommentsAllowed()==1)
            allow_comments.setSelected(true);
        else
            allow_comments.setSelected(false);
    }
    @Override
    public void start(Stage stage) throws IOException
    {
        this.imagestage=stage;
        // Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Podcast.fxml"));
        // Path to the FXML File

        VBox root = (VBox) loader.load();
        Podcast controller =  loader.getController();

        controller.name.setText(podcast.getPodcastName().toString());
        controller.description.setText(podcast.getPodcastDescription().toString());
        if(podcast.getCurrentlyLive()==1)
            controller.currentlylive.setSelected(true);
        else
            controller.currentlylive.setSelected(false);
        Home.currentPodcast=null;
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
    public void addPodcast () throws IOException {
        CRUDPodcast cp=new CRUDPodcast();
//"INSERT INTO podcast (playlist_id_id, podcast_name, currently_live, is_blocked,comments_allowed,podcast_description," +

        entities.Podcast podcast=new entities.Podcast();
        podcast.setIsBlocked(block.isSelected()?1:0);
        podcast.setPodcastDescription(description.getText());
        podcast.setPodcastName(name.getText());
        podcast.setCurrentlyLive(currentlylive.isSelected()?1:0);
        podcast.setCommentsAllowed(allow_comments.isSelected()?1:0);
        // podcast.getPlaylistIdId();
        System.out.println("current podcast " +podcast.toString());


        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);

        HttpPost httppost = new HttpPost("http://localhost:8000/AddPodcastFromJava");
        System.out.println("AUDIO PATH : " + audiopath);
        System.out.println("IMAGE PATH : " + imagepath);


        File image= new File (imagepath);
        MultipartEntity mpEntity = new MultipartEntity();
        ContentBody cbFile = new FileBody(new File(imagepath), "image/jpeg");
        mpEntity.addPart("PodcastImage", cbFile);
       ContentBody cbFile1 = new FileBody(new File(audiopath), "aduio/*");
        mpEntity.addPart("PodcastSource", cbFile1);
        StringBody sb=new StringBody(podcast.getPodcastName());
        mpEntity.addPart("PodcastName",sb);
        StringBody sb1=new StringBody(podcast.getPodcastDescription());
        mpEntity.addPart("PodcastDescription",sb1);

        httppost.setEntity(mpEntity);
        System.out.println("executing request " + httppost.getRequestLine());
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity resEntity = response.getEntity();

        System.out.println(response.getStatusLine());
        if (resEntity != null) {
            String x= EntityUtils.toString(resEntity);
            System.out.println(x);
            JSONObject obj = new JSONObject(x);
            podcast.setPodcastImage(obj.getString("image"));
            podcast.setPodcastSource(obj.getString("audio"));
           // if (EntityUtils.toString(resEntity).contains("200"));
            System.out.println("UPLOAD FINISHED WITH SUCCESS");
            //cp.addPodcast(podcast);
        }

        if (resEntity != null) {
            resEntity.consumeContent();
        }
        httpclient.getConnectionManager().shutdown();
        if (this.podcast!=null)
        {
            System.out.println(podcast);
            cp.updatePodcast(podcast,this.podcast.getId());
        }
        else
        cp.addPodcast(podcast);
        result.setText("Podcast added successfully");







    }
    @FXML
    public void addImage() {
        imgview = new ImageView();
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        File selectedFile = fileChooser.showOpenDialog(imagestage);
        String imageFile = "";
        try {
            imageFile = (selectedFile.getAbsoluteFile().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        imagepath = selectedFile.toString();


    }
    @FXML
    public void addAudio()  {
       FileChooser fileChooser = new FileChooser();


        File selectedFile = fileChooser.showOpenDialog(imagestage);
        String audiofile = "";
        try {
            audiofile = (selectedFile.getAbsoluteFile().toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        audiopath=selectedFile.toString();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("INITIALIZED");
        if (Home.currentPodcast!=null)
        {
            System.out.println("recieved : "+Home.currentPodcast);
            this.podcast=Home.currentPodcast;
            Home.currentPodcast=null;
        }
    }
}