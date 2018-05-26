import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ParseTableCellAvailableAssertion;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.HashSet;

public class LR0ParseTableCellAvailableAssertionTest
{
    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForShiftActionWhenTableContainsShiftForTheSameStateAndSymbol()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("a"), new LR0ShiftAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForShiftActionWhenTableContainsShiftForTheSameStateAndDifferentSymbol()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0ShiftAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForShiftActionWhenTableContainsShiftForDifferentStateAndTheSameSymbol()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, otherState, new TerminalNode("a"), new LR0ShiftAction(0));
        assertion.validate();
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForReduceActionWhenTableContainsReduceForTheSameState()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0ReduceAction(1));
        assertion.validate();
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForShiftActionWhenTableContainsReduceActionWithTheSameState()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0ShiftAction(0));
        assertion.validate();
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForReduceActionWhenTableContainsShiftActionWithTheSameState()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0ReduceAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForShiftActionWhenTableContainsReduceActionWithDifferentState()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, otherState, new TerminalNode("a"), new LR0ShiftAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForReduceActionWhenTableContainsShiftActionWithDifferentState()
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, otherState, new TerminalNode("a"), new LR0ReduceAction(0));
        assertion.validate();
    }
}
