import org.junit.Test;

import larp.grammar.CharacterToken;
import larp.grammar.RegularExpressionSyntaxNode;
import larp.grammar.RegularExpressionSyntaxParser;
import larp.grammar.RegularExpressionSyntaxToken;

import java.util.Vector;

public class RegularExpressionSyntaxParserTest
{
    @Test
    public void testParseReturnsConcatenateNodeForSingleCharacter()
    {
        RegularExpressionSyntaxParser parser = new RegularExpressionSyntaxParser();

        Vector<RegularExpressionSyntaxToken> input = new Vector<RegularExpressionSyntaxToken>();
        input.add(new CharacterToken('a'));
        RegularExpressionSyntaxNode rootNode = parser.parse(input);
    }
}
