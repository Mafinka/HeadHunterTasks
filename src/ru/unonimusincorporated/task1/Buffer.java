package ru.unonimusincorporated.task1;

import java.util.ArrayList;

/**
 * Created by user on 17.10.2015.
 * Буфер char-ов для поддержки парсинга строки в классе Expression
 */
public class Buffer {

    boolean minus = false;
    boolean exp = false;
    boolean var = false;
    ArrayList<Character> buffer = new java.util.ArrayList<Character>();

    // Буффер текущих символов
    public ArrayList<Character> getBuffer() {
        return buffer;
    }

    // Флаг наличия минуса в выражении
    public boolean isMinus() {
        return minus;
    }

    public void setMinus(boolean minus) {
        this.minus = minus;
    }

    // Флаг того, что в буфере содеожатся символы, которые следует интерпретировать как выражение (Expression)
    public boolean isExp() {
        return exp;
    }

    public void setExp(boolean exp) {
        this.exp = exp;
    }

    // Флаг того, что в буффере содержатся символы, которые следует интерпретировать как элемент с переменной (Element)
    public boolean isVar() {
        return var;
    }

    public void setVar(boolean var) {
        this.var = var;
    }

    // Внезапно, добавление символа в буффер
    public void add(char ch){
        buffer.add(ch);
    }

    // Сброс буффера и всех флагов
    public void clear(){
        buffer.clear();
        var = false;
        exp = false;
        minus = false;
    }

    // Есть ли хоть что-то в буффере
    public boolean isEmpty(){
        return buffer.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (char x : buffer){
            builder.append(x);
        }
        return builder.toString();
    }
}
