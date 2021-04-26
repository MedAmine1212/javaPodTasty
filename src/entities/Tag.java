/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.awt.Color;
import java.util.List;
import javafx.scene.image.ImageView;

/**
 *
 * @author MedAmine
 */
public class Tag {
    private int id;
    private String name;
    private String tagStyle;
    private ImageView tagColor;

    public ImageView getTagColor() {
        return tagColor;
    }

    public void setTagColor(ImageView tagColor) {
        this.tagColor = tagColor;
    }
    private List<Podcast> listPodcasts;

    public Tag() {
    }

    public Tag(String name, String style) {
        this.name = name;
        this.tagStyle = style;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagStyle() {
        return tagStyle;
    }

    public void setTagStyle(String style) {
        this.tagStyle = style;
    }

    public List<Podcast> getListPodcasts() {
        return listPodcasts;
    }

    public void setListPodcasts(List<Podcast> listPodcasts) {
        this.listPodcasts = listPodcasts;
    }
    
    public void addPodcast(Podcast pod) {
        this.listPodcasts.add(pod);
    }
    
    public boolean removePodcast(Podcast pod) {
        if(this.listPodcasts.remove(pod)) {
            return true;
        }
        return false;
    }
    
    
    
}
