import model.Token;
import org.junit.Assert;
import org.junit.Test;
import repo.Record;
import repo.RecordsRepo;

import java.io.IOException;
import java.util.List;

public class RecordRepoTest {
    private static final String filePath = "assets/repoData.csv";
    private final RecordsRepo repo;

    public RecordRepoTest() throws IOException,IllegalArgumentException {
        repo = new RecordsRepo(filePath);
        repo.loadRecordsFromFile();
    }

    @Test
    public void getRecords_equalsOperator_allRecordsWith_A_Equals_7() {
        //given
        Token operand1 = new Token("a", Token.FIELD);
        Token operator = new Token("=", Token.FIELD_OPERATOR);
        Token operand2 = new Token("7", Token.NUMBER);
        List<Record> recordsCollection = repo.getRecordsList();

        //when
        List<Record> foundRecords = repo.getRecords(operand2, operand1, operator);

        //then
        for (Record record : foundRecords) {
            Assert.assertEquals(record.getA(), 7);
        }

        int counter = 0;
        for (Record record : recordsCollection) {
            if (record.getA() == 7) counter++;
        }

        Assert.assertEquals(counter, foundRecords.size());
    }

    @Test
    public void getRecords_notEqualsOperator_allRecordsWith_B_NotEquals_3() {
        //given
        Token operand1 = new Token("b", Token.FIELD);
        Token operator = new Token("!=", Token.FIELD_OPERATOR);
        Token operand2 = new Token("3", Token.NUMBER);
        List<Record> recordsCollection = repo.getRecordsList();

        //when
        List<Record> foundRecords = repo.getRecords(operand2, operand1, operator);

        //then
        for (Record record : foundRecords) {
            Assert.assertNotEquals(record.getB(), 3);
        }

        int counter = 0;
        for (Record record : recordsCollection) {
            if (record.getB() != 3) counter++;
        }

        Assert.assertEquals(counter, foundRecords.size());
    }

    @Test
    public void getRecords_moreOperator_allRecordsWith_C_MoreThen_3() {
        //given
        Token operand1 = new Token("c", Token.FIELD);
        Token operator = new Token(">", Token.FIELD_OPERATOR);
        Token operand2 = new Token("3", Token.NUMBER);
        List<Record> recordsCollection = repo.getRecordsList();

        //when
        List<Record> foundRecords = repo.getRecords(operand2, operand1, operator);

        //then
        for (Record record : foundRecords) {
            Assert.assertTrue(record.getC() > 3);
        }

        int counter = 0;
        for (Record record : recordsCollection) {
            if (record.getC() > 3) counter++;
        }

        Assert.assertEquals(counter, foundRecords.size());
    }

    @Test
    public void getRecords_moreOrEqualsOperator_allRecordsWith_A_MoreOrEqualsThen_5() {
        //given
        Token operand1 = new Token("a", Token.FIELD);
        Token operator = new Token(">=", Token.FIELD_OPERATOR);
        Token operand2 = new Token("5", Token.NUMBER);
        List<Record> recordsCollection = repo.getRecordsList();

        //when
        List<Record> foundRecords = repo.getRecords(operand2, operand1, operator);

        //then
        for (Record record : foundRecords) {
            Assert.assertTrue(record.getA() >= 5);
        }

        int counter = 0;
        for (Record record : recordsCollection) {
            if (record.getA() >= 5) counter++;
        }

        Assert.assertEquals(counter, foundRecords.size());
    }

    @Test
    public void getRecords_lessOperator_allRecordsWith_A_lessThen_Minus5() {
        //given
        Token operand1 = new Token("a", Token.FIELD);
        Token operator = new Token("<", Token.FIELD_OPERATOR);
        Token operand2 = new Token("-5", Token.NUMBER);
        List<Record> recordsCollection = repo.getRecordsList();

        //when
        List<Record> foundRecords = repo.getRecords(operand2, operand1, operator);

        //then
        for (Record record : foundRecords) {
            Assert.assertTrue(record.getA() < -5);
        }

        int counter = 0;
        for (Record record : recordsCollection) {
            if (record.getA() < -5) counter++;
        }

        Assert.assertEquals(counter, foundRecords.size());
    }

    @Test
    public void getRecords_lessOrEqualsOperator_allRecordsWith_B_lessOrEqualsThen_Minus5() {
        //given
        Token operand1 = new Token("b", Token.FIELD);
        Token operator = new Token("<=", Token.FIELD_OPERATOR);
        Token operand2 = new Token("-5", Token.NUMBER);
        List<Record> recordsCollection = repo.getRecordsList();

        //when
        List<Record> foundRecords = repo.getRecords(operand2, operand1, operator);

        //then
        for (Record record : foundRecords) {
            Assert.assertTrue(record.getB() <= -5);
        }

        int counter = 0;
        for (Record record : recordsCollection) {
            if (record.getB() <= -5) counter++;
        }

        Assert.assertEquals(counter, foundRecords.size());
    }


    @Test
    public void getRecords_andOperator_productOfTwoSetsOfRecords() {
        //given
        List<Record> recordsCollection = repo.getRecordsList();
        List<Record> firstSet = repo.getRecords(
                new Token("-5", Token.NUMBER),
                new Token("a", Token.FIELD),
                new Token("=", Token.FIELD_OPERATOR)
        );
        List<Record> secondSet = repo.getRecords(
                new Token("3", Token.NUMBER),
                new Token("b", Token.FIELD),
                new Token("=", Token.FIELD_OPERATOR)
        );
        Token operator = new Token("and",Token.SET_OPERATOR);

        //when
        List<Record> foundRecords = repo.getRecords(firstSet, secondSet, operator);

        //then
        for (Record record : foundRecords) {
            Assert.assertTrue(record.getA() == -5 && record.getB() == 3);
        }

        int counter = 0;
        for (Record record : recordsCollection) {
            if (record.getA() == -5 && record.getB() == 3) counter++;
        }

        Assert.assertEquals(counter, foundRecords.size());
    }


    @Test
    public void getRecords_orOperator_sumOfTwoSetsOfRecords() {
        //given
        List<Record> recordsCollection = repo.getRecordsList();
        List<Record> firstSet = repo.getRecords(
                new Token("4", Token.NUMBER),
                new Token("a", Token.FIELD),
                new Token(">", Token.FIELD_OPERATOR)
        );
        List<Record> secondSet = repo.getRecords(
                new Token("0", Token.NUMBER),
                new Token("b", Token.FIELD),
                new Token("<=", Token.FIELD_OPERATOR)
        );
        Token operator = new Token("or",Token.SET_OPERATOR);

        //when
        List<Record> foundRecords = repo.getRecords(firstSet, secondSet, operator);

        //then
        for (Record record : foundRecords) {
            Assert.assertTrue(record.getA() > 4 || record.getB() <= 0);
        }

        int counter = 0;
        for (Record record : recordsCollection) {
            if (record.getA() > 4 || record.getB() <= 0) counter++;
        }

        Assert.assertEquals(counter, foundRecords.size());
    }
}
