package CleanCode;

import javax.json.*;
import java.util.*;

public class Message {

    private UUID id;
    private String author;
    private long timestamp;
    private String message;

    public UUID getId() {return id;}
    public String getAuthor() {return author;}
    public String getMessage() { return message;}
    public long getTimestamp(){return timestamp;}

    public Message(){
        this.id = null;
        this.author = null;
        this.timestamp = 0;
        this.message = null;

    }
    public Message(UUID id,String author,long timestamp,String message){
        this.id = id;
        this.author = author;
        this.timestamp = timestamp;
        this.message = message;
    }
    public Message parseFromJson(JsonObject jsonObject){
        author = jsonObject.getString("author");
        message = jsonObject.getString("message");
        timestamp = jsonObject.getJsonNumber("timestamp").longValue();
        this.id = UUID.fromString(jsonObject.getString("id"));
        return this;
    }
    public JsonObject getJson(){
        return Json.createObjectBuilder()
                .add("author", author)
                .add("message", message)
                .add("id", id.toString())
                .add("timestamp", timestamp).build();
    }
}
