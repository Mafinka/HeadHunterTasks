package ru.unonimusincorporated.task1.Arguments;

/**
 * Created by user on 17.10.2015.
 * Интерфейс для обязанности реализацции методов основных операций для полиномов и входного выражения
 * Можно было использовать наследование, но учитывая абсолютно разную реализацию методов для разных элементов,
 * непонятно, как определять методы.
 */
public interface IOperationable {
    Polinom getPolinom();
    Polinom add(Polinom polinom);
    Polinom multiply(Polinom polinom);
    Polinom pow(int power);
}
