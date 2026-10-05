package com.lab.lab3_2.model;

/**
 * Класс, представляющий многоугольник.
 *
 * @author User
 * @version 1.0
 */
public class Polygon extends Figure {

    /** Минимальное количество сторон. */
    private static final int MIN_POLYGON_SIDES = 3;

    /** Делитель в формуле шнуровки. */
    private static final double SHOELACE_DIVISOR = 2.0;

    /** Количество сторон. */
    private int numberOfSides;

    /** Длины сторон. */
    private double[] sideLengths;

    /** Координаты X вершин. */
    private double[] xCoordinates;

    /** Координаты Y вершин. */
    private double[] yCoordinates;

    /** Флаг выпуклости. */
    private boolean isConvex;

    /**
     * Конструктор.
     *
     * @param id            ID
     * @param name          название
     * @param x             X центра
     * @param y             Y центра
     * @param description   описание
     * @param numberOfSides количество сторон
     * @param sideLengths   длины сторон
     * @param xCoordinates  X вершин
     * @param yCoordinates  Y вершин
     * @param isConvex      флаг выпуклости
     */
    public Polygon(int id, String name, double x, double y,
                   String description, int numberOfSides,
                   double[] sideLengths, double[] xCoordinates,
                   double[] yCoordinates, boolean isConvex) {
        super(id, name, x, y, description);
        this.numberOfSides = numberOfSides;
        this.sideLengths = sideLengths;
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
        this.isConvex = isConvex;
    }

    /**
     * Вычисляет площадь по формуле шнуровки.
     *
     * @return площадь
     */
    @Override
    public double getArea() {
        if (!isValid()) {
            return 0;
        }
        double sum = 0;
        for (int i = 0; i < numberOfSides; i++) {
            int j = (i + 1) % numberOfSides;
            sum += xCoordinates[i] * yCoordinates[j];
            sum -= xCoordinates[j] * yCoordinates[i];
        }
        return Math.abs(sum) / SHOELACE_DIVISOR;
    }

    /**
     * Периметр.
     *
     * @return периметр
     */
    @Override
    public double getPerimeter() {
        double sum = 0;
        for (double side : sideLengths) {
            sum += side;
        }
        return sum;
    }

    /**
     * Количество сторон.
     *
     * @return количество сторон
     */
    @Override
    public int getSideCount() {
        return numberOfSides;
    }

    /**
     * Проверка корректности.
     *
     * @return true, если корректно
     */
    public boolean isValid() {
        return numberOfSides >= MIN_POLYGON_SIDES
                && sideLengths != null
                && sideLengths.length == numberOfSides
                && xCoordinates != null
                && xCoordinates.length == numberOfSides
                && yCoordinates != null
                && yCoordinates.length == numberOfSides;
    }

    /**
     * Пересчёт координат (заглушка).
     */
    public void calculateByCoordinates() {
        // логика может быть расширена
    }

    /**
     * Вычисляет координаты вершин правильного n-угольника.
     *
     * @param cx     X центра
     * @param cy     Y центра
     * @param radius радиус описанной окружности
     * @param n      количество сторон
     * @return массив [x[], y[]]
     */
    public static double[][] calculateCoordinates(double cx, double cy,
                                                  double radius, int n) {
        double[] xs = new double[n];
        double[] ys = new double[n];
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            xs[i] = cx + radius * Math.cos(angle);
            ys[i] = cy + radius * Math.sin(angle);
        }
        return new double[][]{xs, ys};
    }

    /**
     * Вычисляет длины сторон по координатам вершин.
     *
     * @param xs X вершин
     * @param ys Y вершин
     * @return длины сторон
     */
    public static double[] calculateSideLengths(double[] xs, double[] ys) {
        int n = xs.length;
        double[] sides = new double[n];
        for (int i = 0; i < n; i++) {
            int j = (i + 1) % n;
            double dx = xs[j] - xs[i];
            double dy = ys[j] - ys[i];
            sides[i] = Math.sqrt(dx * dx + dy * dy);
        }
        return sides;
    }

    /**
     * Количество сторон.
     *
     * @return количество сторон
     */
    public int getNumberOfSides() {
        return numberOfSides;
    }

    /**
     * Флаг выпуклости.
     *
     * @return true, если выпуклый
     */
    public boolean isConvex() {
        return isConvex;
    }
}