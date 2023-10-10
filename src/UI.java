import java.util.Scanner;

public class UI {

    public int inputSalaryNet() {

        System.out.println("Please, enter Net salary: (780 lv - 1 000 000 lv)");

        int input = 0;
        while (input <= 780 || input >= 1_000_000) {
            try {
                Scanner scanner = new Scanner(System.in);
                input = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Numbers only:");
            }
        }

        return input;
    }
}
