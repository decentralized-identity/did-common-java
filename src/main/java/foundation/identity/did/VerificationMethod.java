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

public class VerificationMethod extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { DIDContexts.JSONLD_CONTEXT_W3_NS_DID_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { };
	public static final String DEFAULT_JSONLD_PREDICATE = DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = DIDContexts.DOCUMENT_LOADER;

	@JsonCreator
	public VerificationMethod() {
		super();
	}

	protected VerificationMethod(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends JsonLDObject.Builder<B> {

		private String publicKeyBase64;
		private String publicKeyBase58;
		private String publicKeyHex;
		private String publicKeyPem;
		private Map<String, Object> publicKeyJwk;

		public Builder(VerificationMethod jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public VerificationMethod build() {

			super.build();

			// add JSON-LD properties
			if (this.publicKeyBase64 != null) JsonLDUtils.jsonLdAdd(this.jsonLDObject, DIDKeywords.JSONLD_TERM_PUBLICKEYBASE64, this.publicKeyBase64);
			if (this.publicKeyBase58 != null) JsonLDUtils.jsonLdAdd(this.jsonLDObject, DIDKeywords.JSONLD_TERM_PUBLICKEYBASE58, this.publicKeyBase58);
			if (this.publicKeyHex != null) JsonLDUtils.jsonLdAdd(this.jsonLDObject, DIDKeywords.JSONLD_TERM_PUBLICKEYHEX, this.publicKeyHex);
			if (this.publicKeyPem != null) JsonLDUtils.jsonLdAdd(this.jsonLDObject, DIDKeywords.JSONLD_TERM_PUBLICKEYPEM, this.publicKeyPem);
			if (this.publicKeyJwk != null) JsonLDUtils.jsonLdAdd(this.jsonLDObject, DIDKeywords.JSONLD_TERM_PUBLICKEYJWK, this.publicKeyJwk);

			return (VerificationMethod) this.jsonLDObject;
		}

		public B publicKeyBase64(String publicKeyBase64) {
			this.publicKeyBase64 = publicKeyBase64;
			return (B) this;
		}

		public B publicKeyBase58(String publicKeyBase58) {
			this.publicKeyBase58 = publicKeyBase58;
			return (B) this;
		}

		public B publicKeyHex(String publicKeyHex) {
			this.publicKeyHex = publicKeyHex;
			return (B) this;
		}

		public B publicKeyPem(String publicKeyPem) {
			this.publicKeyPem = publicKeyPem;
			return (B) this;
		}

		public B publicKeyJwk(Map<String, Object> publicKeyJwk) {
			this.publicKeyJwk = publicKeyJwk;
			return (B) this;
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder(new VerificationMethod());
	}

	public static VerificationMethod fromJsonObject(Map<String, Object> jsonObject) {
		return new VerificationMethod(jsonObject);
	}

	public static VerificationMethod fromJson(Reader reader) {
		return new VerificationMethod(readJson(reader));
	}

	public static VerificationMethod fromJson(String json) {
		return new VerificationMethod(readJson(json));
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static VerificationMethod getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(VerificationMethod.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(VerificationMethod.class, jsonLdObject);
	}

	/*
	 * Getters
	 */

	public String getPublicKeyBase64() {
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDKeywords.JSONLD_TERM_PUBLICKEYBASE64);
	}

	public String getPublicKeyBase58() {
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDKeywords.JSONLD_TERM_PUBLICKEYBASE58);
	}

	public String getPublicKeyHex() {
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDKeywords.JSONLD_TERM_PUBLICKEYHEX);
	}

	public String getPublicKeyPem() {
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDKeywords.JSONLD_TERM_PUBLICKEYPEM);
	}

	public Map<String, Object> getPublicKeyJwk() {
		return JsonLDUtils.jsonLdGetJsonObject(this.getJsonObject(), DIDKeywords.JSONLD_TERM_PUBLICKEYJWK);
	}
}