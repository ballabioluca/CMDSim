import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String path = System.getProperty("user.home");
        boolean exit = false;

        do {
            File f = new File(path);
            System.out.print(f.getAbsolutePath() + "> ");

            String input = sc.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parti = input.split(" ", 2);
            String cmd = parti[0].toLowerCase();
            String argomento = (parti.length > 1) ? parti[1] : "";

            switch (cmd) {
                case "dir":
                    File[] lista = f.listFiles();
                    if (f.exists()) {
                        System.out.println("\n    Directory di " + f.getAbsolutePath() + "\n");
                        if (lista != null) {
                            for (File file : lista) {
                                String info = file.isDirectory() ? "  <DIR>  " : "         ";
                                System.out.println(TimeConverter.converti(file.lastModified()) + info + file.getName());
                            }
                        }
                        System.out.println();
                    } else {
                        System.out.println("Empty path");
                    }
                    break;

                case "cd":
                    if (argomento.isEmpty()) {
                        path = System.getProperty("user.home");
                    } else if (argomento.equals("..")) {
                        File parent = f.getParentFile();
                        if (parent != null) {
                            path = parent.getAbsolutePath();
                        }
                    } else {
                        File nuovaDir = new File(argomento);
                        if (!nuovaDir.isAbsolute()) {
                            nuovaDir = new File(f, argomento);
                        }

                        if (nuovaDir.exists() && nuovaDir.isDirectory()) {
                            path = nuovaDir.getAbsolutePath();
                        } else {
                            System.out.println("Path not found");
                        }
                    }
                    break;

                case "cd..":
                    File parent = f.getParentFile();
                    if (parent != null) {
                        path = parent.getAbsolutePath();
                    }
                    break;

                case "exit":
                    exit = true;
                    break;

                default:
                    System.out.println("Unknown command");
                    break;
            }
        } while (!exit);
    }
}