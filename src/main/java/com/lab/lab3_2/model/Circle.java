package com.lab.lab3_2.model;

/**
 * Класс, представляющий круг.
 *
 * @author User
 * @version 1.0
 */
public class Circle extends Figure {

    /**
     * Количество сторон круга.
     */
    private static final int SIDE_COUNT = 0;

    /**
     * Радиус круга.
     */
    private double radius;
    
    /**
     * Конструктор по умолчанию
     */
    public Circle(){
        super();
        this.radius = 0;
    }

    /**
     * Конструктор для создания круга.
     *
     * @param id          уникальный идентификатор
     * @param name        название фигуры
     * @param x           координата X центра
     * @param y           координата Y центра
     * @param description описание фигуры
     * @param radius      радиус круга
     */
    public Circle(int id, String name, double x, double y,
                  String description, double radius) {
        super(id, name, x, y, description);
        this.radius = radius;
    }

    /**
     * Вычисляет площадь круга.
     *
     * @return площадь
     */
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    /**
     * Вычисляет периметр круга.
     *
     * @return периметр
     */
    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    /**
     * Возвращает количество сторон круга.
     *
     * @return 0
     */
    @Override
    public int getSideCount() {
        return SIDE_COUNT;
    }

    /**
     * Возвращает информацию о параметрах круга.
     *
     * @return строка с радиусом
     */
    @Override
    public String getParamsInfo() {
        return "Радиус: " + getRadius();
    }

    /**
     * Устанавливает радиус.
     *
     * @param radius новый радиус
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Возвращает радиус.
     *
     * @return радиус
     */
    public double getRadius() {
        return radius;
    }
}