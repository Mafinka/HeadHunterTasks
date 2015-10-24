package ru.unonimusincorporated.task1.Arguments;

import java.io.StringReader;

/**
 * Created by user on 17.10.2015.
 *
 * Класс, описывающий простейший член полинома
 * Может содержать константу (как юольше 0, так и меньше), переменную и степень переменной
 */
public class Element implements IOperationable {
    long value = 1;
    char var = ' ';
    int power = 0;

    public long getValue() {
        return value;
    }

    public char getVar() {
        return var;
    }

    public int getPower() {
        return power;
    }

    public Element(long value){
        this.value = value;
    }

    public Element(long value, char var, int power){
        this.value = value;
        this.var = var;
        this.power = power;
    }

    @Override
    public Polinom getPolinom(){
        Polinom ref = new Polinom();
        ref.addElement(this);
        return ref;
    }

    @Override
    public Polinom pow(int power){
        thisPow(power);
        Polinom ref = new Polinom();
        ref.add(this);
        return ref;
    }

    public void thisPow(int power){
        this.value = (int)Math.pow(this.value,power);
        if (this.power != 0) this.power += power;
    }

    @Override
    public Polinom add(Polinom polinom) {
        Polinom ref = new Polinom();
        ref.addElement(this);
        if (polinom != null){
            for (Element elem : polinom.getElements()){
                ref.addElement(elem);
            }
        }
        return ref;
    }

    // "Опасный" метод для объекдинения элементов (легко получить Exception)
    public void add(Element elem){
        if (elem.getPower() != this.getPower()) throw new IllegalArgumentException("Степени переменных не совпадают!");
        this.value += elem.value;
    }

    @Override
    public Polinom multiply(Polinom polinom) {
        Polinom ref = new Polinom();
        ref.addElement(this);
        if (polinom != null){
            ref = ref.multiply(polinom);
        }
        return ref;
    }

    public Polinom multiply(Element el){
        thisMultiply(el);
        Polinom ref = new Polinom();
        ref.addElement(this);
        return ref;
    }

    // Игнорируем случаи по условиям задачи, когда переменная может быть различная
    public void thisMultiply(Element el){
        this.value *= el.getValue();
        if (this.var == ' ')
            this.var = el.getVar();
        this.power += el.getPower();
    }

    public Element newElMultiply (Element el){
        Element ref = new Element(this.value,this.var,this.power);
        ref.thisMultiply(el);
        return ref;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        if ((this.value == -1 && this.power != 0))
            builder.append('-');
        else if (!(this.value == 1 && this.power != 0))
            builder.append(this.value);
        if (this.power != 0){
            builder.append(this.var);
            if (power != 1) {
                builder.append('^');
                builder.append(this.power);
            }
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        if (value != element.value) return false;
        if (var != element.var) return false;
        return power == element.power;
    }
}
