package org.example;

import org.example.controller.LabelController;
import org.example.controller.PostController;
import org.example.controller.WriterController;

import org.example.model.Label;
import org.example.model.Post;
import org.example.model.Writer;
import org.example.repository.GenericRepository;
import org.example.repository.LabelRepository.GsonLabelRepositoryImpl;
import org.example.repository.PostRepository.GsonPostRepositoryImpl;
import org.example.repository.WriterRepository.GsonWriterRepositoryImpl;

import org.example.view.LabelView.LabelView;
import org.example.view.LabelView.LabelViewImpl;
import org.example.view.PostView.PostView;
import org.example.view.PostView.PostViewImpl;
import org.example.view.WriterView.WriterView;
import org.example.view.WriterView.WriterViewImpl;

import java.util.Scanner;

public class ConsoleApplication {
    public void run() {
        boolean exit = true;
        Scanner scanner = new Scanner(System.in);

        ///  init repository
        GenericRepository<Writer, Long> gsonWriterRepository = new GsonWriterRepositoryImpl();
        GenericRepository<Post, Long> gsonPostRepository = new GsonPostRepositoryImpl();
        GenericRepository<Label, Long> gsonLabelRepository = new GsonLabelRepositoryImpl();


        ///  init controller
        WriterController writerController = new WriterController(gsonWriterRepository, gsonPostRepository, gsonLabelRepository);
        PostController postController = new PostController(gsonPostRepository, gsonLabelRepository, gsonWriterRepository);
        LabelController labelController = new LabelController(gsonLabelRepository, gsonPostRepository);


        /// init view
        WriterView writerView = new WriterViewImpl(writerController, scanner);
        PostView postView = new PostViewImpl(postController, scanner);
        LabelView labelView = new LabelViewImpl(labelController, scanner);

        while (exit) {
            System.out.println("Добро пожаловать в консольное приложение\nЧто бы продолжить работу выберете пункт из меню");
            System.out.println("1 - Пользователи");
            System.out.println("2 - Посты");
            System.out.println("3 - Лейблы");
            System.out.println("0 - Выход из программы");
            String text = scanner.nextLine();

            if (text.equalsIgnoreCase("1")) {
                writerView.mainMenu();
            } else if (text.equalsIgnoreCase("2")) {
                postView.mainMenu();
            } else if (text.equalsIgnoreCase("3")) {
                labelView.mainMenu();
            } else if (text.equalsIgnoreCase("N")) {
                exit = false;
            }
        }
        scanner.close();
        System.out.println("Вы вышли из программы");
    }
}
