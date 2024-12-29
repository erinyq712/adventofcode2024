package se.nyquist;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.io.IO.println;

public class Day3 {
    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }
        var input = args[0];
        if (args.length > 0) {
            input = args[0];
        }
        try (var stream = Day3.class.getClassLoader().getResourceAsStream(input)) {
            if (stream != null) {
                var lines = new BufferedReader(new InputStreamReader(stream)).lines().toList();
                exercise1(lines);
                exercise2(lines);
            }
        } catch (IOException e) {
            println(e.getMessage());
        }
    }

    private static final Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

    private static void exercise1(List<String> lines) {
        var result = compute(lines);
        println(result);
    }

    private static @NotNull Long compute(List<String> lines) {
        return lines.stream().map(s -> {
            final Matcher matcher = pattern.matcher(s);
            long sum = 0;
            while (matcher.find()) {
                var x = Integer.parseInt(matcher.group(1));
                var y = Integer.parseInt(matcher.group(2));
                sum += (long) x * y;
            }
            return sum;
        }).reduce(0L, Long::sum);
    }

    private static final Pattern splitPattern = Pattern.compile("(mul|do|don't)\\(((\\d{1,3}),(\\d{1,3}))?\\)");

    private static void exercise2(List<String> lines) {
        var combinedLines = String.join("", lines);
        final Matcher matcher = splitPattern.matcher(combinedLines);
        long sum = 0;
        boolean include = true;
        while (matcher.find()) {
            if (matcher.group(1).equals("mul") && include) {
                var x = Integer.parseInt(matcher.group(matcher.groupCount()-1));
                var y = Integer.parseInt(matcher.group(matcher.groupCount()));
                sum += (long) x * y;
            }
            else if (matcher.group(1).equals("do")) {
                include = true;
            } else if (matcher.group(1).equals("don't")) {
                include = false;
            }
            IntStream.range(0, matcher.groupCount()+1).forEach(i -> println(matcher.group(i)));
        }
        println(sum);
    }
}
