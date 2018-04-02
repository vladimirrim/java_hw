package ru.spbau.egorov.hw_9.streams;

import java.util.*;
import java.util.stream.Collectors;

public final class SecondPartTasks {

    private SecondPartTasks() {
    }

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) {
        return paths.parallelStream().filter(s -> s.contains(sequence)).collect(Collectors.toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        Random r = new Random();
        ArrayList<Double> points = new ArrayList<>();
        double numberOfTries = 100000;
        for (int i = 0; i < numberOfTries; i++) {
            double x = r.nextDouble() - 0.5;
            double y = r.nextDouble() - 0.5;
            points.add(x * x + y * y);
        }
        return points.parallelStream().filter(p -> p <= 0.5 * 0.5).count() / numberOfTries;
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        return compositions.entrySet().stream().
                max(Comparator.comparing(s -> s.getValue().stream().collect(Collectors.joining()).length())).get().getKey();
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        return orders.parallelStream().flatMap(s -> s.entrySet().parallelStream()).
                collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)));
    }
}
