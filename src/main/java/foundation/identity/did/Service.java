package foundation.identity.did;

import foundation.identity.did.jsonld.DIDContexts;
import foundation.identity.did.jsonld.DIDKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.Reader;
import java.net.URI;
import java.util.Map;

public class Service extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { DIDContexts.JSONLD_CONTEXT_W3_NS_DID_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { };
	public static final String DEFAULT_JSONLD_PREDICATE = DIDKeywords.JSONLD_TERM_SERVICE;

	private Service() {
		super(DIDContexts.DOCUMENT_LOADER);
	}

	public Service(Map<String, Object> jsonObject) {
		super(DIDContexts.DOCUMENT_LOADER, jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<Builder, Service> {

		private String serviceEndpoint;

		public Builder(Service jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public Service build() {

			super.build();

			// add JSON-LD properties
			if (this.serviceEndpoint != null) JsonLDUtils.jsonLdAdd(this.jsonLDObject, DIDKeywords.JSONLD_TERM_SERVICEENDPOINT, this.serviceEndpoint);

			return this.jsonLDObject;
		}

		public Builder serviceEndpoint(String serviceEndpoint) {
			this.serviceEndpoint = serviceEndpoint;
			return this;
		}
	}

	public static Builder builder() {
		return new Builder(new Service());
	}

	/*
	 * Reading the JSON-LD object
	 */

	public static Service fromJson(Reader reader) {
		return JsonLDObject.fromJson(Service.class, reader);
	}

	public static Service fromJson(String json) {
		return JsonLDObject.fromJson(Service.class, json);
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static Service getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(Service.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(Service.class, jsonLdObject);
	}

	/*
	 * Getters
	 */

	public String getServiceEndpoint() {
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDKeywords.JSONLD_TERM_SERVICEENDPOINT);
	}
}