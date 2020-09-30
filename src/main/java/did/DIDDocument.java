package did;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

import did.jsonld.DIDKeywords;
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

	public DIDDocument(JsonObject jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<Builder, DIDDocument> {

		private List<PublicKey> publicKeys;
		private List<Authentication> authentications;
		private List<Service> services;

		public Builder() {
			super(new DIDDocument());
		}

		public DIDDocument build() {

			super.build();

			// add JSON-LD properties
			if (this.publicKeys != null) JsonLDUtils.jsonLdAddJsonValueList(this.jsonLDObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_PUBLICKEY, this.publicKeys.stream().map(x -> x.getJsonObject()).collect(Collectors.toList()));
			if (this.authentications != null) JsonLDUtils.jsonLdAddJsonValueList(this.jsonLDObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_AUTHENTICATION, this.authentications.stream().map(x -> x.getJsonObject()).collect(Collectors.toList()));
			if (this.services != null) JsonLDUtils.jsonLdAddJsonValueList(this.jsonLDObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_SERVICE, this.services.stream().map(x -> x.getJsonObject()).collect(Collectors.toList()));

			return this.jsonLDObject;
		}

		public Builder publicKeys(List<PublicKey> publicKeys) {
			this.publicKeys = new ArrayList<PublicKey> (publicKeys);
			return this;
		}

		public Builder publicKey(PublicKey publicKey) {
			return this.publicKeys(Collections.singletonList(publicKey));
		}

		public Builder authentications(List<Authentication> authentications) {
			this.authentications = new ArrayList<Authentication> (authentications);
			return this;
		}

		public Builder authentication(Authentication authentication) {
			return this.authentications(Collections.singletonList(authentication));
		}

		public Builder services(List<Service> services) {
			this.services = new ArrayList<Service> (services);
			return this;
		}

		public Builder service(Service service) {
			return this.services(Collections.singletonList(service));
		}
	}

	public static Builder builder() {

		return new Builder().contexts(DEFAULT_CONTEXTS);
	}

	/*
	 * Serialization
	 */

	public static DIDDocument fromJson(String json) {

		JsonObject jsonObject = Json.createParser(new StringReader(json)).getObject();
		return new DIDDocument(jsonObject);
	}

	public static DIDDocument fromJson(Reader reader) {

		JsonObject jsonObject = Json.createParser(reader).getObject();
		return new DIDDocument(jsonObject);
	}

	/*
	 * Getters
	 */

	public List<PublicKey> getPublicKeys() {

		return JsonLDUtils.jsonLdGetJsonValueList(this.getJsonObject(), DIDKeywords.JSONLD_TERM_PUBLICKEY).stream().map(x -> new PublicKey((JsonObject) x)).collect(Collectors.toList());
	}

	public List<Authentication> getAuthentications() {

		return JsonLDUtils.jsonLdGetJsonValueList(this.getJsonObject(), DIDKeywords.JSONLD_TERM_AUTHENTICATION).stream().map(x -> new Authentication((JsonObject) x)).collect(Collectors.toList());
	}

	public List<Service> getServices() {

		return JsonLDUtils.jsonLdGetJsonValueList(this.getJsonObject(), DIDKeywords.JSONLD_TERM_SERVICE).stream().map(x -> new Service((JsonObject) x)).collect(Collectors.toList());
	}

	/*
	 * Object methods
	 */

	@Override
	public String toString() {

		return this.toJson();
	}
}
