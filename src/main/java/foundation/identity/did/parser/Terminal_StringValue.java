/* -----------------------------------------------------------------------------
 * Terminal_StringValue.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.5
 * Produced : Mon Jul 13 23:12:39 CEST 2020
 *
 * -----------------------------------------------------------------------------
 */

package foundation.identity.did.parser;

import java.util.ArrayList;

public class Terminal_StringValue extends Rule
{
  private Terminal_StringValue(String spelling, ArrayList<Rule> rules)
  {
    super(spelling, rules);
  }

  public static Terminal_StringValue parse(
    ParserContext context, 
    String regex)
  {
    context.push("StringValue", regex);

    boolean parsed = true;

    Terminal_StringValue stringValue = null;
    try
    {
      String value = 
        context.text.substring(
          context.index, 
          context.index + regex.length());

      if ((parsed = value.equalsIgnoreCase(regex)))
      {
        context.index += regex.length();
        stringValue = new Terminal_StringValue(value, null);
      }
    }
    catch (IndexOutOfBoundsException e) {parsed = false;}

    context.pop("StringValue", parsed);

    return stringValue;
  }

  public Object accept(Visitor visitor)
  {
    return visitor.visit(this);
  }
}
/* -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
