import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0GotoAction;
import larp.parser.contextfreelanguage.LR0Parser;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.EpsilonNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LR0ParserTest
{
    @Test
    public void testAcceptsReturnsTrueForCorrectCharacterInSingleCharacterCFG() throws AmbiguousLR0ParseTableException
    {
        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertTrue(parser.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsFalseForIncorrectCharacterInSingleCharacterCFG() throws AmbiguousLR0ParseTableException
    {
        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts("b"));
    }

    @Test
    public void testAcceptsReturnsTrueForMultiCharacterCFGUsingMultipleTerminalNodes() throws AmbiguousLR0ParseTableException
    {
        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        parseTable.addCell(state2, new TerminalNode("b"), new LR0ShiftAction(state3));
        parseTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state3, new TerminalNode("b"), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertTrue(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsDoesNotEquateCharactersWithMultiCharacterTerminalNode() throws AmbiguousLR0ParseTableException
    {
        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("ab"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("ab"), new LR0ReduceAction(1));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsFalseForUnmatchedInputCharacter() throws AmbiguousLR0ParseTableException
    {
        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsFalseForUnmatchedCFGCharacter() throws AmbiguousLR0ParseTableException
    {
        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        parseTable.addCell(state2, new TerminalNode("b"), new LR0ShiftAction(state3));
        parseTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state3, new TerminalNode("b"), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsTrueForEmptyString() throws AmbiguousLR0ParseTableException
    {
        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new EpsilonNode());

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state2));
        parseTable.addCell(state1, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        assertTrue(parser.accepts(""));
    }

    @Test
    public void testAcceptsReturnsFalseForEmptyStringWhenCFGIsEmpty()
    {
        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, null);

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts(""));
    }

    @Test
    public void testAcceptsReturnsFalseForNonEmptyStringWhenCFGIsEmpty()
    {
        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, null);

        LR0Parser parser = new LR0Parser(parseTable);

        assertFalse(parser.accepts("a"));
    }

    @Test
    public void testGetAppliedRulesReturnsEmptyListBeforeParseIsRun() throws AmbiguousLR0ParseTableException
    {
        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("a"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        parseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(2));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(2));
        parseTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesOnSuccessfulParse() throws AmbiguousLR0ParseTableException
    {
        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("a"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        parseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(2));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(2));
        parseTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);
        parser.accepts("a");

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();
        expectedRuleIndexes.add(0);
        expectedRuleIndexes.add(1);

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesUntilTableLookupFailure() throws AmbiguousLR0ParseTableException
    {
        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        ContextFreeGrammar augmentedGrammar = new ContextFreeGrammar();
        augmentedGrammar.addProduction(new NonTerminalNode("S'"), new NonTerminalNode("S"), new EndOfStringNode());
        augmentedGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        augmentedGrammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("a"));

        LR0ParseTable parseTable = new LR0ParseTable(augmentedGrammar, state1);
        parseTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        parseTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        parseTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state3));
        parseTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(2));
        parseTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(2));
        parseTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(1));
        parseTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(1));
        parseTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        LR0Parser parser = new LR0Parser(parseTable);
        parser.accepts("ab");

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();
        expectedRuleIndexes.add(0);
        expectedRuleIndexes.add(1);

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesUntilEndStateFailure()
    {
        throw new RuntimeException();
    }

    @Test
    public void testStructureEqualsReturnsTrueWhenParseTablesHaveSameStructure() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ProductionSetDFAState startState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ParseTable parseTable = new LR0ParseTable(cfg, startState);
        parseTable.addCell(startState, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(startState, new TerminalNode("b"), new LR0ReduceAction(1));
        LR0Parser parser = new LR0Parser(parseTable);

        LR0ProductionSetDFAState otherStartState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, otherStartState);
        otherParseTable.addCell(otherStartState, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(otherStartState, new TerminalNode("b"), new LR0ReduceAction(1));
        LR0Parser otherParser = new LR0Parser(otherParseTable);

        assertTrue(parser.structureEquals(otherParser));
    }

    @Test
    public void testStructureEqualsReturnsFalseWhenParseTablesHaveDifferentStructure() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ProductionSetDFAState startState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ParseTable parseTable = new LR0ParseTable(cfg, startState);
        parseTable.addCell(startState, new TerminalNode("a"), new LR0ReduceAction(0));
        parseTable.addCell(startState, new TerminalNode("b"), new LR0ReduceAction(1));
        LR0Parser parser = new LR0Parser(parseTable);

        LR0ProductionSetDFAState otherStartState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ParseTable otherParseTable = new LR0ParseTable(cfg, otherStartState);
        otherParseTable.addCell(otherStartState, new TerminalNode("a"), new LR0ReduceAction(0));
        otherParseTable.addCell(otherStartState, new TerminalNode("b"), new LR0ReduceAction(0));
        LR0Parser otherParser = new LR0Parser(otherParseTable);

        assertFalse(parser.structureEquals(otherParser));
    }
}
