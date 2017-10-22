import static org.junit.Assert.assertEquals;
import org.junit.Test;

import larp.automaton.contextfreelanguage.AmbiguousLL1ParseTableException;
import larp.automaton.contextfreelanguage.ContextFreeGrammarLL1SyntaxCompiler;
import larp.grammar.contextfreelanguage.ConcatenationNode;
import larp.grammar.contextfreelanguage.NonTerminalNode;
import larp.grammar.contextfreelanguage.ProductionNode;
import larp.grammar.contextfreelanguage.TerminalNode;
import larp.parsetable.ContextFreeGrammar;
import larp.parsetable.LL1ParseTable;

public class ContextFreeGrammarLL1SyntaxCompilerTest
{
    @Test
    public void testCompileReturnsParseTableForSingleCharacterProductionCFG()
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableForSingleNonTerminalAndSingleTerminalProductionCFG()
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableWithNonTerminalConcatenation()
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        concatenationNode.addChild(new NonTerminalNode("B"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("B"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("a"), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("a"), 1);
        expectedTable.addCell(new NonTerminalNode("B"), new TerminalNode("b"), 2);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test
    public void testCompileReturnsParseTableWithNonTerminalChain()
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        concatenationNode.addChild(new NonTerminalNode("C"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("B"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("B"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("b"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("C"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("c"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        LL1ParseTable expectedTable = new LL1ParseTable(grammar);
        expectedTable.addCell(new NonTerminalNode("S"), new TerminalNode("b"), 0);
        expectedTable.addCell(new NonTerminalNode("A"), new TerminalNode("b"), 1);
        expectedTable.addCell(new NonTerminalNode("B"), new TerminalNode("b"), 2);
        expectedTable.addCell(new NonTerminalNode("C"), new TerminalNode("c"), 3);

        assertEquals(expectedTable, compiler.compile(grammar));
    }

    @Test(expected = AmbiguousLL1ParseTableException.class)
    public void testCompileThrowsExceptionForFirstAmbiguityBetweenTwoTerminals()
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("s"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("s"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        compiler.compile(grammar);
    }

    @Test(expected = AmbiguousLL1ParseTableException.class)
    public void testCompileThrowsExceptionForFirstAmbiguityBetweenTwoNonTerminals()
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("B"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("B"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        compiler.compile(grammar);
    }

    @Test(expected = AmbiguousLL1ParseTableException.class)
    public void testCompileThrowsExceptionForFirstAmbiguityBetweenTerminalAndNonTerminal()
    {
        ContextFreeGrammarLL1SyntaxCompiler compiler = new ContextFreeGrammarLL1SyntaxCompiler();

        ContextFreeGrammar grammar = new ContextFreeGrammar();
        ProductionNode productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        ConcatenationNode concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new NonTerminalNode("A"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("S"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);

        productionNode = new ProductionNode();
        productionNode.addChild(new NonTerminalNode("A"));
        concatenationNode = new ConcatenationNode();
        concatenationNode.addChild(new TerminalNode("a"));
        productionNode.addChild(concatenationNode);
        grammar.addProduction(productionNode);
    }
}
