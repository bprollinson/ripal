import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0Parser;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;

public class LR0ParserTest
{
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
