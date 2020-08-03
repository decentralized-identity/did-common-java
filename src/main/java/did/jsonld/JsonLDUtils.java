package did.jsonld;

import did.DIDDocumentKeywords;

import javax.json.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonLDUtils {

	/*
	 * add
	 */

	public static void jsonLdAddJsonValue(JsonObjectBuilder jsonObjectBuilder, String term, JsonValue jsonValue) {

		jsonLdAddJsonValueList(jsonObjectBuilder, term, Collections.singletonList(jsonValue));
	}

	public static void jsonLdAddJsonValueList(JsonObjectBuilder jsonObjectBuilder, String term, List<JsonValue> jsonValues) {

		if (jsonObjectBuilder == null || term == null || jsonValues == null) throw new NullPointerException();
		if (jsonValues.size() < 1) return;

		JsonValue jsonValueExisting = null;

		if (jsonValueExisting == null) {
			if (jsonValues.size() == 1) {
				jsonObjectBuilder.add(term, jsonValues.get(0));
			} else {
				JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
				for (JsonValue jsonValue : jsonValues) jsonArrayBuilder.add(jsonValue);
				jsonObjectBuilder.add(term, jsonArrayBuilder);
			}
		} else if (jsonValueExisting instanceof JsonArray) {
			JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
			for (JsonValue jsonValue : ((JsonArray) jsonValueExisting)) jsonArrayBuilder.add(jsonValue);
			for (JsonValue jsonValue : jsonValues) jsonArrayBuilder.add(jsonValue);
			jsonObjectBuilder.add(term, jsonArrayBuilder);
		} else {
			JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
			jsonArrayBuilder.add(jsonValueExisting);
			for (JsonValue jsonValue : jsonValues) jsonArrayBuilder.add(jsonValue);
			jsonObjectBuilder.add(term, jsonArrayBuilder);
		}
	}

	public static void jsonLdAddString(JsonObjectBuilder jsonObjectBuilder, String term, String value) {

		jsonLdAddStringList(jsonObjectBuilder, term, Collections.singletonList(value));
	}

	public static void jsonLdAddStringList(JsonObjectBuilder jsonObjectBuilder, String term, List<String> values) {

		if (jsonObjectBuilder == null || term == null || values == null) throw new NullPointerException();
		if (values.size() < 1) return;

		JsonValue jsonValueExisting = null;

		if (jsonValueExisting == null)  {
			if (values.size() == 1) {
				jsonObjectBuilder.add(term, Json.createValue(values.get(0)));
			} else {
				JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
				for (String value : values) jsonArrayBuilder.add(Json.createValue(value));
				jsonObjectBuilder.add(term, jsonArrayBuilder);
			}
		} else if (jsonValueExisting instanceof JsonArray)  {
			JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
			for (JsonValue jsonValue : ((JsonArray) jsonValueExisting)) jsonArrayBuilder.add(jsonValue);
			for (String value : values) jsonArrayBuilder.add(Json.createValue(value));
			jsonObjectBuilder.add(term, jsonArrayBuilder);
		} else {
			JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
			jsonArrayBuilder.add(jsonValueExisting);
			for (String value : values) jsonArrayBuilder.add(Json.createValue(value));
			jsonObjectBuilder.add(term, jsonArrayBuilder);
		}
	}

	/*
	 * get
	 */

	public static JsonValue jsonLdGetJsonValue(JsonObject jsonObject, String term) {

		return jsonObject.get(term);
	}

	public static List<JsonValue> jsonLdGetJsonValueList(JsonObject jsonObject, String term) {

		JsonValue entry = jsonObject.get(term);
		if (entry == null) return null;

		if (entry instanceof JsonArray) {
			return ((JsonArray) entry);
		} else {
			return Collections.singletonList(entry);
		}
	}

	public static JsonArray jsonLdGetJsonArray(JsonObject jsonObject, String term) {

		JsonValue entry = jsonObject.get(term);
		if (entry == null) return null;

		if (entry instanceof JsonArray) {
			return ((JsonArray) entry);
		} else {
			throw new IllegalArgumentException("Cannot get json array '" + term + "' from " + jsonObject);
		}
	}

	public static JsonObject jsonLdGetJsonObject(JsonObject jsonObject, String term) {

		JsonValue entry = jsonObject.get(term);
		if (entry == null) return null;

		if (entry instanceof JsonObject) {
			return ((JsonObject) entry);
		} else {
			throw new IllegalArgumentException("Cannot get json object '" + term + "' from " + jsonObject);
		}
	}

	public static String jsonLdGetString(JsonObject jsonObject, String term) {

		JsonValue entry = jsonObject.get(term);
		if (entry == null) return null;

		if (entry instanceof JsonString) {
			return ((JsonString) entry).getString();
		} else if (entry instanceof JsonArray) {
			if (((JsonArray) entry).size() == 1 && ((JsonArray) entry).get(0) instanceof JsonString) {
				return ((JsonArray) entry).getString(0);
			} else {
				throw new IllegalArgumentException("Cannot get string '" + term + "' from " + jsonObject + " (array)");
			}
		} else {
			throw new IllegalArgumentException("Cannot get string '" + term + "' from " + jsonObject);
		}
	}

	public static List<String> jsonLdGetStringList(JsonObject jsonObject, String term) {

		JsonValue entry = jsonObject.get(DIDDocumentKeywords.JSONLD_TERM_TYPE);
		if (entry == null) return null;

		if (entry instanceof JsonString) {
			return Collections.singletonList(((JsonString) entry).getString());
		} else if (entry instanceof JsonArray) {
			return ((JsonArray) entry).stream().map(x -> ((JsonString) x).getString()).collect(Collectors.toList());
		} else {
			throw new IllegalArgumentException("Cannot get string list '" + term + "' from " + jsonObject);
		}
	}

	/*
	 * contains
	 */

	public static boolean jsonLdContainsString(JsonObject jsonObject, String value) {

		JsonValue entry = jsonObject.get(DIDDocumentKeywords.JSONLD_TERM_TYPE);
		if (entry == null) return false;

		if (entry instanceof JsonString)
			return ((JsonString) entry).getString().equals(value);
		else if (entry instanceof JsonArray)
			return ((JsonArray) entry).contains(value);
		else
			return false;
	}
}