/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Podcast;
import entities.Tag;
import javafx.collections.ObservableList;

/**
 *  
 * @author khail
 */
public interface ITag<Tag> {
    public boolean addTag(Tag tag);
    public ObservableList<Tag> getTags();
    public boolean deleteTag(int id);
    public boolean updateTag(Tag tag, int id);
    public Tag getTagById(int id);
    public ObservableList<Tag> getTagsByPodcast(Podcast pod);
    
}
