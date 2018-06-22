/**
 * 
 * @author Matthew Silva
 * Professor Hamel
 * CSC 301
 * 16 November 2017
 * Assignment #8
 *
 * Code provided for modification by Professor Hamel
 */

/**
 * A CalcParser is a calculator that evaluates a String
 * containing a numeric expression.  We handle numbers,
 * the operators +,-,*, and / with the usual precedence
 * and associativity, and parentheses.
 */
public class CalcParser {

  /**
   * We use a CalcLexer object to tokenize the input
   * string.
   */
  private CalcLexer lexer;

  /**
   * The result of evaluating the expression (if 
   * no error).
   */
  private String commands;

  /**
   * Constructor for CalcParser.  This actually does 
   * all the work. We parse and evaluate the string from
   * here.  Our caller should then use
   * the getValue() method to get
   * the value we calculated.
   *
   * @param s the string to be parsed
   */
  public CalcParser(String s) {

	  
    // First make a CalcLexer to hold the string.  This
    // will get an error immediately if the first token
    // is bad, so check for that.
	  lexer = new CalcLexer(s);
	  commands = parseExpression();
	  match(CalcLexer.EOLN_TOKEN);
  }

  
  /**
   * Get the value of the expression as a string.
   *
   * @return the value of the expression as a String
   */
  public String getValue() {
	  return commands;
	  //return Double.toString(commands);
  }

  /**
   * Match a given token and advance to the next.  
   * This utility is used by our parsing routines.  
   * If the given token does not match
   * lexer.nextToken(), we generate an appropriate
   * exception.  Advancing to the next token may
   * also cause an exception.
   *
   * @param token the token that must match
   */
  private void match(int token) {

    // First check that the current token matches the
    // one we were passed; if not, make an error.

    if (lexer.nextToken() != token) {
      if (token == CalcLexer.EOLN_TOKEN)
    	  throw new NumberFormatException("Unexpected text after the expression.");
      else if (token == CalcLexer.NUMBER_TOKEN) 
        throw new NumberFormatException("Expected a number.");
      else 
    	  throw new NumberFormatException("Expected a " + ((char) token) + ".");
    }

    // Now advance to the next token.

    lexer.advance();
  }

  /**
   * Parse an expression.
   *
   * @return the String of the sequence of stack commands for the expression 
   */
  private String parseExpression() {

    // <expression> ::= 
    //    <mulexp> { ('+' <mulexp>) | ('-' <mulexp>) }

    String result = parseMulexp();
  
    while (true) {
      if (lexer.nextToken() == '+') {
        match('+');
        result = result + parseMulexp() + "add\n";
      }
      else if (lexer.nextToken() == '-') {
        match('-');
        result = result + parseMulexp() + "subtract\n";
      }
      else return result;
    }
  }


  /**
   * Parse a mulexp, a subexpression at the precedence
   * level of * and /.  
   *
   * @return the String of the sequence of stack commands for the mulexp.
   */
  private String parseMulexp() {

    // <mulexp> ::= 
    //   <rootexp> { ('*' <rootexp>) | ('/' <rootexp>) }

    String result = parseRootexp();
    
    while (true) {
      if (lexer.nextToken() == '*') {
        match('*');
        result = result + parseRootexp() + "multiply\n";
      }
      else if (lexer.nextToken() == '/') {
        match('/');
        result = result + parseRootexp() + "divide\n";
      }
      else return result;
    }
  }

  /**
   * Parse a rootexp, which is a constant or
   * parenthesized subexpression.  If any error occurs
   * we return immediately.
   *
   * @return the String of the sequence of stack commands for the rootexp or garbage
   * in case of errors
   */
  private String parseRootexp() {
    String result = "";

    // <rootexp> ::= '(' <expression> ')'

    if (lexer.nextToken() == '(') {
      match('(');
      result = result + parseExpression();
      match(')');
    }

    // <rootexp> ::= number

    else if (lexer.nextToken()==CalcLexer.NUMBER_TOKEN){
      result = result + "Push " + Double.toString(lexer.getNum()) + "\n";
      match(CalcLexer.NUMBER_TOKEN);
    }

    else {
        throw new NumberFormatException("Expected a number or a parenthesis.");
    }

    return result;
  }
  
}
