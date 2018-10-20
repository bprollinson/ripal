package larp.parser.contextfreelanguage;

import larp.ComparableStructure;
import larp.parser.regularlanguage.State;
import larp.parsetree.contextfreelanguage.ContextFreeGrammarSyntaxNode;
import larp.parsetree.contextfreelanguage.EndOfStringNode;
import larp.parsetree.contextfreelanguage.TerminalNode;

import java.util.List;
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
                this.applyReduction((LR0ReduceAction)action, stack);
            }

            State nextState = action.getNextState();
            if (nextState != null)
            {
                currentState = nextState;
                stack.add(nextState);
            }
            else
            {
                currentState = this.findTopStackState(stack);
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

    private void applyReduction(LR0ReduceAction action, Vector<Object> stack)
    {
        int productionIndex = ((LR0ReduceAction)action).getProductionIndex();
        ContextFreeGrammarSyntaxNode rootNode = this.parseTable.getContextFreeGrammar().getProduction(productionIndex);
        ContextFreeGrammarSyntaxNode leftHandNode = rootNode.getChildNodes().get(0);
        List<ContextFreeGrammarSyntaxNode> rightHandNodes = rootNode.getChildNodes().get(1).getChildNodes();

        for (int i = 0; i < 2 * rightHandNodes.size(); i++)
        {
            stack.remove(stack.size() - 1);
        }

        stack.add(leftHandNode);
    }

    private State findTopStackState(Vector<Object> stack)
    {
        for (int i = stack.size() - 1; i >= 0; i--)
        {
            Object stackEntry = stack.get(i);
            if (stackEntry instanceof State)
            {
                return (State)stackEntry;
            }
        }

        return null;
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
