package repo;

import java.util.ArrayList;
import java.util.List;

/**
 * Returns records that matches given logical sentence
 */
public enum SetOperation {
    AND {
        @Override
        public List<Record> getData(List<Record> operand1, List<Record> operand2) {
            ArrayList<Record> records = new ArrayList<>();

            for (Record record : operand1) {
                if (operand2.contains(record)) {
                    records.add(record);
                }
            }

            return records;
        }
    },
    OR {
        @Override
        public List<Record> getData(List<Record> operand1, List<Record> operand2) {
            ArrayList<Record> records = new ArrayList<>(operand2);

            for (Record record : operand1) {
                if (!operand2.contains(record)) {
                    records.add(record);
                }
            }

            return records;
        }
    };

    public static SetOperation getSetOperation(String operation) {
        return operation.equals("and") ? AND : OR;
    }

    public abstract List<Record> getData(List<Record> operand1, List<Record> operand2);

}
