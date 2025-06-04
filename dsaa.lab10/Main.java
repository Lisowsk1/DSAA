package dsaa.lab10;

import java.util.Iterator;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Main {

    static Scanner scan;

    public static void main(String[] args) {
        System.out.println("START");
        scan = new Scanner(System.in);
        SortedMap<String, Document> sortedMap = new TreeMap<>();
        Document currentDoc = null;
        boolean halt = false;

        while (!halt) {
            String line = scan.nextLine();
            if (line.length() == 0 || line.charAt(0) == '#') continue;
            System.out.println("!" + line);

            String[] word = line.split(" ");

            if (word[0].equalsIgnoreCase("getdoc") && word.length == 2) {
                currentDoc = sortedMap.get(word[1]);
                continue;
            }

            if (word[0].equalsIgnoreCase("ld") && word.length == 2) {
                if (Document.isCorrectId(word[1])) {
                    currentDoc = new Document(word[1], scan);
                    sortedMap.put(currentDoc.name, currentDoc);
                } else {
                    System.out.println("incorrect ID");
                }
                continue;
            }

            if (word[0].equalsIgnoreCase("ha") && word.length == 1) {
                halt = true;
                continue;
            }

            if (word[0].equalsIgnoreCase("clear") && word.length == 1) {
                if (currentDoc != null)
                    currentDoc.link.clear();
                else
                    System.out.println("no current document");
                continue;
            }

            if (word[0].equalsIgnoreCase("show") && word.length == 1) {
                if (currentDoc != null)
                    System.out.println(currentDoc.toString());
                else
                    System.out.println("no current document");
                continue;
            }

            if (word[0].equalsIgnoreCase("size") && word.length == 1) {
                if (currentDoc != null)
                    System.out.println(currentDoc.link.size());
                else
                    System.out.println("no current document");
                continue;
            }

            if (word[0].equalsIgnoreCase("add") && word.length == 2) {
                if (currentDoc != null) {
                    Link link = Document.createLink(word[1]);
                    if (link == null)
                        System.out.println("error");
                    else {
                        currentDoc.link.add(link);
                        System.out.println("true");
                    }
                } else {
                    System.out.println("no current document");
                }
                continue;
            }

            if (word[0].equalsIgnoreCase("get") && word.length == 2) {
                if (currentDoc != null) {
                    boolean found = false;
                    for (Link l : currentDoc.link) {
                        if (l.ref.equals(word[1])) {
                            System.out.println(l);
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                        System.out.println("error");
                } else {
                    System.out.println("no current document");
                }
                continue;
            }

            if (word[0].equalsIgnoreCase("rem") && word.length == 2) {
                if (currentDoc != null) {
                    Iterator<Link> it = currentDoc.link.iterator();
                    boolean removed = false;
                    while (it.hasNext()) {
                        Link l = it.next();
                        if (l.ref.equals(word[1])) {
                            it.remove();
                            System.out.println(l);
                            removed = true;
                            break;
                        }
                    }
                    if (!removed)
                        System.out.println("error");
                } else {
                    System.out.println("no current document");
                }
                continue;
            }

            if (word[0].equalsIgnoreCase("bfs") && word.length == 2) {
                Graph graph = new Graph(sortedMap);
                String str = graph.bfs(word[1]);
                if (str != null)
                    System.out.println(str);
                else
                    System.out.println("error");
                continue;
            }

            if (word[0].equalsIgnoreCase("dfs") && word.length == 2) {
                Graph graph = new Graph(sortedMap);
                String str = graph.dfs(word[1]);
                if (str != null)
                    System.out.println(str);
                else
                    System.out.println("error");
                continue;
            }

            if (word[0].equalsIgnoreCase("cc") && word.length == 1) {
                Graph graph = new Graph(sortedMap);
                int str = graph.connectedComponents();
                System.out.println(str);
                continue;
            }

            System.out.println("Wrong command");
        }

        System.out.println("END OF EXECUTION");
        scan.close();
    }
}
