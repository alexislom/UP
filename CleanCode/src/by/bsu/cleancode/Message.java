package by.bsu.cleancode;

import javax.json.*;
import java.util.*;

public class Message {

    private UUID id;
    private String author;
    private Long timestamp;
    private String message;

    public Message() {
    }

    public Message(String author, String message){
        this.author = author;
        this.message = message;
        this.id = UUID.randomUUID();
        this.timestamp = new Date().getTime();
    }

    public Message(UUID id, String author, long timestamp, String message) {
        this.id = id;
        this.author = author;
        this.timestamp = timestamp;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Message parseFromJson(JsonObject jsonObject) {
        this.author = jsonObject.getString("author");
        this.message = jsonObject.getString("message");
        this.timestamp = jsonObject.getJsonNumber("timestamp").longValue();
        this.id = UUID.fromString(jsonObject.getString("id"));
        return this;
    }

    public JsonObject getJson() {
        return Json.createObjectBuilder()
                .add("author", author)
                .add("message", message)
                .add("id", id.toString())
                .add("timestamp", timestamp).build();
    }
}
