package com.lab.lab3_2.service.impl;

import com.lab.lab3_2.model.Figure;
import com.lab.lab3_2.service.FigureService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для работы с фигурами.
 *
 * @author User
 * @version 1.0
 */
@Service
public class FigureServiceImpl implements FigureService {

    /** Минимальное количество сторон для «большой» фигуры (из ТЗ). */
    public static final int MIN_BIG_POLYGON_SIDES = 5;

    /**
     * Находит площади всех фигур.
     *
     * @param figures список фигур
     * @return список площадей
     */
    @Override
    public List<Double> findAreas(List<Figure> figures) {
        List<Double> areas = new ArrayList<>();
        for (Figure figure : figures) {
            areas.add(figure.getArea());
        }
        return areas;
    }

    /**
     * Средний периметр фигур с количеством сторон больше 5.
     *
     * @param figures список фигур
     * @return средний периметр или 0
     */
    @Override
    public double averagePerimeterBigPolygons(List<Figure> figures) {
        double sum = 0;
        int count = 0;
        for (Figure figure : figures) {
            if (figure.getSideCount() > MIN_BIG_POLYGON_SIDES) {
                sum += figure.getPerimeter();
                count++;
            }
        }
        return count == 0 ? 0 : sum / count;
    }

    /**
     * Сортирует фигуры по возрастанию площади.
     *
     * @param figures список фигур
     * @return отсортированный список
     */
    @Override
    public List<Figure> sortByArea(List<Figure> figures) {
        List<Figure> sorted = new ArrayList<>(figures);
        sorted.sort(Comparator.comparingDouble(Figure::getArea));
        return sorted;
    }

    /**
     * Ищет фигуру по названию.
     *
     * @param figures список фигур
     * @param name    название
     * @return фигура или null
     */
    @Override
    public Figure findByName(List<Figure> figures, String name) {
        for (Figure figure : figures) {
            if (figure.getName().equalsIgnoreCase(name)) {
                return figure;
            }
        }
        return null;
    }

    /**
     * Обновляет название фигуры.
     *
     * @param figure  фигура
     * @param newName новое название
     */
    @Override
    public void updateName(Figure figure, String newName) {
        if (figure != null) {
            figure.setName(newName);
        }
    }

    /**
     * Возвращает полную информацию о фигуре.
     *
     * @param figure фигура
     * @return строка с полной информацией
     */
    @Override
    public String getFullInfo(Figure figure) {
        return figure == null ? "Фигура не найдена" : figure.getFullInfo();
    }
}