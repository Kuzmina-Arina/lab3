package com.lab.lab3_2.runner;

import com.lab.lab3_2.model.Circle;
import com.lab.lab3_2.model.Figure;
import com.lab.lab3_2.model.Polygon;
import com.lab.lab3_2.model.Triangle;
import com.lab.lab3_2.service.FigureService;
import com.lab.lab3_2.service.impl.FigureServiceImpl;
import com.lab.lab3_2.ui.ConsoleUI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Связка UI и сервиса. Выполняет логику ТЗ.
 *
 * @author User
 * @version 1.0
 */
@Component
public class FigureRunner {

    /** Минимальное количество фигур. */
    private static final int MIN_FIGURES = 1;

    /** Максимальное количество фигур. */
    private static final int MAX_FIGURES = 10;

    /** Минимальное количество сторон многоугольника. */
    private static final int MIN_SIDES = 3;

    /** Максимальное «разумное» количество сторон без предупреждения. */
    private static final int SOFT_MAX_SIDES = 100;

    /** Минимальный ID. */
    private static final int MIN_ID = 1;

    /** UI для ввода/вывода. */
    @Autowired
    private ConsoleUI consoleUI;

    /** Сервис логики. */
    @Autowired
    private FigureService figureService;

    /**
     * Запускает весь сценарий ТЗ.
     */
    public void run() {
        printHeader();

        int count = consoleUI.readIntInRange(
                "Сколько фигур ввести? [1..10]: ",
                MIN_FIGURES, MAX_FIGURES);

        List<Figure> figures = readFigures(count);

        printAreas(figures);
        printAverage(figures);
        printSorted(figures);
        findAndEdit(figures);

        consoleUI.close();
    }

    /**
     * Заголовок.
     */
    private void printHeader() {
        consoleUI.println("\nЗАДАНИЕ 2: Геометрические фигуры");
        consoleUI.println("Введите фигуры — программа выполнит все задачи ТЗ.");
    }

    /**
     * Ввод нескольких фигур.
     *
     * @param count количество
     * @return список
     */
    private List<Figure> readFigures(int count) {
        List<Figure> figures = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            consoleUI.println("\n--- Фигура " + i + " ---");
            figures.add(readFigure(figures));
        }
        return figures;
    }

    /**
     * Ввод одной фигуры.
     *
     * @param existing уже введённые
     * @return фигура
     */
    private Figure readFigure(List<Figure> existing) {
        consoleUI.println("Выберите тип фигуры:");
        consoleUI.println("1 — Круг");
        consoleUI.println("2 — Треугольник");
        consoleUI.println("3 — Многоугольник");
        int type = consoleUI.readInt("Ваш выбор: ");

        return switch (type) {
            case 1 -> readCircle(existing);
            case 2 -> readTriangle(existing);
            case 3 -> readPolygon(existing);
            default -> {
                consoleUI.println("Неверный тип, попробуйте снова.");
                yield readFigure(existing);
            }
        };
    }

    /**
     * Ввод круга.
     *
     * @param existing уже введённые
     * @return Circle
     */
    private Circle readCircle(List<Figure> existing) {
        int id = readUniqueId(existing);
        String name = consoleUI.readLine("Введите название: ");
        double x = consoleUI.readDouble("Введите X центра: ");
        double y = consoleUI.readDouble("Введите Y центра: ");
        String description = consoleUI.readLine("Введите описание: ");
        double radius = consoleUI.readPositiveDouble("Введите радиус (>0): ");
        return new Circle(id, name, x, y, description, radius);
    }

    /**
     * Ввод треугольника.
     *
     * @param existing уже введённые
     * @return Triangle
     */
    private Triangle readTriangle(List<Figure> existing) {
        int id = readUniqueId(existing);
        String name = consoleUI.readLine("Введите название: ");
        double x = consoleUI.readDouble("Введите X центра: ");
        double y = consoleUI.readDouble("Введите Y центра: ");
        String description = consoleUI.readLine("Введите описание: ");
        double sideA = consoleUI.readPositiveDouble("Введите сторону A (>0): ");
        double sideB = consoleUI.readPositiveDouble("Введите сторону B (>0): ");
        double sideC = consoleUI.readPositiveDouble("Введите сторону C (>0): ");

        if (!isValidTriangle(sideA, sideB, sideC)) {
            consoleUI.println("Ошибка: треугольник с такими сторонами не существует!");
            return readTriangle(existing);
        }
        return new Triangle(id, name, x, y, description, sideA, sideB, sideC);
    }

    /**
     * Ввод многоугольника.
     *
     * @param existing уже введённые
     * @return Polygon
     */
    private Polygon readPolygon(List<Figure> existing) {
        int id = readUniqueId(existing);
        String name = consoleUI.readLine("Введите название: ");
        double x = consoleUI.readDouble("Введите X центра: ");
        double y = consoleUI.readDouble("Введите Y центра: ");
        String description = consoleUI.readLine("Введите описание: ");
        int n = readSidesCount();
        double radius = consoleUI.readPositiveDouble(
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
            int n = consoleUI.readIntInRange(
                    "Введите количество сторон [3..1000]: ",
                    MIN_SIDES, Integer.MAX_VALUE);
            if (n <= SOFT_MAX_SIDES) {
                return n;
            }
            consoleUI.println("Внимание: " + n + " сторон — это очень много.");
            String answer = consoleUI.readLine("Продолжить? (да/нет): ");
            if (answer.equalsIgnoreCase("да")) {
                return n;
            }
        }
    }

    /**
     * Проверяет неравенство треугольника.
     *
     * @param a сторона A
     * @param b сторона B
     * @param c сторона C
     * @return true, если треугольник существует
     */
    private boolean isValidTriangle(double a, double b, double c) {
        return a + b > c && a + c > b && b + c > a;
    }

    /**
     * Уникальный ID.
     *
     * @param existing уже введённые
     * @return ID
     */
    private int readUniqueId(List<Figure> existing) {
        while (true) {
            int id = consoleUI.readIntInRange(
                    "Введите ID [1..1000000]: ",
                    MIN_ID, 1_000_000);
            if (!isIdUsed(id, existing)) {
                return id;
            }
            consoleUI.println("Ошибка: ID " + id + " уже используется!");
        }
    }

    /**
     * Проверка использования ID.
     *
     * @param id       ID
     * @param existing список
     * @return true, если уже есть
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
     * Задача 1: площади.
     *
     * @param figures список
     */
    private void printAreas(List<Figure> figures) {
        consoleUI.println("\n--- Задача 1: Площади фигур ---");
        List<Double> areas = figureService.findAreas(figures);
        for (int i = 0; i < figures.size(); i++) {
            consoleUI.println("  " + (i + 1) + ". " + figures.get(i).getName()
                    + " — площадь " + areas.get(i));
        }
    }

    /**
     * Задача 2: средний периметр.
     *
     * @param figures список
     */
    private void printAverage(List<Figure> figures) {
        consoleUI.println("\n--- Задача 2: Средний периметр (>" 
                + FigureServiceImpl.MIN_BIG_POLYGON_SIDES + " сторон) ---");

        int bigCount = countBigPolygons(figures);
        consoleUI.println("Фигур с >"
                + FigureServiceImpl.MIN_BIG_POLYGON_SIDES
                + " сторон: " + bigCount);

        if (bigCount == 0) {
            consoleUI.println("Средний периметр: нет данных.");
            return;
        }
        double avg = figureService.averagePerimeterBigPolygons(figures);
        consoleUI.println("Средний периметр: " + avg);
    }

    /**
     * Считает фигуры с >5 сторон.
     *
     * @param figures список
     * @return количество
     */
    private int countBigPolygons(List<Figure> figures) {
        int count = 0;
        for (Figure f : figures) {
            if (f.getSideCount() > FigureServiceImpl.MIN_BIG_POLYGON_SIDES) {
                count++;
            }
        }
        return count;
    }

    /**
     * Задача 3: сортировка.
     *
     * @param figures список
     */
    private void printSorted(List<Figure> figures) {
        consoleUI.println("\n--- Задача 3: Сортировка по площади ---");
        consoleUI.println("До сортировки:");
        printFiguresWithArea(figures);

        List<Figure> sorted = figureService.sortByArea(figures);

        consoleUI.println("После сортировки:");
        printFiguresWithArea(sorted);
    }

    /**
     * Выводит фигуры с площадями.
     *
     * @param figures список
     */
    private void printFiguresWithArea(List<Figure> figures) {
        int index = 1;
        for (Figure f : figures) {
            consoleUI.println("  " + index + ". " + f.getName()
                    + " — площадь " + f.getArea());
            index++;
        }
    }

    /**
     * Задача 4: поиск + редактирование.
     *
     * @param figures список
     */
    private void findAndEdit(List<Figure> figures) {
        consoleUI.println("\n--- Задача 4: Поиск и редактирование ---");
        String name = consoleUI.readLine("Введите название для поиска: ");
        Figure found = figureService.findByName(figures, name);

        if (found == null) {
            consoleUI.println("Фигура \"" + name + "\" не найдена.");
            return;
        }

        consoleUI.println("\nНайдено:");
        consoleUI.println("  " + found.getFullInfo());

        editField(found);

        consoleUI.println("\nПосле редактирования:");
        consoleUI.println("  " + figureService.getFullInfo(found));
    }

    /**
     * Меню редактирования поля.
     *
     * @param figure фигура
     */
    private void editField(Figure figure) {
        while (true) {
            consoleUI.println("\nЧто изменить?");
            consoleUI.println("0 — Ничего не менять");
            consoleUI.println("1 — Название");
            consoleUI.println("2 — Координату X");
            consoleUI.println("3 — Координату Y");
            consoleUI.println("4 — Описание");
            int choice = consoleUI.readInt("Ваш выбор: ");

            switch (choice) {
                case 0 -> {
                    consoleUI.println("Изменения отменены.");
                    return;
                }
                case 1 -> {
                    figure.setName(consoleUI.readLine("Новое название: "));
                    return;
                }
                case 2 -> {
                    figure.setX(consoleUI.readDouble("Новая X: "));
                    return;
                }
                case 3 -> {
                    figure.setY(consoleUI.readDouble("Новая Y: "));
                    return;
                }
                case 4 -> {
                    figure.setDescription(consoleUI.readLine("Новое описание: "));
                    return;
                }
                default -> consoleUI.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }
}