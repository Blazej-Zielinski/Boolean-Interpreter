package model;

/**
 * Checks if the next character in a logical sentence may precede the previous character
 */
public enum TokenValidation {
    BRACKET_LEFT_VALIDATION {
        @Override
        public void validateToken(Token nextToken) {
            if (nextToken.getType() != Token.BRACKET_LEFT && nextToken.getType() != Token.FIELD && nextToken.getType() != Token.NUMBER) {
                throw new IllegalArgumentException("Left bracket validation error");
            }
        }
    },
    BRACKET_RIGHT_VALIDATION {
        @Override
        public void validateToken(Token nextToken) {
            if (nextToken.getType() != Token.BRACKET_RIGHT && nextToken.getType() != Token.SET_OPERATOR) {
                throw new IllegalArgumentException("Right bracket validation error");
            }
        }
    },
    FIELD_OR_NUMBER_VALIDATION {
        @Override
        public void validateToken(Token nextToken) {
            if (nextToken.getType() != Token.BRACKET_RIGHT && nextToken.getType() != Token.SET_OPERATOR && nextToken.getType() != Token.FIELD_OPERATOR) {
                throw new IllegalArgumentException("Operands validation error");
            }
        }
    },
    FIELD_OPERATOR_VALIDATION {
        @Override
        public void validateToken(Token nextToken) {
            if (nextToken.getType() != Token.FIELD && nextToken.getType() != Token.NUMBER) {
                throw new IllegalArgumentException("Field operator validation error");
            }
        }
    },
    SET_OPERATION_VALIDATION {
        @Override
        public void validateToken(Token nextToken) {
            if (nextToken.getType() != Token.BRACKET_LEFT && nextToken.getType() != Token.FIELD && nextToken.getType() != Token.NUMBER) {
                throw new IllegalArgumentException("Set operator validation error");
            }
        }
    };

    public static TokenValidation getValidation(Token token) {
        return switch (token.getType()) {
            case Token.BRACKET_LEFT -> BRACKET_LEFT_VALIDATION;
            case Token.BRACKET_RIGHT -> BRACKET_RIGHT_VALIDATION;
            case Token.SET_OPERATOR -> SET_OPERATION_VALIDATION;
            case Token.FIELD_OPERATOR -> FIELD_OPERATOR_VALIDATION;
            default -> FIELD_OR_NUMBER_VALIDATION;
        };
    }

    public abstract void validateToken(Token nextToken);
}
