import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.automaton.EpsilonNFA;
import larp.automaton.EpsilonNFAState;
import larp.automaton.RegularExpressionSyntaxCompiler;
import larp.automaton.StateTransition;
import larp.grammar.CharacterNode;
import larp.grammar.ConcatenationNode;
import larp.grammar.KleeneClosureNode;
import larp.grammar.OrNode;

public class RegularExpressionSyntaxCompilerTest
{
    @Test
    public void testCompileReturnsEpsilonNFAForCharacterNode()
    {
        RegularExpressionSyntaxCompiler compiler = new RegularExpressionSyntaxCompiler();
        CharacterNode rootNode = new CharacterNode('a');

        EpsilonNFAState state = new EpsilonNFAState("S0", false);
        state.addTransition(new StateTransition('a', new EpsilonNFAState("S1", true)));
        EpsilonNFA expectedEpsilonNFA = new EpsilonNFA(state);

        assertEquals(expectedEpsilonNFA, compiler.compile(rootNode));
    }

    @Test
    public void testCompilerReturnsEpsilonNFAForKleeneClosureNode()
    {
        RegularExpressionSyntaxCompiler compiler = new RegularExpressionSyntaxCompiler();
        KleeneClosureNode rootNode = new KleeneClosureNode();
        rootNode.addChild(new CharacterNode('a'));

        EpsilonNFAState state1 = new EpsilonNFAState("S0", false);
        EpsilonNFAState state2 = new EpsilonNFAState("S1", true);
        state1.addTransition(new StateTransition('a', state2));
        state1.addTransition(new StateTransition(null, state2));
        state2.addTransition(new StateTransition(null, state1));
        EpsilonNFA expectedEpsilonNFA = new EpsilonNFA(state1);

        assertEquals(expectedEpsilonNFA, compiler.compile(rootNode));
    }

    @Test
    public void testCompilerReturnsEpsilonNFAForConcatenationNode()
    {
        RegularExpressionSyntaxCompiler compiler = new RegularExpressionSyntaxCompiler();
        ConcatenationNode rootNode = new ConcatenationNode();
        rootNode.addChild(new CharacterNode('a'));
        rootNode.addChild(new CharacterNode('b'));
        rootNode.addChild(new CharacterNode('c'));

        EpsilonNFAState state1 = new EpsilonNFAState("0", false);
        EpsilonNFAState state2 = new EpsilonNFAState("0", false);
        EpsilonNFAState state3 = new EpsilonNFAState("0", false);
        EpsilonNFAState state4 = new EpsilonNFAState("0", false);
        EpsilonNFAState state5 = new EpsilonNFAState("0", false);
        EpsilonNFAState state6 = new EpsilonNFAState("0", true);
        state1.addTransition(new StateTransition('a', state2));
        state2.addTransition(new StateTransition(null, state3));
        state3.addTransition(new StateTransition('b', state4));
        state4.addTransition(new StateTransition(null, state5));
        state5.addTransition(new StateTransition('c', state6));
        EpsilonNFA expectedEpsilonNFA = new EpsilonNFA(state1);

        assertEquals(expectedEpsilonNFA, compiler.compile(rootNode));
    }

    @Test
    public void testCompilerReturnsEpsilonNFAForOrNode()
    {
        RegularExpressionSyntaxCompiler compiler = new RegularExpressionSyntaxCompiler();
        OrNode rootNode = new OrNode();
        rootNode.addChild(new CharacterNode('a'));
        rootNode.addChild(new CharacterNode('b'));
        rootNode.addChild(new CharacterNode('c'));

        EpsilonNFAState startState = new EpsilonNFAState("0", false);
        EpsilonNFAState upperState1 = new EpsilonNFAState("0", false);
        EpsilonNFAState upperState2 = new EpsilonNFAState("0", false);
        EpsilonNFAState middleState1 = new EpsilonNFAState("0", false);
        EpsilonNFAState middleState2 = new EpsilonNFAState("0", false);
        EpsilonNFAState lowerState1 = new EpsilonNFAState("0", false);
        EpsilonNFAState lowerState2 = new EpsilonNFAState("0", false);
        EpsilonNFAState endState = new EpsilonNFAState("0", true);
        startState.addTransition(new StateTransition(null, upperState1));
        startState.addTransition(new StateTransition(null, middleState1));
        startState.addTransition(new StateTransition(null, lowerState1));
        upperState1.addTransition(new StateTransition('a', upperState2));
        middleState1.addTransition(new StateTransition('b', middleState2));
        lowerState1.addTransition(new StateTransition('c', lowerState2));
        upperState2.addTransition(new StateTransition(null, endState));
        middleState2.addTransition(new StateTransition(null, endState));
        lowerState2.addTransition(new StateTransition(null, endState));
        EpsilonNFA expectedEpsilonNFA = new EpsilonNFA(startState);

        assertEquals(expectedEpsilonNFA, compiler.compile(rootNode));
    }
}
