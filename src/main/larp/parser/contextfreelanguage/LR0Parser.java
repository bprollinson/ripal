package larp.parser.contextfreelanguage;

import larp.ComparableStructure;
import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.Vector;

public class LR0Parser implements ComparableStructure
{
    private LR0ParseTable parseTable;

    public LR0Parser(LR0ParseTable parseTable)
    {
        this.parseTable = parseTable;
    }

    public LR0ParseTable getParseTable()
    {
        return this.parseTable;
    }

    public boolean accepts(String inputString)
    {
        State currentState = this.parseTable.getStartState();

        Vector<Object> stack = new Vector<Object>();
        stack.add(currentState);

        while (true)
        {
            ContextFreeGrammarSyntaxNode characterNode = this.calculateCharacterNode(inputString, stack);
            LR0ParseTableAction action = this.parseTable.getCell(currentState, characterNode);

            if (action == null)
            {
                return false;
            }
            if (action instanceof LR0AcceptAction)
            {
                return true;
            }
            if (action instanceof LR0ShiftAction)
            {
                inputString = inputString.substring(1);
                stack.add(characterNode);
            }
            if (action instanceof LR0ReduceAction)
            {
                stack.remove(stack.size() - 1);
                stack.remove(stack.size() - 1);

                ContextFreeGrammarSyntaxNode leftHandNode = this.parseTable.getContextFreeGrammar().getProduction(((LR0ReduceAction)action).getProductionIndex()).getChildNodes().get(0);
                stack.add(leftHandNode);
            }

            State nextState = action.getNextState();
            if (nextState != null)
            {
                currentState = nextState;
                stack.add(nextState);
            }
            else
            {
                for (int i = stack.size() - 1; i >= 0; i--)
                {
                    Object stackEntry = stack.get(i);
                    if (stackEntry instanceof State)
                    {
                        currentState = (State)stackEntry;
                        break;
                    }
                }
            }
        }
    }

    private ContextFreeGrammarSyntaxNode calculateCharacterNode(String inputString, Vector<Object> stack)
    {
        if (stack.elementAt(stack.size() - 1) instanceof ContextFreeGrammarSyntaxNode)
        {
            return (ContextFreeGrammarSyntaxNode)stack.elementAt(stack.size() - 1);
        }
        if (inputString.length() > 0)
        {
            String nextCharacter = inputString.substring(0, 1);
            return new TerminalNode(nextCharacter);
        }

        return new EndOfStringNode();
    }

    public boolean structureEquals(Object other)
    {
        if (!(other instanceof LR0Parser))
        {
            return false;
        }

        return this.parseTable.structureEquals(((LR0Parser)other).getParseTable());
    }
}
