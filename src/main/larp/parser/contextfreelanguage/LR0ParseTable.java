package larp.parser.contextfreelanguage;

import larp.grammar.contextfreelanguage.ContextFreeGrammar;
import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;

import java.util.HashMap;
import java.util.Map;

public class LR0ParseTable
{
    private ContextFreeGrammar contextFreeGrammar;
    private Map<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>> table;

    public LR0ParseTable(ContextFreeGrammar contextFreeGrammar)
    {
        this.contextFreeGrammar = contextFreeGrammar;
        this.table = new HashMap<State, Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>>();
    }

    public void addCell(State state, ContextFreeGrammarSyntaxNode syntaxNode, LR0ParseTableAction action)
    {
        new LR0ParseTableCellAvailableAssertion(this, state, syntaxNode, action).validate();

        Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction> entry = this.table.get(state);
        if (entry == null)
        {
            entry = new HashMap<ContextFreeGrammarSyntaxNode, LR0ParseTableAction>();
        }

        entry.put(syntaxNode, action);
        this.table.put(state, entry);
    }

    public LR0ParseTableAction getCell(State state, ContextFreeGrammarSyntaxNode syntaxNode)
    {
        LR0ParseTableAction action = null;
        Map<ContextFreeGrammarSyntaxNode, LR0ParseTableAction> entry = this.table.get(state);
        if (entry != null)
        {
            action = entry.get(syntaxNode);
        }

        return action;
    }

    public ContextFreeGrammar getContextFreeGrammar()
    {
        return this.contextFreeGrammar;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof LR0ParseTable))
        {
            return false;
        }

        if (!this.contextFreeGrammar.equals(((LR0ParseTable)other).getContextFreeGrammar()))
        {
            return false;
        }

        return true;
    }
}
