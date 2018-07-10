import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.contextfreelanguage.AmbiguousLR0ParseTableException;
import larp.parser.contextfreelanguage.LR0AcceptAction;
import larp.parser.contextfreelanguage.LR0GotoAction;
import larp.parser.contextfreelanguage.LR0ParseTable;
import larp.parser.contextfreelanguage.LR0ProductionSetDFAState;
import larp.parser.contextfreelanguage.LR0ReduceAction;
import larp.parser.contextfreelanguage.LR0ShiftAction;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.parsetree.contextfreelanguage.TerminalNode;
import larp.syntaxcompiler.contextfreelanguage.ContextFreeGrammarLR0SyntaxCompiler;

import java.util.HashSet;

public class ContextFreeGrammarLR0SyntaxCompilerTest
{
    @Test
    public void testCompileReturnsEmptyParseTableForEmptyCFG()
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        LR0ParseTable expectedTable = new LR0ParseTable(grammar);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableForSingleCharacterProductionCFG() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable expectedTable = new LR0ParseTable(grammar);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state3));
        expectedTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(2));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableForSingleNonTerminalProductionCFG() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable expectedTable = new LR0ParseTable(grammar);
        expectedTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state3));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0AcceptAction());

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableForSingleNonTerminalAndSingleTerminalProductionCFG() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable expectedTable = new LR0ParseTable(grammar);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state3));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        expectedTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(2));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state3, new NonTerminalNode("A"), new LR0ReduceAction(3));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(3));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileHandlesMultipleTerminalProductionsWithinTheSameState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable expectedTable = new LR0ParseTable(grammar);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new TerminalNode("b"), new LR0ShiftAction(state3));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        expectedTable.addCell(state2, new TerminalNode("a"), new LR0ReduceAction(2));
        expectedTable.addCell(state2, new TerminalNode("b"), new LR0ReduceAction(2));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state3, new TerminalNode("a"), new LR0ReduceAction(3));
        expectedTable.addCell(state3, new TerminalNode("b"), new LR0ReduceAction(3));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(3));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileHandlesMultipleNonTerminalProductionsWithinTheSameState() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state5 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state6 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable expectedTable = new LR0ParseTable(grammar);
        expectedTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("B"), new LR0GotoAction(state3));
        expectedTable.addCell(state1, new NonTerminalNode("C"), new LR0GotoAction(state4));
        expectedTable.addCell(state1, new TerminalNode("c"), new LR0ShiftAction(state5));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state6));
        expectedTable.addCell(state2, new TerminalNode("c"), new LR0ReduceAction(2));
        expectedTable.addCell(state2, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state3, new TerminalNode("c"), new LR0ReduceAction(3));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(3));
        expectedTable.addCell(state4, new TerminalNode("c"), new LR0ReduceAction(4));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0ReduceAction(4));
        expectedTable.addCell(state5, new TerminalNode("c"), new LR0ReduceAction(5));
        expectedTable.addCell(state5, new EndOfStringNode(), new LR0ReduceAction(5));
        expectedTable.addCell(state6, new EndOfStringNode(), new LR0AcceptAction());

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableWithTerminalChain() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new TerminalNode("b"), new TerminalNode("c"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state5 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable expectedTable = new LR0ParseTable(grammar);
        expectedTable.addCell(state1, new TerminalNode("a"), new LR0ShiftAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state5));
        expectedTable.addCell(state2, new TerminalNode("b"), new LR0ShiftAction(state3));
        expectedTable.addCell(state3, new TerminalNode("c"), new LR0ShiftAction(state4));
        expectedTable.addCell(state4, new TerminalNode("a"), new LR0ReduceAction(2));
        expectedTable.addCell(state4, new TerminalNode("b"), new LR0ReduceAction(2));
        expectedTable.addCell(state4, new TerminalNode("c"), new LR0ReduceAction(2));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state5, new EndOfStringNode(), new LR0AcceptAction());

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsTableForCFGWithDFAContainingCycle() throws AmbiguousLR0ParseTableException
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));

        LR0ProductionSetDFAState state1 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state2 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state3 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());
        LR0ProductionSetDFAState state4 = new LR0ProductionSetDFAState("", false, new HashSet<ContextFreeGrammarSyntaxNode>());

        LR0ParseTable expectedTable = new LR0ParseTable(grammar);
        expectedTable.addCell(state1, new NonTerminalNode("A"), new LR0GotoAction(state2));
        expectedTable.addCell(state1, new NonTerminalNode("S"), new LR0GotoAction(state4));
        expectedTable.addCell(state2, new NonTerminalNode("A"), new LR0GotoAction(state2));
        expectedTable.addCell(state2, new NonTerminalNode("S"), new LR0GotoAction(state3));
        expectedTable.addCell(state3, new EndOfStringNode(), new LR0ReduceAction(2));
        expectedTable.addCell(state4, new EndOfStringNode(), new LR0AcceptAction());

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileThrowsExceptionForShiftReduceConflict()
    {
        assertEquals(0, 1);
    }

    @Test(expected = AmbiguousLR0ParseTableException.class)
    public void testCompileThrowsExceptionForReduceReduceConflict()
    {
        ContextFreeGrammarLR0SyntaxCompiler compiler = new ContextFreeGrammarLR0SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        compiler.compile(grammar);
    }
}
