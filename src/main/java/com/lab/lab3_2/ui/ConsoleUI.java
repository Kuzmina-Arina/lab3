package com.lab.lab3_2.ui;

import com.lab.lab3_2.model.Circle;
import com.lab.lab3_2.model.Figure;
import com.lab.lab3_2.model.Polygon;
import com.lab.lab3_2.model.Triangle;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 * Класс для взаимодействия с консолью.
 * Содержит методы ввода/вывода и сценарий работы с фигурами.
 * Вся логика, связанная с фигурами, вынесена в класс {@link Figure}.
 *
 * @author User
 * @version 1.0
 */
@Component
public class ConsoleUI {

    /**
     * Минимальное количество сторон многоугольника.
     */
    private static final int MIN_SIDES = 4;

    /**
     * Максимальное «разумное» количество сторон без предупреждения.
     */
    private static final int SOFT_MAX_SIDES = 100;

    /**
     * Минимальный ID.
     */
    private static final int MIN_ID = 1;

    /**
     * Максимальный ID.
     */
    private static final int MAX_ID = 1_000_000;

    /**
     * Минимальное количество сторон для «большой» фигуры.
     */
    private static final int MIN_BIG_POLYGON_SIDES = 5;

    /**
     * Сканер для чтения ввода.
     */
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
        Figure.initDefaultFigures();
        mainMenu();
        close();
    }

    /**
     * Выводит заголовок.
     */
    private void printHeader() {
        println("\nЗАДАНИЕ 2: Геометрические фигуры");
        println("В коллекции уже есть 2 круга, 2 треугольника, 2 многоугольника.");
    }

    /**
     * Главное меню.
     */
    private void mainMenu() {
        while (true) {
            println("\n=== ГЛАВНОЕ МЕНЮ ===");
            println("1 — Показать все фигуры");
            println("2 — Задача 1: площади фигур");
            println("3 — Задача 2: средний периметр (>5 сторон)");
            println("4 — Задача 3: сортировка по площади");
            println("5 — Задача 4: поиск и редактирование");
            println("6 — Добавить фигуру");
            println("7 — Удалить фигуру по ID");
            println("0 — Выход");
            int choice = readInt("Ваш выбор: ");

            switch (choice) {
                case 1 -> printAllFigures();
                case 2 -> printAreas();
                case 3 -> printAverage();
                case 4 -> printSorted();
                case 5 -> findAndEdit();
                case 6 -> addFigure();
                case 7 -> removeFigure();
                case 0 -> {
                    println("Выход из программы.");
                    return;
                }
                default -> println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    /**
     * Показывает все фигуры.
     */
    private void printAllFigures() {
        println("\n--- Все фигуры (" + Figure.getFigures().size() + ") ---");
        int index = 1;
        for (Figure f : Figure.getFigures()) {
            println("  " + index + ". " + f.getFullInfo());
            index++;
        }
    }

    /**
     * Задача 1: площади фигур.
     */
    private void printAreas() {
        println("\n--- Задача 1: Площади фигур ---");
        int index = 1;
        for (Figure f : Figure.getFigures()) {
            println("  " + index + ". " + f.getName()
                    + " — площадь " + f.getArea());
            index++;
        }
    }

    /**
     * Задача 2: средний периметр.
     */
    private void printAverage() {
        println("\n--- Задача 2: Средний периметр (>"
                + MIN_BIG_POLYGON_SIDES + " сторон) ---");

        int bigCount = 0;
        for (Figure f : Figure.getFigures()) {
            if (f.getSideCount() > MIN_BIG_POLYGON_SIDES) {
                bigCount++;
            }
        }

        println("Фигур с >" + MIN_BIG_POLYGON_SIDES
                + " сторон: " + bigCount);

        if (bigCount == 0) {
            println("Средний периметр: нет данных.");
            return;
        }

        double avg = Figure.averagePerimeterBigPolygons(Figure.getFigures());
        println("Средний периметр: " + avg);
    }

    /**
     * Задача 3: сортировка по площади.
     */
    private void printSorted() {
        println("\n--- Задача 3: Сортировка по площади ---");
        println("До сортировки:");
        printFiguresWithArea();

        println("После сортировки:");
        int index = 1;
        for (Figure f : Figure.sortByArea(Figure.getFigures())) {
            println("  " + index + ". " + f.getName()
                    + " — площадь " + f.getArea());
            index++;
        }
    }

    /**
     * Выводит фигуры с площадями (текущая коллекция).
     */
    private void printFiguresWithArea() {
        int index = 1;
        for (Figure f : Figure.getFigures()) {
            println("  " + index + ". " + f.getName()
                    + " — площадь " + f.getArea());
            index++;
        }
    }

    /**
     * Задача 4: поиск + редактирование.
     */
    private void findAndEdit() {
        println("\n--- Задача 4: Поиск и редактирование ---");
        String name = readLine("Введите название для поиска: ");
        Figure found = Figure.findByName(Figure.getFigures(), name);

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

            if (figure instanceof Circle) {
                println("5 — Радиус");
            } else if (figure instanceof Triangle) {
                println("5 — Сторону A");
                println("6 — Сторону B");
                println("7 — Сторону C");
            } else if (figure instanceof Polygon) {
                println("5 — Радиус описанной окружности");
                println("6 — Количество сторон");
            }

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
                case 5 -> {
                    if (figure instanceof Circle circle) {
                        circle.setRadius(
                                readPositiveDouble("Новый радиус (>0): "));
                        return;
                    } else if (figure instanceof Triangle triangle) {
                        triangle.setSideA(
                                readPositiveDouble("Новая сторона A (>0): "));
                        if (!triangle.isValid()) {
                            println("Ошибка: треугольник с такими сторонами не существует!");
                        }
                        return;
                    } else if (figure instanceof Polygon polygon) {
                        polygon.setRadius(
                                readPositiveDouble("Новый радиус (>0): "));
                        return;
                    }
                }
                case 6 -> {
                    if (figure instanceof Triangle triangle) {
                        triangle.setSideB(
                                readPositiveDouble("Новая сторона B (>0): "));
                        if (!triangle.isValid()) {
                            println("Ошибка: треугольник с такими сторонами не существует!");
                        }
                        return;
                    } else if (figure instanceof Polygon polygon) {
                        int n = readIntInRange(
                                "Новое количество сторон [4..1000]: ",
                                MIN_SIDES, 1000);
                        polygon.setNumberOfSides(n);
                        return;
                    }
                }
                case 7 -> {
                    if (figure instanceof Triangle triangle) {
                        triangle.setSideC(
                                readPositiveDouble("Новая сторона C (>0): "));
                        if (!triangle.isValid()) {
                            println("Ошибка: треугольник с такими сторонами не существует!");
                        }
                        return;
                    }
                }
                default -> println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    /**
     * Добавляет фигуру в коллекцию.
     */
    private void addFigure() {
        Figure newFigure = readFigure();
        Figure.getFigures().add(newFigure);
        println("Фигура добавлена. Всего фигур: "
                + Figure.getFigures().size());
    }

    /**
     * Удаляет фигуру по ID.
     */
    private void removeFigure() {
        int id = readIntInRange(
                "Введите ID фигуры для удаления: ",
                MIN_ID, MAX_ID);

        Figure toRemove = null;
        for (Figure f : Figure.getFigures()) {
            if (f.getId() == id) {
                toRemove = f;
                break;
            }
        }

        if (toRemove == null) {
            println("Фигура с ID " + id + " не найдена.");
            return;
        }

        Figure.getFigures().remove(toRemove);
        println("Фигура удалена. Всего фигур: "
                + Figure.getFigures().size());
    }

    /**
     * Ввод одной фигуры.
     *
     * @return фигура
     */
    private Figure readFigure() {
        println("Выберите тип фигуры:");
        println("1 — Круг");
        println("2 — Треугольник");
        println("3 — Многоугольник");
        int type = readInt("Ваш выбор: ");

        return switch (type) {
            case 1 -> readCircle();
            case 2 -> readTriangle();
            case 3 -> readPolygon();
            default -> {
                println("Неверный тип, попробуйте снова.");
                yield readFigure();
            }
        };
    }

    /**
     * Ввод круга.
     *
     * @return круг
     */
    private Circle readCircle() {
        int id = readUniqueId();
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
     * @return треугольник
     */
    private Triangle readTriangle() {
        int id = readUniqueId();
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
            return readTriangle();
        }
        return triangle;
    }

    /**
     * Ввод многоугольника.
     *
     * @return многоугольник
     */
    private Polygon readPolygon() {
        int id = readUniqueId();
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
                coords[0], coords[1], true, radius);
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
     * @return ID
     */
    private int readUniqueId() {
        while (true) {
            int id = readIntInRange(
                    "Введите ID [1..1000000]: ",
                    MIN_ID, MAX_ID);
            if (!isIdUsed(id)) {
                return id;
            }
            println("Ошибка: ID " + id + " уже используется!");
        }
    }

    /**
     * Проверка использования ID.
     *
     * @param id ID
     * @return true, если ID уже есть
     */
    private boolean isIdUsed(int id) {
        for (Figure f : Figure.getFigures()) {
            if (f.getId() == id) {
                return true;
            }
        }
        return false;
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