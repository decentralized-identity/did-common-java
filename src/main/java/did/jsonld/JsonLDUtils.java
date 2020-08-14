package did.jsonld;

import javax.json.*;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class JsonLDUtils {

	/*
	 * convert
	 */

	public static final SimpleDateFormat DATE_FORMAT;
	public static final SimpleDateFormat DATE_FORMAT_MILLIS;

	static {

		DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));

		DATE_FORMAT_MILLIS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
		DATE_FORMAT_MILLIS.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	public static URI stringToUri(String string) {
		return string == null ? null : URI.create(string);
	}

	public static String uriToString(URI uri) {
		return uri == null ? null : uri.toString();
	}

	public static Date stringToDate(String string) {
		try {
			return string == null ? null : DATE_FORMAT.parse(string);
		} catch (ParseException ex) {
			try {
				return DATE_FORMAT_MILLIS.parse(string);
			} catch (ParseException ex2) {
				throw new RuntimeException(ex.getMessage(), ex);
			}
		}
	}

	public static String dateToString(Date date) {
		return date == null ? null : DATE_FORMAT.format(date);
	}

	/*
	 * add
	 */

	public static void jsonLdAddAll(JsonObjectBuilder jsonObjectBuilder, JsonObject jsonObject) {

		for (Map.Entry<String, JsonValue> entry : jsonObject.entrySet())
			jsonObjectBuilder.add(entry.getKey(), entry.getValue());
	}

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
	 * remove
	 */

	public static void jsonLdRemove(JsonObjectBuilder jsonObjectBuilder, String term) {

		jsonObjectBuilder.remove(term);
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

		JsonValue entry = jsonObject.get(JsonLDKeywords.JSONLD_TERM_TYPE);
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

		JsonValue entry = jsonObject.get(JsonLDKeywords.JSONLD_TERM_TYPE);
		if (entry == null) return false;

		if (entry instanceof JsonString)
			return ((JsonString) entry).getString().equals(value);
		else if (entry instanceof JsonArray)
			return ((JsonArray) entry).contains(value);
		else
			return false;
	}
}