package larp.parser.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;
import larp.util.PairToValueMap;

public class LL1ParseTable
{
    private ContextFreeGrammar grammar;
    private PairToValueMap<NonTerminalNode, ContextFreeGrammarSyntaxNode, Integer> cells;

    public LL1ParseTable(ContextFreeGrammar grammar)
    {
        this.grammar = grammar;
        this.cells = new PairToValueMap<NonTerminalNode, ContextFreeGrammarSyntaxNode, Integer>();
    }

    public void addCell(NonTerminalNode nonTerminalNode, ContextFreeGrammarSyntaxNode terminalNode, int ruleIndex) throws AmbiguousLL1ParseTableException
    {
        new LL1ParseTableCellAvailableAssertion(this, nonTerminalNode, terminalNode).validate();

        this.cells.put(nonTerminalNode, terminalNode, ruleIndex);
    }

    public Integer getCell(NonTerminalNode nonTerminalNode, ContextFreeGrammarSyntaxNode terminalNode)
    {
        return this.cells.get(nonTerminalNode, terminalNode);
    }

    public ContextFreeGrammar getGrammar()
    {
        return this.grammar;
    }

    public PairToValueMap<NonTerminalNode, ContextFreeGrammarSyntaxNode, Integer> getCells()
    {
        return this.cells;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LL1ParseTable))
        {
            return false;
        }

        if (!this.grammar.equals(((LL1ParseTable)other).getGrammar()))
        {
            return false;
        }

        return this.cells.equals(((LL1ParseTable)other).getCells());
    }
}
