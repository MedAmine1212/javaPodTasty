/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Podcast;
import javafx.collections.ObservableList;

import java.util.List;

/**
 *
 * @author khail
 */
public interface IReclamation<Reclamation> {

    public boolean addReclamation(Reclamation reclamation);
    public boolean deleteReclamation(int id);
    public boolean updateReclamation(String ReclamationText, int id);
    public Reclamation getReclamationById(int id);
    public ObservableList<Reclamation> getReclamationsByPodcast(Podcast pod);
    public ObservableList<Reclamation> getReclamationsByComText(Podcast pod,String text);
    public ObservableList<Podcast> getPodcastByPlaylist(int id, int podId);
    public int getReclamationsAllowedForPod(Podcast pod);
    public List<Reclamation> getAll();
    public boolean accept(Reclamation reclamation);
    public boolean refuse(Reclamation reclamation);
}