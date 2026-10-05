package com.lab.lab3_2.service;

import com.lab.lab3_2.model.Figure;
import java.util.List;

/**
 * Интерфейс сервиса для работы с геометрическими фигурами.
 *
 * @author User
 * @version 1.0
 */
public interface FigureService {

    /**
     * Находит площади всех фигур.
     *
     * @param figures список фигур
     * @return список площадей
     */
    List<Double> findAreas(List<Figure> figures);

    /**
     * Средний периметр фигур с количеством сторон больше 5.
     *
     * @param figures список фигур
     * @return средний периметр
     */
    double averagePerimeterBigPolygons(List<Figure> figures);

    /**
     * Сортирует фигуры по возрастанию площади.
     *
     * @param figures список фигур
     * @return отсортированный список
     */
    List<Figure> sortByArea(List<Figure> figures);

    /**
     * Ищет фигуру по названию.
     *
     * @param figures список фигур
     * @param name    название
     * @return фигура или null
     */
    Figure findByName(List<Figure> figures, String name);

    /**
     * Обновляет название фигуры.
     *
     * @param figure  фигура
     * @param newName новое название
     */
    void updateName(Figure figure, String newName);

    /**
     * Возвращает полную информацию о фигуре.
     *
     * @param figure фигура
     * @return строка с полной информацией
     */
    String getFullInfo(Figure figure);
}