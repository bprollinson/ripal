import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.automaton.EpsilonNFA;
import larp.automaton.EpsilonNFAState;
import larp.automaton.RegularExpressionSyntaxCompiler;
import larp.automaton.StateTransition;
import larp.grammar.CharacterNode;

public class RegularExpressionSyntaxCompilerTest
{
    @Test
    public void testCompileReturnsEpsilonNFAForCharacterNode()
    {
        RegularExpressionSyntaxCompiler compiler = new RegularExpressionSyntaxCompiler();
        CharacterNode rootNode = new CharacterNode('a');

        EpsilonNFAState state = new EpsilonNFAState("S0", false);
        state.addTransition(new StateTransition('a', new EpsilonNFAState("S1", false)));
        EpsilonNFA expectedEpsilonNFA = new EpsilonNFA(state);

        assertEquals(expectedEpsilonNFA, compiler.compile(rootNode));
    }
}
