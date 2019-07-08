package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class EmailHelp {
    @JsonProperty("ids")
    private ArrayList<Integer> ids;

    @JsonProperty("header")
    private String header;

    @JsonProperty("message")
    private String message;

    public EmailHelp(){}

    public EmailHelp(ArrayList<Integer> ids, String header, String message) {
        this.ids = ids;
        this.header = header;
        this.message = message;
    }

    public ArrayList<Integer> getIds() {
        return ids;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "EmailHelp{" +
                "ids=" + ids +
                ", header='" + header + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
