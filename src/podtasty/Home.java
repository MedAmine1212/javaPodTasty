package podtasty;

import entities.Podcast;
import entities.Reclamation;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.CRUDPodcast;
import services.CRUDReport;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Home extends Application  {
    @FXML
    GridPane podcastlist;
    Stage primaryStage;
    public static entities.Podcast currentPodcast = null;
    public static void main(String[] args)
    {
        Application.launch(args);
    }
    public int reportview=0;
    public int initializing=0;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        // Path to the FXML File
        System.out.println("STARTING");
        VBox root = (VBox) loader.load();

        // Create the Scene
        Scene scene = new Scene(root);
        // Set the Scene to the Stage
        primaryStage.setScene(scene);
        // Set the Title to the Stage
        primaryStage.setTitle("PodTasty Home");
        // Display the Stage
        primaryStage.show();
        CRUDPodcast crudPodcast=new CRUDPodcast();
        ArrayList<Podcast> podcasts=(ArrayList<entities.Podcast>)crudPodcast.getAll();
        ObservableList<Podcast> observableList = FXCollections.observableArrayList(podcasts);

        showPlaylist(observableList);
    }
    @FXML
    public void showPlaylist(ObservableList<entities.Podcast> podcasts) {

        podcastlist.getChildren().clear();
        podcastlist.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (reportview == 0 ||initializing==0){
                    initializing=1;
                    System.out.println("CLICKED" + mouseEvent.getY());

                int index = (int) (mouseEvent.getY() / 120);
                currentPodcast = (podcasts.get(index));
                System.out.println(podcasts.get(index));


                // Step 3
                Stage stage = new Stage();
                stage.close();
                try {
                    // Step 4
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("podcast.fxml"));

                    //  URL url = new File("src/podtasty/podcast.fxml").toURI().toURL();


                    Parent root = loader.load();
                    podtasty.Podcast controller = loader.getController();
                    if (loader.getController() == null) {
                        System.out.println("CONTROLLER IS NULL");
                    } else
                        System.out.println("CONTROLLER IS NOT NULL");
                    Scene scene = new Scene(root);
                    controller = loader.getController();
                    if (loader.getController() == null) {
                        System.out.println("CONTROLLER IS NULL");
                    } else
                        System.out.println("CONTROLLER IS NOT NULL");
                    stage.setScene(scene);
                    controller = loader.getController();
                    if (loader.getController() == null) {
                        System.out.println("CONTROLLER IS NULL");
                    } else
                        System.out.println("CONTROLLER IS NOT NULL");

                    // Step 7
                    stage.show();
                    controller = loader.getController();
                    if (loader.getController() == null) {
                        System.out.println("CONTROLLER IS NULL");
                    } else
                        System.out.println("CONTROLLER IS NOT NULL");

                    controller.initData(podcasts.get(index));

                } catch (IOException e) {
                    System.err.println(String.format("Error: %s", e.getMessage()));
                }


            }



            }
        });
        System.out.println(podcasts.size());
        int i = 3;
        for(Podcast pod : podcasts) {
            try {

                FXMLLoader fx = new FXMLLoader(getClass().getResource("podcastView.fxml"));
                Pane pn = fx.load();
                PodcastViewController controller = fx.getController();

                controller.setView(pod);
                pn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {

                    }
                });
                podcastlist.add(pn, 0, i);
                i++;

            } catch (IOException e ) {
                System.out.println("error 1: "+e.getMessage());
            }
        }
    }
    @FXML
    private void addPodcastAction(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Podcast.fxml"));

            Stage reviewStage = new Stage();
            reviewStage.setTitle("Add Podcast");
            reviewStage.setScene(new Scene(root));
            reviewStage.initModality(Modality.WINDOW_MODAL);
            reviewStage.initOwner(((Node) (event.getSource())).getScene().getWindow());
            reviewStage.show();
        /*Label secondLabel = new Label("I'm a Label on new Window");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 230, 100);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.


        newWindow.show();*/
            new PodcastReport().start(reviewStage);
        } catch (Exception e )
        {
            System.out.printf(e.getMessage());
        }

    }

    public void showPlaylist(MouseEvent mouseEvent) {

        CRUDPodcast crudPodcast=new CRUDPodcast();
        ArrayList<Podcast> podcasts=(ArrayList<entities.Podcast>)crudPodcast.getAll();
        ObservableList<Podcast> observableList = FXCollections.observableArrayList(podcasts);

        showPlaylist(observableList);
    }
    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }


        return result;
    }
    @FXML
    void clearList()
    {
        this.reportview=1;
        this.initializing=1;
        podcastlist.getChildren().clear();
        CRUDReport cr=new CRUDReport();
        List<Reclamation> reports=new ArrayList();
        reports=cr.getAll();
        int i = 3;
        for(Reclamation rep : reports) {
            try {

                FXMLLoader fx = new FXMLLoader(getClass().getResource("ReportView.fxml"));
                Pane pn = fx.load();
                String pname=new CRUDPodcast().getPodcastById(rep.getPodcastIdId()).getPodcastName();
                ReportView controller = fx.getController();
                controller.initData(rep,pname,rep.getPodcastIdId());
                pn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {

                    }
                });
                podcastlist.add(pn, 0, i);
                i++;
            } catch (IOException e ) {
                System.out.println("error 1: "+e.getMessage());
            }
        }

    }
}
