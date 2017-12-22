import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parser.regularlanguage.DFA;
import larp.parser.regularlanguage.DFAState;
import larp.parser.regularlanguage.StateTransition;
import larp.parserfactory.regularlanguage.RegularLanguageParserFactory;
import larp.syntaxtokenizer.regularlanguage.IncorrectRegularExpressionNestingException;
import larp.syntaxtokenizer.regularlanguage.RegularExpressionSyntaxTokenizerException;

public class RegularLanguageParserFactoryTest
{
    @Test
    public void testFactoryCreatesDFAForRegularExpression() throws RegularExpressionSyntaxTokenizerException
    {
        RegularLanguageParserFactory factory = new RegularLanguageParserFactory();
        DFA dfa = factory.factory("ab*");

        DFAState state1 = new DFAState("0", false);
        DFAState state2 = new DFAState("0", true);
        DFAState state3 = new DFAState("0", true);
        state1.addTransition(new StateTransition('a', state2));
        state2.addTransition(new StateTransition('b', state3));
        state3.addTransition(new StateTransition('b', state3));
        DFA expectedDFA = new DFA(state1);

        assertTrue(expectedDFA.structureEquals(dfa));
    }

    @Test(expected = IncorrectRegularExpressionNestingException.class)
    public void testFactoryThrowsSyntaxTokenizerExceptionForIncorrectRegularExpression() throws RegularExpressionSyntaxTokenizerException
    {
        RegularLanguageParserFactory factory = new RegularLanguageParserFactory();
        DFA dfa = factory.factory(")(");
    }
}
