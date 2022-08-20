package foundation.identity.did;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.fasterxml.jackson.annotation.JsonCreator;
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
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = DIDContexts.DOCUMENT_LOADER;

	@JsonCreator
	public Service() {
		super();
	}

	protected Service(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends JsonLDObject.Builder<B> {

		private Object serviceEndpoint;

		public Builder(Service jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public Service build() {

			super.build();

			// add JSON-LD properties
			if (this.serviceEndpoint != null) JsonLDUtils.jsonLdAdd(this.jsonLdObject, DIDKeywords.JSONLD_TERM_SERVICEENDPOINT, this.serviceEndpoint);

			return (Service) this.jsonLdObject;
		}

		public B serviceEndpoint(Object serviceEndpoint) {
			this.serviceEndpoint = serviceEndpoint;
			return (B) this;
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder(new Service());
	}

	public static Service fromJsonObject(Map<String, Object> jsonObject) {
		return new Service(jsonObject);
	}

	public static Service fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

	public static Service fromJson(Reader reader) {
		return new Service(readJson(reader));
	}

	public static Service fromJson(String json) {
		return new Service(readJson(json));
	}

	public static Service fromMap(Map<String, Object> map) {
		return new Service(map);
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

	public Object getServiceEndpoint() {
		return JsonLDUtils.jsonLdGetJsonValue(this.getJsonObject(), DIDKeywords.JSONLD_TERM_SERVICEENDPOINT);
	}
}