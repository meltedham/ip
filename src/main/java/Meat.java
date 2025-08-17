import java.util.ArrayList;
import java.util.Scanner;

public class Meat {
    public static void main(String[] args) {
        String name = "Meat";
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?\n");

        ArrayList<String> list = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye"))
        {
            if (input.equals("list"))
            {
                for (int i = 0; i < list.size(); i++)
                {
                    System.out.println(i + 1 + ". " + list.get(i));
                }
            } else {
                list.add(input);
                System.out.println("added: " + input);
            }
            input = scanner.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!");
    }
}