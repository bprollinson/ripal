package larp.parser.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.NonTerminalNode;

import java.util.HashMap;
import java.util.Map;

public class LL1ParseTable
{
    private ContextFreeGrammar contextFreeGrammar;
    private Map<NonTerminalNode, HashMap<ContextFreeGrammarSyntaxNode, Integer>> table;

    public LL1ParseTable(ContextFreeGrammar contextFreeGrammar)
    {
        this.contextFreeGrammar = contextFreeGrammar;
        this.table = new HashMap<NonTerminalNode, HashMap<ContextFreeGrammarSyntaxNode, Integer>>();
    }

    public void addCell(NonTerminalNode nonTerminalNode, ContextFreeGrammarSyntaxNode terminalNode, int contextFreeGrammarRuleIndex) throws AmbiguousLL1ParseTableException
    {
        new LL1ParseTableCellAvailableAssertion(this, nonTerminalNode, terminalNode).validate();

        HashMap<ContextFreeGrammarSyntaxNode, Integer> entry = this.table.get(nonTerminalNode);
        if (entry == null)
        {
            entry = new HashMap<ContextFreeGrammarSyntaxNode, Integer>();
        }

        entry.put(terminalNode, contextFreeGrammarRuleIndex);
        this.table.put(nonTerminalNode, entry);
    }

    public Integer getCell(NonTerminalNode nonTerminalNode, ContextFreeGrammarSyntaxNode terminalNode)
    {
        Integer position = null;
        HashMap<ContextFreeGrammarSyntaxNode, Integer> entry = this.table.get(nonTerminalNode);
        if (entry != null)
        {
            position = entry.get(terminalNode);
        }

        return position;
    }

    public ContextFreeGrammar getContextFreeGrammar()
    {
        return this.contextFreeGrammar;
    }

    public Map<NonTerminalNode, HashMap<ContextFreeGrammarSyntaxNode, Integer>> getTable()
    {
        return this.table;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LL1ParseTable))
        {
            return false;
        }

        if (!this.contextFreeGrammar.equals(((LL1ParseTable)other).getContextFreeGrammar()))
        {
            return false;
        }

        return this.table.equals(((LL1ParseTable)other).getTable());
    }
}
