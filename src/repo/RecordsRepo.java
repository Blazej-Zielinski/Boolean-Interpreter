package repo;

import model.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representation of a local repository
 */
public class RecordsRepo {
    public static final int MIN_RECORD_VALUE = -10;
    public static final int MAX_RECORD_VALUE = 10;
    private ArrayList<Record> recordsList = new ArrayList<>();
    private final CSVParser csvParser;

    public RecordsRepo(String filePath) {
        csvParser = new CSVParser(filePath);
    }

    /**
     * Seek for a records that matches a logical sentence
     * @param operand1 Token that represent a field or a number
     * @param operand2 Token that represent a field or a number
     * @param operator Token that represent a field operator
     * @return List of records that matches a logical sentence
     */
    public List<Record> getRecords(Token operand1, Token operand2, Token operator) {
        FieldOperation fieldOperation = FieldOperation.getFieldOperation(operator.getValue());

        return new ArrayList<>(fieldOperation.getData(recordsList, operand1, operand2));
    }

    /**
     * Returns a logical product or logical sum of two sets
     * @param operand1 Set of records
     * @param operand2 Set of records
     * @param operator Token that represent a set operator
     * @return Logical product or logical sum of two sets
     */
    public List<Record> getRecords(List<Record> operand1, List<Record> operand2, Token operator) {
        return SetOperation.getSetOperation(operator.getValue()).getData(operand1, operand2);
    }

    /**
     * Generates random records, sorts them and write them into local repository and into CSV file
     *
     * @throws IOException when can't write into file {
     */
    public void writeRandomRecordsIntoCSVFile() throws IOException {
        recordsList.clear();
        for (int i = 0; i < 1000; i++) {
            recordsList.add(new Record(new int[]{
                    getRandomInt(),
                    getRandomInt(),
                    getRandomInt(),
                    getRandomInt(),
                    getRandomInt(),
                    getRandomInt(),
                    getRandomInt(),
                    getRandomInt(),
                    getRandomInt()
            }));
        }
        recordsList.sort(Record::compareTo);

        csvParser.writeRecordsIntoCSVFile(recordsList);
    }

    /**
     * Gets records from CSV file and write them into local repository
     * @throws IllegalArgumentException,IOException when there is a problem with a CSV file
     */
    public void loadRecordsFromFile() throws IllegalArgumentException, IOException {
        recordsList = csvParser.getRecordsFromCSVFile();
    }

    public boolean recordsListIsEmpty() {
        return recordsList.isEmpty();
    }

    public ArrayList<Record> getRecordsList() {
        return recordsList;
    }

    /**
     * Generates a random int in range specified inside an object
     * @return Random int
     */
    private int getRandomInt() {
        Random random = new Random();
        return random.nextInt(MAX_RECORD_VALUE - (MIN_RECORD_VALUE - 1)) + MIN_RECORD_VALUE;
    }

}
