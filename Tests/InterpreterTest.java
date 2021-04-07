import model.Interpreter;
import model.StringParser;
import model.Token;
import org.junit.Assert;
import org.junit.Test;
import repo.RecordsRepo;

import java.util.List;

public class InterpreterTest {
    private static final String filePath = "assets/repoData.csv";
    private final Interpreter interpreter = new Interpreter(new RecordsRepo(filePath));
    private final StringParser stringParser = new StringParser();

    @Test
    public void validateInput_moreLeftBrackets_throwsIllegalArgumentException() {
        //given
        List<Token> tokens = stringParser.parseToTokens("(( a=5 )");


        //then
        Assert.assertThrows(IllegalArgumentException.class, () -> interpreter.validateInput(tokens));
    }

    @Test
    public void validateInput_wrongOrderOfBrackets_throwsIllegalArgumentException() {
        //given
        List<Token> tokens = stringParser.parseToTokens("3>a) and (b=4");


        //then
        Assert.assertThrows(IllegalArgumentException.class, () -> interpreter.validateInput(tokens));
    }

    @Test
    public void validateInput_sentenceToShort_throwsIllegalArgumentException() {
        //given
        List<Token> tokens = stringParser.parseToTokens("a=");


        //then
        Assert.assertThrows(IllegalArgumentException.class, () -> interpreter.validateInput(tokens));
    }

    @Test
    public void validateInput_wrongOrderOfOperators_throwsIllegalArgumentException() {
        //given
        List<Token> tokens = stringParser.parseToTokens("a>b>c>d");


        //then
        Assert.assertThrows(IllegalArgumentException.class, () -> interpreter.validateInput(tokens));
    }

    @Test
    public void validateInput_wrongAmountOfOperands_throwsIllegalArgumentException() {
        //given
        List<Token> tokens = stringParser.parseToTokens("a>b and c");


        //then
        Assert.assertThrows(IllegalArgumentException.class, () -> interpreter.validateInput(tokens));
    }

    @Test
    public void validateInput_correctSentence_notThrowsIllegalArgumentException() {
        //given
        List<Token> tokens = stringParser.parseToTokens("a=10 or (h=3 and (b=10 or c=10))");


        //then
        try {
            interpreter.validateInput(tokens);
        } catch (IllegalArgumentException e) {
            Assert.fail("Should not have thrown any exception");
        }
    }


    @Test
    public void convertToRPN_correctSentence_properRPNQueue() {
        //given
        List<Token> tokens = stringParser.parseToTokens("(a=5 and b=5) or c>0 or c=-10");

        //when
        tokens = interpreter.convertToRPN(tokens);

        //then
        List<Token> desiredOutput = stringParser.parseToTokens("a 5 = b 5 = and c 0 > or c -10 = or");

        Assert.assertEquals(desiredOutput.size(), tokens.size());
        for (int i = 0; i < desiredOutput.size(); i++) {
            Assert.assertEquals(desiredOutput.get(i).getValue(), tokens.get(i).getValue());
        }
    }


}
