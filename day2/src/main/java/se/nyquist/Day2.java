package se.nyquist;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.io.IO.println;
import static java.lang.Math.abs;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Day2 {
    public static void main(String[] args) {
        var input = "input.txt";
        // var input = "sample.txt";
        if (args.length > 0) {
            input = args[0];
        }
        try (var stream = Day2.class.getClassLoader().getResourceAsStream(input)) {
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
        var numbers = lines.stream().map(s -> s.split("\\s+")).toList();
        var safeSequences = numbers.stream()
                .filter(Day2::isMonotonic)
                .filter(Day2::isSafe)
                .toList();
        println(safeSequences.size());
    }

    private static boolean isMonotonic(@NotNull String[] numbers) {
        var diffs = IntStream.range(0, numbers.length-1).mapToObj(i -> {
            var first = Integer.parseInt(numbers[i]);
            var second = Integer.parseInt(numbers[i+1]);
            return Integer.signum(second - first);
        }).collect(groupingBy(i -> i, HashMap::new, counting()));
        return diffs.size() == 1;
    }

    private static boolean isSafe(@NotNull String[] numbers) {
        return IntStream.range(0, numbers.length-1).noneMatch(i -> {
            var first = Integer.parseInt(numbers[i]);
            var second = Integer.parseInt(numbers[i+1]);
            return abs(second - first) > 3;
        });
    }

    private static boolean isSafeAndMonotonic(@NotNull String[] numbers) {
        return isMonotonic(numbers) && isSafe(numbers);
    }

    private static boolean isMonotonic(@NotNull List<String> numbers) {
        var diffs = IntStream.range(0, numbers.size()-1).mapToObj(i -> {
            var first = Integer.parseInt(numbers.get(i));
            var second = Integer.parseInt(numbers.get(i+1));
            return Integer.signum(second - first);
        }).collect(groupingBy(i -> i, HashMap::new, counting()));
        return diffs.size() == 1;
    }

    private static boolean isSafe(@NotNull List<String> numbers) {
        return IntStream.range(0, numbers.size()-1).noneMatch(i -> {
            var first = Integer.parseInt(numbers.get(i));
            var second = Integer.parseInt(numbers.get(i+1));
            return abs(second - first) > 3 || abs(second - first) < 1;
        });
    }

    private static boolean isSafeAndMonotonic(@NotNull List<String> numbers) {
        return isMonotonic(numbers) && isSafe(numbers);
    }

    private static void exercise2(List<String> numbers) {
        var sequences = numbers.stream()
                .map(s -> s.split("\\s+"))
                .map(n -> Map.entry(isSafeAndMonotonic(n),n))
                .collect(groupingBy(Map.Entry::getKey));
        var unsafeSequences = sequences.get(false);
        var mutations = unsafeSequences.stream()
                .map(Map.Entry::getValue)
                        .map(a -> {
                                    var list = Arrays.asList(a);
                                    return IntStream.range(0, a.length)
                                            .mapToObj(i -> {
                                                var result = new ArrayList<String>(a.length-1);
                                                result.addAll(list.subList(0, i));
                                                result.addAll(list.subList(i + 1, list.size()));
                                                return result;
                                            })
                                            .filter(Day2::isSafeAndMonotonic)
                                            .findFirst();
                                })
                .filter(Optional::isPresent)
                .toList();
        println(sequences.get(true).size() + mutations.size());
    }
}
