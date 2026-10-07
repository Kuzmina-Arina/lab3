package com.lab.lab3_2.ui;

import com.lab.lab3_2.model.Circle;
import com.lab.lab3_2.model.Figure;
import com.lab.lab3_2.model.Polygon;
import com.lab.lab3_2.model.Triangle;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 * Класс для взаимодействия с консолью.
 * <p>
 * Содержит методы ввода/вывода и сценарий работы с фигурами.
 * Вся логика, связанная с фигурами, вынесена в класс {@link Figure}.
 *
 * @author User
 * @version 1.0
 */
@Component
public class ConsoleUI {

    /** Минимальное количество фигур. */
    private static final int MIN_FIGURES = 1;

    /** Максимальное количество фигур. */
    private static final int MAX_FIGURES = 10;

    /** Минимальное количество сторон многоугольника. */
    private static final int MIN_SIDES = 4;

    /** Максимальное «разумное» количество сторон без предупреждения. */
    private static final int SOFT_MAX_SIDES = 100;

    /** Минимальный ID. */
    private static final int MIN_ID = 1;

    /** Максимальный ID. */
    private static final int MAX_ID = 1_000_000;

    /** Минимальное количество сторон для «большой» фигуры. */
    private static final int MIN_BIG_POLYGON_SIDES = 5;

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
     * Запускает весь сценарий ТЗ.
     */
    public void run() {
        printHeader();

        int count = readIntInRange(
                "Сколько фигур ввести? [1..10]: ",
                MIN_FIGURES, MAX_FIGURES);

        List<Figure> figures = readFigures(count);

        printAreas(figures);
        printAverage(figures);
        printSorted(figures);
        findAndEdit(figures);

        close();
    }

    /**
     * Выводит заголовок.
     */
    private void printHeader() {
        println("\nЗАДАНИЕ 2: Геометрические фигуры");
        println("Введите фигуры — программа выполнит все задачи ТЗ.");
    }

    /**
     * Ввод нескольких фигур.
     *
     * @param count количество
     * @return список фигур
     */
    private List<Figure> readFigures(int count) {
        List<Figure> figures = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            println("\n--- Фигура " + i + " ---");
            figures.add(readFigure(figures));
        }
        return figures;
    }

    /**
     * Ввод одной фигуры.
     *
     * @param existing уже введённые фигуры
     * @return фигура
     */
    private Figure readFigure(List<Figure> existing) {
        println("Выберите тип фигуры:");
        println("1 — Круг");
        println("2 — Треугольник");
        println("3 — Многоугольник");
        int type = readInt("Ваш выбор: ");

        return switch (type) {
            case 1 -> readCircle(existing);
            case 2 -> readTriangle(existing);
            case 3 -> readPolygon(existing);
            default -> {
                println("Неверный тип, попробуйте снова.");
                yield readFigure(existing);
            }
        };
    }

    /**
     * Ввод круга.
     *
     * @param existing уже введённые фигуры
     * @return круг
     */
    private Circle readCircle(List<Figure> existing) {
        int id = readUniqueId(existing);
        String name = readLine("Введите название: ");
        double x = readDouble("Введите X центра: ");
        double y = readDouble("Введите Y центра: ");
        String description = readLine("Введите описание: ");
        double radius = readPositiveDouble("Введите радиус (>0): ");
        return new Circle(id, name, x, y, description, radius);
    }

    /**
     * Ввод треугольника.
     *
     * @param existing уже введённые фигуры
     * @return треугольник
     */
    private Triangle readTriangle(List<Figure> existing) {
        int id = readUniqueId(existing);
        String name = readLine("Введите название: ");
        double x = readDouble("Введите X центра: ");
        double y = readDouble("Введите Y центра: ");
        String description = readLine("Введите описание: ");
        double sideA = readPositiveDouble("Введите сторону A (>0): ");
        double sideB = readPositiveDouble("Введите сторону B (>0): ");
        double sideC = readPositiveDouble("Введите сторону C (>0): ");

        Triangle triangle = new Triangle(id, name, x, y, description,
                sideA, sideB, sideC);

        if (!triangle.isValid()) {
            println("Ошибка: треугольник с такими сторонами не существует!");
            return readTriangle(existing);
        }
        return triangle;
    }

    /**
     * Ввод многоугольника.
     *
     * @param existing уже введённые фигуры
     * @return многоугольник
     */
    private Polygon readPolygon(List<Figure> existing) {
        int id = readUniqueId(existing);
        String name = readLine("Введите название: ");
        double x = readDouble("Введите X центра: ");
        double y = readDouble("Введите Y центра: ");
        String description = readLine("Введите описание: ");
        int n = readSidesCount();
        double radius = readPositiveDouble(
                "Введите радиус описанной окружности (>0): ");

        double[][] coords = Polygon.calculateCoordinates(x, y, radius, n);
        double[] sides = Polygon.calculateSideLengths(coords[0], coords[1]);

        return new Polygon(id, name, x, y, description, n, sides,
                coords[0], coords[1], true);
    }

    /**
     * Ввод количества сторон с предупреждением.
     *
     * @return число сторон
     */
    private int readSidesCount() {
        while (true) {
            int n = readIntInRange(
                    "Введите количество сторон [4..1000]: ",
                    MIN_SIDES, Integer.MAX_VALUE);
            if (n <= SOFT_MAX_SIDES) {
                return n;
            }
            println("Внимание: " + n + " сторон — это очень много.");
            String answer = readLine("Продолжить? (да/нет): ");
            if (answer.equalsIgnoreCase("да")) {
                return n;
            }
        }
    }

    /**
     * Уникальный ID.
     *
     * @param existing уже введённые фигуры
     * @return ID
     */
    private int readUniqueId(List<Figure> existing) {
        while (true) {
            int id = readIntInRange(
                    "Введите ID [1..1000000]: ",
                    MIN_ID, MAX_ID);
            if (!isIdUsed(id, existing)) {
                return id;
            }
            println("Ошибка: ID " + id + " уже используется!");
        }
    }

    /**
     * Проверка использования ID.
     *
     * @param id       ID
     * @param existing список фигур
     * @return true, если ID уже есть
     */
    private boolean isIdUsed(int id, List<Figure> existing) {
        for (Figure f : existing) {
            if (f.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Задача 1: площади фигур.
     *
     * @param figures список фигур
     */
    private void printAreas(List<Figure> figures) {
        println("\n--- Задача 1: Площади фигур ---");
        List<Double> areas = Figure.findAreas(figures);
        for (int i = 0; i < figures.size(); i++) {
            println("  " + (i + 1) + ". " + figures.get(i).getName()
                    + " — площадь " + areas.get(i));
        }
    }

    /**
     * Задача 2: средний периметр.
     *
     * @param figures список фигур
     */
    private void printAverage(List<Figure> figures) {
        println("\n--- Задача 2: Средний периметр (>"
                + MIN_BIG_POLYGON_SIDES + " сторон) ---");

        int bigCount = countBigPolygons(figures);
        println("Фигур с >" + MIN_BIG_POLYGON_SIDES
                + " сторон: " + bigCount);

        if (bigCount == 0) {
            println("Средний периметр: нет данных.");
            return;
        }

        double avg = Figure.averagePerimeterBigPolygons(figures);
        println("Средний периметр: " + avg);
    }

    /**
     * Считает фигуры с >5 сторон.
     *
     * @param figures список фигур
     * @return количество
     */
    private int countBigPolygons(List<Figure> figures) {
        int count = 0;
        for (Figure f : figures) {
            if (f.getSideCount() > MIN_BIG_POLYGON_SIDES) {
                count++;
            }
        }
        return count;
    }

    /**
     * Задача 3: сортировка по площади.
     *
     * @param figures список фигур
     */
    private void printSorted(List<Figure> figures) {
        println("\n--- Задача 3: Сортировка по площади ---");
        println("До сортировки:");
        printFiguresWithArea(figures);

        List<Figure> sorted = Figure.sortByArea(figures);

        println("После сортировки:");
        printFiguresWithArea(sorted);
    }

    /**
     * Выводит фигуры с площадями.
     *
     * @param figures список фигур
     */
    private void printFiguresWithArea(List<Figure> figures) {
        int index = 1;
        for (Figure f : figures) {
            println("  " + index + ". " + f.getName()
                    + " — площадь " + f.getArea());
            index++;
        }
    }

    /**
     * Задача 4: поиск + редактирование.
     *
     * @param figures список фигур
     */
    private void findAndEdit(List<Figure> figures) {
        println("\n--- Задача 4: Поиск и редактирование ---");
        String name = readLine("Введите название для поиска: ");
        Figure found = Figure.findByName(figures, name);

        if (found == null) {
            println("Фигура \"" + name + "\" не найдена.");
            return;
        }

        println("\nНайдено:");
        println("  " + found.getFullInfo());

        editField(found);

        println("\nПосле редактирования:");
        println("  " + found.getFullInfo());
    }

    /**
     * Меню редактирования поля.
     *
     * @param figure фигура
     */
    private void editField(Figure figure) {
        while (true) {
            println("\nЧто изменить?");
            println("0 — Ничего не менять");
            println("1 — Название");
            println("2 — Координату X");
            println("3 — Координату Y");
            println("4 — Описание");
            int choice = readInt("Ваш выбор: ");

            switch (choice) {
                case 0 -> {
                    println("Изменения отменены.");
                    return;
                }
                case 1 -> {
                    figure.setName(readLine("Новое название: "));
                    return;
                }
                case 2 -> {
                    figure.setX(readDouble("Новая X: "));
                    return;
                }
                case 3 -> {
                    figure.setY(readDouble("Новая Y: "));
                    return;
                }
                case 4 -> {
                    figure.setDescription(readLine("Новое описание: "));
                    return;
                }
                default -> println("Неверный выбор. Попробуйте снова.");
            }
        }
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