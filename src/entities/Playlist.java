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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "playlist")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Playlist.findAll", query = "SELECT p FROM Playlist p"),
    @NamedQuery(name = "Playlist.findById", query = "SELECT p FROM Playlist p WHERE p.id = :id"),
    @NamedQuery(name = "Playlist.findByPlaylistName", query = "SELECT p FROM Playlist p WHERE p.playlistName = :playlistName"),
    @NamedQuery(name = "Playlist.findByPlaylistDescription", query = "SELECT p FROM Playlist p WHERE p.playlistDescription = :playlistDescription"),
    @NamedQuery(name = "Playlist.findByPlaylistCreationDate", query = "SELECT p FROM Playlist p WHERE p.playlistCreationDate = :playlistCreationDate"),
    @NamedQuery(name = "Playlist.findByImageName", query = "SELECT p FROM Playlist p WHERE p.imageName = :imageName"),
    @NamedQuery(name = "Playlist.findByUpdatedAt", query = "SELECT p FROM Playlist p WHERE p.updatedAt = :updatedAt")})
public class Playlist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "playlist_name")
    private String playlistName;
    @Column(name = "playlist_description")
    private String playlistDescription;
    @Basic(optional = false)
    @Column(name = "playlist_creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date playlistCreationDate;
    @Column(name = "image_name")
    private String imageName;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "channel_id_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Channel channelIdId;
    @OneToMany(mappedBy = "playlistIdId")
    private Collection<Podcast> podcastCollection;

    public Playlist() {
    }

    public Playlist(Integer id) {
        this.id = id;
    }

    public Playlist(Integer id, String playlistName, Date playlistCreationDate, Date updatedAt) {
        this.id = id;
        this.playlistName = playlistName;
        this.playlistCreationDate = playlistCreationDate;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getPlaylistDescription() {
        return playlistDescription;
    }

    public void setPlaylistDescription(String playlistDescription) {
        this.playlistDescription = playlistDescription;
    }

    public Date getPlaylistCreationDate() {
        return playlistCreationDate;
    }

    public void setPlaylistCreationDate(Date playlistCreationDate) {
        this.playlistCreationDate = playlistCreationDate;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Channel getChannelIdId() {
        return channelIdId;
    }

    public void setChannelIdId(Channel channelIdId) {
        this.channelIdId = channelIdId;
    }

    @XmlTransient
    public Collection<Podcast> getPodcastCollection() {
        return podcastCollection;
    }

    public void setPodcastCollection(Collection<Podcast> podcastCollection) {
        this.podcastCollection = podcastCollection;
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
        if (!(object instanceof Playlist)) {
            return false;
        }
        Playlist other = (Playlist) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Playlist[ id=" + id + " ]";
    }
    
}
