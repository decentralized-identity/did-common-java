/* -----------------------------------------------------------------------------
 * Visitor.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.5
 * Produced : Fri Apr 19 22:09:52 CEST 2019
 *
 * -----------------------------------------------------------------------------
 */

package did.parser;

public interface Visitor
{
  public Object visit(Rule_did rule);
  public Object visit(Rule_method_name rule);
  public Object visit(Rule_method_char rule);
  public Object visit(Rule_method_specific_id rule);
  public Object visit(Rule_idchar rule);
  public Object visit(Rule_did_url rule);
  public Object visit(Rule_param rule);
  public Object visit(Rule_param_name rule);
  public Object visit(Rule_param_value rule);
  public Object visit(Rule_param_char rule);
  public Object visit(Rule_path_abempty rule);
  public Object visit(Rule_segment rule);
  public Object visit(Rule_pchar rule);
  public Object visit(Rule_query rule);
  public Object visit(Rule_fragment rule);
  public Object visit(Rule_pct_encoded rule);
  public Object visit(Rule_unreserved rule);
  public Object visit(Rule_reserved rule);
  public Object visit(Rule_gen_delims rule);
  public Object visit(Rule_sub_delims rule);
  public Object visit(Rule_ALPHA rule);
  public Object visit(Rule_DIGIT rule);
  public Object visit(Rule_HEXDIG rule);

  public Object visit(Terminal_StringValue value);
  public Object visit(Terminal_NumericValue value);
}

/* -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
