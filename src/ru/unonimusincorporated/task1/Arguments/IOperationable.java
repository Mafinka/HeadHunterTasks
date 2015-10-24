package ru.unonimusincorporated.task1.Arguments;

/**
 * Created by user on 17.10.2015.
 * Интерфейс
 */
public interface IOperationable {
    Polinom getPolinom();
    Polinom add(Polinom polinom);
    Polinom multiply(Polinom polinom);
    Polinom pow(int power);
}
