import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.automaton.contextfreelanguage.ContextFreeGrammarLL1SyntaxCompiler;
import larp.grammar.contextfreelanguage.EndOfStringNode;
import larp.grammar.contextfreelanguage.EpsilonNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.parsetable.AmbiguousLL1ParseTableException;
import larp.parsetable.ContextFreeGrammar;
import larp.parsetable.LL1ParseTable;

public class ContextFreeGrammarLL1SyntaxCompilerTest
{
    @Test
    public void testCompileReturnsParseTableForSingleCharacterProductionCFG() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableForSingleNonTerminalAndSingleTerminalProductionCFG() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableWithNonTerminalConcatenation() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        expectedTable.addCell(new NonTerminalNode("B"), new TerminalNode("b"), 2);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableForProductionWithMultipleCharactersInFirstSetViaDirectProductions() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 1);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableForProductionWithMultipleCharactersInFirstSetViaIndirectProductions() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("b"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("b"), 2);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableWithNonTerminalChain() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("C"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("C"), new TerminalNode("c"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("b"), 1);
        expectedTable.addCell(new NonTerminalNode("B"), new TerminalNode("b"), 2);
        expectedTable.addCell(new NonTerminalNode("C"), new TerminalNode("c"), 3);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test(expected = AmbiguousLL1ParseTableException.class)
    public void testCompileThrowsExceptionForFirstAmbiguityBetweenTwoTerminals() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("s"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("s"));

        compiler.compile(grammar);
    }

    @Test(expected = AmbiguousLL1ParseTableException.class)
    public void testCompileThrowsExceptionForFirstAmbiguityBetweenTwoNonTerminals() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("a"));

        compiler.compile(grammar);
    }

    @Test(expected = AmbiguousLL1ParseTableException.class)
    public void testCompileThrowsExceptionForFirstAmbiguityBetweenTerminalAndNonTerminal() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        compiler.compile(grammar);
    }

    @Test(expected = AmbiguousLL1ParseTableException.class)
    public void testCompileThrowsExceptionForAmbiguousGrammarContainingCycle() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"));
        grammar.addProduction(new NonTerminalNode("A"), new NonTerminalNode("S"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));

        compiler.compile(grammar);
    }

    @Test
    public void testCompileReturnsParseTableForGrammarContainingCycle() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("S"));
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("b"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 1);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileOnlyConsidersFirstCharacterFromMultiCharacterTerminal() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("ab"));

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileHandlesTerminalFollowSets() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new TerminalNode("a"), new NonTerminalNode("B"), new TerminalNode("b"), new NonTerminalNode("C"), new TerminalNode("c"), new NonTerminalNode("C"), new TerminalNode("d"));
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("C"), new EpsilonNode());

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("B"), new TerminalNode("b"), 1);
        expectedTable.addCell(new NonTerminalNode("C"), new TerminalNode("c"), 2);
        expectedTable.addCell(new NonTerminalNode("C"), new TerminalNode("d"), 2);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileHandlesEndOfStringFollowSets() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("a"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("B"), new EpsilonNode());

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("S"), new EndOfStringNode(), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        expectedTable.addCell(new NonTerminalNode("A"), new EndOfStringNode(), 2);
        expectedTable.addCell(new NonTerminalNode("B"), new EndOfStringNode(), 3);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test(expected = AmbiguousLL1ParseTableException.class)
    public void testCompileThrowsExceptionForFirstFollowAmbiguity() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("B"), new TerminalNode("b"));

        compiler.compile(grammar);
    }

    @Test(expected = AmbiguousLL1ParseTableException.class)
    public void testCompileThrowsExceptionForFollowFollowAmbiguity() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new TerminalNode("b"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        compiler.compile(grammar);
    }

    @Test(expected = AmbiguousLL1ParseTableException.class)
    public void testCompileHandlesDanglingNonTerminalAfterNonTerminalThatGoesToEpsilon() throws AmbiguousLL1ParseTableException
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        grammar.addProduction(new NonTerminalNode("S"), new NonTerminalNode("A"), new NonTerminalNode("B"));
        grammar.addProduction(new NonTerminalNode("A"), new EpsilonNode());

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        assertEquals(expectedTable, compiler.compile(grammar));
    }
}
