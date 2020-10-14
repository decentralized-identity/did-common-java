package foundation.identity.did;

import foundation.identity.did.parser.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DIDURL {

	public static final String URI_SCHEME = "did";

	private String didUrlString;
	private transient DID did;
	private transient String path;
	private transient String query;
	private transient String fragment;
	private transient Map<String, String> parameters = new HashMap<String, String> ();
	private transient String parseTree;
	private transient Map<String, Integer> parseRuleCount;

	private DIDURL() {

	}

	private DIDURL(String didUrlString, boolean keepParseTree) throws IllegalArgumentException, ParserException {

		this.didUrlString = didUrlString;

		this.parse((Rule_did_url) Parser.parse("did-url", this.didUrlString), keepParseTree);
	}

	private void parse(Rule_did_url rule, boolean keepParseTree) throws IllegalArgumentException, ParserException {

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

	public static DIDURL fromString(String string) throws IllegalArgumentException, ParserException {

		return new DIDURL(string, false);
	}

	public static DIDURL fromString(String string, boolean keepParseTree) throws IllegalArgumentException, ParserException {

		return new DIDURL(string, keepParseTree);
	}

	public static DIDURL fromUri(URI uri) throws IllegalArgumentException, ParserException {

		return fromString(uri.toString());
	}

	public static DIDURL fromUri(URI uri, boolean keepParseTree) throws IllegalArgumentException, ParserException {

		return fromString(uri.toString(), keepParseTree);
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

			try {

				DIDURL.this.did = DID.fromRule(rule, this.keepParseTree);
			} catch (ParserException ex) {

				throw new RuntimeException(ex.getMessage(), ex);
			}

			return visitRules(rule.rules);
		}

		public Object visit(Rule_path_abempty rule) {

			DIDURL.this.path = rule.spelling;
			return visitRules(rule.rules);
		}

		public Object visit(Rule_query rule) {

			DIDURL.this.query = rule.spelling;
			DIDURL.this.parameters = URLEncodedUtils.parse(rule.spelling, StandardCharsets.UTF_8).stream().collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));
			return visitRules(rule.rules);
		}

		public Object visit(Rule_fragment rule) {

			DIDURL.this.fragment = rule.spelling;
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
				.add("did", this.getDid() == null ? JsonValue.NULL : this.getDid().toJsonObject())
				.add("parameters", this.getParameters() == null ? JsonValue.NULL : Json.createObjectBuilder(new HashMap<String, Object>(this.getParameters())).build())
				.add("path", this.getPath() == null ? JsonValue.NULL : Json.createValue(this.getPath()))
				.add("query", this.getQuery() == null ? JsonValue.NULL : Json.createValue(this.getQuery()))
				.add("fragment", this.getFragment() == null ? JsonValue.NULL : Json.createValue(this.getFragment()))
				.add("parseTree", this.getParseTree() == null ? JsonValue.NULL : Json.createValue(this.getParseTree()))
				.add("parseRuleCount", this.getParseRuleCount() == null ? JsonValue.NULL : Json.createObjectBuilder(new HashMap<String, Object>(this.getParseRuleCount())).build())
				.build();
	}

	/*
	 * Getters
	 */

	public final String getDidUrlString() {
		return this.didUrlString;
	}

	public final void setDidUrlString(String didUrlString) {
		this.didUrlString = didUrlString;
	}

	public final DID getDid() {
		return this.did;
	}

	public final void setDid(DID did) {
		this.did = did;
	}

	public final Map<String, String> getParameters() {
		return this.parameters;
	}

	public final void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public final String getPath() {
		return this.path;
	}

	public final void setPath(String path) {
		this.path = path;
	}

	public final String getQuery() {
		return this.query;
	}

	public final void setQuery(String query) {
		this.query = query;
	}

	public final String getFragment() {
		return this.fragment;
	}

	public final void setFragment(String fragment) {
		this.fragment = fragment;
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

		return this.didUrlString.hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null || ! (obj instanceof DIDURL)) return false;
		if (obj == this) return true;

		return this.didUrlString.equals(((DIDURL) obj).didUrlString);
	}

	@Override
	public String toString() {

		return this.didUrlString.toString();
	}
}
