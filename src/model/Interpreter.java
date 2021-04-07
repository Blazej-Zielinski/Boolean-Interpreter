package model;

import repo.Record;
import repo.RecordsRepo;

import java.util.*;

/**
 * Interprets a logical statements
 */
public class Interpreter {
    public static final List<String> OPERATORS = new ArrayList<>(Arrays.asList("=", "<", "<=", ">=", ">", "!=", "or", "and"));
    public static final List<String> FIELD_OPERATORS = new ArrayList<>(Arrays.asList("=", "<", "<=", ">=", ">", "!="));
    public static final List<String> BRACKETS = new ArrayList<>(Arrays.asList("(", ")"));
    public static final List<String> FIELDS = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h"));
    private final RecordsRepo repo;

    public Interpreter(RecordsRepo repo) {
        this.repo = repo;
    }

    /**
     * Validates logical sentence
     * @param tokens Logical sentence represented by the token list
     * @throws IllegalArgumentException if the sentence is not correct
     */
    public void validateInput(List<Token> tokens) {
        if (tokens.size() < 3) {
            throw new IllegalArgumentException("Sentence is too short");
        }

        int leftBracketsCounter = 0;
        int fieldsAndNumbersAmount = 0;
        int nextOperatorFlag = Token.FIELD_OPERATOR;
        for (Token token : tokens) {
            if (token.getType() == Token.FIELD || token.getType() == Token.NUMBER) fieldsAndNumbersAmount++;

            //In a logically correct sentence, the field operator and the set operator should alternate
            if (OPERATORS.contains(token.getValue())) {
                if (token.getType() != nextOperatorFlag) {
                    throw new IllegalArgumentException("Operators Error");
                } else {
                    nextOperatorFlag = nextOperatorFlag == Token.FIELD_OPERATOR ? Token.SET_OPERATOR : Token.FIELD_OPERATOR;
                }
            }

            //Checks if brackets are opening and closing in right order
            if (token.getType() == Token.BRACKET_LEFT) {
                leftBracketsCounter++;
            }

            if (token.getType() == Token.BRACKET_RIGHT) {
                if (leftBracketsCounter == 0) {
                    throw new IllegalArgumentException("Brackets Error");
                } else {
                    leftBracketsCounter--;
                }
            }

        }

        if(leftBracketsCounter != 0){
            throw new IllegalArgumentException("Brackets Error");
        }

        if (nextOperatorFlag != Token.SET_OPERATOR) {
            throw new IllegalArgumentException("Operators Error");
        }

        if (fieldsAndNumbersAmount % 2 != 0) {
            throw new IllegalArgumentException("Variables Error");
        }

        Token firstToken = tokens.get(0);
        if (firstToken.getType() != Token.BRACKET_LEFT && firstToken.getType() != Token.FIELD && firstToken.getType() != Token.NUMBER) {
            throw new IllegalArgumentException("Unexpected character at the beginning of the sentence");
        }

        //Checking if the next character in a logical sentence may precede the previous character
        for (int i = 0; i < tokens.size() - 1; i++) {
            TokenValidation.getValidation(tokens.get(i)).validateToken(tokens.get(i + 1));
        }

        Token lastToken = tokens.get(tokens.size() - 1);
        if (lastToken.getType() != Token.BRACKET_RIGHT && lastToken.getType() != Token.FIELD && lastToken.getType() != Token.NUMBER) {
            throw new IllegalArgumentException("Unexpected character in the end of an sentence");
        }
    }

    /**
     * Converts logical sentence into Reverse Polish Notation
     * @param tokens Logical sentence represented by the token list
     * @return List of tokens that represent sentence in Reverse Polish Notation
     */
    public List<Token> convertToRPN(List<Token> tokens) {
        List<Token> queue = new LinkedList<>();
        Stack<Token> stack = new Stack<>();

        //algorithm for conversion to Reverse Polish Notation
        for (Token token : tokens) {
            if (token.getType() == Token.BRACKET_LEFT) {
                stack.push(token);
                continue;
            }

            if (token.getType() == Token.BRACKET_RIGHT) {
                while (stack.peek().getType() != Token.BRACKET_LEFT) {
                    queue.add(stack.pop());
                }
                stack.pop();
                continue;
            }

            if (token.getType() == Token.FIELD_OPERATOR || token.getType() == Token.SET_OPERATOR) {
                while (!stack.empty() && token.getType() <= stack.peek().getType()) {
                    queue.add(stack.pop());
                }
                stack.push(token);
                continue;
            }

            if (token.getType() == Token.NUMBER || token.getType() == Token.FIELD) {
                queue.add(token);
            }

        }

        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }

        return queue;
    }

    /**
     * Finds records that match the given logical sentence
     * @param rpnQueue List of tokens that represent sentence in Reverse Polish Notation
     * @return List of records that match the given logical sentence
     */
    public List<Record> invokeStatement(List<Token> rpnQueue) {
        Stack<Token> stack = new Stack<>();
        Stack<List<Record>> recordsSets = new Stack<>();

        for (Token token : rpnQueue) {
            if (token.getType() == Token.NUMBER || token.getType() == Token.FIELD) {
                stack.push(token);
                continue;
            }

            if (token.getType() == Token.FIELD_OPERATOR) {
                List<Record> records = repo.getRecords(stack.pop(), stack.pop(), token);
                recordsSets.push(records);
                continue;
            }

            if (token.getType() == Token.SET_OPERATOR) {
                List<Record> records = repo.getRecords(recordsSets.pop(), recordsSets.pop(), token);
                recordsSets.push(records);
            }
        }

        return recordsSets.pop();
    }
}
