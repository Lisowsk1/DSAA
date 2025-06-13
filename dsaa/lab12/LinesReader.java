package dsaa.lab12;

import java.util.Scanner;

public class LinesReader {

    public String concatLines(int count, Scanner scanner) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            if (!scanner.hasNextLine())
                break;
            sb.append(scanner.nextLine());
        }
        return sb.toString();
    }
}
