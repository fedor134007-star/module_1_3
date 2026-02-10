package org.example.view.LabelView;

import org.example.controller.LabelController;
import org.example.model.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LabelViewImpl implements LabelView {

    final LabelController labelController;
    final Scanner scanner;

    public LabelViewImpl(LabelController labelController, Scanner scanner) {
        this.labelController = labelController;
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
        IO.println("1 - Добавить лейбл");
        IO.println("2 - Получить все лейблы");
        IO.println("3 - Получить лейбл по ID");
        IO.println("4 - Редактировать лейбл");
        IO.println("5 - Удалить лейбл");
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
        IO.println(labelController.getAll());
    }


    private void getById() {
        IO.println("Выберите ID поста");
        Long id = inputInt();
        IO.println(labelController.getById(id));
    }


    // получить от пользователя имя и фамилию
    // отправить в контроллер
    // должен выбрать id поста к которому прикрепится лейбл
    private void create() {
        List<Post> posts = labelController.getAllActivePosts();
        if (posts.isEmpty()) {
            IO.println("Вы не можете добавить лейбл, нет постов к которым можно его прикрепить");
            return; // Просто возвращаемся, без исключений
        }
        posts.forEach(IO::println);
        IO.println("Выберите пост и введите его id");
        Long postId = inputInt();
        IO.println("Введите имя лейбла");
        String firstName = inputString();
        labelController.create(firstName, postId);
    }

    // получить от пользователя id имя и фамилию
    // отправить в контроллер
    private void update() {
        this.getAll();
        IO.println("Выберите пост для обновления и введите его ID");
        Long id = inputInt();
        IO.println("Введите имя лейбла");
        String name = inputString();
        labelController.update(id, name);
    }

    // получить от пользователя id
    // отправить в контроллер
    private void delete() {
        this.getAll();
        IO.println("Выберите лейбл для удаления и введите его ID");
        Long id = inputInt();
        labelController.delete(id);
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




