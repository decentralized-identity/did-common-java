package did;

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

	private Authentication(JsonObject jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static Authentication build(String id, List<String> types, String publicKey) {

		Authentication authentication = new Authentication();
		authentication.build(null, id, types);

		// add JSON-LD properties

		if (publicKey != null) JsonLDUtils.jsonLdAddString(authentication.getJsonObjectBuilder(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEY, publicKey);

		authentication.build();
		return authentication;
	}

	public static Authentication build(String id, String type, String publicKey) {

		return build(id, Collections.singletonList(type), publicKey);
	}

	public static Authentication build(List<String> types, String publicKey) {

		return build(null, types, publicKey);
	}

	public static Authentication build(String type, String publicKey) {

		return build(null, Collections.singletonList(type), publicKey);
	}

	public static Authentication build(JsonObject jsonObject) {

		return new Authentication(jsonObject);
	}

	/*
	 * Getters
	 */

	public List<PublicKey> getPublicKeys() {

		return JsonLDUtils.jsonLdGetJsonValueList(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEY).stream().map(x -> PublicKey.build((JsonObject) x)).collect(Collectors.toList());
	}

	public String getPublicKey() {

		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEY);
	}
}