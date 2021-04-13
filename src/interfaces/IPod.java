/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Podcast;
import java.util.List;

/**
 *
 * @author LENOVO
 * @param <podcast>
 */
public interface IPod<podcast> {
    
    public void addPodcast(Podcast podcast);
    public void deletePodcast(int id);
    public List<Podcast> getAllpodcast();
    public Podcast getPodcastBYId(int id);
    
}
