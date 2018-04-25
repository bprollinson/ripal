import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.LR0ProductionSetDFA;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.regularlanguage.StateTransition;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.syntaxcompiler.contextfreelanguage.ContextFreeGrammarLR0ProductionSetDFACompiler;

public class ContextFreeGrammarLR0ProductionSetDFACompilerTest
{
    @Test
    public void testCompileCreatesDFAForTerminalRuleFromStartState()
    {
        ContextFreeGrammarLR0ProductionSetDFACompiler compiler = new ContextFreeGrammarLR0ProductionSetDFACompiler();

        LR0ProductionSetDFAState s0 = new LR0ProductionSetDFAState("", false, null);
        LR0ProductionSetDFAState s1 = new LR0ProductionSetDFAState("", false, null);
        LR0ProductionSetDFAState s2 = new LR0ProductionSetDFAState("", false, null);
        LR0ProductionSetDFAState s3 = new LR0ProductionSetDFAState("", true, null);
        s0.addTransition(new StateTransition(new TerminalNode("a"), s1));
        s0.addTransition(new StateTransition(new NonTerminalNode("S"), s2));
        s2.addTransition(new StateTransition(new EndOfStringNode(), s3));
        LR0ProductionSetDFA expectedProductionSetDFA = new LR0ProductionSetDFA(s0);

        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        assertTrue(expectedProductionSetDFA.structureEquals(compiler.compile(cfg)));
    }

    @Test
    public void testCompileCreatesDFAForNonTerminalRuleFromStartState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileCreateDFAForTerminalAndNonTerminalRuleFromStartState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileCreateDFAForTerminalRuleFromSubsequentState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileCreateDFAForNonTerminalRuleFromSubsequentState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileCreateDFAForTerminalAndNonTerminalRuleFromSubsequentState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileAddsDeepClosureToStartState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileAddsDeepClosureToSubsequentState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileLoopsTransitionToExistingState()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileCreatesStateChain()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileAddsMultipleProductionsToNextStateBasedOnSameSymbol()
    {
        assertEquals(0, 1);
    }

    @Test
    public void testCompileRemovesDuplicateProduction()
    {
        assertEquals(0, 1);
    }
}
