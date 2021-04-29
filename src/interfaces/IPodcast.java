/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Podcast;
import entities.Reclamation;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author khail
 */
public interface IPodcast<Podcast> {

    public boolean addPodcast(Podcast tag);
    public List<Podcast> getAll();

    public boolean deletePodcast(int id) throws SQLException;
    public boolean updatePodcast(Podcast  podcast,int id);
    public Podcast getPodcastById(int id);


    boolean block(Reclamation reclamation);
}
