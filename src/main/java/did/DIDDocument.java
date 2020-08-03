package did;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

import did.jsonld.JsonLDObject;
import did.jsonld.JsonLDUtils;

import javax.json.Json;
import javax.json.JsonObject;

public class DIDDocument extends JsonLDObject {

	public static final List<String> DEFAULT_CONTEXTS = Collections.singletonList("https://www.w3.org/ns/did/v1");

	public static final String MIME_TYPE_JSON_LD = "application/did+ld+json";
	public static final String MIME_TYPE_JSON = "application/did+json";
	public static final String MIME_TYPE_CBOR = "application/did+cbor";

	private DIDDocument() {
		super();
	}

	private DIDDocument(JsonObject jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static DIDDocument build(List<String> contexts, String id, List<PublicKey> publicKeys, List<Authentication> authentications, List<Service> services) {

		DIDDocument didDocument = new DIDDocument();
		didDocument.build(contexts, id, null);

		// add JSON-LD properties

		if (publicKeys != null) JsonLDUtils.jsonLdAddJsonValueList(didDocument.getJsonObjectBuilder(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEY, publicKeys.stream().map(x -> x.getJsonObject()).collect(Collectors.toList()));
		if (authentications != null) JsonLDUtils.jsonLdAddJsonValueList(didDocument.getJsonObjectBuilder(), DIDDocumentKeywords.JSONLD_TERM_AUTHENTICATION, authentications.stream().map(x -> x.getJsonObject()).collect(Collectors.toList()));
		if (services != null) JsonLDUtils.jsonLdAddJsonValueList(didDocument.getJsonObjectBuilder(), DIDDocumentKeywords.JSONLD_TERM_SERVICE, services.stream().map(x -> x.getJsonObject()).collect(Collectors.toList()));

		didDocument.build();
		return didDocument;
	}

	public static DIDDocument build(String id, List<PublicKey> publicKeys, List<Authentication> authentications, List<Service> services) {

		return build(DEFAULT_CONTEXTS, id, publicKeys, authentications, services);
	}

	public static DIDDocument build(String id) {

		return build(DEFAULT_CONTEXTS, id, null, null, null);
	}

	public static DIDDocument build(JsonObject jsonObject) {

		return new DIDDocument(jsonObject);
	}

	/*
	 * Serialization
	 */

	public static DIDDocument fromJson(String json) throws JsonLdError {

		JsonObject jsonObject = Json.createParser(new StringReader(json)).getObject();
		return new DIDDocument(jsonObject);
	}

	public static DIDDocument fromJson(Reader reader) throws JsonLdError {

		JsonObject jsonObject = Json.createParser(reader).getObject();
		return new DIDDocument(jsonObject);
	}

	/*
	 * Getters
	 */

	public List<PublicKey> getPublicKeys() {

		return JsonLDUtils.jsonLdGetJsonValueList(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEY).stream().map(x -> PublicKey.build((JsonObject) x)).collect(Collectors.toList());
	}

	public List<Authentication> getAuthentications() {

		return JsonLDUtils.jsonLdGetJsonValueList(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_AUTHENTICATION).stream().map(x -> Authentication.build((JsonObject) x)).collect(Collectors.toList());
	}

	public List<Service> getServices() {

		return JsonLDUtils.jsonLdGetJsonValueList(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_SERVICE).stream().map(x -> Service.build((JsonObject) x)).collect(Collectors.toList());
	}

	/*
	 * Object methods
	 */

	@Override
	public String toString() {

		try {
			return this.toJson();
		} catch (JsonLdError ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
	}
}
