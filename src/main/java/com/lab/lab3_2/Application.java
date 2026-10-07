package com.lab.lab3_2;

import com.lab.lab3_2.ui.ConsoleUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Главный класс приложения.
 *
 * @author User
 * @version 1.0
 */
@SpringBootApplication
public class Application {

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        ConsoleUI consoleUI = context.getBean(ConsoleUI.class);
        consoleUI.run();
    }
}