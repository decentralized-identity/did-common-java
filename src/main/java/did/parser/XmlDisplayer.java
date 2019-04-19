/* -----------------------------------------------------------------------------
 * XmlDisplayer.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.5
 * Produced : Fri Apr 19 22:09:52 CEST 2019
 *
 * -----------------------------------------------------------------------------
 */

package did.parser;

import java.util.ArrayList;

public class XmlDisplayer implements Visitor
{
  private boolean terminal = true;

  public Object visit(Rule_did rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<did>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</did>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_method_name rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<method-name>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</method-name>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_method_char rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<method-char>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</method-char>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_method_specific_id rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<method-specific-id>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</method-specific-id>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_idchar rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<idchar>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</idchar>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_did_url rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<did-url>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</did-url>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_param rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<param>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</param>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_param_name rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<param-name>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</param-name>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_param_value rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<param-value>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</param-value>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_param_char rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<param-char>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</param-char>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_path_abempty rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<path-abempty>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</path-abempty>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_segment rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<segment>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</segment>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_pchar rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<pchar>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</pchar>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_query rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<query>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</query>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_fragment rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<fragment>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</fragment>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_pct_encoded rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<pct-encoded>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</pct-encoded>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_unreserved rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<unreserved>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</unreserved>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_reserved rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<reserved>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</reserved>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_gen_delims rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<gen-delims>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</gen-delims>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_sub_delims rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<sub-delims>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</sub-delims>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_ALPHA rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<ALPHA>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</ALPHA>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_DIGIT rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<DIGIT>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</DIGIT>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_HEXDIG rule)
  {
    if (!terminal) System.out.println();
    System.out.print("<HEXDIG>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) System.out.println();
    System.out.print("</HEXDIG>");
    terminal = false;
    return null;
  }

  public Object visit(Terminal_StringValue value)
  {
    System.out.print(value.spelling);
    terminal = true;
    return null;
  }

  public Object visit(Terminal_NumericValue value)
  {
    System.out.print(value.spelling);
    terminal = true;
    return null;
  }

  private Boolean visitRules(ArrayList<Rule> rules)
  {
    for (Rule rule : rules)
      rule.accept(this);
    return null;
  }
}

/* -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
