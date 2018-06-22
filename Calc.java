/**
 * 
 * @author Matthew Silva
 * Professor Hamel
 * CSC 301
 * 16 November 2017
 * Assignment #8
 * 
 * Code provided for modification by Professor Hamel
 * 
 */

/* driver class for the Java Calculator.                                        
 * You give it an expression in double quotes and it will print out the value:  
 *      $ java Calc "1+1"                                                       
 *      value = 2.0                                                             
 *      $                                                                       
 */

public class Calc
{
    public static void main(String [] args)
    {
    	try {
    		CalcParser parser = new CalcParser(args[0]);
    		System.out.println(parser.getValue());
    	}
    	catch (NumberFormatException e) {
            System.out.println(e);
          }
        
        
    }
}