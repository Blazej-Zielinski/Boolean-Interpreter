import model.StringParser;
import org.junit.Assert;
import org.junit.Test;


public class StringParserTest {
    private final StringParser stringParser = new StringParser();

    @Test
    public void parseToTokens_whiteCharactersBetweenAndOperator_ThrowsIllegalArgumentException() {
        //given
        String input = "a=5 a nd b=4";

        //then
        Assert.assertThrows(IllegalArgumentException.class,() ->stringParser.parseToTokens(input));

    }

    @Test
    public void parseToTokens_UnexpectedCharacters_ThrowsIllegalArgumentException() {
        //given
        String input = "a=5 and j=4";

        //then
         Assert.assertThrows(IllegalArgumentException.class,() ->stringParser.parseToTokens(input));

    }

    @Test
    public void parseToTokens_noUnexpectedCharacters_notThrowsIllegalArgumentException() {
        //given
        String input = "a=10 or (h=3 and (b=10 or c=10))";

        //then
        try {
            stringParser.parseToTokens(input);
        } catch(IllegalArgumentException e) {
            Assert.fail("Should not have thrown any exception");
        }
    }
}
