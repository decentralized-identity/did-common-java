package foundation.identity.did;

import apg.Ast;
import apg.Parser;
import foundation.identity.did.parser.DIDGrammar;
import foundation.identity.did.parser.ParserException;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DIDURL {

	public static final String URI_SCHEME = "did";

	private String didUrlString;
	private DID did;
	private String path;
	private String query;
	private String fragment;
	private Map<String, String> parameters = new HashMap<String, String> ();
	private String parseTree;

	DIDURL(String didUrlString, DID did, String path, String query, Map<String, String> parameters, String fragment, String parseTree) {

		this.didUrlString = didUrlString;
		this.did = did;
		this.path = path;
		this.query = query;
		this.parameters = parameters;
		this.fragment = fragment;
		this.parseTree = parseTree;
	}

	/*
	 * Factory methods
	 */

	private static DIDURL parse(String didUrlString, boolean keepParseTree) throws IllegalArgumentException, ParserException {

		try {

			apg.Parser parser = new apg.Parser(DIDGrammar.getInstance());
			parser.setInputString(didUrlString);
			parser.setStartRule(DIDGrammar.RuleNames.DID_URL.ruleID());

			Ast ast = parser.enableAst(true);
			ast.enableRuleNode(DIDGrammar.RuleNames.DID.ruleID(), true);
			ast.enableRuleNode(DIDGrammar.RuleNames.METHOD_NAME.ruleID(), true);
			ast.enableRuleNode(DIDGrammar.RuleNames.METHOD_SPECIFIC_ID.ruleID(), true);
			ast.enableRuleNode(DIDGrammar.RuleNames.PATH_ABEMPTY.ruleID(), true);
			ast.enableRuleNode(DIDGrammar.RuleNames.QUERY.ruleID(), true);
			ast.enableRuleNode(DIDGrammar.RuleNames.FRAGMENT.ruleID(), true);

			final String[] parsedStrings = new String[6];

			ast.setRuleCallback(DIDGrammar.RuleNames.METHOD_NAME.ruleID(), new Ast.AstCallback(ast) {
				public void postBranch(int offset, int length) {
					parsedStrings[0] = didUrlString.substring(offset, offset+length);
				}
			});
			ast.setRuleCallback(DIDGrammar.RuleNames.METHOD_SPECIFIC_ID.ruleID(), new Ast.AstCallback(ast) {
				public void postBranch(int offset, int length) {
					parsedStrings[1] = didUrlString.substring(offset, offset+length);
				}
			});
			ast.setRuleCallback(DIDGrammar.RuleNames.DID.ruleID(), new Ast.AstCallback(ast) {
				public void postBranch(int offset, int length) {
					parsedStrings[2] = didUrlString.substring(offset, offset+length);
				}
			});
			ast.setRuleCallback(DIDGrammar.RuleNames.PATH_ABEMPTY.ruleID(), new Ast.AstCallback(ast) {
				public void postBranch(int offset, int length) {
					parsedStrings[3] = didUrlString.substring(offset, offset+length);
				}
			});
			ast.setRuleCallback(DIDGrammar.RuleNames.QUERY.ruleID(), new Ast.AstCallback(ast) {
				public void postBranch(int offset, int length) {
					parsedStrings[4] = didUrlString.substring(offset, offset+length);
				}
			});
			ast.setRuleCallback(DIDGrammar.RuleNames.FRAGMENT.ruleID(), new Ast.AstCallback(ast) {
				public void postBranch(int offset, int length) {
					parsedStrings[5] = didUrlString.substring(offset, offset+length);
				}
			});

			Parser.Result result = parser.parse();
			if (!result.success()) throw new ParserException("Cannot parse DID URL: " + didUrlString);

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
			String didString = parsedStrings[2] == null ? null : parsedStrings[2];
			String path = parsedStrings[3] == null ? null : parsedStrings[3];
			String query = parsedStrings[4] == null ? null : parsedStrings[4];
			String fragment = parsedStrings[5] == null ? null : parsedStrings[5];

			DID did = didString == null ? null : new DID(didString, methodName, methodSpecificId, null);
			Map<String, String> parameters = query == null ? null : URLEncodedUtils.parse(query, StandardCharsets.UTF_8).stream().collect(Collectors.toMap(NameValuePair::getName, NameValuePair::getValue));

			return new DIDURL(didUrlString, did, path, query, parameters, fragment, parseTree);
		} catch (ParserException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ParserException("Error while parsing DID URL " + didUrlString + ": " + ex.getMessage(), ex);
		}
	}

	public static DIDURL fromString(String string) throws IllegalArgumentException, ParserException {
		return parse(string, false);
	}

	public static DIDURL fromString(String string, boolean keepParseTree) throws IllegalArgumentException, ParserException {
		return parse(string, keepParseTree);
	}

	public static DIDURL fromUri(URI uri) throws IllegalArgumentException, ParserException {
		return fromString(uri.toString());
	}

	public static DIDURL fromUri(URI uri, boolean keepParseTree) throws IllegalArgumentException, ParserException {
		return fromString(uri.toString(), keepParseTree);
	}

	/*
	 * Helper methods
	 */

	public URI toUri() {
		return URI.create(this.getDidUrlString());
	}

	public boolean isBareDid() {
		return this.getDidUrlString().equals(this.getDid().getDidString());
	}

	public URI getUriWithoutDid() {
		if (isBareDid()) return null;
		return URI.create(this.getDidUrlString().substring(this.getDid().getDidString().length()));
	}

	public URI getUriWithoutFragment() {
		if (this.getFragment() == null) return this.toUri();
		return URI.create(this.getDidUrlString().substring(0, this.getDidUrlString().indexOf('#')));
	}

	public JsonObject toJsonObject(boolean addParseTree) {

		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder(this.toMap(addParseTree));

		jsonObjectBuilder = jsonObjectBuilder
				.add("didUrlString", this.getDidUrlString() == null ? JsonValue.NULL : Json.createValue(this.getDidUrlString()))
				.add("did", this.getDid() == null ? JsonValue.NULL : this.getDid().toJsonObject(addParseTree))
				.add("parameters", this.getParameters() == null ? JsonValue.NULL : Json.createObjectBuilder(new HashMap<String, Object>(this.getParameters())).build())
				.add("path", this.getPath() == null ? JsonValue.NULL : Json.createValue(this.getPath()))
				.add("query", this.getQuery() == null ? JsonValue.NULL : Json.createValue(this.getQuery()))
				.add("fragment", this.getFragment() == null ? JsonValue.NULL : Json.createValue(this.getFragment()));

		if (addParseTree) {
			jsonObjectBuilder = jsonObjectBuilder
					.add("parseTree", this.getParseTree() == null ? JsonValue.NULL : Json.createValue(this.getParseTree()));
		}

		return jsonObjectBuilder.build();
	}

	public JsonObject toJsonObject() {
		return this.toJsonObject(false);
	}

	public Map<String, Object> toMap(boolean addParseTree) {

		Map<String, Object> map = new HashMap<> ();

		map.put("didUrlString", this.getDidUrlString() == null ? null : this.getDidUrlString());
		map.put("did", this.getDid() == null ? null : this.getDid().toMap(addParseTree));
		map.put("parameters", this.getParameters() == null ? null : new HashMap<String, Object>(this.getParameters()));
		map.put("path", this.getPath() == null ? null : this.getPath());
		map.put("query", this.getQuery() == null ? null : this.getQuery());
		map.put("fragment", this.getFragment() == null ? null : this.getFragment());

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

		return this.didUrlString;
	}
}
