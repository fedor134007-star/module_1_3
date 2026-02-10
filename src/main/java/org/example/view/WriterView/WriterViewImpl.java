package org.example.view.WriterView;

import org.example.controller.WriterController;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WriterViewImpl implements WriterView {

    final WriterController writerController;
    final Scanner scanner;

    public WriterViewImpl(WriterController writerController, Scanner scanner) {
        this.writerController = writerController;
        this.scanner = scanner;
    }

    Map<Integer, Runnable> commands = new HashMap<>();

    private void initCommands() {
        commands.put(1, this::create);
        commands.put(2, this::getAll);
        commands.put(3, this::getById);
        commands.put(4, this::update);
        commands.put(5, this::delete);

    }

    private void printMenu() {
        IO.println("Введите команду:");
        IO.println("1 - Добавить пользователя");
        IO.println("2 - Получить всех пользователей");
        IO.println("3 - Получить пользователя по ID");
        IO.println("4 - Редактировать пользователя");
        IO.println("5 - Удалить пользователя");
        IO.println("0 - Что бы выйти из данного меню");
    }

    public void mainMenu() {
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
                IO.println("Ошибка: пожалуйста, введите число от 0 до 5!");
                scanner.nextLine();
            }
        }
    }

    // получить всех пользователей
    private void getAll() {
        IO.println(writerController.getAllActiveWriters());
    }


    private void getById() {
        IO.println("Выберите ID пользователя");
        Long id = inputInt();
        IO.println(writerController.getById(id));
    }


    // получить от пользователя имя и фамилию
    // отправить в контроллер
    private void create() {
        IO.println("Введите имя пользователя");
        String firstName = inputString();
        IO.println("Введите фамилию пользователя");
        String lastName = inputString();
        writerController.create(firstName, lastName);
    }

    // получить от пользователя id имя и фамилию
    // отправить в контроллер
    private void update() {
        this.getAll();
        IO.println("Выберите пользователя для обновления и введите его ID");
        Long id = inputInt();
        IO.println("Введите имя пользователя");
        String firstName = inputString();
        IO.println("Введите фамилию пользователя");
        String lastName = inputString();
        writerController.update(id, firstName, lastName);
    }

    // получить от пользователя id
    // отправить в контроллер
    private void delete() {
        this.getAll();
        IO.println("Выберите пользователя для удаления и введите его ID");
        Long id = inputInt();
        writerController.delete(id);
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


    private Long inputInt() {
        Long result = 0L;
        boolean valid = false;

        while (!valid) {
            try {
                result = (long) scanner.nextInt();
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




