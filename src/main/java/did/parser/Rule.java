/* -----------------------------------------------------------------------------
 * Rule.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.5
 * Produced : Fri Apr 19 22:09:52 CEST 2019
 *
 * -----------------------------------------------------------------------------
 */

package did.parser;

import java.util.ArrayList;

public abstract class Rule
{
  public final String spelling;
  public final ArrayList<Rule> rules;

  protected Rule(String spelling, ArrayList<Rule> rules)
  {
    this.spelling = spelling;
    this.rules = rules;
  }

  public String toString()
  {
    return spelling;
  }

  public boolean equals(Object object)
  {
    return object instanceof Rule && spelling.equals(((Rule)object).spelling);
  }

  public int hashCode()
  {
    return spelling.hashCode();
  }

  public int compareTo(Rule rule)
  {
    return spelling.compareTo(rule.spelling);
  }

  public abstract Object accept(Visitor visitor);
}

/* -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
