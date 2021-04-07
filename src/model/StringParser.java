package model;

import java.util.*;

/**
 * Parses a given string to a list of logical tokens
 */
public class StringParser {

    /**
     * Parses a given string to a list of logical tokens
     * @param sentence String that should represent a logical sentence
     * @return List of logical tokens
     */
    public List<Token> parseToTokens(String sentence) {
        ArrayList<String> sentenceParts = new ArrayList<>(Arrays.asList(sentence.split("[ \t]")));
        sentenceParts.removeIf(n -> Objects.equals(n, ""));
        ArrayList<Token> tokens = new ArrayList<>();

        //The sentence was broken down into smaller sentences. Each of those will be broken into logical tokens.
        for (String part : sentenceParts) {
            tokens.addAll(decomposeSentencePart(part));
        }

        return tokens;
    }

    /**
     * Breaks sub-sentences into logical tokens.
     * @param sentence String that represent a part of logical sentence
     * @return List of logical tokens
     * @throws IllegalArgumentException if a sentence contains characters that cannot be compared to any logical tokens
     */
    private List<Token> decomposeSentencePart(String sentence) {
        //Queue is a list of characters in a sentence
        LinkedList<String> queue = new LinkedList<>(Arrays.asList(sentence.split("")));
        ArrayList<Token> tokens = new ArrayList<>();

        while (!queue.isEmpty()) {
            String character = queue.peek();

            String operator = getOperator(queue, 3);
            if (!operator.isEmpty()) {
                int tokenType = Interpreter.FIELD_OPERATORS.contains(operator) ? Token.FIELD_OPERATOR : Token.SET_OPERATOR;
                tokens.add(new Token(operator,tokenType));
                continue;
            }

            if (character.equals("-") || Character.isDigit(character.charAt(0))) {
                tokens.add(new Token(getNumber(queue),Token.NUMBER));
                continue;
            }

            if (Interpreter.BRACKETS.contains(character)) {
                int bracketType = character.equals("(") ? Token.BRACKET_LEFT : Token.BRACKET_RIGHT;
                tokens.add(new Token(queue.pop(),bracketType));
                continue;
            }

            if (Interpreter.FIELDS.contains(character)) {
                tokens.add(new Token(queue.pop(),Token.FIELD));
                continue;
            }

            throw new IllegalArgumentException("Unexpected character");
        }
        return tokens;
    }

    /**
     * Looks for the logical operator in a logical sentence
     * @param queue List of characters that represents a sentence
     * @param deep Number of chars that is compared to an operator. Should starts at 3.
     * @return String that represents an operator
     */
    private String getOperator(LinkedList<String> queue, int deep) {
        if (deep == 0) {
            return "";
        }

        if (deep > queue.size()) {
            return getOperator(queue, deep - 1);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < deep; i++) {
            sb.append(queue.get(i));
        }

        if (Interpreter.OPERATORS.contains(sb.toString())) {
            for (int i = 0; i < deep; i++) {
                queue.pop();
            }
            return sb.toString();
        } else {
            return getOperator(queue, deep - 1);
        }
    }


    /**
     * Looks for the number in a logical sentence
     * @param queue List of characters that represents a sentence
     * @return String that represents a number
     * @throws IllegalArgumentException if found token is not a number
     */
    private String getNumber(LinkedList<String> queue) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(queue.pop());
        } while (!queue.isEmpty() && Character.isDigit(queue.peek().charAt(0)));

        String number = sb.toString();
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("Unexpected character");
        }

        return number;
    }
}
