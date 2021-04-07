package model;

/**
 * Representation of a logical token
 *
 * Values ​​that have been assigned to different types are not random. They are used later in token conversion to RPN.
 */
public class Token {
    public static final int BRACKET_LEFT = 1;
    public static final int BRACKET_RIGHT = 2;
    public static final int SET_OPERATOR = 3;
    public static final int FIELD_OPERATOR = 4;
    public static final int FIELD = 5;
    public static final int NUMBER = 6;

    private final String value;
    private final int type;

    public Token(String value, int type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return value;
    }
}
