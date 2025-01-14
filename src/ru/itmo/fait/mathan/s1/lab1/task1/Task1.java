package ru.itmo.fait.mathan.s1.lab1.task1;

import java.util.ArrayList;
import java.util.function.Function;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Task1 {

    interface IFuncMinimumFinder {

        ArrayList<Double> run(Function<Double, Double> func, double eps, double a, double b);

    }

    static IFuncMinimumFinder dichotomy = new IFuncMinimumFinder() {
        public ArrayList<Double> run(Function<Double, Double> func, double eps, double a, double b) {

            ArrayList<Double> answer = new ArrayList<Double>();

            double mid, y, z, d = eps / 2;

            while ((b - a) > eps) {
                mid = (a + b) / 2;
                y = mid - d;
                z = mid + d;

                if (func.apply(y) <= func.apply(z)) {
                    b = z;
                    answer.add((a + b) / 2);
                } else {
                    a = y;
                    answer.add((a + b) / 2);
                }
            }

            return answer;
        };
    };

    static IFuncMinimumFinder gs = new IFuncMinimumFinder() {
        private static final double PHI = 1.61803398874989484820d;

        @Override
        public ArrayList<Double> run(Function<Double, Double> func, double eps, double a, double b) {
            ArrayList<Double> answer = new ArrayList<>();

            double y, z;
            y = b - (b - a) / PHI;
            z = a + (b - a) / PHI;

            while ((b - a) > eps) {
                if (func.apply(y) <= func.apply(z)) {
                    b = z;
                    z = y;
                    y = b - (b - a) / PHI;
                    answer.add((a + b) / 2);
                } else {
                    a = y;
                    y = z;
                    z = a + (b - a) / PHI;
                    answer.add((a + b) / 2);
                }
            }

            return answer;
        }

    };

    static class GraphPanel extends JPanel {
        private final Function<Double, Double> func;
        private final double a, b, eps;
        private final ArrayList<Double> minima;

        public GraphPanel(Function<Double, Double> func, double eps, double a, double b, ArrayList<Double> minima) {
            this.func = func;
            this.eps = eps;
            this.a = a;
            this.b = b;
            this.minima = minima;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(Color.BLUE);
            for (double x = a; x < b; x += eps) {
                int x1 = translateX(x, a, b, getWidth());
                int y1 = translateY(func.apply(x), a, b, getHeight());
                int x2 = translateX(x + eps, a, b, getWidth());
                int y2 = translateY(func.apply(x + eps), a, b, getHeight());
                g2.drawLine(x1, y1, x2, y2);
            }

            g2.setColor(Color.RED);
            for (double x : minima) {
                int xCoord = translateX(x, a, b, getWidth());
                int yCoord = translateY(func.apply(x), a, b, getHeight());
                g2.fillOval(xCoord - 3, yCoord - 3, 6, 6);
            }
        }

        private int translateX(double x, double a, double b, int width) {
            return (int) ((x - a) / (b - a) * (width));
        }

        private int translateY(double y, double a, double b, int height) {
            double maxY = Double.NEGATIVE_INFINITY;
            double minY = Double.POSITIVE_INFINITY;

            for (double x = a; x <= b; x += eps) {
                double currentY = func.apply(x);
                maxY = Math.max(maxY, currentY);
                minY = Math.min(minY, currentY);
            }

            return (int) ((maxY - y) / (maxY - minY) * height);
        }
    }

    public static void main(String[] args) {

        // Function<Double, Double> func = (x) -> x+2*x*x+3*x*x*x-3;
        Function<Double, Double> func = (x) -> x * x;
        System.out.println("Функция f(x) = x * x");
        double a, b, eps;
        a = -10;
        b = 10;
        eps = .1;

        ArrayList<Double> minDichotomy = dichotomy.run(func, eps, a, b);
        System.out.printf("минимум по дихотомии: %s (%.4f); кол-во итераций: %s\n", minDichotomy.getLast(),
                minDichotomy.getLast(), minDichotomy.size());

        ArrayList<Double> mings = gs.run(func, eps, a, b);
        System.out.printf("минимум по золотому сечению: %s (%.4f); кол-во итераций: %s\n", minDichotomy.getLast(),
                mings.getLast(), mings.size());


        JFrame frame1 = new JFrame("Task1 graph dichotomy visualization");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(800, 600);
        GraphPanel panel1 = new GraphPanel(func, eps, a, b, minDichotomy);
        frame1.add(panel1);
        frame1.setVisible(true);

        JFrame frame2 = new JFrame("Task1 graph gs visualization");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(800, 600);
        GraphPanel panel2 = new GraphPanel(func, eps, a, b, mings);
        frame2.add(panel2);
        frame2.setVisible(true);

    }

}
