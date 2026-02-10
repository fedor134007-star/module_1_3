package org.example.view.PostView;

import org.example.controller.PostController;
import org.example.model.Post;
import org.example.model.Writer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PostViewImpl implements PostView {

    final PostController postController;
    final Scanner scanner;

    public PostViewImpl(PostController postController, Scanner scanner) {
        this.postController = postController;
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
        IO.println("1 - Добавить пост");
        IO.println("2 - Получить все посты");
        IO.println("3 - Получить пост по ID");
        IO.println("4 - Редактировать пост");
        IO.println("5 - Удалить пост");
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
        IO.println(postController.getAll());
    }


    private void getById() {
        IO.println("Выберите ID поста");
        Long id = inputInt();
        IO.println(postController.getById(id));
    }


    // получить от пользователя имя и фамилию
    // отправить в контроллер
    // получить от пользователя ID к которому прикрепим пост
    private void create() {
        List<Writer> writers = postController.getAllActiveWriters();
        if (writers.isEmpty()) {
            IO.println("Вы не можете добавить пост, нет пользователей к которым можно его прикрепить");
            return; // Просто возвращаемся, без исключений
        }
        writers.forEach(IO::println);
        IO.println("Выберите пользователя и введите его id");
        Long userId = inputInt();
        IO.println("Введите заголовок поста");
        String name = inputString();
        IO.println("Введите контент поста");
        String content = inputString();
        postController.create(name, content, userId);
    }

    // получить от пользователя id имя и фамилию
    // отправить в контроллер
    private void update() {
        this.getAll();
        IO.println("Выберите пост для обновления и введите его ID");
        Long id = inputInt();
        IO.println("Введите заголовок поста");
        String firstName = inputString();
        IO.println("Введите контент поста");
        String lastName = inputString();
        postController.update(id, firstName, lastName);
    }

    // получить от пользователя id
    // отправить в контроллер
    private void delete() {
        this.getAll();
        IO.println("Выберите пост для удаления и введите его ID");
        Long id = inputInt();
        postController.delete(id);
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




