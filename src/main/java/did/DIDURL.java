package did;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import did.parser.Displayer;
import did.parser.Parser;
import did.parser.ParserException;
import did.parser.Rule;
import did.parser.Rule_did;
import did.parser.Rule_did_url;
import did.parser.Rule_fragment;
import did.parser.Rule_param_name;
import did.parser.Rule_param_value;
import did.parser.Rule_path_abempty;
import did.parser.Rule_query;
import did.parser.Terminal_NumericValue;
import did.parser.Terminal_StringValue;

public class DIDURL {

	public static final String URI_SCHEME = "did";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private String didUrlString;
	private DID did;
	private String parametersString;
	private Map<String, String> parameters = new HashMap<String, String> ();
	private String path;
	private String query;
	private String fragment;
	private String parseTree;
	private Map<String, Integer> parseRuleCount;

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
	 * Serialization
	 */

	public static DIDURL fromJson(String json) throws JsonParseException, JsonMappingException, IOException {

		return objectMapper.readValue(json, DIDURL.class);
	}

	public String toJson() throws JsonProcessingException {

		return objectMapper.writeValueAsString(this);
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

		private String param_name = null;

		public Object visit(Rule_param_name rule) {

			param_name = rule.spelling;
			if (DIDURL.this.parametersString == null) DIDURL.this.parametersString = rule.spelling; else DIDURL.this.parametersString += ";" + rule.spelling;
			DIDURL.this.parameters.put(rule.spelling, null);
			return visitRules(rule.rules);
		}

		public Object visit(Rule_param_value rule) {

			DIDURL.this.parametersString += "=" + rule.spelling;
			DIDURL.this.parameters.put(param_name, rule.spelling);
			return visitRules(rule.rules);
		}

		public Object visit(Rule_path_abempty rule) {

			DIDURL.this.path = rule.spelling;
			return visitRules(rule.rules);
		}

		public Object visit(Rule_query rule) {

			DIDURL.this.query = rule.spelling;
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
	 * Getters
	 */

	@JsonGetter
	public final String getDidUrlString() {

		return this.didUrlString;
	}

	@JsonSetter
	public final void setDidUrlString(String didUrlString) {

		this.didUrlString = didUrlString;
	}

	@JsonGetter
	public final DID getDid() {

		return this.did;
	}

	@JsonSetter
	public final void setDid(DID did) {

		this.did = did;
	}

	@JsonGetter
	public final String getParametersString() {

		return this.parametersString;
	}

	@JsonSetter
	public final void setParametersString(String parametersString) {

		this.parametersString = parametersString;
	}

	@JsonGetter
	public final Map<String, String> getParameters() {

		return this.parameters;
	}

	@JsonSetter
	public final void setParameters(Map<String, String> parameters) {

		this.parameters = parameters;
	}

	@JsonGetter
	public final String getPath() {

		return this.path;
	}

	@JsonSetter
	public final void setPath(String path) {

		this.path = path;
	}

	@JsonGetter
	public final String getQuery() {

		return this.query;
	}

	@JsonSetter
	public final void setQuery(String query) {

		this.query = query;
	}

	@JsonGetter
	public final String getFragment() {

		return this.fragment;
	}

	@JsonSetter
	public final void setFragment(String fragment) {

		this.fragment = fragment;
	}

	@JsonGetter
	public final String getParseTree() {

		return this.parseTree;
	}

	@JsonSetter
	public final void setParseTree(String parseTree) {

		this.parseTree = parseTree;
	}

	@JsonGetter
	public final Map<String, Integer> getParseRuleCount() {

		return this.parseRuleCount;
	}

	@JsonSetter
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

		return this.didUrlString.equals(obj);
	}

	@Override
	public String toString() {

		return this.didUrlString.toString();
	}
}
