import larp.grammar.CharacterToken;
import larp.grammar.CloseBraceToken;
import larp.grammar.KleeneClosureToken;
import larp.grammar.OpenBraceToken;
import larp.grammar.OrToken;
import larp.grammar.RegularExpressionSyntaxTokenizer;
import larp.grammar.RegularExpressionSyntaxTokenizerException;
import larp.statemachine.State;
import larp.statemachine.StateMachine;
import larp.statemachine.StateTransition;

import java.util.Vector;

public class LARP
{
    public static void main(String args[])
    {
        System.out.println("LARP main");

        State state0 = new State("S0", false);
        State state1 = new State("S1", true);
        state0.addTransition(new StateTransition('a', state1));

        StateMachine machine = new StateMachine(state0);
        System.out.println(": " + machine.accepts(""));
        System.out.println("a: " + machine.accepts("a"));
        System.out.println("b: " + machine.accepts("b"));

        RegularExpressionSyntaxTokenizer tokenizer = new RegularExpressionSyntaxTokenizer();
        try
        {
            Vector result = tokenizer.tokenize("test");
            Vector expectedResult = new Vector();
            expectedResult.add(new CharacterToken('t'));
            expectedResult.add(new CharacterToken('e'));
            expectedResult.add(new CharacterToken('s'));
            expectedResult.add(new CharacterToken('t'));

            System.out.println(result.equals(expectedResult) ? "Success" : "Failure");
        }
        catch (RegularExpressionSyntaxTokenizerException e)
        {
            System.out.println("Failure");
        }

        try
        {
            tokenizer.tokenize("(");
            System.out.println("Failure");
        }
        catch (RegularExpressionSyntaxTokenizerException e)
        {
            System.out.println("Success");
        }

        try
        {
            tokenizer.tokenize(")(");
            System.out.println("Failure");
        }
        catch (RegularExpressionSyntaxTokenizerException e)
        {
            System.out.println("Success");
        }

        try
        {
            Vector result =  tokenizer.tokenize("a*");
            Vector expectedResult = new Vector();
            expectedResult.add(new CharacterToken('a'));
            expectedResult.add(new KleeneClosureToken());

            System.out.println(result.equals(expectedResult) ? "Success" : "Failure");
        }
        catch (RegularExpressionSyntaxTokenizerException e)
        {
            System.out.println("Failure");
        }

        try
        {
            Vector result = tokenizer.tokenize("(a)*");
            Vector expectedResult = new Vector();
            expectedResult.add(new OpenBraceToken());
            expectedResult.add(new CharacterToken('a'));
            expectedResult.add(new CloseBraceToken());
            expectedResult.add(new KleeneClosureToken());

            System.out.println(result.equals(expectedResult) ? "Success" : "Failure");
        }
        catch (RegularExpressionSyntaxTokenizerException e)
        {
            System.out.println("Failure");
        }

        try
        {
            tokenizer.tokenize("(*)");
            System.out.println("Failure");
        }
        catch (RegularExpressionSyntaxTokenizerException e)
        {
            System.out.println("Success");
        }

        try
        {
            tokenizer.tokenize("*");
            System.out.println("Failure");
        }
        catch (RegularExpressionSyntaxTokenizerException e)
        {
            System.out.println("Success");
        }

        try
        {
            Vector result = tokenizer.tokenize("a|b");
            Vector expectedResult = new Vector();
            expectedResult.add(new CharacterToken('a'));
            expectedResult.add(new OrToken());
            expectedResult.add(new CharacterToken('b'));

            System.out.println(result.equals(expectedResult) ? "Success" : "Failure");
        }
        catch (RegularExpressionSyntaxTokenizerException e)
        {
            System.out.println("Failure");
        }

        try
        {
            tokenizer.tokenize("(|a)");
            System.out.println("Failure");
        }
        catch (RegularExpressionSyntaxTokenizerException e)
        {
            System.out.println("Success");
        }

        try
        {
            tokenizer.tokenize("(a|)");
            System.out.println("Failure");
        }
        catch (RegularExpressionSyntaxTokenizerException e)
        {
            System.out.println("Success");
        }
    }
}
