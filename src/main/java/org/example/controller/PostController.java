package org.example.controller;

import org.example.model.Label;
import org.example.model.Post;
import org.example.model.Status;
import org.example.view.PostView.PostView;
import org.example.view.WriterView.WriterView;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class PostController {

    private final PostView postView;
    private final WriterView writerView;
    private Scanner scanner;

    public PostController(PostView postView, WriterView writerView, Scanner scanner) {
        this.postView = postView;
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
        IO.println("1 - Добавить пост");
        IO.println("2 - Получить все посты");
        IO.println("3 - Редактировать пост");
        IO.println("4 - Удалить пост");
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
        postView.getAll().forEach(writer -> IO.println(writer.toString()));
    }

    ///  Работа с постами
    private void create() {

        Post post = new Post();

        IO.println("Введите ID пользователя, к которому прикрепится пост");
        writerView.getAll().forEach(writer -> IO.println(writer.toString()));
        post.setUserId(inputInt());
        IO.println("Введите заголовок поста");
        post.setTitle(inputString());
        IO.println("Введите контент поста");
        post.setContent(inputString());
        IO.println("Введите ID поста");
        post.setId(inputInt());
        post.setStatus(Status.ACTIVE);
        IO.println(post.toString());
        postView.create(post);
    }


    private void update() {
        this.getAll();
        IO.println("Выберите пост для обновления и введите его ID");
        Post post = new Post();
        post.setId(inputInt());
        IO.println("Введите заголовок поста");
        post.setTitle(inputString());
        IO.println("Введите контент поста");
        post.setContent(inputString());
        post.setStatus(Status.ACTIVE);
        IO.println(post.toString());
        postView.update(post);
    }

    private void delete() {
        this.getAll();
        IO.println("Выберите пост для удаления и введите его ID");
        postView.delete(inputInt());
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
