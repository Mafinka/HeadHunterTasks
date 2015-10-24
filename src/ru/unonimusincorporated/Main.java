package ru.unonimusincorporated;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        try {
            parseInput("help");
            String buffer = null;
            while (!(buffer != null && buffer.equals("exit"))){
                buffer = readLine();
                if (!buffer.equals("exit")){
                    parseInput(buffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readLine() throws IOException {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        return bufferRead.readLine();
    }

    private static String readLine(String message) throws IOException {
        System.out.println(message);
        return readLine();
    }

    private static void parseInput(String message) throws IOException {
        if (message.equals("1")) {
            ru.unonimusincorporated.task1.Main.main(new String[0]);
            parseInput("help");
        }
        else if (message.equals("2")){
            ru.unonimusincorporated.task2.Main.main(new String[0]);
            parseInput("help");
        }
        else if (message.equals("help"))
            System.out.println("1 - первая задача, 2 - вторая задача, help - помощь, exit - выход");
        else
            parseInput("help");
    }
}
