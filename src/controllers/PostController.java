package controllers;

import entities.UserInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import objects.Account;
import objects.Post;
import objects.PostAudience;
import objects.Reactions;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import podtasty.PodTasty;
import services.CRUDPost;

public class PostController implements Initializable {
    @FXML
    private ImageView imgProfile;

    @FXML
    private Label username;

    @FXML
    private ImageView imgVerified;

    @FXML
    private Label date;

    @FXML
    private ImageView audience;

    @FXML
    private Label caption;

    @FXML
    private ImageView imgPost;

    @FXML
    private Label nbReactions;

    @FXML
    private Label nbComments;

    @FXML
    private Label nbShares;

    @FXML
    private HBox reactionsContainer;

    @FXML
    private ImageView imgLike;

    @FXML
    private ImageView imgLove;

    @FXML
    private ImageView imgCare;

    @FXML
    private ImageView imgHaha;

    @FXML
    private ImageView imgWow;

    @FXML
    private ImageView imgSad;

    @FXML
    private ImageView imgAngry;

    @FXML
    private HBox likeContainer;

    @FXML
    private ImageView imgReaction;

    @FXML
    private Label reactionName;

    private long startTime = 0;
    private Reactions currentReaction;
    private Post post;
    @FXML
    private Button btndelete;
    @FXML
    private VBox everything;

    @FXML
    public void onLikeContainerPressed(MouseEvent me){
        startTime = System.currentTimeMillis();
    }

    @FXML
    public void onLikeContainerMouseReleased(MouseEvent me){
        if(System.currentTimeMillis() - startTime > 500){
            reactionsContainer.setVisible(true);
        }else {
            if(reactionsContainer.isVisible()){
                reactionsContainer.setVisible(false);
            }
            if(currentReaction == Reactions.NON){
                setReaction(Reactions.LIKE);
            }else{
                setReaction(Reactions.NON);
            }
        }
    }

    @FXML
    public void onReactionImgPressed(MouseEvent me){
        switch (((ImageView) me.getSource()).getId()){
            case "imgLove":
                setReaction(Reactions.LOVE);
                break;
            case "imgCare":
                setReaction(Reactions.CARE);
                break;
            case "imgHaha":
                setReaction(Reactions.HAHA);
                break;
            case "imgWow":
                setReaction(Reactions.WOW);
                break;
            case "imgSad":
                setReaction(Reactions.SAD);
                break;
            case "imgAngry":
                setReaction(Reactions.ANGRY);
                break;
            default:
                setReaction(Reactions.LIKE);
                break;
        }
        reactionsContainer.setVisible(false);
    }

    public void setReaction(Reactions reaction){
        Image image = new Image(getClass().getResourceAsStream(reaction.getImgSrc()));
        imgReaction.setImage(image);
        reactionName.setText(reaction.getName());
        reactionName.setTextFill(Color.web(reaction.getColor()));

        if(currentReaction == Reactions.NON){
            post.setTotalReactions(post.getTotalReactions() + 1);
        }

        currentReaction = reaction;

        if(currentReaction == Reactions.NON){
            post.setTotalReactions(post.getTotalReactions() - 1);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
    }

    public void setData(Post post){
        this.post = post;
        Image img;
        img = new Image(getClass().getResourceAsStream(post.getAccount().getProfileImg()));
        imgProfile.setImage(img);
        CRUDPost c = new CRUDPost();
        if (post.getIdUser()!=0) {
             UserInfo user = c.getOwner(post.getIdUser());
        
        username.setText(user.getUserFirstName()+" "+user.getUserLastName());
        }else   
        {
            username.setText("username");
        }
       
        
        if(post.getAccount().isVerified()){
            imgVerified.setVisible(true);
        }else{
            imgVerified.setVisible(false);
        }

        date.setText(post.getDate());
        if(post.getAudience() == PostAudience.PUBLIC){
            img = new Image(getClass().getResourceAsStream(PostAudience.PUBLIC.getImgSrc()));
        }else{
            img = new Image(getClass().getResourceAsStream(PostAudience.FRIENDS.getImgSrc()));
        }
        audience.setImage(img);

        if(post.getCaption() != null && !post.getCaption().isEmpty()){
            caption.setText(post.getCaption());
        }else{
            caption.setManaged(false);
        }

        if(post.getImage() != null && !post.getImage().isEmpty()){
            img = new Image(getClass().getResourceAsStream(post.getImage()));
            imgPost.setImage(img);
        }else{
            imgPost.setVisible(false);
            imgPost.setManaged(false);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
        nbComments.setText(post.getNbComments() + " comments");
        nbShares.setText(post.getNbShares()+" shares");

        currentReaction = Reactions.NON;
        
        
               if(PodTasty.getUserInfo().getId()!=post.getIdUser()){
            btndelete.setVisible(false);
        }else
               {
                   btndelete.setVisible(true);
               }
        
        System.out.println("connecté:  "+PodTasty.getUserInfo().getId());
        System.out.println("mta3 el post: "+post.getIdUser());
        
        
    }

    private Post getPost(){
        Post post = new Post();
        Account account = new Account();
        account.setName("Issam Ben Ammar");
        account.setProfileImg("/img/user.png");
        account.setVerified(true);
        post.setAccount(account);
        post.setDate("Feb 18, 2021 at 12:00 PM");
        post.setAudience(PostAudience.PUBLIC);
        post.setCaption("Hello everybody.");
        post.setImage("/img/img2.jpg");
        post.setTotalReactions(10);
        post.setNbComments(2);
        post.setNbShares(3);
        
        
 
        

        return post;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData(getPost());
        
        
    }

    @FXML
    private void deletePost(ActionEvent event) {
        
        System.out.println(post.getId());
        CRUDPost c = new CRUDPost();
        c.deletePost(post.getId());
        everything.toFront();
        everything.setVisible(false);
        
    }
}