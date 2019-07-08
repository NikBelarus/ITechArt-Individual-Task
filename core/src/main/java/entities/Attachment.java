package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attachment {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("date")
    private String date;

    @JsonProperty("comment")
    private String comment;

    Attachment(){}

    public Attachment(Integer id, String name, String date, String comment) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
