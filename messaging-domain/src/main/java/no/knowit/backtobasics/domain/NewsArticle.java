package no.knowit.backtobasics.domain;

import java.util.Date;

public class NewsArticle {
    private int id;
    private String headline;
    private String body;
    private Date created;
    private Date updated;

    public NewsArticle(int id, String headline, String body, Date created, Date updated) {
        this.id = id;
        this.headline = headline;
        this.body = body;
        this.created = created;
        this.updated = updated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return getId() + ": " + getHeadline();
    }
}
