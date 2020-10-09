package foundation.identity.did;

import foundation.identity.did.jsonld.DIDContexts;
import foundation.identity.did.jsonld.DIDKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

public class Authentication extends JsonLDObject {

	private Authentication() {
		super();
	}

	public Authentication(JsonObject jsonObject) {
		super(DIDContexts.DOCUMENT_LOADER, jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<Builder, Authentication> {

		private String verificationMethod;

		public Builder() {
			super(new Authentication());
		}

		public Authentication build() {

			super.build();

			// add JSON-LD properties
			if (this.verificationMethod != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD, this.verificationMethod);

			return this.jsonLDObject;
		}

		public Builder publicKey(String verificationMethod) {
			this.verificationMethod = verificationMethod;
			return this;
		}
	}

	public static Service.Builder builder(boolean addContext) {
		Service.Builder builder = new Service.Builder();
		if (addContext) builder.context(DIDDocument.DEFAULT_JSONLD_CONTEXT.toString());
		return builder;
	}

	public static Service.Builder builder() {
		return builder(false);
	}

	public static Authentication getFromJsonLDObject(JsonLDObject jsonLdObject) {
		JsonObject jsonObject = JsonLDUtils.jsonLdGetJsonObject(jsonLdObject.getJsonObject(), DIDKeywords.JSONLD_TERM_AUTHENTICATION);
		return jsonObject == null ? null : new Authentication(jsonObject);
	}

	public void addToJsonLDObject(JsonLDObject jsonLdObject) {
		JsonLDUtils.jsonLdAddJsonValue(jsonLdObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_AUTHENTICATION, jsonLdObject.getJsonObject());
	}

	/*
	 * Serialization
	 */

	public static Authentication fromJson(Reader reader) {
		JsonObject jsonObject = Json.createReader(reader).readObject();
		return new Authentication(jsonObject);
	}

	public static Authentication fromJson(String json) {
		return fromJson(new StringReader(json));
	}

	/*
	 * Getters
	 */

	public List<VerificationMethod> getPublicKeys() {

		return JsonLDUtils.jsonLdGetJsonValueList(this.getJsonObject(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD).stream().map(x -> new VerificationMethod((JsonObject) x)).collect(Collectors.toList());
	}

	public String getPublicKey() {

		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD);
	}
}