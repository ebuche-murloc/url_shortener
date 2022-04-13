package com.example.url_shortener.domain;

import javax.persistence.*;

@Entity
public class LinkItem {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String longURL;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    public LinkItem() {
    }

    public LinkItem(String longURL, User user) {
        this.author = user;
        this.longURL = longURL;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongURL() {
        return longURL;
    }

    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }
}
