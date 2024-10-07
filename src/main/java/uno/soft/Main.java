package uno.soft;

import uno.soft.util.FileUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String[] lines = {
                "\"111\";\"123\";\"222\"",
                "\"200\";\"123\";\"100\"",
                "\"300\";\"\";\"100\"",
                "\"8383\"200000741652251",
                "\"79855053897\"83100000580443402;\"200000133000191\""
        };

        for (String line : lines) {
            String[] split = line.split(";");
            System.out.println(Arrays.toString(split));
        }

//        List<String> lines;
//        try {
//            lines = FileUtil.getLinesFromFile();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//            return;
//        }
//
//        System.out.println(lines.size());
//
//        lines.stream().limit(25).forEach(System.out::println);

    }
}