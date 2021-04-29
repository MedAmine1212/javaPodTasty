/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javafx.scene.image.ImageView;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author khail
 */
@Entity
@Table(name = "tag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tag_1.findAll", query = "SELECT t FROM Tag_1 t"),
    @NamedQuery(name = "Tag_1.findById", query = "SELECT t FROM Tag_1 t WHERE t.id = :id"),
    @NamedQuery(name = "Tag_1.findByName", query = "SELECT t FROM Tag_1 t WHERE t.name = :name"),
    @NamedQuery(name = "Tag_1.findByTagStyle", query = "SELECT t FROM Tag_1 t WHERE t.tagStyle = :tagStyle")})
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "tag_style")
    private String tagStyle;
    @ManyToMany(mappedBy = "tagCollection")
    private Collection<Podcast> podcastCollection;
    
    private ImageView tagColor;


    public Tag() {
    }

    public Tag(Integer id) {
        this.id = id;
    }

    public Tag(Integer id, String name, String tagStyle) {
        this.id = id;
        this.name = name;
        this.tagStyle = tagStyle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public void setTagStyle(String tagStyle) {
        this.tagStyle = tagStyle;
    }

    @XmlTransient
    public Collection<Podcast> getPodcastCollection() {
        return podcastCollection;
    }

    public void setPodcastCollection(Collection<Podcast> podcastCollection) {
        this.podcastCollection = podcastCollection;
    }
    
    
    public ImageView getTagColor() {
        return tagColor;
    }

    public void setTagColor(ImageView tagColor) {
        this.tagColor = tagColor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Tag_1[ id=" + id + " ]";
    }
    
}
