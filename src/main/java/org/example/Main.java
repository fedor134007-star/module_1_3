package org.example;

import org.example.controller.LabelController;
import org.example.controller.PostController;
import org.example.controller.WriterController;
import org.example.repository.LabelRepository.GsonLabelRepositoryImpl;
import org.example.repository.LabelRepository.LabelRepository;
import org.example.repository.PostRepository.GsonPostRepositoryImpl;
import org.example.repository.PostRepository.PostRepository;
import org.example.repository.WriterRepository.WriterRepository;
import org.example.repository.WriterRepository.GsonWriterRepositoryImpl;
import org.example.view.LabelView.LabelView;
import org.example.view.LabelView.LabelViewImpl;
import org.example.view.PostView.PostView;
import org.example.view.PostView.PostViewImpl;
import org.example.view.WriterView.WriterView;
import org.example.view.WriterView.WriterViewImpl;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        boolean exit = true;
        Scanner scanner = new Scanner(System.in);

        ///  init repository
        WriterRepository gsonWriterRepository = new GsonWriterRepositoryImpl();
        PostRepository gsonPostRepository = new GsonPostRepositoryImpl();
        LabelRepository gsonLabelRepository = new GsonLabelRepositoryImpl();
        /// init view
        WriterView writerView = new WriterViewImpl(gsonWriterRepository, gsonPostRepository, gsonLabelRepository);
        PostView postView = new PostViewImpl(gsonPostRepository, gsonLabelRepository, gsonWriterRepository);
        LabelView labelView = new LabelViewImpl(gsonLabelRepository, gsonPostRepository);

        ///  init controller
        WriterController writerController = new WriterController(writerView, scanner);
        PostController postController = new PostController(postView, writerView, scanner);
        LabelController labelController = new LabelController(labelView, postView, scanner);

        while (exit) {
            System.out.println("Добро пожаловать в консольное приложение\nЧто бы продолжить работу выберете пункт из меню");
            System.out.println("1 - Пользователи");
            System.out.println("2 - Посты");
            System.out.println("3 - Лейблы");
            System.out.println("0 - Выход из программы");
            String text = scanner.nextLine();

            if (text.equalsIgnoreCase("1")) {
                writerController.mainMenu();
            } else if (text.equalsIgnoreCase("2")) {
                postController.mainMenu();
            } else if (text.equalsIgnoreCase("3")) {
                labelController.mainMenu();
            } else if (text.equalsIgnoreCase("N")) {
                exit = false;
            }
        }
        scanner.close();
        System.out.println("Вы вышли из программы");

    }

}
