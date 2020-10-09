package foundation.identity.did;

import foundation.identity.did.jsonld.DIDContexts;
import foundation.identity.did.jsonld.DIDKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.Reader;
import java.io.StringReader;

public class VerificationMethod extends JsonLDObject {

	private VerificationMethod() {
		super(DIDContexts.DOCUMENT_LOADER);
	}

	public VerificationMethod(JsonObject jsonObject) {
		super(DIDContexts.DOCUMENT_LOADER, jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<Builder, VerificationMethod> {

		private String publicKeyBase64;
		private String publicKeyBase58;
		private String publicKeyHex;
		private String publicKeyPem;
		private JsonObject publicKeyJwk;

		public Builder() {
			super(new VerificationMethod());
		}

		@Override
		public VerificationMethod build() {

			super.build();

			// add JSON-LD properties
			if (this.publicKeyBase64 != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_PUBLICKEYBASE64, this.publicKeyBase64);
			if (this.publicKeyBase58 != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_PUBLICKEYBASE58, this.publicKeyBase58);
			if (this.publicKeyHex != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_PUBLICKEYHEX, this.publicKeyHex);
			if (this.publicKeyPem != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_PUBLICKEYPEM, this.publicKeyPem);
			if (this.publicKeyJwk != null) JsonLDUtils.jsonLdAddJsonValue(this.jsonLDObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_PUBLICKEYJWK, this.publicKeyJwk);

			return this.jsonLDObject;
		}

		public Builder publicKeyBase64(String publicKeyBase64) {
			this.publicKeyBase64 = publicKeyBase64;
			return this;
		}

		public Builder publicKeyBase58(String publicKeyBase58) {
			this.publicKeyBase58 = publicKeyBase58;
			return this;
		}

		public Builder publicKeyHex(String publicKeyHex) {
			this.publicKeyHex = publicKeyHex;
			return this;
		}

		public Builder publicKeyPem(String publicKeyPem) {
			this.publicKeyPem = publicKeyPem;
			return this;
		}

		public Builder publicKeyJwk(JsonObject publicKeyJwk) {
			this.publicKeyJwk = publicKeyJwk;
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

	public static VerificationMethod getFromJsonLDObject(JsonLDObject jsonLdObject) {
		JsonObject jsonObject = JsonLDUtils.jsonLdGetJsonObject(jsonLdObject.getJsonObject(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD);
		return jsonObject == null ? null : new VerificationMethod(jsonObject);
	}

	public void addToJsonLDObject(JsonLDObject jsonLdObject) {
		JsonLDUtils.jsonLdAddJsonValue(jsonLdObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD, jsonLdObject.getJsonObject());
	}

	/*
	 * Serialization
	 */

	public static VerificationMethod fromJson(Reader reader) {
		JsonObject jsonObject = Json.createReader(reader).readObject();
		return new VerificationMethod(jsonObject);
	}

	public static VerificationMethod fromJson(String json) {
		return fromJson(new StringReader(json));
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

	public JsonObject getPublicKeyJwk() {

		return JsonLDUtils.jsonLdGetJsonObject(this.getJsonObject(), DIDKeywords.JSONLD_TERM_PUBLICKEYJWK);
	}
}