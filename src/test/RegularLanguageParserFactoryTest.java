import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parser.regularlanguage.DFA;
import larp.parser.regularlanguage.DFAState;
import larp.parser.regularlanguage.StateTransition;
import larp.parserfactory.regularlanguage.RegularLanguageParserFactory;

public class RegularLanguageParserFactoryTest
{
    @Test
    public void testFactoryCreatesDFAForRegularExpression()
    {
        RegularLanguageParserFactory factory = new RegularLanguageParserFactory();
        DFA dfa = factory.factory("a");

        DFAState state1 = new DFAState("0", false);
        DFAState state2 = new DFAState("0", true);
        state1.addTransition(new StateTransition('a', state2));
        DFA expectedDFA = new DFA(state1);

        assertTrue(expectedDFA.structureEquals(dfa));
    }
}
