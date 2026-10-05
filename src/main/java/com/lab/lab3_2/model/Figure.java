package com.lab.lab3_2.model;

/**
 * Абстрактный класс, представляющий геометрическую фигуру.
 *
 * @author User
 * @version 1.0
 */
public abstract class Figure {

    /** Уникальный идентификатор фигуры. */
    private int id;

    /** Название фигуры. */
    private String name;

    /** Координата X центра фигуры. */
    private double x;

    /** Координата Y центра фигуры. */
    private double y;

    /** Описание фигуры. */
    private String description;

    /**
     * Конструктор для создания фигуры.
     *
     * @param id          уникальный идентификатор
     * @param name        название фигуры
     * @param x           координата X центра
     * @param y           координата Y центра
     * @param description описание фигуры
     */
    public Figure(int id, String name, double x, double y, String description) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.description = description;
    }

    /**
     * Вычисляет площадь фигуры.
     *
     * @return площадь
     */
    public abstract double getArea();

    /**
     * Вычисляет периметр фигуры.
     *
     * @return периметр
     */
    public abstract double getPerimeter();

    /**
     * Возвращает количество сторон фигуры.
     *
     * @return количество сторон
     */
    public abstract int getSideCount();

    /**
     * Возвращает полную информацию о фигуре.
     *
     * @return строка с полной информацией
     */
    public String getFullInfo() {
        return "ID: " + id
                + ", Название: " + name
                + ", Координаты центра: (" + x + ", " + y + ")"
                + ", Описание: " + description;
    }

    /**
     * Устанавливает новое название фигуры.
     *
     * @param name новое название
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Устанавливает новую координату X.
     *
     * @param x новая координата X
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Устанавливает новую координату Y.
     *
     * @param y новая координата Y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Устанавливает новое описание.
     *
     * @param description новое описание
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Возвращает идентификатор фигуры.
     *
     * @return идентификатор
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает название фигуры.
     *
     * @return название
     */
    public String getName() {
        return name;
    }

    /**
     * Возвращает координату X центра.
     *
     * @return координата X
     */
    public double getX() {
        return x;
    }

    /**
     * Возвращает координату Y центра.
     *
     * @return координата Y
     */
    public double getY() {
        return y;
    }

    /**
     * Возвращает описание фигуры.
     *
     * @return описание
     */
    public String getDescription() {
        return description;
    }
}