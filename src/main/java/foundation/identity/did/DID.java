package foundation.identity.did;

import foundation.identity.did.parser.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DID {

	public static final String URI_SCHEME = "did";

	private String didString;
	private transient String method;
	private transient String methodSpecificId;
	private transient String parseTree;
	private transient Map<String, Integer> parseRuleCount;

	private DID() {

	}

	private DID(String didString, boolean keepParseTree) throws IllegalArgumentException, ParserException {

		this.didString = didString;

		this.parse((Rule_did) Parser.parse("did", this.didString), keepParseTree);
	}

	private DID(Rule_did rule, boolean keepParseTree) throws IllegalArgumentException, ParserException {

		this.didString = rule.spelling;

		this.parse(rule, keepParseTree);
	}

	private void parse(Rule_did rule, boolean keepParseTree) throws IllegalArgumentException, ParserException {

		DIDVisitor visitor = new DIDVisitor(keepParseTree);
		rule.accept(visitor);

		if (keepParseTree) {

			this.parseTree = visitor.parseTree.toString();
			this.parseRuleCount = visitor.parseRuleCount;
		}
	}

	/*
	 * Factory methods
	 */

	public static DID fromString(String string) throws IllegalArgumentException, ParserException {

		return new DID(string, false);
	}

	public static DID fromString(String string, boolean keepParseTree) throws IllegalArgumentException, ParserException {

		return new DID(string, keepParseTree);
	}

	public static DID fromUri(URI uri) throws IllegalArgumentException, ParserException {

		return fromString(uri.toString());
	}

	public static DID fromUri(URI uri, boolean keepParseTree) throws IllegalArgumentException, ParserException {

		return fromString(uri.toString(), keepParseTree);
	}

	static DID fromRule(Rule_did rule) throws IllegalArgumentException, ParserException {

		return new DID(rule, false);
	}

	static DID fromRule(Rule_did rule, boolean keepParseTree) throws IllegalArgumentException, ParserException {

		return new DID(rule, keepParseTree);
	}

	/*
	 * Helper classes
	 */

	private class DIDVisitor extends Displayer {

		private boolean keepParseTree;
		private int indent;
		private StringBuffer parseTree;
		private Map<String, Integer> parseRuleCount;

		private DIDVisitor(boolean keepParseTree) {

			this.keepParseTree = keepParseTree;

			if (keepParseTree) {

				this.indent = 0;
				this.parseTree = new StringBuffer();
				this.parseRuleCount = new HashMap<String, Integer> ();
			}
		}

		public Object visit(Rule_did rule) {

			DID.this.didString = rule.spelling;
			return visitRules(rule.rules);
		}

		public Object visit(Rule_method_name rule) {

			DID.this.method = rule.spelling;
			return visitRules(rule.rules);
		}

		public Object visit(Rule_method_specific_id rule) {

			DID.this.methodSpecificId = rule.spelling;
			return visitRules(rule.rules);
		}

		@Override
		public Object visit(Terminal_StringValue value) {

			return null;
		}

		@Override
		public Object visit(Terminal_NumericValue value) {

			return null;
		}

		@Override
		public Object visitRules(ArrayList<Rule> rules) {

			for (Rule rule : rules) {

				if (this.keepParseTree) {

					String ruleName = rule.getClass().getSimpleName().substring(rule.getClass().getSimpleName().indexOf("_") + 1);

					if (! (rule instanceof Terminal_NumericValue || rule instanceof Terminal_StringValue)) {

						if (parseTree.length() > 0) parseTree.append(System.lineSeparator());
						for (int i=0; i<indent; i++) parseTree.append("  ");
						parseTree.append(ruleName);
						parseTree.append(": " + "\"" + rule.spelling + "\"");
					}

					Integer ruleCount = parseRuleCount.get(ruleName);
					ruleCount = ruleCount == null ? Integer.valueOf(1) : Integer.valueOf(ruleCount.intValue() + 1);
					parseRuleCount.put(ruleName, ruleCount);

					indent++;
					rule.accept(this);
					indent--;
				} else {

					rule.accept(this);
				}
			}
			return null;
		}
	}

	/*
	 * Helper methods
	 */

	public JsonObject toJsonObject() {

		return Json.createObjectBuilder()
				.add("method", this.getMethod() == null ? JsonValue.NULL : Json.createValue(this.getMethod()))
				.add("methodSpecificId", this.getMethodSpecificId() == null ? JsonValue.NULL : Json.createValue(this.getMethodSpecificId()))
				.add("parseTree", this.getParseTree() == null ? JsonValue.NULL : Json.createValue(this.getParseTree()))
				.add("parseRuleCount", this.getParseRuleCount() == null ? JsonValue.NULL : Json.createObjectBuilder(new HashMap<String, Object>(this.getParseRuleCount())).build())
				.build();
	}

	/*
	 * Getters
	 */

	public final String getDidString() {
		return this.didString;
	}

	public final void setDidString(String didString) {
		this.didString = didString;
	}

	public final String getMethod() {
		return this.method;
	}

	public final void setMethod(String method) {
		this.method = method;
	}

	public final String getMethodSpecificId() {
		return this.methodSpecificId;
	}

	public final void setMethodSpecificId(String methodSpecificId) {
		this.methodSpecificId = methodSpecificId;
	}

	public final String getParseTree() {
		return this.parseTree;
	}

	public final void setParseTree(String parseTree) {
		this.parseTree = parseTree;
	}

	public final Map<String, Integer> getParseRuleCount() {
		return this.parseRuleCount;
	}

	public final void setParseRuleCount(Map<String, Integer> parseRuleCount) {
		this.parseRuleCount = parseRuleCount;
	}

	/*
	 * Object methods
	 */

	@Override
	public int hashCode() {

		return this.didString.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null || ! (obj instanceof DID)) return false;
		if (obj == this) return true;

		return this.didString.equals(((DID) obj).didString);
	}

	@Override
	public String toString() {

		return this.didString.toString();
	}
}
