package CleanCode;

import java.io.*;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws IOException {

        MessageExchange messageExchange = new MessageExchange();
        int number = Integer.MAX_VALUE;

        System.out.println("1 : Downloading messages from file");
        System.out.println("2 : Save messages to file");
        System.out.println("3 : Add message");
        System.out.println("4 : View the history of messages in chronological order");
        System.out.println("5 : Delete the message by ID");
        System.out.println("6 : Search by author");
        System.out.println("7 : Search by lexeme");
        System.out.println("8 : Search by regex");
        System.out.println("9 : View history for a certain period");
        System.out.println("0 : Exit from menu");

        do {
            Scanner scan = new Scanner(System.in);
            System.out.println("Put the number of command:");
            if(scan.hasNextInt()) {
                number = scan.nextInt();
            } else {
                System.out.println("Wrong Input!");
            }
            switch (number) {
                case 1: {
                    messageExchange.readFromFile("input.json");
                    break;
                }
                case 2: {
                    messageExchange.saveToJsonFile("output.json");
                    break;
                }
                case 3: {
                    messageExchange.addMessage();
                    break;
                }
                case 4:{
                    messageExchange.viewAllHistory();
                    break;
                }
                case 5:{
                    messageExchange.deleteMessage();
                    break;
                }
                case 6:{
                    messageExchange.searchByAuthor();
                    break;
                }
                case 7:{
                    messageExchange.searchByWord();
                    break;
                }
                case 8:{
                    messageExchange.searchRegex();
                    break;
                }
                case 9:{
                    messageExchange.messageHistoryPeriod();
                    break;
                }
            }
        }while(number != 0);
    }
}
