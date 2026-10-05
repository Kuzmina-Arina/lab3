package com.lab.lab3_2.ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 * Класс для взаимодействия с консолью.
 * <p>
 * Содержит только методы ввода/вывода.
 *
 * @author User
 * @version 1.0
 */
@Component
public class ConsoleUI {

    /** Сканер для чтения ввода. */
    private final Scanner scanner;

    /**
     * Конструктор класса ConsoleUI.
     */
    public ConsoleUI() {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    /**
     * Выводит текст без перевода строки.
     *
     * @param text текст
     */
    public void print(String text) {
        System.out.print(text);
    }

    /**
     * Выводит текст с переводом строки.
     *
     * @param text текст
     */
    public void println(String text) {
        System.out.println(text);
    }

    /**
     * Читает строку с валидацией.
     *
     * @param prompt приглашение
     * @return строка
     */
    public String readLine(String prompt) {
        while (true) {
            try {
                print(prompt);
                String input = scanner.nextLine();
                if (input != null && !input.trim().isEmpty()) {
                    return input.trim();
                }
                println("Ошибка: строка не может быть пустой!");
            } catch (NoSuchElementException e) {
                println("Ошибка: поток ввода закрыт.");
                return null;
            }
        }
    }

    /**
     * Читает int с валидацией.
     *
     * @param prompt приглашение
     * @return число
     */
    public int readInt(String prompt) {
        while (true) {
            try {
                print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (java.util.InputMismatchException e) {
                println("Ошибка: введите целое число!");
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                println("Ошибка: поток ввода закрыт.");
                return 0;
            }
        }
    }

    /**
     * Читает int в диапазоне [min, max].
     *
     * @param prompt приглашение
     * @param min    минимум
     * @param max    максимум
     * @return число
     */
    public int readIntInRange(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            println("Ошибка: значение должно быть от " + min + " до " + max + "!");
        }
    }

    /**
     * Читает double с валидацией.
     *
     * @param prompt приглашение
     * @return число
     */
    public double readDouble(String prompt) {
        while (true) {
            try {
                print(prompt);
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (java.util.InputMismatchException e) {
                println("Ошибка: введите число!");
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                println("Ошибка: поток ввода закрыт.");
                return 0;
            }
        }
    }

    /**
     * Читает положительный double (> 0).
     *
     * @param prompt приглашение
     * @return число > 0
     */
    public double readPositiveDouble(String prompt) {
        while (true) {
            double value = readDouble(prompt);
            if (value > 0) {
                return value;
            }
            println("Ошибка: значение должно быть больше 0!");
        }
    }

    /**
     * Закрывает сканер.
     */
    public void close() {
        scanner.close();
    }
}