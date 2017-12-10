import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.EndOfStringNode;
import larp.grammar.contextfreelanguage.EpsilonNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.parsetable.AmbiguousLL1ParseTableException;
import larp.parsetable.ContextFreeGrammar;
import larp.parsetable.LL1Parser;
import larp.parsetable.LL1ParseTable;

import java.util.ArrayList;
import java.util.List;

public class LL1ParserTest
{
    @Test
    public void testAcceptsReturnsTrueForCorrectCharacterInSingleCharacterCFG() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertTrue(parser.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsFalseForIncorrectCharacterInSingleCharacterCFG() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts("b"));
    }

    @Test
    public void testAcceptsReturnsTrueForMultiCharacterCFG() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertTrue(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsTrueForMultiCharacterCFGUsingMultipleTerminalNodes() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertTrue(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsFalseForCharacterMismatchInMultiCharacterCFG() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts("ac"));
    }

    @Test
    public void testAcceptsReturnsFalseForUnmatchedInputCharacter() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsFalseForUnmatchedCFGCharacter() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsTrueForTerminalAndNonterminalProductionChainDependingOnCollectionPrefixing() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("C"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("ab"));
        contextFreeGrammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        parseTable.addCell(new NonTerminalNode("C"), new TerminalNode("c"), 2);
        LL1Parser parser = new LL1Parser(parseTable);

        assertTrue(parser.accepts("abc"));
    }


    @Test
    public void testAcceptsReturnsTrueWhenEndOfStringNodeMatchesNonTerminal() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        contextFreeGrammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);
        LL1Parser parser = new LL1Parser(parseTable);

        assertTrue(parser.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsFalseWhenEndOfStringNonTerminalExistsWithoutEndOfStringInInput() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        contextFreeGrammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsTrueForEmptyStringMatchingSequenceOfEndOfStringProductions() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        contextFreeGrammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new EndOfStringNode(), 0);
        parseTable.addCell(new NonTerminalNode("A"), new EndOfStringNode(), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);
        LL1Parser parser = new LL1Parser(parseTable);

        assertTrue(parser.accepts(""));
    }

    @Test
    public void testAcceptsReturnsFalseForNonEmptyStringWithOnlyEndOfStringProduction() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        contextFreeGrammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new EndOfStringNode(), 0);
        parseTable.addCell(new NonTerminalNode("A"), new EndOfStringNode(), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);
        LL1Parser parser = new LL1Parser(parseTable);

        assertFalse(parser.accepts("a"));
    }

    @Test
    public void testGetAppliedRulesReturnsEmptyListBeforeParseIsRun() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        LL1Parser parser = new LL1Parser(parseTable);

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesOnSuccessfulParse() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        LL1Parser parser = new LL1Parser(parseTable);
        parser.accepts("a");

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();
        expectedRuleIndexes.add(0);
        expectedRuleIndexes.add(1);

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesUntilTableLookupFailure() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        LL1Parser parser = new LL1Parser(parseTable);
        parser.accepts("ab");

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();
        expectedRuleIndexes.add(0);
        expectedRuleIndexes.add(1);

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesUntilEndStateFailure() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        contextFreeGrammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        parseTable.addCell(new NonTerminalNode("B"), new TerminalNode("b"), 2);
        LL1Parser parser = new LL1Parser(parseTable);
        parser.accepts("a");

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();
        expectedRuleIndexes.add(0);
        expectedRuleIndexes.add(1);

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesForSequenceOfEndOfStringProductions() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        contextFreeGrammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new EndOfStringNode(), 0);
        parseTable.addCell(new NonTerminalNode("A"), new EndOfStringNode(), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);
        LL1Parser parser = new LL1Parser(parseTable);
        parser.accepts("");

        List<Integer> expectedRuleIndexes = new ArrayList<Integer>();
        expectedRuleIndexes.add(0);
        expectedRuleIndexes.add(1);
        expectedRuleIndexes.add(2);

        assertEquals(expectedRuleIndexes, parser.getAppliedRules());
    }
}
