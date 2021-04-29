package podtasty;

import entities.Reclamation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import services.CRUDPodcast;
import services.CRUDReport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

public class ReportView {
    Reclamation reclamation;
    @FXML
    Label id,description,type,name,state;

    @FXML
    Button blockbtn,accept,refuse;
    public void initData(Reclamation r, String pname, int podcastIdId)
    {
        CRUDPodcast cp=new CRUDPodcast();
       // System.out.println("CURREENT REPORT POST ID "+ reclamation.getPodcastIdId());
        //System.out.println("THIS PODCAST WITH ID 1 "+cp.getPodcastById(podcastIdId));
        entities.Podcast pod= cp.getPodcastById(podcastIdId);

        this.reclamation=r;
        name.setText(pname);
        blockbtn.setVisible(false);
        reclamation=r;
        id.setText(reclamation.getId().toString());
        description.setText(reclamation.getDescription());
        type.setText(reclamation.getType());
        if (reclamation.getStatus()==1)
        {
            state.setText("Accepted");
            accept.setDisable(true);
            refuse.setDisable(true);
        }
        if (reclamation.getStatus()==-1)
        {
            accept.setDisable(true);
            refuse.setDisable(true);
        }
        if (reclamation.getStatus()==-1)
            state.setText("Refused");

        if (pod.getIsBlocked()==1)
        {
            state.setText("BLOCKED");
            accept.setDisable(true);
            refuse.setDisable(true);

        }
        else if (pod.getIsBlocked()==0)
        {
            CRUDReport cr=new CRUDReport();

            int total=cr.getTotal(reclamation);
            System.out.println("total "+total);
            if (total>2) blockbtn.setVisible(true);
            else
                blockbtn.setVisible(false);
        }
    }


    public void accept(MouseEvent mouseEvent) throws IOException {
        CRUDReport cr=new CRUDReport();
        cr.accept(reclamation);
        int total=cr.getTotal(reclamation);
        if (total>2) blockbtn.setVisible(true);
        accept.setDisable(true);
        refuse.setDisable(true);
        String rid=reclamation.getId().toString();

        String toggle = "Report #"+rid+": accepted ";
        String urlParameters = "cause="+toggle+"&description=Your report has been aproved by admin";
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

    public void refuse(MouseEvent mouseEvent) throws IOException {
        CRUDReport cr=new CRUDReport();
        cr.refuse(reclamation);
        state.setText("Refused");
        accept.setDisable(true);
        refuse.setDisable(true);
        String rid=reclamation.getId().toString();

        String toggle = "Report #"+rid+": rejected ";
        String urlParameters = "cause="+toggle+"&description=Your report has been rejected by admin";
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

    public void blockpost(MouseEvent mouseEvent) {
        CRUDPodcast cp=new CRUDPodcast();
        cp.block(reclamation);

        accept.setDisable(true);
        refuse.setDisable(true);
    }
}
