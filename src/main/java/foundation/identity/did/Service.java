package foundation.identity.did;

import foundation.identity.did.jsonld.DIDContexts;
import foundation.identity.did.jsonld.DIDKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.Reader;
import java.io.StringReader;

public class Service extends JsonLDObject {

	private Service() {
		super();
	}

	public Service(JsonObject jsonObject) {
		super(DIDContexts.DOCUMENT_LOADER, jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<Builder, Service> {

		private String serviceEndpoint;

		public Builder() {
			super(new Service());
		}

		public Service build() {

			super.build();

			// add JSON-LD properties
			if (this.serviceEndpoint != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_SERVICEENDPOINT, this.serviceEndpoint);

			return this.jsonLDObject;
		}

		public Builder serviceEndpoint(String serviceEndpoint) {
			this.serviceEndpoint = serviceEndpoint;
			return this;
		}
	}

	public static Builder builder(boolean addContext) {
		Builder builder = new Builder();
		if (addContext) builder.context(DIDDocument.DEFAULT_JSONLD_CONTEXT.toString());
		return builder;
	}

	public static Builder builder() {
		return builder(false);
	}

	public static Service getFromJsonLDObject(JsonLDObject jsonLdObject) {
		JsonObject jsonObject = JsonLDUtils.jsonLdGetJsonObject(jsonLdObject.getJsonObject(), DIDKeywords.JSONLD_TERM_SERVICE);
		return jsonObject == null ? null : new Service(jsonObject);
	}

	public void addToJsonLDObject(JsonLDObject jsonLdObject) {
		JsonLDUtils.jsonLdAddJsonValue(jsonLdObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_SERVICE, jsonLdObject.getJsonObject());
	}

	/*
	 * Serialization
	 */

	public static Service fromJson(Reader reader) {
		JsonObject jsonObject = Json.createReader(reader).readObject();
		return new Service(jsonObject);
	}

	public static Service fromJson(String json) {
		return fromJson(new StringReader(json));
	}

	/*
	 * Getters
	 */

	public String getServiceEndpoint() {

		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDKeywords.JSONLD_TERM_SERVICEENDPOINT);
	}
}