package controller;

import model.Interpreter;
import model.StringParser;
import model.Token;
import repo.Record;
import view.InterpreterView;

import java.util.List;

/**
 * Mediates in communication between the UI and the application logic
 */
public class InterpreterController {
    private final Interpreter interpreter;
    private final InterpreterView interpreterView;
    private final StringParser stringParser;

    public InterpreterController(Interpreter interpreter, InterpreterView interpreterView) {
        this.interpreter = interpreter;
        this.interpreterView = interpreterView;
        stringParser = new StringParser();
    }

    /**
     * It asks the user for a logical sentence. If the sentence is correct, it displays the appropriate records from the
     * repository in the console. If the sentence is incorrect, it displays the appropriate error in the console and
     * asks the user to enter the sentence again.
     */
    public void interpretStatement() {
        while (true) {
            try {
                String input = interpreterView.getInput();
                List<Token> tokens = stringParser.parseToTokens(input);
                interpreter.validateInput(tokens);

                List<Token> rpnQueue = interpreter.convertToRPN(tokens);
                System.out.println(rpnQueue);
                interpreterView.displayTree(rpnQueue);

                if (interpreterView.approvedInput().equals(InterpreterView.APPROVED_INPUT)) {
                    List<Record> outputRecords = interpreter.invokeStatement(rpnQueue);
                    interpreterView.displayRecords(outputRecords);
                    break;
                }

            } catch (IllegalArgumentException e) {
                interpreterView.displayUnexpectedCharacterError(e.getMessage());
            }
        }
    }
}
