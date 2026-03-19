package foundation.identity.did;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.did.jsonld.DIDContexts;
import foundation.identity.jsonld.JsonLDObject;

import java.io.Reader;
import java.net.URI;
import java.util.Map;

public class DIDDocumentV1_0 extends DIDDocument {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { DIDContexts.JSONLD_CONTEXT_W3_NS_DID_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { };
	public static final String DEFAULT_JSONLD_PREDICATE = null;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = DIDContexts.DOCUMENT_LOADER;

	@JsonCreator
	public DIDDocumentV1_0() {
		super();
	}

	protected DIDDocumentV1_0(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends DIDDocument.Builder<B> {

		public Builder(DIDDocumentV1_0 jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public DIDDocumentV1_0 build() {

			super.build();

			return (DIDDocumentV1_0) this.jsonLdObject;
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder<>(new DIDDocumentV1_0());
	}

	public static DIDDocumentV1_0 fromJsonObject(Map<String, Object> jsonObject) {
		return new DIDDocumentV1_0(jsonObject);
	}

	public static DIDDocumentV1_0 fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

	public static DIDDocumentV1_0 fromJson(Reader reader) {
		return new DIDDocumentV1_0(readJson(reader));
	}

	public static DIDDocumentV1_0 fromJson(String json) {
		return new DIDDocumentV1_0(readJson(json));
	}

	public static DIDDocumentV1_0 fromMap(Map<String, Object> map) {
		return new DIDDocumentV1_0(map);
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static DIDDocumentV1_0 getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(DIDDocumentV1_0.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(DIDDocumentV1_0.class, jsonLdObject);
	}
}
