package uno.soft;

import uno.soft.util.FileUtil;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        List<String> lines;
        try {
            lines = FileUtil.getLinesFromFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        System.out.println(lines.size());

        lines.stream().limit(25).forEach(System.out::println);

    }
}