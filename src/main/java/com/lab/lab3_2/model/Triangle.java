package com.lab.lab3_2.model;

/**
 * Класс, представляющий треугольник.
 *
 * @author User
 * @version 1.0
 */
public class Triangle extends Figure {

    /**
     * Количество сторон треугольника.
     */
    private static final int SIDE_COUNT = 3;

    /**
     * Сторона A.
     */
    private double sideA;

    /**
     * Сторона B.
     */
    private double sideB;

    /**
     * Сторона C.
     */
    private double sideC;
    
    /**
     * Конструктор по умолчанию
     */
    public Triangle(){
        super();
        this.sideA = 0;
        this.sideB = 0;
        this.sideC = 0;
    }

    /**
     * Конструктор для создания треугольника.
     *
     * @param id          идентификатор
     * @param name        название
     * @param x           координата X
     * @param y           координата Y
     * @param description описание
     * @param sideA       сторона A
     * @param sideB       сторона B
     * @param sideC       сторона C
     */
    public Triangle(int id, String name, double x, double y,
                    String description, double sideA,
                    double sideB, double sideC) {
        super(id, name, x, y, description);
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    /**
     * Вычисляет площадь по формуле Герона.
     *
     * @return площадь
     */
    @Override
    public double getArea() {
        if (!isValid()) {
            return 0;
        }
        double p = getPerimeter() / 2.0;
        return Math.sqrt(p * (p - sideA) * (p - sideB) * (p - sideC));
    }

    /**
     * Вычисляет периметр.
     *
     * @return периметр
     */
    @Override
    public double getPerimeter() {
        return sideA + sideB + sideC;
    }

    /**
     * Возвращает количество сторон.
     *
     * @return 3
     */
    @Override
    public int getSideCount() {
        return SIDE_COUNT;
    }

    /**
     * Проверяет корректность треугольника.
     *
     * @return true, если корректен
     */
    public boolean isValid() {
        return sideA > 0 && sideB > 0 && sideC > 0
                && sideA + sideB > sideC
                && sideA + sideC > sideB
                && sideB + sideC > sideA;
    }

    /**
     * Возвращает информацию о параметрах треугольника.
     *
     * @return строка со сторонами
     */
    @Override
    public String getParamsInfo() {
        return "Стороны: A=" + getSideA()
                + ", B=" + getSideB()
                + ", C=" + getSideC();
    }

    /**
     * Устанавливает сторону A.
     *
     * @param sideA новая сторона A
     */
    public void setSideA(double sideA) {
        this.sideA = sideA;
    }

    /**
     * Устанавливает сторону B.
     *
     * @param sideB новая сторона B
     */
    public void setSideB(double sideB) {
        this.sideB = sideB;
    }

    /**
     * Устанавливает сторону C.
     *
     * @param sideC новая сторона C
     */
    public void setSideC(double sideC) {
        this.sideC = sideC;
    }

    /**
     * Возвращает сторону A.
     *
     * @return сторона A
     */
    public double getSideA() {
        return sideA;
    }

    /**
     * Возвращает сторону B.
     *
     * @return сторона B
     */
    public double getSideB() {
        return sideB;
    }

    /**
     * Возвращает сторону C.
     *
     * @return сторона C
     */
    public double getSideC() {
        return sideC;
    }
}