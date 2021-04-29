/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Podcast;
import entities.User;

/**
 *
 * @author khail
 */
public interface IFavorite {
    public boolean addFavorite(Podcast p, User u);
    public boolean removeFavorite(Podcast p, User u);
    public boolean getFavoriteByPodcastAnduser(Podcast p, User u);
}
