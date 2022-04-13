package com.example.url_shortener.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LogItem {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String ip;
    private String userAgent;
    private LocalDateTime redirectDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "link_id")
    private LinkItem link;

    public LogItem() {
    }

    public LogItem(String ip, String userAgent, LinkItem link) {
        this.ip = ip;
        this.userAgent = userAgent;
        this.link = link;
        this.redirectDate = LocalDateTime.now();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public LinkItem getLink() {
        return link;
    }

    public void setLink(LinkItem link) {
        this.link = link;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
