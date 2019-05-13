/* -----------------------------------------------------------------------------
 * Displayer.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.5
 * Produced : Fri Apr 19 22:09:52 CEST 2019
 *
 * -----------------------------------------------------------------------------
 */

package did.parser;

import java.util.ArrayList;

public class Displayer implements Visitor
{

  public Object visit(Rule_did rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_method_name rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_method_char rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_method_specific_id rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_idchar rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_did_url rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_param rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_param_name rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_param_value rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_param_char rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_path_abempty rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_segment rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_pchar rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_query rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_fragment rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_pct_encoded rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_unreserved rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_reserved rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_gen_delims rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_sub_delims rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_ALPHA rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_DIGIT rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_HEXDIG rule)
  {
    return visitRules(rule.rules);
  }

  public Object visit(Terminal_StringValue value)
  {
    System.out.print(value.spelling);
    return null;
  }

  public Object visit(Terminal_NumericValue value)
  {
    System.out.print(value.spelling);
    return null;
  }

  public Object visitRules(ArrayList<Rule> rules)
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
