package foundation.identity.did;

import foundation.identity.did.jsonld.DIDKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import javax.json.JsonObject;
import java.util.List;
import java.util.stream.Collectors;

public class Authentication extends JsonLDObject {

	private Authentication() {
		super();
	}

	public Authentication(JsonObject jsonObject) {
		super(jsonObject);
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

	public static Builder builder() {

		return new Builder();
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