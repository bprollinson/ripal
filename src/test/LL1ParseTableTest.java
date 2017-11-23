import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.EndOfStringNode;
import larp.grammar.contextfreelanguage.EpsilonNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.parsetable.ContextFreeGrammar;
import larp.parsetable.LL1ParseTable;

public class LL1ParseTableTest
{
    @Test
    public void testAcceptsReturnsTrueForCorrectCharacterInSingleCharacterCFG()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parseTable.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsFalseForIncorrectCharacterInSingleCharacterCFG()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertFalse(parseTable.accepts("b"));
    }

    @Test
    public void testAcceptsReturnsTrueForMultiCharacterCFG()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parseTable.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsTrueForMultiCharacterCFGUsingMultipleTerminalNodes()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parseTable.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsFalseForCharacterMismatchInMultiCharacterCFG()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertFalse(parseTable.accepts("ac"));
    }

    @Test
    public void testAcceptsReturnsFalseForUnmatchedInputCharacter()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertFalse(parseTable.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsFalseForUnmatchedCFGCharacter()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertFalse(parseTable.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsTrueForTerminalAndNonterminalProductionChainDependingOnCollectionPrefixing()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("C"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("ab"));
        contextFreeGrammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        parseTable.addCell(new NonTerminalNode("C"), new TerminalNode("c"), 2);

        assertTrue(parseTable.accepts("abc"));
    }


    @Test
    public void testAcceptsReturnsTrueWhenEndOfStringNodeMatchesNonTerminal()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        contextFreeGrammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);

        assertTrue(parseTable.accepts("a"));
    }

    @Test
    public void testAcceptsReturnsFalseWhenEndOfStringNonTerminalExistsWithoutEndOfStringInInput()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        contextFreeGrammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);

        assertFalse(parseTable.accepts("ab"));
    }

    @Test
    public void testAcceptsReturnsTrueForEmptyStringMatchingSequenceOfEndOfStringProductions()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        contextFreeGrammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new EndOfStringNode(), 0);
        parseTable.addCell(new NonTerminalNode("A"), new EndOfStringNode(), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);

        assertTrue(parseTable.accepts(""));
    }

    @Test
    public void testAcceptsReturnsFalseForNonEmptyStringWithOnlyEndOfStringProduction()
    {
        ContextFreeGrammar contextFreeGrammar = new ContextFreeGrammar();
        contextFreeGrammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        contextFreeGrammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        contextFreeGrammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable parseTable = new LL1ParseTable(contextFreeGrammar);
        parseTable.addCell(new NonTerminalNode("S"), new EndOfStringNode(), 0);
        parseTable.addCell(new NonTerminalNode("A"), new EndOfStringNode(), 1);
        parseTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 2);

        assertFalse(parseTable.accepts("a"));
    }

    @Test
    public void testGetAppliedRulesReturnsEmptyListBeforeParseIsRun()
    {
        assertTrue(false);
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesOnSuccessfulParse()
    {
        assertTrue(false);
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesUntilTableLookupFailure()
    {
        assertTrue(false);
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesUntilEndStateFailure()
    {
        assertTrue(false);
    }

    @Test
    public void testGetAppliedRulesReturnsRuleIndexesForSequenceOfEndOfStringProductions()
    {
        assertTrue(false);
    }

    @Test
    public void testEqualsReturnsTrueForEmptyCFGAndNoTableEntries()
    {
        LL1ParseTable parseTable = new LL1ParseTable(new ContextFreeGrammar());
        LL1ParseTable otherParseTable = new LL1ParseTable(new ContextFreeGrammar());

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueForNonEmptyCFGAndTableEntries()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        otherCfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherCfg);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentCFGs()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        otherCfg.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherCfg);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsFalseForDifferentTableEntries()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        otherCfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherCfg);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);

        assertFalse(parseTable.equals(otherParseTable));
    }

    @Test
    public void testEqualsReturnsTrueForSameTableEntriesInDifferentOrder()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable parseTable = new LL1ParseTable(cfg);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        parseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);

        ContextFreeGrammar otherCfg = new ContextFreeGrammar();
        otherCfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable otherParseTable = new LL1ParseTable(otherCfg);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        otherParseTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertTrue(parseTable.equals(otherParseTable));
    }
}
