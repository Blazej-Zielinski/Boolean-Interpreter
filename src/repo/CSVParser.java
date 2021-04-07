package repo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for conversions between list of records and CSV file
 */
public class CSVParser {
    private final String filePath;

    public CSVParser(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Write a list of records into CSV file
     *
     * @param recordsList List that contains records and represent local repository
     * @throws IOException when can't write into file
     */
    public void writeRecordsIntoCSVFile(List<Record> recordsList) throws IOException {
        List<List<String>> formattedRecords = new ArrayList<>();
        for (Record record : recordsList) {
            List<String> temp = new ArrayList<>();
            temp.add(Integer.toString(record.getA()));
            temp.add(Integer.toString(record.getB()));
            temp.add(Integer.toString(record.getC()));
            temp.add(Integer.toString(record.getD()));
            temp.add(Integer.toString(record.getE()));
            temp.add(Integer.toString(record.getF()));
            temp.add(Integer.toString(record.getG()));
            temp.add(Integer.toString(record.getH()));
            formattedRecords.add(temp);
        }


        FileWriter csvWriter = new FileWriter(filePath);

        for (List<String> rowData : formattedRecords) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();

    }

    /**
     * It writes records form a CSV file into recordsList
     *
     * @return List that contains all records stored in a CSV file
     * @throws IllegalArgumentException when the file is empty or when file contains invalid data
     * @throws IOException              when program can't find a file
     */
    public ArrayList<Record> getRecordsFromCSVFile() throws IllegalArgumentException, IOException {
        ArrayList<Record> recordsList = new ArrayList<>();

        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] recordData = row.split(",");

            if (recordData.length != 8) {
                throw new IllegalArgumentException("CSV file error. Wrong number of arguments in a single record");
            }

            int[] recordValues = new int[recordData.length];
            try {
                for (int i = 0; i < recordData.length; i++) {
                    recordValues[i] = Integer.parseInt(recordData[i]);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("CSV file error. Invalid character in a file");
            }

            recordsList.add(new Record(recordValues));
        }
        csvReader.close();

        if (recordsList.isEmpty()) {
            throw new IllegalArgumentException("No records was found in a file. Generate new set");
        }

        return recordsList;
    }
}
