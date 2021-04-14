/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Podcast;
import javafx.collections.ObservableList;

/**
 *
 * @author khail
 */
public interface IComments<PodcastComment> {
    
     public boolean addComment(PodcastComment tag);
    public boolean deleteComment(int id);
    public boolean updateComment(String commentText, int id);
    public PodcastComment getCommentById(int id);
    public ObservableList<PodcastComment> getCommentsByPodcast(Podcast pod);
    
    
}
