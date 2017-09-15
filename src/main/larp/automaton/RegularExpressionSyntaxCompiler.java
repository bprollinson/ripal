package larp.automaton;

import larp.grammar.CharacterNode;
import larp.grammar.RegularExpressionSyntaxNode;

public class RegularExpressionSyntaxCompiler
{
    public EpsilonNFA compile(RegularExpressionSyntaxNode rootNode)
    {
        StateGroup stateGroup = this.compileStateGroup(rootNode);

        return new EpsilonNFA(stateGroup.getStartState());
    }

    private StateGroup compileStateGroup(RegularExpressionSyntaxNode node)
    {
        if (node instanceof CharacterNode)
        {
            State startState = new State("", false);
            State endState = new State("", false);
            startState.addTransition(new StateTransition(null, endState));

            return new StateGroup(startState, endState);
        }

        return null;
    }
}
