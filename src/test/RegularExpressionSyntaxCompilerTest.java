import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.parser.regularlanguage.EpsilonNFA;
import larp.parser.regularlanguage.EpsilonNFAState;
import larp.parser.regularlanguage.StateTransition;
import larp.parsetree.regularlanguage.CharacterNode;
import larp.parsetree.regularlanguage.ConcatenationNode;
import larp.parsetree.regularlanguage.KleeneClosureNode;
import larp.parsetree.regularlanguage.OrNode;
import larp.syntaxcompiler.regularlanguage.RegularExpressionSyntaxCompiler;

public class RegularExpressionSyntaxCompilerTest
{
    @Test
    public void testCompileReturnsEpsilonNFAForCharacterNode()
    {
        RegularExpressionSyntaxCompiler compiler = new RegularExpressionSyntaxCompiler();
        CharacterNode rootNode = new CharacterNode('a');

        EpsilonNFAState state = new EpsilonNFAState("S0", false);
        state.addTransition(new StateTransition<Character, EpsilonNFAState>('a', new EpsilonNFAState("S1", true)));
        EpsilonNFA expectedEpsilonNFA = new EpsilonNFA(state);

        assertTrue(expectedEpsilonNFA.structureEquals(compiler.compile(rootNode)));
    }

    @Test
    public void testCompilerReturnsEpsilonNFAForKleeneClosureNode()
    {
        RegularExpressionSyntaxCompiler compiler = new RegularExpressionSyntaxCompiler();
        KleeneClosureNode rootNode = new KleeneClosureNode();
        rootNode.addChild(new CharacterNode('a'));

        EpsilonNFAState state1 = new EpsilonNFAState("S0", false);
        EpsilonNFAState state2 = new EpsilonNFAState("S1", true);
        state1.addTransition(new StateTransition<Character, EpsilonNFAState>('a', state2));
        state1.addTransition(new StateTransition<Character, EpsilonNFAState>(null, state2));
        state2.addTransition(new StateTransition<Character, EpsilonNFAState>(null, state1));
        EpsilonNFA expectedEpsilonNFA = new EpsilonNFA(state1);

        assertTrue(expectedEpsilonNFA.structureEquals(compiler.compile(rootNode)));
    }

    @Test
    public void testCompilerReturnsEpsilonNFAForEmptyConcatenationNode()
    {
        RegularExpressionSyntaxCompiler compiler = new RegularExpressionSyntaxCompiler();
        ConcatenationNode rootNode = new ConcatenationNode();

        EpsilonNFAState state1 = new EpsilonNFAState("0", false);
        EpsilonNFAState state2 = new EpsilonNFAState("0", true);
        state1.addTransition(new StateTransition<Character, EpsilonNFAState>(null, state2));
        EpsilonNFA expectedEpsilonNFA = new EpsilonNFA(state1);

        assertTrue(expectedEpsilonNFA.structureEquals(compiler.compile(rootNode)));
    }

    @Test
    public void testCompilerReturnsEpsilonNFAForConcatenationNodeWithSingleChild()
    {
        RegularExpressionSyntaxCompiler compiler = new RegularExpressionSyntaxCompiler();
        ConcatenationNode rootNode = new ConcatenationNode();
        rootNode.addChild(new CharacterNode('a'));

        EpsilonNFAState state1 = new EpsilonNFAState("0", false);
        EpsilonNFAState state2 = new EpsilonNFAState("0", true);
        state1.addTransition(new StateTransition<Character, EpsilonNFAState>('a', state2));
        EpsilonNFA expectedEpsilonNFA = new EpsilonNFA(state1);

        assertTrue(expectedEpsilonNFA.structureEquals(compiler.compile(rootNode)));
    }

    @Test
    public void testCompilerReturnsEpsilonNFAForConcatenationNodeWithMultipleChildren()
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
        state1.addTransition(new StateTransition<Character, EpsilonNFAState>('a', state2));
        state2.addTransition(new StateTransition<Character, EpsilonNFAState>(null, state3));
        state3.addTransition(new StateTransition<Character, EpsilonNFAState>('b', state4));
        state4.addTransition(new StateTransition<Character, EpsilonNFAState>(null, state5));
        state5.addTransition(new StateTransition<Character, EpsilonNFAState>('c', state6));
        EpsilonNFA expectedEpsilonNFA = new EpsilonNFA(state1);

        assertTrue(expectedEpsilonNFA.structureEquals(compiler.compile(rootNode)));
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
        startState.addTransition(new StateTransition<Character, EpsilonNFAState>(null, upperState1));
        startState.addTransition(new StateTransition<Character, EpsilonNFAState>(null, middleState1));
        startState.addTransition(new StateTransition<Character, EpsilonNFAState>(null, lowerState1));
        upperState1.addTransition(new StateTransition<Character, EpsilonNFAState>('a', upperState2));
        middleState1.addTransition(new StateTransition<Character, EpsilonNFAState>('b', middleState2));
        lowerState1.addTransition(new StateTransition<Character, EpsilonNFAState>('c', lowerState2));
        upperState2.addTransition(new StateTransition<Character, EpsilonNFAState>(null, endState));
        middleState2.addTransition(new StateTransition<Character, EpsilonNFAState>(null, endState));
        lowerState2.addTransition(new StateTransition<Character, EpsilonNFAState>(null, endState));
        EpsilonNFA expectedEpsilonNFA = new EpsilonNFA(startState);

        assertTrue(expectedEpsilonNFA.structureEquals(compiler.compile(rootNode)));
    }
}
