package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Phone {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("countryCode")
    private Integer countryCode;

    @JsonProperty("operatorCode")
    private Integer operatorCode;

    @JsonProperty("number")
    private String number;

    @JsonProperty("type")
    private String type;

    @JsonProperty("comment")
    private String comment;

    public Phone() {}

    public Phone(Integer id, Integer countryCode, Integer operatorCode, String number, String type, String comment) {
        this.id = id;
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.number = number;
        this.type = type;
        this.comment = comment;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Integer operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", countryCode=" + countryCode +
                ", operatorCode=" + operatorCode +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
