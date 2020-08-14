package did;

import did.jsonld.DIDKeywords;
import did.jsonld.JsonLDObject;
import did.jsonld.JsonLDUtils;

import javax.json.JsonObject;
import java.util.Collections;
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

		private String publicKey;

		public Builder() {
			super(new Authentication());
		}

		public Authentication build() {

			super.build();

			// add JSON-LD properties
			if (this.publicKey != null) JsonLDUtils.jsonLdAddString(this.jsonLDObject.getJsonObjectBuilder(), DIDKeywords.JSONLD_TERM_PUBLICKEY, this.publicKey);

			return this.jsonLDObject;
		}

		public Builder publicKey(String publicKey) {
			this.publicKey = publicKey;
			return this;
		}
	}

	public static Builder builder() {

		return new Builder();
	}

	/*
	 * Getters
	 */

	public List<PublicKey> getPublicKeys() {

		return JsonLDUtils.jsonLdGetJsonValueList(this.getJsonObject(), DIDKeywords.JSONLD_TERM_PUBLICKEY).stream().map(x -> new PublicKey((JsonObject) x)).collect(Collectors.toList());
	}

	public String getPublicKey() {

		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDKeywords.JSONLD_TERM_PUBLICKEY);
	}
}