/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author khail
 */
@Entity
@Table(name = "podcast_review")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PodcastReview.findAll", query = "SELECT p FROM PodcastReview p"),
    @NamedQuery(name = "PodcastReview.findById", query = "SELECT p FROM PodcastReview p WHERE p.id = :id"),
    @NamedQuery(name = "PodcastReview.findByRating", query = "SELECT p FROM PodcastReview p WHERE p.rating = :rating")})
public class PodcastReview implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "rating")
    private float rating;
    @JoinColumn(name = "user_id_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userIdId;
    @JoinColumn(name = "podcast_id_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Podcast podcastIdId;

    public PodcastReview() {
    }

    public PodcastReview(Integer id) {
        this.id = id;
    }

    public PodcastReview(Integer id, int rating) {
        this.id = id;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public User getUserIdId() {
        return userIdId;
    }

    public void setUserIdId(User userIdId) {
        this.userIdId = userIdId;
    }

    public Podcast getPodcastIdId() {
        return podcastIdId;
    }

    public void setPodcastIdId(Podcast podcastIdId) {
        this.podcastIdId = podcastIdId;
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
        if (!(object instanceof PodcastReview)) {
            return false;
        }
        PodcastReview other = (PodcastReview) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PodcastReview[ id=" + id + " ]";
    }
    
}
