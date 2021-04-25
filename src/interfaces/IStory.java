/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Story;
import javafx.collections.ObservableList;

/**
 *
 * @author Issam
 */
public interface IStory {

    public boolean addPost(Story story);

    public ObservableList<Story> getStories();

    public boolean deleteStory(int id);

    public boolean updateStory(Story story, int id);

    public Story getStoryById(int id);

    public ObservableList<Story> getStoriesByOwner(Story story);
}
