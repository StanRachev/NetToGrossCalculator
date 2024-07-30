import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UI {

    BufferedReader reader;

    public UI() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public int inputSalaryNet() {
        System.out.println("Please, enter Net salary: (780 lv - 1 000 000 lv)");

        int input = 0;
        while (input <= 780 || input >= 1_000_000) {
            try {
                input = Integer.parseInt(reader.readLine());
            } catch (IOException | NumberFormatException e) {
                System.out.println("Numbers only:");
            }
        }
        return input;
    }
}