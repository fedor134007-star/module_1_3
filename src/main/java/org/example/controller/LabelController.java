package org.example.controller;

import org.example.model.Label;
import org.example.model.Status;
import org.example.view.LabelView.LabelView;
import org.example.view.PostView.PostView;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LabelController {

    private final LabelView labelView;
    private final PostView postView;
    private Scanner scanner;

    public LabelController(LabelView labelView, PostView postView, Scanner scanner) {
        this.labelView = labelView;
        this.postView = postView;
        this.scanner = scanner;
    }

    Map<Integer, Runnable> commands = new HashMap<>();

    private void initCommands() {
        commands.put(1, this::create);
        commands.put(2, this::getAll);
        commands.put(3, this::update);
        commands.put(4, this::delete);

    }

    private void printMenu() {
        IO.println("Введите команду:");
        IO.println("1 - Добавить лейбл");
        IO.println("2 - Получить все лейблы");
        IO.println("3 - Редактировать лейбл");
        IO.println("4 - Удалить лейбл");
        IO.println("0 - Что бы выйти из данного меню");
    }

    public boolean mainMenu() {
        initCommands();
        boolean exit = true;
        while (exit) {
            printMenu();
            try {
                int command = scanner.nextInt();
                scanner.nextLine();
                if (command == 0) {
                    exit = false;
                    continue;
                }
                if (commands.containsKey(command)) commands.get(command).run();
            } catch (Exception e) {
                IO.println("Ошибка: пожалуйста, введите число от 0 до 4!");
                scanner.nextLine();
            }

        }
        return false;
    }


    private void getAll() {
        labelView.getAll().forEach(writer -> IO.println(writer.toString()));
    }

    ///  Работа с лейблами
    private void create() {
        Label label = new Label();
        IO.println("Введите ID поста, к которому прикрепится лейбл");
        postView.getAll().forEach(writer -> IO.println(writer.toString()));
        label.setPostId(inputInt());
        IO.println("Введите имя лейбла");
        label.setName(inputString());
        IO.println("Введите ID лейбла");
        label.setId(inputInt());
        label.setStatus(Status.ACTIVE);
        IO.println(label.toString());
        labelView.create(label);
    }


    private void update() {
        this.getAll();
        IO.println("Выберите лейбл для обновления и введите его ID");
        Label label = new Label();
        label.setId(inputInt());
        IO.println("Введите имя лейбла");
        label.setName(inputString());
        label.setStatus(Status.ACTIVE);
        IO.println(label.toString());
        labelView.update(label);
    }

    private void delete() {
        this.getAll();
        IO.println("Выберите лейбл для удаления и введите его ID");
        labelView.delete(inputInt());
    }


    private String inputString() {
        String result = null;
        boolean valid = false;

        while (!valid) {
            try {
                result = scanner.nextLine().trim();

                if (result.isEmpty()) {
                    IO.println("Вы не ввели значение. Попробуйте еще раз:");
                } else {
                    valid = true;
                }
            } catch (Exception e) {
                IO.println("Произошла ошибка попробуйте еще раз");
            }
        }
        return result;
    }


    private int inputInt() {
        int result = 0;
        boolean valid = false;

        while (!valid) {
            try {
                result = scanner.nextInt();
                scanner.nextLine();
                if (result == 0) {
                    IO.println("Введите значение больше 0");
                } else {
                    valid = true;
                }
            } catch (Exception e) {
                IO.println("Произошла ошибка попробуйте еще раз");
                scanner.nextLine();
            }
        }
        return result;
    }
}
