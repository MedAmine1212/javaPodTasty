/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author khail
 */
@Entity
@Table(name = "podcast_comment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PodcastComment.findAll", query = "SELECT p FROM PodcastComment p"),
    @NamedQuery(name = "PodcastComment.findById", query = "SELECT p FROM PodcastComment p WHERE p.id = :id"),
    @NamedQuery(name = "PodcastComment.findByCommentText", query = "SELECT p FROM PodcastComment p WHERE p.commentText = :commentText"),
    @NamedQuery(name = "PodcastComment.findByCommentDate", query = "SELECT p FROM PodcastComment p WHERE p.commentDate = :commentDate")})
public class PodcastComment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "comment_text")
    private String commentText;
    @Basic(optional = false)
    @Column(name = "comment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDate;
    @JoinColumn(name = "user_id_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userIdId;
    @JoinColumn(name = "podcast_id_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Podcast podcastIdId;
    private String userName;

   
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public PodcastComment() {
    }

    public PodcastComment(Integer id) {
        this.id = id;
    }

    public PodcastComment(Integer id, String commentText, Date commentDate) {
        this.id = id;
        this.commentText = commentText;
        this.commentDate = commentDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
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
        if (!(object instanceof PodcastComment)) {
            return false;
        }
        PodcastComment other = (PodcastComment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PodcastComment[ id=" + id + " ]";
    }
    
}
