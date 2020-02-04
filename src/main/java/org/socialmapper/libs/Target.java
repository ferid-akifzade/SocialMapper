package org.socialmapper.libs;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@Entity
public class Target {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String facebook;
    private String twitter;
    private String linkedin;
    private String instagram;
    private String vkontakte;

    public Target(String name, String surname, String facebook, String twitter, String linkedin, String instagram, String vkontakte) {
        this.name = name;
        this.surname = surname;
        this.facebook = facebook;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.instagram = instagram;
        this.vkontakte = vkontakte;
    }

    public Target() {
       clear();
    }

    public Target(Target target) {
        this.name = target.getName();
        this.surname = target.getSurname();
        this.facebook = target.getFacebook();
        this.twitter = target.getTwitter();
        this.linkedin = target.getLinkedin();
        this.instagram = target.getInstagram();
        this.vkontakte = target.getVkontakte();
    }

    public boolean isNotEmpty() {
        return !(this.name.isEmpty() &&
                this.surname.isEmpty() &&
                this.facebook.isEmpty() &&
                this.twitter.isEmpty() &&
                this.linkedin.isEmpty() &&
                this.instagram.isEmpty() &&
                this.vkontakte.isEmpty());
    }

    public void clear() {
        this.name = "";
        this.surname = "";
        this.facebook = "";
        this.twitter = "";
        this.linkedin = "";
        this.instagram = "";
        this.vkontakte = "";
    }
}
