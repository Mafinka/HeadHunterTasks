package ru.unonimusincorporated.task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by user on 17.10.2015.
 * main для второго задания. Можно вызывать отдельно, можно через базовый main
 * предоставяет возможность ввода-вывода через консоль
 */
public class Main {

    public static void main(String[] args) {
//        System.out.println("task2");
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
        if (message.equals("help"))
            System.out.println("введите n и m через пробел, exit - выход из второй задачи");
        else if (message.split(" ").length == 2){
            String[] result = message.split(" ");
            long n = parseLong(result[0]);
            long m = parseLong(result[1]);
            if (m < n){
                System.out.println("n (первое число) должно быть <= m (второе число)");
                parseInput("help");
                return;
            }
            System.out.println("Длина кратчайшей последовательности преобразований: " + iterationCounter(n, m));
        }
        else{
            parseInput("help");
        }
    }

    private static long parseLong(String buffer){
        try{
            long result = Long.parseLong(buffer);
            if (result < 0 || result > 1000000000000000L)
                throw new NumberFormatException("Выход за пределы диапазона");
            return result;
        } catch (NumberFormatException e){
            System.out.println("Входными аргументами должны быть положительные числа в пределах 10^15!");
        }
        return -1;
    }

    // Собственно метод решения. Так как решается в лоб за линейное время, не вижу смысла огород городить
    private static int iterationCounter(long n, long m) {
        int counter = 0;
        long doubleN = n * 2;
        long incN = n + 1;
        // Идем от обратного, уменьшаем m
        while (n < m) {
            // Случай, когда остается только операция n+2
            if (m == 2 && n == 0) {
                counter++;
                break;
            }
            // Если m делится на 2 нацело, внезапно делим на 2 (2n), иначе уменьшаем на 1 (n + 1)
            if (m >= doubleN) {
                if (m % 2 == 0) {
                    m /= 2;
                    counter++;
                } else {
                    m -= 1;
                    counter++;
                }
            } else {
                // В остатке только набор опрецаций n + 2 и  n + 1
                if (m > incN) {
                    m -= 2;
                    counter++;
                } else {
                    m -= 1;
                    counter++;
                }
            }
        }
        return counter;
    }
}
