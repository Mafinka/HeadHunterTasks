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

    public ArrayList<Character> getBuffer() {
        return buffer;
    }

    public boolean isMinus() {
        return minus;
    }

    public void setMinus(boolean minus) {
        this.minus = minus;
    }

    public boolean isExp() {
        return exp;
    }

    public void setExp(boolean exp) {
        this.exp = exp;
    }

    public boolean isVar() {
        return var;
    }

    public void setVar(boolean var) {
        this.var = var;
    }

    public void add(char ch){
        buffer.add(ch);
    }

    public void clear(){
        buffer.clear();
        var = false;
        exp = false;
        minus = false;
    }

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
