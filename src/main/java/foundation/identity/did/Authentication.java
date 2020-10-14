package foundation.identity.did;

import foundation.identity.did.jsonld.DIDContexts;
import foundation.identity.did.jsonld.DIDKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.Reader;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Authentication extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { DIDContexts.JSONLD_CONTEXT_W3_NS_DID_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { };
	public static final String DEFAULT_JSONLD_PREDICATE = DIDKeywords.JSONLD_TERM_AUTHENTICATION;

	private Authentication() {
		super(DIDContexts.DOCUMENT_LOADER);
	}

	public Authentication(Map<String, Object> jsonObject) {
		super(DIDContexts.DOCUMENT_LOADER, jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<Builder, Authentication> {

		private URI verificationMethod;

		public Builder(Authentication jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public Authentication build() {

			super.build();

			// add JSON-LD properties
			if (this.verificationMethod != null) JsonLDUtils.jsonLdAdd(this.jsonLDObject, DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD, JsonLDUtils.uriToString(this.verificationMethod));

			return this.jsonLDObject;
		}

		public Builder verificationMethod(URI verificationMethod) {
			this.verificationMethod = verificationMethod;
			return this;
		}
	}

	public static Builder builder() {
		return new Builder(new Authentication());
	}

	/*
	 * Reading the JSON-LD object
	 */

	public static Authentication fromJson(Reader reader) {
		return JsonLDObject.fromJson(Authentication.class, reader);
	}

	public static Authentication fromJson(String json) {
		return JsonLDObject.fromJson(Authentication.class, json);
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static Authentication getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(Authentication.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(Authentication.class, jsonLdObject);
	}

	/*
	 * Getters
	 */

	public List<VerificationMethod> getVerificationMethods() {
		return JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD).stream().map(x -> new VerificationMethod((Map<String, Object>) x)).collect(Collectors.toList());
	}

	public URI URI() {
		return JsonLDUtils.stringToUri(JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD));
	}
}