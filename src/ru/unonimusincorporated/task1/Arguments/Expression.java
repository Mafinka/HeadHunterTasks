package ru.unonimusincorporated.task1.Arguments;

import ru.unonimusincorporated.task1.Buffer;

import java.util.ArrayList;

/**
 * Created by user on 17.10.2015.
 * Служит для хранения выражения в строковом выиде и поддержки метода toString, для которого вычисляется
 * итоговый полином
 */
public class Expression implements IOperationable{
    private String str;

    public void setString(String str) {
        this.str = str;
    }

    public String getString() {
        return str;
    }

    public Expression(String str){
        if (str == null || str.isEmpty()) throw new IllegalArgumentException("Выражение не может быть пустым");
        // К черту пробелы, просьба не пихать всякие странные символы
        // как говорится, для прототипа сойдет
        this.str = str.replaceAll(" ","");
    }

    private Polinom parseString(){
        // Будем обрабатывать входное выражение как массив char-ов
        char[] arr = str.toCharArray();
        // Буффер для хранения символов текущего слагаемого или множителя, с поддержкой служебных флагов
        Buffer buffer = new Buffer();
        // Буффер для хранения множителей (как эелементов, так и выражений или даже полиномов)
        ArrayList<IOperationable> multiplyBuffer = new ArrayList<IOperationable>();
        // Счетчик уровня скобок
        int level = 0;
        // Итоговый полином
        Polinom result = new Polinom();
        // Нужен для поддержки степеней выражений (степень после скобок)
        ArrayList<Character> powerBuffer = new ArrayList<Character>();
        for (int i = 0; i < arr.length; i++){
            if (arr[i] == '(') {
                // Если имеем дело с умножением, то буфер будет не пустой, поэтому надо добавить значение в буффер умножения
                if (!buffer.isEmpty() && level == 0){
                    if (powerBuffer.isEmpty())
                        multiplyBuffer.add(releaseBuffer(buffer));
                    else
                        multiplyBuffer.add(releaseBuffer(buffer).pow(releasePowerBuffer(powerBuffer)));
                }
                // для случая -1
                if (buffer.isMinus())
                    multiplyBuffer.add(new Element(-1));
                // Увеличиваем счетчик уровня скобок и устанавливаем флаг, что это выражение, скобку не записываем в буфер
                if (level != 0)
                    buffer.add(arr[i]);
                level++;
                if (!buffer.isExp())
                    buffer.setExp(true);
            }
            // Закрывающая скобка, level != 0
            else if (level > 0 && arr[i] != ')')
                buffer.add(arr[i]);
            // Закрывающая скобка
            else if (arr[i] == ')'){
                // Уменьшаем счетчик уровня вложенности
                level--;
                if (level != 0)
                    buffer.add(arr[i]);
            }
            // При нахождении плюса или минуса сворачиваем все степени и множители
            else if (level == 0 && (arr[i] == '+' || arr[i] == '-')){
                // Если буффер умножения пустой
                if (multiplyBuffer.isEmpty()){
                    // Если буфер степни пустой, то просто складываем итоговый полином с вычисленным
                    // Если нет, то предварительно возводим в степень
                    if (powerBuffer.isEmpty())
                        result.add(releaseBuffer(buffer).getPolinom());
                    else
                        result.add(releaseBuffer(buffer).getPolinom().pow(releasePowerBuffer(powerBuffer)));
                }
                // Если буфер множителей не пустой
                else{
                    // Варианты в зависимости от того, пустой или не пустой буфер степени
                    if (powerBuffer.isEmpty())
                        multiplyBuffer.add(releaseBuffer(buffer));
                    else
                        multiplyBuffer.add(releaseBuffer(buffer).pow(releasePowerBuffer(powerBuffer)));
                    // В любом случае необходимо произвести перемножение и сложить с итоговым полиномом
                    result.add(releaseMultiplyBuffer(multiplyBuffer));
                }
                if (arr[i] == '-') buffer.setMinus(true);
            }
            // Проверка на степень выражения
            else if (arr[i] == '^'){
                if (buffer.isExp() && level == 0)
                    powerBuffer.add(arr[i]);
                else
                    buffer.add(arr[i]);
            }
            // Если у нас что-то в буфере степени, добавляем в него
            else if (!powerBuffer.isEmpty()){
                powerBuffer.add(arr[i]);
            }
            // Случай, кгда после скобки сразу идет умножение на какой-либо элемент
            else if (buffer.isExp() && level == 0){
                multiplyBuffer.add(releaseBuffer(buffer));
                buffer.setVar(true);
                buffer.add(arr[i]);
            }
            // Если после всех проверок встречаем символ, то это - переменная
            else if (!isNumber(arr[i])){
                buffer.setVar(true);
                buffer.add(arr[i]);
            }
            // Если элемент прошел все фильтры, то остается только его дописать в буффер
            else{
                if (powerBuffer.isEmpty())
                    buffer.add(arr[i]);
                else
                    powerBuffer.add(arr[i]);
            }
            // Проверка на конец строки
            if (i == arr.length - 1 && level != 0){
                throw new IllegalArgumentException("Несовпадение колическтва открывающих и закрывающих скобок!");
            }
            else if (i == arr.length - 1 && !buffer.isEmpty()){
                if (multiplyBuffer.isEmpty())
                    if (powerBuffer.isEmpty())
                        result.add(releaseBuffer(buffer).getPolinom());
                    else
                        result.add(releaseBuffer(buffer).getPolinom().pow(releasePowerBuffer(powerBuffer)));
                else {
                    if (powerBuffer.isEmpty())
                        multiplyBuffer.add(releaseBuffer(buffer));
                    else
                        multiplyBuffer.add(releaseBuffer(buffer).pow(releasePowerBuffer(powerBuffer)));
                    result.add(releaseMultiplyBuffer(multiplyBuffer));
                }
            }
            else if (i == arr.length - 1 && !multiplyBuffer.isEmpty()) {
                result.add(releaseMultiplyBuffer(multiplyBuffer));
            }
        }
        return result;
    }

    private IOperationable releaseBuffer(Buffer buffer){
        IOperationable result;
        if (buffer.isExp()){
            result = new Expression(buffer.toString());
        }
        else{
            result = createElement(buffer);
        }
        buffer.clear();
        return result;
    }

    private Polinom releaseMultiplyBuffer (ArrayList<IOperationable> multiplyBuffer){
        Polinom ref = null;
        for (IOperationable elem : multiplyBuffer){
            if (ref == null)
                ref = elem.getPolinom();
            else
                ref = ref.multiply(elem.getPolinom());
        }
        multiplyBuffer.clear();
        return ref;
    }

    private int releasePowerBuffer (ArrayList<Character> powerBuffer){
        int power = 0;
        for (char x : powerBuffer){
            if (x != '^'){
                power *= 10;
                power += (int) x - (int)'0';
            }
        }
        powerBuffer.clear();
        return power;
    }

    // Ввиду затруднений в распознавани элементов для создания вынескено в отдельный метод
    private Element createElement(Buffer buffer){
        ArrayList<Character> temp =  buffer.getBuffer();
        int value = 0;
        int varIndex = -1;
        for (int i = 0; i < temp.size();i++){
            if (i == 0 && !isNumber(temp.get(0))) {
                value = 1;
                varIndex = 0;
                break;
            }
            else if (isNumber(temp.get(i))){
                value *= 10;
                value += (int) temp.get(i) - (int)'0';
            }
            else {
                varIndex = i;
                break;
            }
        }
        // Если установлен флаг минуса, действия очевидны
        if (buffer.isMinus()) value = -value;
        // Инициализируем Element как переменную
        if (buffer.isVar()) {
            // Если переменная, то надо определить степень
            int power = 1;
            if (temp.size() > varIndex + 1 && temp.get(varIndex + 1) == '^'){
                power = 0;
                for (int i = varIndex + 2; i < temp.size();i++){
                    if (!isNumber(temp.get(i))) throw new IllegalArgumentException("В степени не число!");
                    power *= 10;
                    power += (int) temp.get(i) - (int)'0';
                }
            }
            return new Element(value,temp.get(varIndex),power);
        }
        // Инициализируем Element как число
        else if (!buffer.isExp()) {
            return new Element(value);
        }
        return null;
    }

    private boolean isNumber(char ch) {
        // Проверка по кодировке, что это число
        return (int) ch >= (int)'0' && (int) ch <= (int)'9';
    }

    @Override
    public Polinom getPolinom() {
        return parseString();
    }

    @Override
    public Polinom add(Polinom polinom) {
        return getPolinom().add(polinom);
    }

    @Override
    public Polinom multiply(Polinom polinom) {
        return getPolinom().multiply(polinom);
    }

    @Override
    public Polinom pow(int power) {
        return getPolinom().pow(power);
    }

    @Override
    public String toString(){
        return getPolinom().toString();
    }
}
