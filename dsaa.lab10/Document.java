package dsaa.lab10;

import java.util.ArrayList;
import java.util.Scanner;

public class Document implements IWithName {
    public String name;
    public ArrayList<Link> link;

    public Document(String name) {
        this.name = name.toLowerCase();
        link = new ArrayList<>();
    }

    public Document(String name, Scanner scan) {
        this.name = name.toLowerCase();
        link = new ArrayList<>();
        load(scan);
    }

    public void load(Scanner scan) {
        while (scan.hasNext()) {
            String word = scan.next();
            if (word.equals("eod")) break;
            if (word.startsWith("link=")) {
                String linkStr = word.substring(5);
                Link l = createLink(linkStr);
                if (l != null) {
                    link.add(l);
                }
            }
        }
    }

    public static boolean isCorrectId(String id) {
        if (id == null || id.length() == 0) return false;
        if (!Character.isLetter(id.charAt(0))) return false;
        for (char c : id.toCharArray()) {
            if (!(Character.isLetterOrDigit(c) || c == '_')) return false;
        }
        return true;
    }

    static Link createLink(String linkStr) {
        if (linkStr == null || linkStr.length() == 0) return null;

        String id;
        int weight = 1;

        if (linkStr.contains("(") && linkStr.endsWith(")")) {
            int idx = linkStr.indexOf('(');
            id = linkStr.substring(0, idx);
            try {
                weight = Integer.parseInt(linkStr.substring(idx + 1, linkStr.length() - 1));
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            id = linkStr;
        }

        if (isCorrectId(id)) {
            return new Link(id.toLowerCase(), weight);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder retStr = new StringBuilder("Document: " + name + "\n");
        for (int i = 0; i < link.size(); i++) {
            retStr.append(link.get(i));
            if (i < link.size() - 1) {
                retStr.append(" ");
            }
        }
        return retStr.toString();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String getName() {
        return name;
    }
}
