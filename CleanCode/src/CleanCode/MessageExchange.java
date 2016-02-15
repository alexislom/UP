package CleanCode;

import javax.json.*;
import java.io.*;
import java.util.*;
import java.text.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageExchange {

    final static Logger logger = Logger.getLogger(MessageExchange.class.getName());

    public ArrayList<Message> arrayList;
    FileHandler fileHandler;


    public MessageExchange() throws IOException {
        arrayList = new ArrayList<>();
        fileHandler = new FileHandler("logger.log");
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
    }
    public void readFromFile(String filename){
        try {
            String content = new Scanner(new File(filename)).useDelimiter("\\Z").next();
            JsonReader reader = Json.createReader(new StringReader(content));
            JsonArray items = reader.readArray();
            reader.close();
            for (JsonValue item : items) {
                arrayList.add(new Message().parseFromJson((JsonObject) item));
            }
        }catch (IOException e){
            logger.info("Wrong path!");
        }
        logger.info("Read successful from file "+ filename );
    }
    public void saveToJsonFile(String file) throws FileNotFoundException {
        JsonWriter writer = Json.createWriter(new FileOutputStream(file));
        JsonArrayBuilder builder = Json.createArrayBuilder();
        if(arrayList.size() != 0) {
            for (Message item : arrayList) {
                builder = builder.add(item.getJson());
            }
            writer.writeArray(builder.build());
            writer.close();
            System.out.println("File " + file + " created");
        }else{
            System.out.println("History is empty");
            logger.info("Error, history is empty");
        }
        logger.info("Save successful to file "+ file );
    }
    public void addMessage(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Author:");
        String author = new String();
        String message = new String();
        if(scan.hasNextLine()) {
            author = scan.nextLine();
        } else {
            System.out.println("Empty input!");
            logger.info("Empty input!");
        }
        System.out.println("Message:");
        if(scan.hasNextLine()) {
            message = scan.nextLine();
        } else {
            System.out.println("Empty input!");
            logger.info("Empty input!");
        }
        UUID id = UUID.randomUUID();
        long timestamp = new Date().getTime();
        Message msg = new Message(id,author,timestamp,message);
        arrayList.add(msg);
        logger.info("Add message successful");
    }
    public void deleteMessage(){
        System.out.println("Enter the message ID to be removed:");
        Scanner scan = new Scanner(System.in);
        String id = new String();
        if(scan.hasNextLine()) {
            id = scan.nextLine();
        } else {
            logger.info("Empty input!");
            System.out.println("Empty input!");
        }
        UUID Id = UUID.fromString(id);
        int i = 0;
        boolean flag = false;
        if(arrayList.size() != 0) {
            for(i = 0;i<arrayList.size();i++){
                if(arrayList.get(i).getId().equals(Id)){
                    flag = true;
                    break;
                }
            }
            if(flag) {
                arrayList.remove(i);
            }else{
                System.out.println("Message not found!");
                logger.info("Message not found!");
            }
        }else{
            logger.info("Error, history is empty");
            System.out.println("History is empty");
        }
        logger.info("Remove successful");
    }
    public void viewAllHistory(){
        if(arrayList.size() !=0 ){
            Collections.sort(arrayList, new Comparator<Message>() {
                @Override
                public int compare(Message message1, Message message2) {
                    return (int) message1.getTimestamp()-(int)message2.getTimestamp();
                }
            });
            StringBuilder stringbuilder = new StringBuilder();
            SimpleDateFormat formatting = new SimpleDateFormat("YYYY:MM:dd HH:mm:ss");
            for(Message item :arrayList){
                stringbuilder.append("Author: ");
                stringbuilder.append(item.getAuthor());
                stringbuilder.append("\nTimestamp: ");
                stringbuilder.append(formatting.format(item.getTimestamp()));
                stringbuilder.append("\nId: ");
                stringbuilder.append(item.getId());
                stringbuilder.append("\nMessage:\n");
                stringbuilder.append(item.getMessage());
                stringbuilder.append("\n");
                System.out.println(stringbuilder);
                stringbuilder.delete(0,stringbuilder.length());
            }
        }else{
            logger.info("Error, history is empty");
            System.out.println("History is empty");
        }
        logger.info("View all history");
    }
    public void searchByAuthor(){
        if(arrayList.size() !=0 ){
            System.out.println("Enter author:");
            Scanner scan = new Scanner(System.in);
            String author = new String();
            if(scan.hasNextLine()) {
                author = scan.nextLine();
            } else {
                logger.info("Empty input!");
                System.out.println("Empty input!");
            }
            int i = 0;
            boolean flag = false;
            SimpleDateFormat formatting = new SimpleDateFormat("YYYY:MM:dd HH:mm:ss");
            for(i = 0;i<arrayList.size();i++){
                if(arrayList.get(i).getAuthor().equals(author)){
                    flag = true;
                    StringBuilder stringbuilder = new StringBuilder();
                    stringbuilder.append("Author: ");
                    stringbuilder.append(arrayList.get(i).getAuthor());
                    stringbuilder.append("\nTimestamp: ");
                    stringbuilder.append(formatting.format(arrayList.get(i).getTimestamp()));
                    stringbuilder.append("\nId: ");
                    stringbuilder.append(arrayList.get(i).getId());
                    stringbuilder.append("\nMessage:\n");
                    stringbuilder.append(arrayList.get(i).getMessage());
                    stringbuilder.append("\n");
                    System.out.println(stringbuilder);
                    stringbuilder.delete(0,stringbuilder.length());
                }
            }
            if(!flag){
                logger.info("Message not found!");
                System.out.println("Messages not found!");
            }
        }else{
            logger.info("Error, history is empty");
            System.out.println("History is empty");
        }
        logger.info("Search by author succsessful");
    }
    public void searchByWord(){
        if(arrayList.size() !=0 ){
            System.out.println("Enter lexeme:");
            Scanner scan = new Scanner(System.in);
            String lexeme = new String();
            if(scan.hasNextLine()) {
                lexeme = scan.nextLine();
            } else {
                logger.info("Empty input!");
                System.out.println("Empty input!");
            }
            int i = 0;
            boolean flag = false;
            SimpleDateFormat formatting = new SimpleDateFormat("YYYY:MM:dd HH:mm:ss");
            for(i = 0;i<arrayList.size();i++){
                if(arrayList.get(i).getMessage().toLowerCase().contains(lexeme.toLowerCase())){
                    flag = true;
                    StringBuilder stringbuilder = new StringBuilder();
                    stringbuilder.append("Author: ");
                    stringbuilder.append(arrayList.get(i).getAuthor());
                    stringbuilder.append("\nTimestamp: ");
                    stringbuilder.append(formatting.format(arrayList.get(i).getTimestamp()));
                    stringbuilder.append("\nId: ");
                    stringbuilder.append(arrayList.get(i).getId());
                    stringbuilder.append("\nMessage:\n");
                    stringbuilder.append(arrayList.get(i).getMessage());
                    stringbuilder.append("\n");
                    System.out.println(stringbuilder);
                    stringbuilder.delete(0,stringbuilder.length());
                }
            }
            if(!flag){
                logger.info("Message not found!");
                System.out.println("Messages not found!");
            }
        }else{
            logger.info("Error, history is empty");
            System.out.println("History is empty");
        }
        logger.info("Search by lexeme succsessful");
    }
    public void searchRegex(){
        System.out.println("Enter regex:");
        Scanner scan = new Scanner(System.in);
        String regex = new String();
        if(scan.hasNextLine()) {
            regex = scan.nextLine();
        } else {
            logger.info("Empty input!");
            System.out.println("Empty input!");
        }
        Pattern p = Pattern.compile(regex);
        Matcher m;
        SimpleDateFormat formatting = new SimpleDateFormat("YYYY:MM:dd HH:mm:ss");
        boolean flag = false;
        for(Message i: arrayList) {
            m = p.matcher(i.getMessage());
            if(m.matches()) {
                StringBuilder stringbuilder = new StringBuilder();
                stringbuilder.append("Author: ");
                stringbuilder.append(i.getAuthor());
                stringbuilder.append("\nTimestamp: ");
                stringbuilder.append(formatting.format(i.getTimestamp()));
                stringbuilder.append("\nId: ");
                stringbuilder.append(i.getId());
                stringbuilder.append("\nMessage:\n");
                stringbuilder.append(i.getMessage());
                stringbuilder.append("\n");
                System.out.println(stringbuilder);
                stringbuilder.delete(0, stringbuilder.length());
                flag = true;
                logger.info("Search messages by regex: " + regex+" succsessful");
            }
        }
        if(!flag){
            logger.info("Search messages by regex: " + regex+" failed");
        }
    }
    public void messageHistoryPeriod(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter begin of period:");
        String begin = new String();
        String end = new String();
        if(scan.hasNextLine()) {
            begin = scan.nextLine();
        }else{
            logger.info("Empty input!");
            System.out.println("Empty input!");
        }
        System.out.println("Enter end of period:");
        if(scan.hasNextLine()){
            end = scan.nextLine();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date beginDate = new Date();
        Date endDate = new Date();
        Date messageDate;
        try {
            beginDate = formatter.parse(begin);
            endDate = formatter.parse(end);

        } catch (ParseException e) {
            logger.info("Parsing failed, uncorrected date");
        }
        SimpleDateFormat formatting = new SimpleDateFormat("YYYY:MM:dd HH:mm:ss");
        boolean flag = false;
        for(Message i: arrayList){
            messageDate = new Date(i.getTimestamp());
            if(messageDate.after(beginDate) && messageDate.before(endDate)){
                StringBuilder stringbuilder = new StringBuilder();
                stringbuilder.append("Author: ");
                stringbuilder.append(i.getAuthor());
                stringbuilder.append("\nTimestamp: ");
                stringbuilder.append(formatting.format(i.getTimestamp()));
                stringbuilder.append("\nId: ");
                stringbuilder.append(i.getId());
                stringbuilder.append("\nMessage:\n");
                stringbuilder.append(i.getMessage());
                stringbuilder.append("\n");
                System.out.println(stringbuilder);
                stringbuilder.delete(0, stringbuilder.length());
                flag = true;
                logger.info("Get message history by period succsessful");
            }
        }
        if(!flag){
            logger.info("Get message history by period failed");
        }
    }
}
