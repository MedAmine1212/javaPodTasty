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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "channel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Channel.findAll", query = "SELECT c FROM Channel c"),
    @NamedQuery(name = "Channel.findById", query = "SELECT c FROM Channel c WHERE c.id = :id"),
    @NamedQuery(name = "Channel.findByChannelName", query = "SELECT c FROM Channel c WHERE c.channelName = :channelName"),
    @NamedQuery(name = "Channel.findByChannelDescription", query = "SELECT c FROM Channel c WHERE c.channelDescription = :channelDescription"),
    @NamedQuery(name = "Channel.findByChannelCreationDate", query = "SELECT c FROM Channel c WHERE c.channelCreationDate = :channelCreationDate"),
    @NamedQuery(name = "Channel.findByChannelStatus", query = "SELECT c FROM Channel c WHERE c.channelStatus = :channelStatus")})
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "channel_name")
    private String channelName;
    @Basic(optional = false)
    @Column(name = "channel_description")
    private String channelDescription;
    @Basic(optional = false)
    @Column(name = "channel_creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date channelCreationDate;
    @Basic(optional = false)
    @Column(name = "channel_status")
    private int channelStatus;
    @JoinTable(name = "user_channel", joinColumns = {
        @JoinColumn(name = "channel_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<User> userCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "channelIdId")
    private Collection<Playlist> playlistCollection;
    @OneToOne(mappedBy = "channelIdId")
    private User user;

    public Channel() {
    }

    public Channel(Integer id) {
        this.id = id;
    }

    public Channel(Integer id, String channelName, String channelDescription, Date channelCreationDate, int channelStatus) {
        this.id = id;
        this.channelName = channelName;
        this.channelDescription = channelDescription;
        this.channelCreationDate = channelCreationDate;
        this.channelStatus = channelStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
    }

    public Date getChannelCreationDate() {
        return channelCreationDate;
    }

    public void setChannelCreationDate(Date channelCreationDate) {
        this.channelCreationDate = channelCreationDate;
    }

    public int getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(int channelStatus) {
        this.channelStatus = channelStatus;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @XmlTransient
    public Collection<Playlist> getPlaylistCollection() {
        return playlistCollection;
    }

    public void setPlaylistCollection(Collection<Playlist> playlistCollection) {
        this.playlistCollection = playlistCollection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof Channel)) {
            return false;
        }
        Channel other = (Channel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Channel[ id=" + id + " ]";
    }
    
}
