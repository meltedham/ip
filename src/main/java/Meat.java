import java.util.Scanner;

public class Meat {
    public static void main(String[] args) {
        String name = "Meat";
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?\n");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        while (!input.equals("bye"))
        {
            System.out.println(input);
            input = scanner.next();
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}