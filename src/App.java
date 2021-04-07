import controller.InterpreterController;
import model.Interpreter;
import repo.RecordsRepo;
import view.InterpreterView;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.exit;

/**
 * Looks for records in the local repository that match the given logical sentence
 */
public class App {

    /**
     * The main function of an app
     *
     * @param args should contain only one argument which is a path to a csv file we want to work on
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong number of arguments");
            exit(0);
        }

        RecordsRepo repo = new RecordsRepo(args[0]);
        Interpreter interpreter = new Interpreter(repo);
        InterpreterView interpreterView = new InterpreterView();
        InterpreterController controller = new InterpreterController(interpreter, interpreterView);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1 - Generate new random set of records");
            System.out.println("2 - Work with saved set of records");
            System.out.println("3 - Exit program");
            System.out.println("Enter order:");
            String order = scanner.nextLine();

            switch (order) {
                case "1" -> {
                    try{
                        repo.writeRandomRecordsIntoCSVFile();
                        System.out.println("Set has been generated");
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                }
                case "2" -> {
                    try{
                        repo.loadRecordsFromFile();
                        controller.interpretStatement();
                    }catch (IOException | IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                }
                case "3" -> {
                    exit(0);
                }
                default -> {
                    System.out.println("Wrong order");
                }
            }
        }
    }
}
