/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Podcast;
import entities.PodcastReview;
import javafx.collections.ObservableList;

/**
 *
 * @author khail
 */
public interface IReview {
    public boolean addReview(PodcastReview review);
    public boolean deleteReview(int id);
    public ObservableList<PodcastReview> getReviewsByPodcast(Podcast pod);
}
