import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0GotoAction;
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
    public void testValidateThrowsExceptionForShiftActionWhenTableContainsShiftForTheSameStateAndSymbol() throws AmbiguousLR0ParseTableException
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
    public void testValidateDoesNotThrowExceptionForShiftActionWhenTableContainsShiftForTheSameStateAndDifferentSymbol() throws AmbiguousLR0ParseTableException
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
    public void testValidateDoesNotThrowExceptionForShiftActionWhenTableContainsShiftForDifferentStateAndTheSameSymbol() throws AmbiguousLR0ParseTableException
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
    public void testValidateThrowsExceptionForShiftActionWhenTableContainsReduceActionForTheSameState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0ShiftAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForShiftActionWhenTableContainsReduceActionForDifferentState() throws AmbiguousLR0ParseTableException
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

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForShiftActionWhenTableContainsGotoActionForTheSameStateAndSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0GotoAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("a"), new LR0ShiftAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForShiftActionWhenTableContainsGotoActionForTheSameStateAndDifferentSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0GotoAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0ShiftAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForShiftActionWhenTableContainsGotoActionForDifferentStateAndTheSameSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0GotoAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, otherState, new TerminalNode("a"), new LR0ShiftAction(0));
        assertion.validate();
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForShiftActionWhenTableContainsAcceptActionForTheSameStateAndSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0AcceptAction());

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("a"), new LR0ShiftAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForShiftActionWhenTableContainsAcceptActionForTheSameStateAndDifferentSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0AcceptAction());

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0ShiftAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForShiftActionWhenTableContainsAcceptActionForDifferentStateAndTheSameSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0AcceptAction());

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, otherState, new TerminalNode("a"), new LR0ShiftAction(0));
        assertion.validate();
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForReduceActionWhenTableContainsShiftActionForTheSameState() throws AmbiguousLR0ParseTableException
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
    public void testValidateDoesNotThrowExceptionForReduceActionWhenTableContainsShiftActionForDifferentState() throws AmbiguousLR0ParseTableException
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

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForReduceActionWhenTableContainsReduceForTheSameState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0ReduceAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForReduceActionWhenTableContainsReduceActionForDifferentState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ReduceAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, otherState, new TerminalNode("a"), new LR0ReduceAction(0));
        assertion.validate();
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForReduceActionWhenTableContainsGotoActionForTheSameState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0GotoAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0ReduceAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForReduceActionWhenTableContainsGotoActionForDifferentState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0GotoAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, otherState, new TerminalNode("a"), new LR0ReduceAction(0));
        assertion.validate();
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForReduceActionWhenTableContainsAcceptActionForTheSameState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0AcceptAction());

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0ReduceAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForReduceActionWhenTableContainsAcceptActionForDifferentState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0AcceptAction());

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, otherState, new TerminalNode("a"), new LR0ReduceAction(0));
        assertion.validate();
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForGotoActionWhenTableContainsShiftActionForTheSameStateAndSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("a"), new LR0GotoAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForGotoActionWhenTableContainsShiftActionForTheSameStateAndDifferentSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0GotoAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForGotoActionWhenTableContainsShiftActionForDifferentStateAndTheSameSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0ShiftAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, otherState, new TerminalNode("a"), new LR0GotoAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateThrowsExceptionForGotoActionWhenTableContainsReduceActionForTheSameState()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForGotoActionWhenTableContainsReduceActionForDifferentState()
    {
        throw new RuntimeException();
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForGotoActionWhenTableContainsGotoForTheSameStateAndSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0GotoAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("a"), new LR0GotoAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForGotoActionWhenTableContainsGotoForTheSameStateAndDifferentSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0GotoAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0GotoAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForGotoActionWhenTableContainsGotoForDifferentStateAndTheSameSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0GotoAction(0));

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, otherState, new TerminalNode("a"), new LR0GotoAction(0));
        assertion.validate();
    }

    @Test
    public void testValidateThrowsExceptionForGotoActionWhenTableContainsAcceptForTheSameStateAndSymbol()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForGotoActionWhenTableContainsAcceptForTheSameStateAndDifferentSymbol()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForGotoActionWhenTableContainsAcceptForDifferentStateAndTheSameSymbol()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateThrowsExceptionForAcceptActionWhenTableContainsShiftForTheSameStateAndSymbol()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForAcceptActionWhenTableContainsShiftForTheSameStateAndDifferentSymbol()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForAcceptActionWhenTableContainsShiftForDifferentStateAndTheSameSymbol()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateThrowsExceptionForAcceptActionWhenTableContainsReduceActionForTheSameState()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForAcceptActionWhenTableContainsReduceActionForDifferentState()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateThrowsExceptionForAcceptActionWhenTableContainsGotoForTheSameStateAndSymbol()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForAcceptActionWhenTableContainsGotoForTheSameStateAndDifferentSymbol()
    {
        throw new RuntimeException();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForAcceptActionWhenTableContainsGotoForDifferentStateAndTheSameSymbol()
    {
        throw new RuntimeException();
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testValidateThrowsExceptionForAcceptActionWhenTableContainsAcceptForTheSameStateAndSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0AcceptAction());

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("a"), new LR0AcceptAction());
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForAcceptActionWhenTableContainsAcceptForTheSameStateAndDifferentSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0AcceptAction());

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, state, new TerminalNode("b"), new LR0AcceptAction());
        assertion.validate();
    }

    @Test
    public void testValidateDoesNotThrowExceptionForAcceptActionWhenTableContainsAcceptForDifferentStateAndTheSameSymbol() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammar cfg = new ContextFreeGrammar();
        cfg.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState otherState = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable parseTable = new LR0ParseTable(cfg);
        parseTable.addCell(state, new TerminalNode("a"), new LR0AcceptAction());

        LR0ParseTableCellAvailableAssertion assertion = new LR0ParseTableCellAvailableAssertion(parseTable, otherState, new TerminalNode("a"), new LR0AcceptAction());
        assertion.validate();
    }
}
