package did;

import did.jsonld.JsonLDObject;
import did.jsonld.JsonLDUtils;

import javax.json.JsonObject;
import java.util.Collections;
import java.util.List;

public class PublicKey extends JsonLDObject {

	private PublicKey() {
		super();
	}

	private PublicKey(JsonObject jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static PublicKey build(String id, List<String> types, String publicKeyBase64, String publicKeyBase58, String publicKeyHex, String publicKeyPem, JsonObject publicKeyJwk) {

		PublicKey publicKey = new PublicKey();
		publicKey.build(null, id, types);

		// add JSON-LD properties

		if (publicKeyBase64 != null) JsonLDUtils.jsonLdAddString(publicKey.getJsonObjectBuilder(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEYBASE64, publicKeyBase64);
		if (publicKeyBase58 != null) JsonLDUtils.jsonLdAddString(publicKey.getJsonObjectBuilder(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEYBASE58, publicKeyBase58);
		if (publicKeyHex != null) JsonLDUtils.jsonLdAddString(publicKey.getJsonObjectBuilder(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEYHEX, publicKeyHex);
		if (publicKeyPem != null) JsonLDUtils.jsonLdAddString(publicKey.getJsonObjectBuilder(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEYPEM, publicKeyPem);
		if (publicKeyJwk != null) JsonLDUtils.jsonLdAddJsonValue(publicKey.getJsonObjectBuilder(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEYJWK, publicKeyJwk);

		publicKey.build();
		return publicKey;
	}

	public static PublicKey build(String id, String type, String publicKeyBase64, String publicKeyBase58, String publicKeyHex, String publicKeyPem, JsonObject publicKeyJwk) {

		return build(id, Collections.singletonList(type), publicKeyBase64, publicKeyBase58, publicKeyHex, publicKeyPem, publicKeyJwk);
	}

	public static PublicKey build(List<String> types, String publicKeyBase64, String publicKeyBase58, String publicKeyHex, String publicKeyPem, JsonObject publicKeyJwk) {

		return build(null, types, publicKeyBase64, publicKeyBase58, publicKeyHex, publicKeyPem, publicKeyJwk);
	}

	public static PublicKey build(String type, String publicKeyBase64, String publicKeyBase58, String publicKeyHex, String publicKeyPem, JsonObject publicKeyJwk) {

		return build(null, Collections.singletonList(type), publicKeyBase64, publicKeyBase58, publicKeyHex, publicKeyPem, publicKeyJwk);
	}

	public static PublicKey build(JsonObject jsonObject) {

		return new PublicKey(jsonObject);
	}

	/*
	 * Getters
	 */

	public String getPublicKeyBase64() {

		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEYBASE64);
	}

	public String getPublicKeyBase58() {

		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEYBASE58);
	}

	public String getPublicKeyHex() {

		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEYHEX);
	}

	public String getPublicKeyPem() {

		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEYPEM);
	}

	public JsonObject getPublicKeyJwk() {

		return JsonLDUtils.jsonLdGetJsonObject(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_PUBLICKEYJWK);
	}
}