/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Channel;
import entities.Playlist;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import services.ChannelService;

/**
 * FXML Controller class
 *
 * @author Lwiss
 */
public class ChannelBrowserController implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<Channel> channelList;
    @FXML
    private TableColumn<Channel, String> channelName;
    @FXML
    private TableColumn<Channel, String> channelDescription;
    @FXML
    private Button visitBtn;
    @FXML
    private TableColumn<Channel, Integer> channelId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showChannels();
                 
    }    

    
    public void showChannels(){
         ChannelService ps=new ChannelService();
        ObservableList<Channel> list= ps.getAllChannels();
        channelId.setCellValueFactory(new PropertyValueFactory<>("id"));
        channelName.setCellValueFactory(new PropertyValueFactory<>("channel_Name"));
        
        channelDescription.setCellValueFactory(new PropertyValueFactory<>("channel_Description"));
       
        
        
        FilteredList<Channel> filteredData = new FilteredList<>(list, b -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(channel -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (channel.getChannel_Name().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (channel.getChannel_Description().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Channel> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(channelList.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		channelList.setItems(sortedData);
               
        
    }    
        
        
    
    
    
    @FXML
    private void visitBtn(ActionEvent event) throws SQLException, IOException {
        if (channelList.getSelectionModel().getSelectedItem()==null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Select a channel");
                alert.setHeaderText(null);
                alert.setContentText("Please select a channel and try again.");
                alert.showAndWait();
                return;
        }
        
        else {
            
            
            
            Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("SingleChannelView.fxml"));
        try {
            loader.load();


              } catch (IOException ex) {
                  System.out.println(ex.getMessage());
        }

        Parent parent = loader.getRoot();
        stage.setScene(new Scene(parent));
        stage.setTitle("Welcome to my channel");
        
        SingleChannelViewController SingleChannelViewController=loader.getController();
          SingleChannelViewController.setwelcomeTitle("Welcome to "+channelList.getSelectionModel().getSelectedItem().getChannel_Name());  
          SingleChannelViewController.setdescriptionLabel(channelList.getSelectionModel().getSelectedItem().getChannel_Description());
          Date date= channelList.getSelectionModel().getSelectedItem().getChannel_CreationDate();
          DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
          String strDate = dateFormat.format(date);  
          SingleChannelViewController.setcreatedLabel(strDate);
          
         SingleChannelViewController.setId(channelList.getSelectionModel().getSelectedItem().getId());
        stage.show();}
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
}
