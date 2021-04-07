package repo;

import model.Token;

import java.util.ArrayList;
import java.util.List;

/**
 *  Returns records that matches given logical sentence
 */
public enum FieldOperation {
    EQUAL {
        @Override
        protected boolean compare(int a, int b) {
            return a == b;
        }
    },
    NOT_EQUAL {
        @Override
        protected boolean compare(int a, int b) {
            return a != b;
        }
    },
    MORE {
        @Override
        protected boolean compare(int a, int b) {
            return a > b;
        }
    },
    MORE_AND_EQUAL {
        @Override
        protected boolean compare(int a, int b) {
            return a >= b;
        }
    },
    LESS {
        @Override
        protected boolean compare(int a, int b) {
            return a < b;
        }
    },
    LESS_AND_EQUAL {
        @Override
        protected boolean compare(int a, int b) {
            return a <= b;
        }
    };

    public static FieldOperation getFieldOperation(String operation) {
        return switch (operation) {
            case "=" -> EQUAL;
            case "!=" -> NOT_EQUAL;
            case ">" -> MORE;
            case ">=" -> MORE_AND_EQUAL;
            case "<" -> LESS;
            default -> LESS_AND_EQUAL;
        };
    }

    /**
     * Returns records that matches given logical sentence
     * @param recordsList List that contains all records form local repository
     * @param operand1 Token that represent a field or a number
     * @param operand2 Token that represent a field or a number
     * @return Records that matches given logical sentence
     */
    public List<Record> getData(ArrayList<Record> recordsList, Token operand1, Token operand2) {
        ArrayList<Record> records = new ArrayList<>();
        String operand1Value = operand1.getValue();
        String operand2Value = operand2.getValue();

        //If both operands are numbers
        if (operand1.getType() == Token.NUMBER && operand2.getType() == Token.NUMBER) {
            return compare(Integer.parseInt(operand1Value), Integer.parseInt(operand2Value)) ? recordsList : records;
        }

        if (operand1.getType() == Token.NUMBER) { //If first operand is a number
            for (Record record : recordsList) {
                if (compare(RecordGetter.getRecordMethod(operand2Value).getRecordValue(record), Integer.parseInt(operand1Value))) {
                    records.add(record);
                }
            }
        } else if (operand2.getType() == Token.NUMBER) { //If second operand is a number
            for (Record record : recordsList) {
                if (compare(Integer.parseInt(operand2Value), RecordGetter.getRecordMethod(operand1Value).getRecordValue(record))) {
                    records.add(record);
                }
            }
        } else { //If both operands are fields
            for (Record record : recordsList) {
                if (compare(RecordGetter.getRecordMethod(operand1Value).getRecordValue(record), RecordGetter.getRecordMethod(operand2Value).getRecordValue(record))) {
                    records.add(record);
                }
            }
        }

        return records;
    }

    protected abstract boolean compare(int a, int b);
}
