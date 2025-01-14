package ru.itmo.fait.mathan.s1.lab1;

import java.util.Scanner;

import ru.itmo.fait.mathan.s1.lab1.task1.Task1;
import ru.itmo.fait.mathan.s1.lab1.task2.Task2;

public class App {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Какую задачку запустить? [1/2] >");
                if (scanner.hasNextLine()) {
                    switch (scanner.nextLine()) {
                        case "1":
                            Task1.main(args);
                            break;
                        case "2":
                            Task2.main(args);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
