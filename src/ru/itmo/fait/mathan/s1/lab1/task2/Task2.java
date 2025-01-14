package ru.itmo.fait.mathan.s1.lab1.task2;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Task2 {

    interface RootFinder {

        ArrayList<Double> findRoot(Function<Double, Double> func, double eps, double a, double b);

    }

    static RootFinder bisection = new RootFinder() {

        @Override
        public ArrayList<Double> findRoot(Function<Double, Double> func, double eps, double a, double b) {
            ArrayList<Double> answer = new ArrayList<>();

            double c;

            while ((b - a) / 2 > eps) {
                c = (a + b) / 2;

                if (Math.abs(func.apply(c)) < eps) {
                    answer.add(c);
                    return answer;
                } else if (func.apply(a) * func.apply(c) < 0) {
                    b = c;
                    answer.add(b);
                } else {
                    a = c;
                    answer.add(a);
                }
            }
            return answer;
        }

    };

    public static void main(String[] args) {
        Function<Double, Double> func = (x) -> x - 4;
        System.out.println("Функция f(x) = x - 4");

        double target = 2d;
        // BiFunction<Double, Double, Double> mse = (x,y) -> (x-y)*(x-y);
        BiFunction<ArrayList<Double>, Double, Double> std = (vals, mean) -> Math.sqrt(
                vals.stream()
                        .mapToDouble(v -> Math.pow(v - mean, 2))
                        .average()
                        .orElse(0.0));

        double a = -2;
        double b = 2;
        double eps = .001;

        ArrayList<Double> bisectionRoot = bisection.findRoot(func, eps, a, b);
        System.out.printf("Корень: %s (%.4f) за %s итераций, rds от ожидаемого: %.4f \n",
                bisectionRoot.getLast(),
                bisectionRoot.getLast(),
                bisectionRoot.size(),
                std.apply(bisectionRoot, target));

    }
}
