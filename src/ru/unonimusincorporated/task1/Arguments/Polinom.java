package ru.unonimusincorporated.task1.Arguments;

import java.util.*;

/**
 * Created by user on 17.10.2015.
 * Служит для представления полинома в развернутом виде и поддержки операций сложеня, умножения и возведения в степень полиномов
 * Также сортирует елементы в порядке убывания степени и осуществляет поиск подобных (одинаковые степнеи переменной).
 */
public class Polinom implements IOperationable{

    private TreeSet<Element> elements;

    public TreeSet<Element> getElements() {
        return elements;
    }

    public Polinom(){
        // Обеспечиваем атоматическую сортировку элементов по убыванию степени
        elements = new TreeSet<Element>(new Comparator<Element>() {
            @Override
            public int compare(Element o1, Element o2) {
                if (o1 == null) return 1;
                if (o2 == null) return -1;
                if (o1.getPower() > o2.getPower()) return -1;
                if (o1.getPower() == o2.getPower()) return 0;
                if (o1.getPower() < o2.getPower()) return 1;
                return 0;
            }
        });
    }

    public void addElement(Element el){
        if (el == null) return;
        // Если константа нулевая, не имеет смысла ее хранить
        if (el.getValue() == 0) return;
        Element ref = null;
        // Во избежание хранения элементов с одинаковыми степенями
        // Ищем эелемент с той же степенью переменной. Дороговато, но лучше делать это сразу.
        // Таким образом будем обеспечивать компактность итогового полинома
        for (Element elem : elements){
            if (elem.getPower() == el.getPower()){
                ref = elem;
                break;
            }
        }
        if (ref == null)
            elements.add(el);
        else{
            ref.add(el);
            if (ref.getValue() == 0)
                elements.remove(ref);
        }
    }

    @Override
    public Polinom getPolinom() {
        return this;
    }

    @Override
    public Polinom add(Polinom polinom) {
        for (Element elem : polinom.getElements()){
            addElement(elem);
        }
        return this;
    }

    public Polinom add(Element element){
        addElement(element);
        return this;
    }

    @Override
    public Polinom multiply(Polinom polinom) {
        Polinom ref = new Polinom();
        for (Element t_elem : this.getElements()){
            for (Element p_elem : polinom.getElements()){
                ref.add(t_elem.newElMultiply(p_elem));
            }
        }
        return ref;
    }

    @Override
    public Polinom pow(int power) {
        Polinom ref = new Polinom();
        for (Element elem : elements){
            ref.addElement(new Element(elem.getValue(),elem.getVar(),elem.getPower()));
        }
        for(int i = 1; i < power; i++){
            ref = ref.multiply(this);
        }
        return ref;
    }

    @Override
    public String toString() {
        // Так как собираем результат из кучи лоскутов, целесообразно использовать StringBuilder
        StringBuilder builder = new StringBuilder();
        boolean flag = false;
        for (Element elem : elements){
            if (elem.getValue() < 0 || !flag) {
                builder.append(elem.toString());
            }
            else{
                builder.append('+');
                builder.append(elem.toString());
            }
            flag = true;
        }
        return builder.toString();
    }
}
