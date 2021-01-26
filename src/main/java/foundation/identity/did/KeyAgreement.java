package foundation.identity.did;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.did.jsonld.DIDContexts;
import foundation.identity.did.jsonld.DIDKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.Reader;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeyAgreement extends VerificationRelationship {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { DIDContexts.JSONLD_CONTEXT_W3_NS_DID_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { };
	public static final String DEFAULT_JSONLD_PREDICATE = DIDKeywords.JSONLD_TERM_KEYAGREEMENT;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = DIDContexts.DOCUMENT_LOADER;

	@JsonCreator
	public KeyAgreement() {
		super();
	}

	protected KeyAgreement(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends JsonLDObject.Builder<B> {

		private URI verificationMethod;

		public Builder(KeyAgreement jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public KeyAgreement build() {

			super.build();

			// add JSON-LD properties
			if (this.verificationMethod != null) JsonLDUtils.jsonLdAdd(this.jsonLDObject, DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD, JsonLDUtils.uriToString(this.verificationMethod));

			return (KeyAgreement) this.jsonLDObject;
		}

		public B verificationMethod(URI verificationMethod) {
			this.verificationMethod = verificationMethod;
			return (B) this;
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder(new KeyAgreement());
	}

	public static KeyAgreement fromJsonObject(Map<String, Object> jsonObject) {
		return new KeyAgreement(jsonObject);
	}

	public static KeyAgreement fromJson(Reader reader) {
		return new KeyAgreement(readJson(reader));
	}

	public static KeyAgreement fromJson(String json) {
		return new KeyAgreement(readJson(json));
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static KeyAgreement getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(KeyAgreement.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(KeyAgreement.class, jsonLdObject);
	}

	/*
	 * Getters
	 */

	public List<VerificationMethod> getVerificationMethods() {
		List<Object> jsonArray = JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD);
		return jsonArray == null ? null : jsonArray.stream().map(x -> VerificationMethod.fromJsonObject((Map<String, Object>) x)).collect(Collectors.toList());
	}

	public URI getVerificationMethodURI() {
		return JsonLDUtils.stringToUri(JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD));
	}
}