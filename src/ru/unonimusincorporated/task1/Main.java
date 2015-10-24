package ru.unonimusincorporated.task1;

import ru.unonimusincorporated.task1.Arguments.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by user on 17.10.2015.
 */
public class Main {

    public static void main(String[] args) {
//        System.out.println(new Expression("-x^2 - 10x + 1 + 21 + (x - 1)(x + 1)"));
//        System.out.println(new Expression("-x^2 - 10x + 1 + 21"));
//        System.out.println(new Expression("x(x + 1) + (x(x + 1) - 1)(x + 1) + 1"));
//        System.out.println(new Expression("(x(x + 1) + (x + 1)^2 - 1)"));
//        System.out.println(new Expression("(x + 1)^2"));
//        System.out.println(new Expression("(x-23x^2-25x^5)^10+ 12x^2(7x)+(x-4)4x^6(2x^2-3)"));
//        System.out.println(new Expression("(x-4)(-4)x^3(2x^2-3)"));
//        System.out.println(new Expression("(x-x^2-x^5)^500"));

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
        } catch (Exception e){
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
        if (message.equals("help"))
            System.out.println("exit - выход из первой задачи, остальное понимается как выражение");
        else{
            System.out.println("Результат: " + (new Expression(message)).toString());
        }
    }
}
