package org.example.controller;

import org.example.model.Status;
import org.example.model.Writer;
import org.example.view.WriterView.WriterView;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class WriterController {

    private final WriterView writerView;
    private Scanner scanner;

    public WriterController(WriterView writerView, Scanner scanner) {
        this.writerView = writerView;
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
        IO.println("1 - Добавить пользователя");
        IO.println("2 - Получить всех пользователей");
        IO.println("3 - Редактировать пользователя");
        IO.println("4 - Удалить пользователя");
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
        writerView.getAll().forEach(writer -> IO.println(writer.toString()));
    }

    ///  Работа с пользователями
    private void create() {
        Writer writer = new Writer();
        IO.println("Введите имя пользователя");
        writer.setFirstName(inputString());
        IO.println("Введите фамилию пользователя");
        writer.setLastName(inputString());
        IO.println("Введите ID пользователя");
        writer.setId(inputInt());
        writer.setStatus(Status.ACTIVE);
        IO.println(writer.toString());
        writerView.create(writer);
    }


    private void update() {
        this.getAll();
        IO.println("Выберите пользователя для обновления и введите его ID");
        Writer writer = new Writer();
        writer.setId(inputInt());
        IO.println("Введите имя пользователя");
        writer.setFirstName(inputString());
        IO.println("Введите фамилию пользователя");
        writer.setLastName(inputString());
        writer.setStatus(Status.ACTIVE);
        IO.println(writer.toString());
        writerView.update(writer);
    }

    private void delete() {
        this.getAll();
        IO.println("Выберите пользователя для удаления и введите его ID");
        writerView.delete(inputInt());
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
