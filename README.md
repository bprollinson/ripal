# LARP: Language Analysis and Responsive Parsing

## Regular Languages

### Step 1: Tokenize Regular Expression

* Initialize an empty list of tokens found
* Initialize parenthesis depth as 0
* For each character in the regular expression
  * If the character is an open parenthesis, increment parenthesis depth
  * If the character is a close parenthesis, decrement parenthesis depth
  * If parenthesis depth is negative, fail
  * If the current character is a kleene closure or an or symbol and the token list is empty, append epsilon to the token list
  * If the current character is a close parenthesis, kleene closure or and or symbol and the last token was an open parenthesis or an or token, append epsilon to the token list
  * If the character is a an open parenthesis, close parenthesis, kleene closure, or symbol or character, add it to the list
* If parenthesis depth is not zero, fail
* If the token list contains no tokens or the last token was an or symbol, append epsilon to the token list
* Return the token list

### Step 2: Convert Token List to Parse Tree

* If there is at least one or token at the top level (not within parentheses)
  * Create a concatenation node
  * For each segment of the token list separated by top-level or tokens (excluding the or token), perform the conversion recursively, appending the result as a child of the concatenation node
  * Return the concatenation node
* Otherwise, create a concatenation node
* Initialize parenthesis depth as 0
* For each token in the list
  * If the token is an open parenthesis, increment parenthesis depth
  * If the token is a close parenthesis, decrement parenthesis depth
  * If the token is a character and parenthesis depth is 0, append a character node as a child of the concatenation node
  * If the token is a kleene closure symbol and parenthesis depth is 0, re-parent the previous child as a child of a new kleene closure node
  * If the token is a close parenthesis token which brings parenthesis depth back to 0, perform the conversion recursively on the contents of the parentheses, appending the result as the child of a concatenation node
* Return the concatenation node

### Step 3: Convert Parse Tree to Epsilon-NFA

* Build the state group (Epsilon-NFA with tracked first and last states) from the root node of the parse tree as follows
  * If the parse tree node is a concatenation node, build state groups for all child nodes, then insert epsilon transitions between the end state of each and the start state of the next
  * If the parse tree node is a character node, the state group has two-node Epsilon-NFA with a transition between the states matching the character from the node
  * If the parse tree node is a kleene closure node, build the child node state group, then add epsilon transitions in both directions between the resulting start and end states
  * If the parse tree node is a concatenation node, build state groups for all child nodes, then insert epsilon transitions from a new state state to each group's start state and from each group's end state to a new end state
* Return an Epsilon-NFA with a start state matching that of the top-level state group, setting the corresponding end state to accepting

### Step 4: Convert Epsilon-NFA to NFA

Run the following algorithm for the start state of the Epsilon-NFA:

* If the provided Epsilon-NFA state has already been covered, return the corresponding mapped NFA state
* Create a new start state
* Map the provided Epsilon-NFA state to this new start state, flagging the Epsilon-NFA state as having been covered
* If the provided Epsilon-NFA state is accepting or an accepting Epsilon-NFA state is reachable from that state via a sequence of epsilon transitions, flag the new start state as accepting
* For each non-epsilon transition from the provided Epsilon-NFA state
  * Apply this conversion recursively on the Epsilon-NFA state following the transition, then add a transition from the new start state to the resulting NFA
* For each epsilon transition from the provided Epsilon-NFA state
  * Obtain all tangible Epsilon-NFA state transitions reachable from the provided Epsilon-NFA state via a sequence of epsilon transitions
  * For each such transition, apply this conversion recursively on the Epsilon-NFA state following the transition, then add a transition from the new start state to the resulting NFA, using that transition's input character as the transition input
* Return the new start state

### Step 5: Convert NFA to DFA

Run the following algorithm for the set containing a single element - the start state of the NFA:

* If the provided set of NFA states has alraedy been covered, return the corresponding mapped DFA state
* Create a new start state
* Map the provided set of NFA states to this new start state, flagging the NFA state as having been covered
* Create an empty mapping of input characters to sets of NFA states
* For each NFA state in the provided set of NFA states
  * Add the mapping entry of input character to subsequent NFA state to the mapping, appending to the set corresponding to that input character if one or more elements already exist for that character
  * If the NFA state is accepting, flag the new start state as accepting
* For each entry in the mapping of input characters to NFA states
  * Apply this conversion recursively on the set of NFA states corresponding to that input character
  * Add a transition from the new start state to the resulting DFA, using that input character as the transition input
* Return the new start state

### Step 6: Feed String to DFA

* Initialize the current state to the DFA's start state
* Initialize the current character position to 0
* While the current state exists and the character position is less than the size of the input string
  * Calculate the current input character as input string character at the current character position
  * Update the current state to the state following from the current state's transition with the current input character
  * Increment the current character position
* If the current state exists and is accepting, return true, otherwise return false

## Context-Free Languages

### Step 1: Tokenize Context-Free Grammar

* Initialize an empty list of tokens found
* Initialize a buffer of pending characters to the empty string
* Initialze a flag tracking whether tokenization is currently within a terminal string
* For each character in the context-free grammar production
  * If the character is a colon, flush the buffer, then add a separator token to the token list
  * If the character is a double quote and tokenization is not currently within a terminal string, flush the buffer, then flag that tokenization is within a terminal string
  * If the character is a double quote and tokenization is currently within a terminal string, flush the buffer, then flag that tokenization is not within a terminal string
  * If the character is a space and tokenization is not currently within a terminal string, flush the buffer
  * If the character is none of the above, append it to the buffer
* If tokenization is currently within a terminal string, fail
* If the token list has fewer than three tokens, or doesn't start with a non-terminal token followed by a separator token, fail
* If the number of separator tokens is not one, fail
* Remove all epsilon tokens from the result unless the right-hand side of the production is equivalent to a single epsilon, in which case reduce the right-hand side to a single epsilon
* Return the token list

The process of flushing the buffer can be defined as:

* If the triggering character is a colon, an opening double-quote or a space, add a non-terminal with name equal to the buffer contents to the token list (if not empty), then empty the buffer
* If the triggering character is a closing double-quote, add a terminal with value equal to the buffer contents to the token list (or an epsilon token if the buffer is empty), then empty the buffer

### Step 2: Convert Token List to Parse Tree

* Initialize a production node
* Initialize a non-terminal node with value equal to the value of the first (non-terminal) token from the provided token list, appending it as the first child of the production node
* Initialize a concatenation node, appending it as the second child of the production node
* For each token in the provided token list, create a corresponding node, appending it as a child of the concatenation node
* Return the production node

### Step 3: Convert Parse Tree into Parse Table (LL1)

* Initialize a parse table
* For each production in the provided context-free grammar
  * Calculate the first set for the production rule
  * For each non-epsilon terminal in that first set, map the combination of that rule's left-hand non-terminal node and that terminal from the first set to the production rule index
  * If epsilon is part of the first set, for each terminal in the follow set of that rule's left-hand non-terminal node, map the combination of that rule's left-hand non-terminal and the follow set terminal to the production rule index
* Return the parse table

To calculate the first set for a production node, initialize an empty set of nodes processed, then call the following with that set:

* Initialize an empty set of nodes
* Add the provided production node to the set of nodes processed
* If the first token on the right-hand side of the production rule is a terminal node, append that node to the set of nodes
* If the first token on the right-hand side of the production rule is an epsilon node, append that node to the set of nodes
* If the first token on the right-hand size of the production rule is a non-terminal node
  * For each production node (rule) in the grammar with that non-terminal on the left-hand size, recursively add the first set from that production to the set of nodes, with the exception of epsilon nodes
  * If the right-hand side is in the form of a sequence of non-terminal nodes that go to epsilon followed by a terminal node, add that terminal node to the set of nodes
  * If the right-hand side is in the form of a sequence of non-terminal nodes that go to epsilon, add epsilon to the set of nodes
  * If the right-hand side is in the form of a sequence of non-terminal nodes that go to epsilon followed by a non-terminal node that goes to something other than epsilon, add the first set from that non-terminal node to the set of nodes by performing this calculation recursively for each production node with that non-terminal on the left-hand side (if the production node does not belong to the set of nodes already processed)
* Return the set of nodes

To calculate the follow set for a non-terminal node:

* Create a mapping of non-terminal nodes to sets of follow nodes
* Add an end-of-string node to the set for the grammar's start symbol
* Calculate which non-terminals go to epsilon by looking for production rules where the right-hand-side is simply an epsilon node
* For each production in the context-free grammar
  * For any non-terminal node directly followed by a terminal node, store the first character of the terminal node in the mapping as belonging to the follow of the non-terminal node
  * For any non-terminal node followed by a series of non-terminals that go to epsilon, then a terminal node, store the first character of the terminal node in the mapping as belonging to the follow of the non-terminal node
  * For any non-terminal node directly followed by a non-terminal node, store the first set of the second non-terminal node (excluding epsilon) as belonging to the follow of the first non-terminal node
  * For any non-terminal node directly followed by aseries of non-terminals that go to epsilon, then a non-terminal node, store the first set of the second non-terminal node (excluding epsilon) as belonging to the follow of the first non-terminal node
* ...
* Return the mapping

### Step 4: Attempt to Match String using Parse Table (LL1)
