/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author khail
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByUserEmail", query = "SELECT u FROM User u WHERE u.userEmail = :userEmail"),
    @NamedQuery(name = "User.findByUserPassword", query = "SELECT u FROM User u WHERE u.userPassword = :userPassword"),
    @NamedQuery(name = "User.findByIsAdmin", query = "SELECT u FROM User u WHERE u.isAdmin = :isAdmin"),
    @NamedQuery(name = "User.findByDesactiveAccount", query = "SELECT u FROM User u WHERE u.desactiveAccount = :desactiveAccount"),
    @NamedQuery(name = "User.findByGithubId", query = "SELECT u FROM User u WHERE u.githubId = :githubId")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_password")
    private String userPassword;
    @Basic(optional = false)
    @Column(name = "is_admin")
    private boolean isAdmin;
    @Basic(optional = false)
    @Column(name = "desactive_account")
    private boolean desactiveAccount;
    @Column(name = "github_id")
    private String githubId;
    @ManyToMany(mappedBy = "userCollection")
    private Collection<Podcast> podcastCollection;
    @ManyToMany(mappedBy = "userCollection")
    private Collection<Channel> channelCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userIdId")
    private Collection<Notification> notificationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userIdId")
    private Collection<PodcastComment> podcastCommentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userIdId")
    private Collection<PodcastReview> podcastReviewCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userIdId")
    private Collection<Reclamation> reclamationCollection;
    @JoinColumn(name = "channel_id_id", referencedColumnName = "id")
    @OneToOne
    private Channel channelIdId;
    @JoinColumn(name = "user_info_id_id", referencedColumnName = "id")
    @OneToOne
    private UserInfo userInfoIdId;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String userEmail, boolean isAdmin, boolean desactiveAccount) {
        this.id = id;
        this.userEmail = userEmail;
        this.isAdmin = isAdmin;
        this.desactiveAccount = desactiveAccount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean getDesactiveAccount() {
        return desactiveAccount;
    }

    public void setDesactiveAccount(boolean desactiveAccount) {
        this.desactiveAccount = desactiveAccount;
    }

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    @XmlTransient
    public Collection<Podcast> getPodcastCollection() {
        return podcastCollection;
    }

    public void setPodcastCollection(Collection<Podcast> podcastCollection) {
        this.podcastCollection = podcastCollection;
    }

    @XmlTransient
    public Collection<Channel> getChannelCollection() {
        return channelCollection;
    }

    public void setChannelCollection(Collection<Channel> channelCollection) {
        this.channelCollection = channelCollection;
    }

    @XmlTransient
    public Collection<Notification> getNotificationCollection() {
        return notificationCollection;
    }

    public void setNotificationCollection(Collection<Notification> notificationCollection) {
        this.notificationCollection = notificationCollection;
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

    @XmlTransient
    public Collection<Reclamation> getReclamationCollection() {
        return reclamationCollection;
    }

    public void setReclamationCollection(Collection<Reclamation> reclamationCollection) {
        this.reclamationCollection = reclamationCollection;
    }

    public Channel getChannelIdId() {
        return channelIdId;
    }

    public void setChannelIdId(Channel channelIdId) {
        this.channelIdId = channelIdId;
    }

    public UserInfo getUserInfoIdId() {
        return userInfoIdId;
    }

    public void setUserInfoIdId(UserInfo userInfoIdId) {
        this.userInfoIdId = userInfoIdId;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User[ id=" + id + " ]";
    }
    
}
