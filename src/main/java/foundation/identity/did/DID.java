package foundation.identity.did;

import apg.Ast;
import apg.Parser;
import foundation.identity.did.parser.DIDGrammar;
import foundation.identity.did.parser.ParserException;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class DID {

	public static final String URI_SCHEME = "did";

	private String didString;
	private String methodName;
	private String methodSpecificId;
	private String parseTree;

	DID(String didString, String methodName, String methodSpecificId, String parseTree) {

		this.didString = didString;
		this.methodName = methodName;
		this.methodSpecificId = methodSpecificId;
		this.parseTree = parseTree;
	}

	/*
	 * Factory methods
	 */

	private static DID parse(String didString, boolean keepParseTree) throws IllegalArgumentException, ParserException {

		try {

			apg.Parser parser = new apg.Parser(DIDGrammar.getInstance());
			parser.setInputString(didString);
			parser.setStartRule(DIDGrammar.RuleNames.DID.ruleID());

			Ast ast = parser.enableAst(true);
			ast.enableRuleNode(DIDGrammar.RuleNames.METHOD_NAME.ruleID(), true);
			ast.enableRuleNode(DIDGrammar.RuleNames.METHOD_SPECIFIC_ID.ruleID(), true);

			final String[] parsedStrings = new String[2];

			ast.setRuleCallback(DIDGrammar.RuleNames.METHOD_NAME.ruleID(), new Ast.AstCallback(ast) {
				public void postBranch(int offset, int length) {
					parsedStrings[0] = didString.substring(offset, offset+length);
				}
			});
			ast.setRuleCallback(DIDGrammar.RuleNames.METHOD_SPECIFIC_ID.ruleID(), new Ast.AstCallback(ast) {
				public void postBranch(int offset, int length) {
					parsedStrings[1] = didString.substring(offset, offset+length);
				}
			});

			Parser.Result result = parser.parse();
			if (!result.success()) throw new ParserException("Cannot parse DID: " + didString);

			ast.translateAst();
			for (int i=0; i<parsedStrings.length; i++) if (parsedStrings[i] != null && parsedStrings[i].isEmpty()) parsedStrings[i] = null;

			String parseTree = null;
			if (keepParseTree) {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				ast.display(new PrintStream(byteArrayOutputStream));
				parseTree = new String(byteArrayOutputStream.toByteArray());
			}

			String methodName = parsedStrings[0] == null ? null : parsedStrings[0];
			String methodSpecificId = parsedStrings[1] == null ? null : parsedStrings[1];

			return new DID(didString, methodName, methodSpecificId, parseTree);
		} catch (ParserException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ParserException("Error while parsing DID " + didString + ": " + ex.getMessage(), ex);
		}
	}

	public static DID fromString(String string) throws IllegalArgumentException, ParserException {
		return parse(string, false);
	}

	public static DID fromString(String string, boolean keepParseTree) throws IllegalArgumentException, ParserException {
		return parse(string, keepParseTree);
	}

	public static DID fromUri(URI uri) throws IllegalArgumentException, ParserException {
		return fromString(uri.toString());
	}

	public static DID fromUri(URI uri, boolean keepParseTree) throws IllegalArgumentException, ParserException {
		return fromString(uri.toString(), keepParseTree);
	}

	/*
	 * Helper methods
	 */

	public URI toUri() {
		return URI.create(this.getDidString());
	}

	public JsonObject toJsonObject(boolean addParseTree) {

		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

		jsonObjectBuilder = jsonObjectBuilder
				.add("didString", this.getDidString() == null ? JsonValue.NULL : Json.createValue(this.getDidString()))
				.add("method", this.getMethodName() == null ? JsonValue.NULL : Json.createValue(this.getMethodName()))
				.add("methodSpecificId", this.getMethodSpecificId() == null ? JsonValue.NULL : Json.createValue(this.getMethodSpecificId()));

		if (addParseTree) {
			jsonObjectBuilder = jsonObjectBuilder
					.add("parseTree", this.getParseTree() == null ? JsonValue.NULL : Json.createValue(this.getParseTree()));
		}

		return jsonObjectBuilder.build();
	}

	public JsonObject toJsonObject() {
		return toJsonObject(false);
	}

	public Map<String, Object> toMap(boolean addParseTree) {

		Map<String, Object> map = new HashMap<> ();

		map.put("didString", this.getDidString() == null ? null : this.getDidString());
		map.put("method", this.getMethodName() == null ? null : this.getMethodName());
		map.put("methodSpecificId", this.getMethodSpecificId() == null ? null : this.getMethodSpecificId());

		if (addParseTree) {
			map.put("parseTree", this.getParseTree() == null ? null : this.getParseTree());
		}

		return map;
	}

	public Map<String, Object> toMap() {
		return toMap(false);
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

	public final String getMethodName() {
		return this.methodName;
	}

	public final void setMethodName(String methodName) {
		this.methodName = methodName;
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

		return this.didString;
	}
}
