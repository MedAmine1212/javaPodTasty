/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Podcast;
import entities.Post;
import javafx.collections.ObservableList;

/**
 *
 * @author Issam
 */
public interface IPost<Post> {

    public boolean addPost(Post post);

    public ObservableList<Post> getPosts();

    public boolean deletePost(int id);

    public boolean updatePost(Post post, int id);

    public Post getPostById(int id);

    public ObservableList<Post> getPostByOwner(Post post);
    
    
    public ObservableList<Post> getPostByText(Post post);

}
