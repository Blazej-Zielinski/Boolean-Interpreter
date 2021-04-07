package repo;

/**
 * Returns the value of one field in the given record
 */
public enum RecordGetter {
    A{
        @Override
        public int getRecordValue(Record record) {
            return record.getA();
        }
    },
    B{
        @Override
        public int getRecordValue(Record record) {
            return record.getB();
        }
    },
    C{
        @Override
        public int getRecordValue(Record record) {
            return record.getC();
        }
    },
    D{
        @Override
        public int getRecordValue(Record record) {
            return record.getD();
        }
    },
    E{
        @Override
        public int getRecordValue(Record record) {
            return record.getE();
        }
    },
    F{
        @Override
        public int getRecordValue(Record record) {
            return record.getF();
        }
    },
    G{
        @Override
        public int getRecordValue(Record record) {
            return record.getG();
        }
    },
    H{
        @Override
        public int getRecordValue(Record record) {
            return record.getH();
        }
    };

    public static RecordGetter getRecordMethod(String operand){
        return switch (operand) {
            case "a" -> A;
            case "b" -> B;
            case "c" -> C;
            case "d" -> D;
            case "e" -> E;
            case "f" -> F;
            case "g" -> G;
            default -> H;
        };
    }

    public abstract int getRecordValue(Record record);
}
