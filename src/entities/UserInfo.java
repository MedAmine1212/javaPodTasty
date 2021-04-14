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
@Table(name = "user_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserInfo.findAll", query = "SELECT u FROM UserInfo u"),
    @NamedQuery(name = "UserInfo.findById", query = "SELECT u FROM UserInfo u WHERE u.id = :id"),
    @NamedQuery(name = "UserInfo.findByUserLastName", query = "SELECT u FROM UserInfo u WHERE u.userLastName = :userLastName"),
    @NamedQuery(name = "UserInfo.findByUserFirstName", query = "SELECT u FROM UserInfo u WHERE u.userFirstName = :userFirstName"),
    @NamedQuery(name = "UserInfo.findByUserImage", query = "SELECT u FROM UserInfo u WHERE u.userImage = :userImage"),
    @NamedQuery(name = "UserInfo.findByUserGender", query = "SELECT u FROM UserInfo u WHERE u.userGender = :userGender"),
    @NamedQuery(name = "UserInfo.findByUserBirthDate", query = "SELECT u FROM UserInfo u WHERE u.userBirthDate = :userBirthDate"),
    @NamedQuery(name = "UserInfo.findByUserBio", query = "SELECT u FROM UserInfo u WHERE u.userBio = :userBio")})
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "user_last_name")
    private String userLastName;
    @Basic(optional = false)
    @Column(name = "user_first_name")
    private String userFirstName;
    @Column(name = "user_image")
    private String userImage;
    @Basic(optional = false)
    @Column(name = "user_gender")
    private String userGender;
    @Basic(optional = false)
    @Column(name = "user_birth_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date userBirthDate;
    @Column(name = "user_bio")
    private String userBio;
    @JoinTable(name = "story_user_info", joinColumns = {
        @JoinColumn(name = "user_info_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "story_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Story> storyCollection;
    @JoinTable(name = "user_info_user_info", joinColumns = {
        @JoinColumn(name = "user_info_target", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_info_source", referencedColumnName = "id")})
    @ManyToMany
    private Collection<UserInfo> userInfoCollection;
    @ManyToMany(mappedBy = "userInfoCollection")
    private Collection<UserInfo> userInfoCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Post> postCollection;
    @OneToOne(mappedBy = "userInfoIdId")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerId")
    private Collection<Story> storyCollection1;

    public UserInfo() {
    }

    public UserInfo(Integer id) {
        this.id = id;
    }

    public UserInfo(Integer id, String userLastName, String userFirstName, String userGender, Date userBirthDate) {
        this.id = id;
        this.userLastName = userLastName;
        this.userFirstName = userFirstName;
        this.userGender = userGender;
        this.userBirthDate = userBirthDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public Date getUserBirthDate() {
        return userBirthDate;
    }

    public void setUserBirthDate(Date userBirthDate) {
        this.userBirthDate = userBirthDate;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    @XmlTransient
    public Collection<Story> getStoryCollection() {
        return storyCollection;
    }

    public void setStoryCollection(Collection<Story> storyCollection) {
        this.storyCollection = storyCollection;
    }

    @XmlTransient
    public Collection<UserInfo> getUserInfoCollection() {
        return userInfoCollection;
    }

    public void setUserInfoCollection(Collection<UserInfo> userInfoCollection) {
        this.userInfoCollection = userInfoCollection;
    }

    @XmlTransient
    public Collection<UserInfo> getUserInfoCollection1() {
        return userInfoCollection1;
    }

    public void setUserInfoCollection1(Collection<UserInfo> userInfoCollection1) {
        this.userInfoCollection1 = userInfoCollection1;
    }

    @XmlTransient
    public Collection<Post> getPostCollection() {
        return postCollection;
    }

    public void setPostCollection(Collection<Post> postCollection) {
        this.postCollection = postCollection;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @XmlTransient
    public Collection<Story> getStoryCollection1() {
        return storyCollection1;
    }

    public void setStoryCollection1(Collection<Story> storyCollection1) {
        this.storyCollection1 = storyCollection1;
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
        if (!(object instanceof UserInfo)) {
            return false;
        }
        UserInfo other = (UserInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UserInfo[ id=" + id + " ]";
    }
    
}
