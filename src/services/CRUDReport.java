package services;

import DBConnection.MyConnection;
import entities.Podcast;
import entities.Reclamation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import interfaces.IReclamation;
import javafx.collections.ObservableList;


public class CRUDReport implements IReclamation<Reclamation> {
    @Override
    public boolean addReclamation(Reclamation reclamation) {
        try{
            String requete= "INSERT INTO reclamation (podcast_id_id, user_id_id, description, type,status) VALUES (?,?,?,?,?)";
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            // pst.setInt(1, t.getId());
            pst.setInt(1,reclamation.getPodcastIdId());
            pst.setInt(2,1);
            pst.setString(3,reclamation.getDescription());
            pst.setString(4,reclamation.getType());
            pst.setInt(5,0);
            //executeupdate
            pst.executeUpdate();
          /*  String rq = "Select MAX(id) from podcast_comment";
            PreparedStatement st = MyConnection.getInstance().getCnx()
                    .prepareStatement(rq);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                URL url = new URL("http://127.0.0.1:8000/callMercure/comments/"+rs.getInt(1)+"/"+com.getPodcastIdId().getId());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                int status = con.getResponseCode();
                System.out.println(status);

            }

*/

            return true;


        }catch(Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean deleteReclamation(int id) {
        return false;
    }
    @Override
    public List<Reclamation> getAll() {

        String sql = "select * from reclamation";
        ArrayList<Reclamation> reclamations=new ArrayList();
        try {

            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(sql);

            ResultSet result = statement.executeQuery();
            entities.Reclamation p = null;

            while (result.next()) {
                p = new entities.Reclamation(); // Nouvelle instance de personne
                p.setPodcastIdId((result.getInt(2)));
                p.setId(result.getInt(1));
                p.setStatus(result.getInt(6));   //  p.setPlaylistIdId( result.getInt(2) );
                p.setType(result.getString(4));
                p.setDescription(result.getString(5));
                // Autres champs

                reclamations.add( p ); // Ajout à la collection
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("crud podcast : "+reclamations.size());
        return reclamations;

    }
    public int getTotal(Reclamation reclamation)
    {
        int total=0;
        try{
            String requete =
                    "select count(id) as total from reclamation where podcast_id_id=? and status=1";
            ;
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1,reclamation.getPodcastIdId());
            ResultSet result= pst.executeQuery();
            result.next();
            total = result.getInt("total");
            System.out.println("total"+total);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return total;
    }
    @Override
    public boolean accept(Reclamation reclamation) {
        int total=0;
        try{
            String requete =
                    "select count(id) as total from reclamation where podcast_id_id=?";
            ;
            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            pst.setInt(1,reclamation.getPodcastIdId());
            ResultSet result= pst.executeQuery();
            result.next();
             total = result.getInt("total");
            System.out.println("total"+total);


        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        try {
            String requete =
                    "update reclamation set status=1 where id=?";
            ;

            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            // pst.setInt(1, t.getId());
            pst.setInt(1, reclamation.getId());
            pst.executeUpdate();
        }catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean refuse(Reclamation reclamation) {
        try {
            String requete =
                    "update reclamation set status=-1 where id=?";
            ;

            PreparedStatement pst = MyConnection.getInstance().getCnx()
                    .prepareStatement(requete);
            // pst.setInt(1, t.getId());
            pst.setInt(1, reclamation.getId());
            pst.executeUpdate();
        }catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateReclamation(String ReclamationText, int id) {
        return false;
    }

    @Override
    public Reclamation getReclamationById(int id) {
        return null;
    }

    @Override
    public ObservableList<Reclamation> getReclamationsByPodcast(Podcast pod) {
        return null;
    }

    @Override
    public ObservableList<Reclamation> getReclamationsByComText(Podcast pod, String text) {
        return null;
    }

    @Override
    public ObservableList<Podcast> getPodcastByPlaylist(int id, int podId) {
        return null;
    }

    @Override
    public int getReclamationsAllowedForPod(Podcast pod) {
        return 0;
    }
}
