package view;

import model.Token;
import repo.Record;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;


/**
 * Provides a user interface for interpreting a logical sentence
 */
public class InterpreterView {
    public static final String APPROVED_INPUT = "y";

    /**
     * Asks the user for sentence to interpretation
     * @return Line entered from the console
     */
    public String getInput(){
        System.out.println("Enter sentence: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Display string in a console
     * @param message String for displaying
     */
    public void displayUnexpectedCharacterError(String message){
        System.out.println(message);
    }

    /**
     * Asks the user for sentence approval
     * @return Line entered from the console
     */
    public String approvedInput(){
        System.out.println("Enter 'y' to accept and continue...");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Displays the logical sentence converted to tree form
     * @param rpnQueue List of tokens that represent a logical sentence in Reverse Polish Notation
     */
    public void displayTree(List<Token> rpnQueue){
        Stack<TreeNode> treeNodes = new Stack<>();
        for(Token token: rpnQueue){
            if(token.getType() == Token.FIELD_OPERATOR || token.getType() == Token.SET_OPERATOR){
                treeNodes.push(new TreeNode(token.getValue(),treeNodes.pop(),treeNodes.pop()));
            }

            if(token.getType() == Token.FIELD || token.getType() == Token.NUMBER){
                treeNodes.push(new TreeNode(token.getValue(),null,null));
            }
        }

        System.out.println("Statement tree:");
        treeNodes.pop().display("","");
    }

    /**
     * Displays a given records in a console
     * @param records A list of records for displaying
     */
    public void displayRecords(List<Record> records){
        if(records.isEmpty()){
            System.out.println("No records found");
        }

        System.out.println("\nRecords found:");
        for(Record record: records){
            System.out.println(record);
        }
        System.out.println("");
    }
}
