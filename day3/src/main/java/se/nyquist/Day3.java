package se.nyquist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.io.IO.println;

public class Day3 {
    public static void main(String[] args) {
        var input = "input.txt";
        // var input = "sample.txt";
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
            throw new RuntimeException(e);
        }
    }

    private static final Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

    private static void exercise1(List<String> lines) {
        var result = lines.stream().map(s -> {
            final Matcher matcher = pattern.matcher(s);
            long sum = 0;
            while (matcher.find()) {
                var x = Integer.parseInt(matcher.group(1));
                var y = Integer.parseInt(matcher.group(2));
                sum += x * y;
            }
            return sum;
        }).reduce(0L, Long::sum);
        println(result);
    }


    private static void exercise2(List<String> numbers) {

    }
}
