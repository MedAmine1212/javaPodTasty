/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author khail
 */
@Entity
@Table(name = "podcast")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Podcast_1.findAll", query = "SELECT p FROM Podcast_1 p"),
    @NamedQuery(name = "Podcast_1.findById", query = "SELECT p FROM Podcast_1 p WHERE p.id = :id"),
    @NamedQuery(name = "Podcast_1.findByPodcastName", query = "SELECT p FROM Podcast_1 p WHERE p.podcastName = :podcastName"),
    @NamedQuery(name = "Podcast_1.findByCurrentlyLive", query = "SELECT p FROM Podcast_1 p WHERE p.currentlyLive = :currentlyLive"),
    @NamedQuery(name = "Podcast_1.findByIsBlocked", query = "SELECT p FROM Podcast_1 p WHERE p.isBlocked = :isBlocked"),
    @NamedQuery(name = "Podcast_1.findByCommentsAllowed", query = "SELECT p FROM Podcast_1 p WHERE p.commentsAllowed = :commentsAllowed"),
    @NamedQuery(name = "Podcast_1.findByPodcastDescription", query = "SELECT p FROM Podcast_1 p WHERE p.podcastDescription = :podcastDescription"),
    @NamedQuery(name = "Podcast_1.findByPodcastImage", query = "SELECT p FROM Podcast_1 p WHERE p.podcastImage = :podcastImage"),
    @NamedQuery(name = "Podcast_1.findByPodcastViews", query = "SELECT p FROM Podcast_1 p WHERE p.podcastViews = :podcastViews"),
    @NamedQuery(name = "Podcast_1.findByCurrentlyWatching", query = "SELECT p FROM Podcast_1 p WHERE p.currentlyWatching = :currentlyWatching"),
    @NamedQuery(name = "Podcast_1.findByPodcastDate", query = "SELECT p FROM Podcast_1 p WHERE p.podcastDate = :podcastDate"),
    @NamedQuery(name = "Podcast_1.findByPodcastSource", query = "SELECT p FROM Podcast_1 p WHERE p.podcastSource = :podcastSource")})
public class Podcast implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "podcast_name")
    private String podcastName;
    @Basic(optional = false)
    @Column(name = "currently_live")
    private int currentlyLive;
    @Basic(optional = false)
    @Column(name = "is_blocked")
    private int isBlocked;
    @Basic(optional = false)
    @Column(name = "comments_allowed")
    private int commentsAllowed;
    @Column(name = "podcast_description")
    private String podcastDescription;
    @Column(name = "podcast_image")
    private String podcastImage;
    @Column(name = "podcast_views")
    private Integer podcastViews;
    @Column(name = "currently_watching")
    private Integer currentlyWatching;
    @Basic(optional = false)
    @Column(name = "podcast_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date podcastDate;
    @Column(name = "podcast_source")
    private String podcastSource;
    @JoinTable(name = "user_podcast", joinColumns = {
        @JoinColumn(name = "podcast_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<User> userCollection;
    @JoinTable(name = "tag_podcast", joinColumns = {
        @JoinColumn(name = "podcast_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "tag_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Tag> tagCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "podcastIdId")
    private Collection<PodcastComment> podcastCommentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "podcastIdId")
    private Collection<PodcastReview> podcastReviewCollection;
    @JoinColumn(name = "playlist_id_id", referencedColumnName = "id")
    @ManyToOne
    private Playlist playlistIdId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "podcastIdId")
    private Collection<Reclamation> reclamationCollection;

    public Podcast() {
    }

    public Podcast(Integer id) {
        this.id = id;
    }

    public Podcast(Integer id, String podcastName, int currentlyLive, int isBlocked, int commentsAllowed, Date podcastDate) {
        this.id = id;
        this.podcastName = podcastName;
        this.currentlyLive = currentlyLive;
        this.isBlocked = isBlocked;
        this.commentsAllowed = commentsAllowed;
        this.podcastDate = podcastDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPodcastName() {
        return podcastName;
    }

    public void setPodcastName(String podcastName) {
        this.podcastName = podcastName;
    }

    public int getCurrentlyLive() {
        return currentlyLive;
    }

    public void setCurrentlyLive(int currentlyLive) {
        this.currentlyLive = currentlyLive;
    }

    public int getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(int isBlocked) {
        this.isBlocked = isBlocked;
    }

    public int getCommentsAllowed() {
        return commentsAllowed;
    }

    public void setCommentsAllowed(int commentsAllowed) {
        this.commentsAllowed = commentsAllowed;
    }

    public String getPodcastDescription() {
        return podcastDescription;
    }

    public void setPodcastDescription(String podcastDescription) {
        this.podcastDescription = podcastDescription;
    }

    public String getPodcastImage() {
        return podcastImage;
    }

    public void setPodcastImage(String podcastImage) {
        this.podcastImage = podcastImage;
    }

    public Integer getPodcastViews() {
        return podcastViews;
    }

    public void setPodcastViews(Integer podcastViews) {
        this.podcastViews = podcastViews;
    }

    public Integer getCurrentlyWatching() {
        return currentlyWatching;
    }

    public void setCurrentlyWatching(Integer currentlyWatching) {
        this.currentlyWatching = currentlyWatching;
    }

    public Date getPodcastDate() {
        return podcastDate;
    }

    public void setPodcastDate(Date podcastDate) {
        this.podcastDate = podcastDate;
    }

    public String getPodcastSource() {
        return podcastSource;
    }

    public void setPodcastSource(String podcastSource) {
        this.podcastSource = podcastSource;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @XmlTransient
    public Collection<Tag> getTagCollection() {
        return tagCollection;
    }

    public void setTagCollection(Collection<Tag> tagCollection) {
        this.tagCollection = tagCollection;
    }

    @XmlTransient
    public Collection<PodcastComment> getPodcastCommentCollection() {
        return podcastCommentCollection;
    }

    public void setPodcastCommentCollection(Collection<PodcastComment> podcastCommentCollection) {
        this.podcastCommentCollection = podcastCommentCollection;
    }

    @XmlTransient
    public Collection<PodcastReview> getPodcastReviewCollection() {
        return podcastReviewCollection;
    }

    public void setPodcastReviewCollection(Collection<PodcastReview> podcastReviewCollection) {
        this.podcastReviewCollection = podcastReviewCollection;
    }

    public Playlist getPlaylistIdId() {
        return playlistIdId;
    }

    public void setPlaylistIdId(Playlist playlistIdId) {
        this.playlistIdId = playlistIdId;
    }

    @XmlTransient
    public Collection<Reclamation> getReclamationCollection() {
        return reclamationCollection;
    }

    public void setReclamationCollection(Collection<Reclamation> reclamationCollection) {
        this.reclamationCollection = reclamationCollection;
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
        if (!(object instanceof Podcast)) {
            return false;
        }
        Podcast other = (Podcast) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Podcast_1[ id=" + id + " ]";
    }
    
}
