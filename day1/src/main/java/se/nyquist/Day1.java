package se.nyquist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.io.IO.println;
import static java.lang.Math.abs;

public class Day1 {
    public static void main(String[] args) {
        var input = "input.txt";
        // var input = "sample.txt";
        if (args.length > 0) {
            input = args[0];
        }
        try (var stream = Day1.class.getClassLoader().getResourceAsStream(input)) {
            if (stream != null) {
                var lines = new BufferedReader(new InputStreamReader(stream)).lines().toList();
                exercise1(lines);
                exercise2(lines);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void exercise1(List<String> lines) {
        var p = lines.stream().map(s -> s.split("\\s+")).toList();
        var left = p.stream().map(pair -> Integer.valueOf(pair[0])).sorted().toList();
        var right = p.stream().map(pair -> Integer.valueOf(pair[1])).sorted().toList();
        var sum = IntStream.range(0,left.size()).map(i -> abs(left.get(i)-right.get(i))).sum();
        println(sum);
    }

    private static void exercise2(List<String> lines) {
        var p = lines.stream().map(s -> s.split("\\s+")).toList();
        var left = p.stream().map(pair -> Integer.valueOf(pair[0])).toList();
        var right = p.stream().map(pair -> Integer.valueOf(pair[1]))
                .collect(Collectors.groupingBy(i->i, Collectors.counting()));
        var sum = IntStream.range(0,left.size()).mapToLong(i -> abs(left.get(i)*right.getOrDefault(left.get(i),0L))).sum();
        println(sum);
    }
}
