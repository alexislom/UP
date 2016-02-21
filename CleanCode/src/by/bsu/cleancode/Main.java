package by.bsu.cleancode;

import java.io.*;
import java.util.Scanner;

public class Main {

    public void help(){
        System.out.println(Comments.MENU);
    }
    public void menu() throws IOException {
        MessageExchange messageExchange = new MessageExchange();
        String line;
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println(Comments.INPUT_COMMAND);
            line = scan.nextLine();
            switch (line) {
                case "read": {
                    System.out.print(Comments.INPUT_PATH);
                    messageExchange.readFromFile(scan.nextLine());//"input.json");
                    break;
                }
                case "save": {
                    System.out.print(Comments.INPUT_PATH);
                    messageExchange.saveToJsonFile(scan.nextLine());//"output.json");
                    break;
                }
                case "add": {
                    messageExchange.addMessage();
                    break;
                }
                case "history": {
                    messageExchange.viewAllHistory();
                    break;
                }
                case "delete": {
                    messageExchange.deleteMessage();
                    break;
                }
                case "search -a": {
                    messageExchange.searchByAuthor();
                    break;
                }
                case "search -w": {
                    messageExchange.searchByWord();
                    break;
                }
                case "serach -r": {
                    messageExchange.searchRegex();
                    break;
                }
                case "search -h": {
                    System.out.println("Date format: dd.MM.yyyy");
                    messageExchange.messageHistoryPeriod();
                    break;
                }
                case "help": {
                    this.help();
                    break;
                }
                default: {
                    System.out.println(Comments.NO_SUCH_COMMAND);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.help();
        main.menu();
    }
}
