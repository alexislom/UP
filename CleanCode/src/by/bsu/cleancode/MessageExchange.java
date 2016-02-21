package by.bsu.cleancode;

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
    private List<Message> messageHistory;

    public MessageExchange() throws IOException {
        messageHistory = new ArrayList<>();
        FileHandler fileHandler = new FileHandler("logger.log");
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
    }

    public void readFromFile(String filename) {
        try {
            String content = new Scanner(new File(filename)).useDelimiter("\\Z").next();
            JsonReader reader = Json.createReader(new StringReader(content));
            JsonArray items = reader.readArray();
            reader.close();
            for (JsonValue item : items) {
                messageHistory.add(new Message().parseFromJson((JsonObject) item));
            }
            logger.info(Comments.READ_SUCCESSFUL + filename);
        } catch (IOException e) {
            logger.info(Comments.WRONG_PATH);
        }
    }

    public void saveToJsonFile(String file) {
        try {
            JsonWriter writer = Json.createWriter(new FileOutputStream(file));
            JsonArrayBuilder builder = Json.createArrayBuilder();
            if (!messageHistory.isEmpty()) {
                for (Message item : messageHistory) {
                    builder = builder.add(item.getJson());
                }
                writer.writeArray(builder.build());
                writer.close();
                logger.info(Comments.SAVE_SUCCESSFUL + file);
            } else {
                logger.info(Comments.HISTORY_IS_EMPTY);
            }
        } catch (IOException e) {
            logger.info(Comments.WRONG_PATH);
        }
    }

    public void addMessage() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Author:");
        String author = "";
        String message = "";
        if (scan.hasNextLine()) {
            author = scan.nextLine();
        } else {
            logger.info(Comments.WRONG_INPUT);
        }
        System.out.println("Message:");
        if (scan.hasNextLine()) {
            message = scan.nextLine();
        } else {
            logger.info(Comments.WRONG_INPUT);
        }
        Message msg = new Message(author, message);
        messageHistory.add(msg);
        logger.info(Comments.ADD_MESSAGE);
    }

    public void deleteMessage() {
        System.out.println("Enter the message ID to be removed:");
        Scanner scan = new Scanner(System.in);
        String id = "";
        if (scan.hasNextLine()) {
            id = scan.nextLine();
        } else {
            logger.info(Comments.WRONG_INPUT);
        }
        UUID Id = UUID.fromString(id);
        for (Message message : messageHistory) {
            if (message.getId().equals(Id)) {
                messageHistory.remove(message);
                logger.info(Comments.REMOVE);
                return;
            }
        }
        logger.info(Comments.MESSAGE_NOT_FOUND);
    }

    public void viewAllHistory() {
        if (!messageHistory.isEmpty()) {
            Collections.sort(messageHistory, new Comparator<Message>() {
                @Override
                public int compare(Message message1, Message message2) {
                    return message1.getTimestamp().compareTo(message2.getTimestamp());
                }
            });
            StringBuilder stringbuilder = new StringBuilder();
            SimpleDateFormat formatting = new SimpleDateFormat("YYYY:MM:dd HH:mm:ss");
            for (Message item : messageHistory) {
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
                stringbuilder.delete(0, stringbuilder.length());
            }
            logger.info(Comments.VIEW_ALL_HISTORY);
        } else {
            logger.info(Comments.HISTORY_IS_EMPTY);
        }
    }

    public void searchByAuthor() {
        if (!messageHistory.isEmpty()) {
            System.out.println("Enter author:");
            Scanner scan = new Scanner(System.in);
            String author = "";
            if (scan.hasNextLine()) {
                author = scan.nextLine();
            } else {
                logger.info(Comments.WRONG_INPUT);
            }
            boolean flag = false;
            SimpleDateFormat formatting = new SimpleDateFormat("YYYY:MM:dd HH:mm:ss");
            for (Message item : messageHistory) {
                if (item.getAuthor().equals(author)) {
                    StringBuilder stringbuilder = new StringBuilder();
                    flag = true;
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
                    stringbuilder.delete(0, stringbuilder.length());
                    logger.info(Comments.SEARCH_BY_AUTHOR);
                }
            }
            if (!flag) {
                logger.info(Comments.MESSAGE_NOT_FOUND);
            }
        } else {
            logger.info(Comments.HISTORY_IS_EMPTY);
        }
    }

    public void searchByWord() {
        if (!messageHistory.isEmpty()) {
            System.out.println("Enter lexeme:");
            Scanner scan = new Scanner(System.in);
            String lexeme = "";
            if (scan.hasNextLine()) {
                lexeme = scan.nextLine();
            } else {
                logger.info(Comments.WRONG_INPUT);
            }
            boolean flag = false;
            SimpleDateFormat formatting = new SimpleDateFormat("YYYY:MM:dd HH:mm:ss");
            for (Message item : messageHistory) {
                if (item.getMessage().toLowerCase().contains(lexeme.toLowerCase())) {
                    StringBuilder stringbuilder = new StringBuilder();
                    flag = true;
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
                    stringbuilder.delete(0, stringbuilder.length());
                }
            }
            if (!flag) {
                logger.info(Comments.MESSAGE_NOT_FOUND);
            } else {
                logger.info(Comments.SEARCH_BY_LEXEME);
            }
        } else {
            logger.info(Comments.HISTORY_IS_EMPTY);
        }
    }

    public void searchRegex() {
        System.out.println("Enter regex:");
        Scanner scan = new Scanner(System.in);
        String regex = "";
        if (scan.hasNextLine()) {
            regex = scan.nextLine();
        } else {
            logger.info(Comments.WRONG_INPUT);
        }
        Pattern p = Pattern.compile(regex);
        Matcher m;
        SimpleDateFormat formatting = new SimpleDateFormat("YYYY:MM:dd HH:mm:ss");
        boolean flag = false;
        for (Message i : messageHistory) {
            m = p.matcher(i.getMessage());
            if (m.matches()) {
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
                logger.info(Comments.SEARCH_BY_REGEX + regex + Comments.OK);
            }
        }
        if (!flag) {
            logger.info(Comments.SEARCH_BY_REGEX + regex + Comments.FAIL);
        }
    }

    public void messageHistoryPeriod() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter begin of period:");
        String begin = "";
        String end = "";
        if (scan.hasNextLine()) {
            begin = scan.nextLine();
        } else {
            logger.info(Comments.WRONG_INPUT);
        }
        System.out.println("Enter end of period:");
        if (scan.hasNextLine()) {
            end = scan.nextLine();
        } else {
            logger.info(Comments.WRONG_INPUT);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date beginDate = new Date();
        Date endDate = new Date();
        Date messageDate;
        try {
            beginDate = formatter.parse(begin);
            endDate = formatter.parse(end);

        } catch (ParseException e) {
            logger.info(Comments.PARSING_FAILD);
        }
        SimpleDateFormat formatting = new SimpleDateFormat("YYYY:MM:dd HH:mm:ss");
        boolean flag = false;
        for (Message i : messageHistory) {
            messageDate = new Date(i.getTimestamp());
            if (messageDate.after(beginDate) && messageDate.before(endDate)) {
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
            }
        }
        if (!flag) {
            logger.info(Comments.HISTORY_PERIOD + Comments.FAIL);
        } else {
            logger.info(Comments.HISTORY_PERIOD);
        }
    }
}
