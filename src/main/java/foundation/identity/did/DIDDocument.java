package foundation.identity.did;

import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.did.jsonld.DIDContexts;
import foundation.identity.did.jsonld.DIDKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DIDDocument extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { DIDContexts.JSONLD_CONTEXT_W3_NS_DID_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { };
	public static final String DEFAULT_JSONLD_PREDICATE = null;

	public static final String MIME_TYPE_JSON_LD = "application/did+ld+json";
	public static final String MIME_TYPE_JSON = "application/did+json";
	public static final String MIME_TYPE_CBOR = "application/did+cbor";

	@JsonCreator
	private DIDDocument() {
		super(DIDContexts.DOCUMENT_LOADER);
	}

	public DIDDocument(Map<String, Object> jsonObject) {
		super(DIDContexts.DOCUMENT_LOADER, jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<Builder, DIDDocument> {

		private List<VerificationMethod> verificationMethods;
		private List<PublicKey> publicKeys;
		private List<Authentication> authentications;
		private List<Service> services;

		public Builder(DIDDocument jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public DIDDocument build() {

			super.build();

			// add JSON-LD properties
			if (this.verificationMethods != null) for (VerificationMethod verificationMethod : this.verificationMethods) verificationMethod.addToJsonLDObject(this.jsonLDObject);
			if (this.publicKeys != null) for (PublicKey publicKey : this.publicKeys) publicKey.addToJsonLDObject(this.jsonLDObject);
			if (this.authentications != null) for (Authentication authentication : this.authentications) authentication.addToJsonLDObject(this.jsonLDObject);
			if (this.services != null) for (Service service : this.services) service.addToJsonLDObject(this.jsonLDObject);

			return this.jsonLDObject;
		}

		public Builder verificationMethods(List<VerificationMethod> verificationMethods) {
			this.verificationMethods = new ArrayList<VerificationMethod> (verificationMethods);
			return this;
		}

		public Builder verificationMethod(VerificationMethod verificationMethod) {
			return this.verificationMethods(Collections.singletonList(verificationMethod));
		}

		public Builder publicKeys(List<PublicKey> publicKeys) {
			this.publicKeys = new ArrayList<PublicKey> (publicKeys);
			return this;
		}

		public Builder publicKey(PublicKey publicKey) {
			return this.publicKeys(Collections.singletonList(publicKey));
		}

		public Builder authentications(List<Authentication> authentications) {
			this.authentications = new ArrayList<Authentication> (authentications);
			return this;
		}

		public Builder authentication(Authentication authentication) {
			return this.authentications(Collections.singletonList(authentication));
		}

		public Builder services(List<Service> services) {
			this.services = new ArrayList<Service> (services);
			return this;
		}

		public Builder service(Service service) {
			return this.services(Collections.singletonList(service));
		}
	}

	public static Builder builder() {
		return new Builder(new DIDDocument())
				.defaultContexts(true)
				.defaultTypes(true);
	}

	/*
	 * Reading the JSON-LD object
	 */

	public static DIDDocument fromJson(Reader reader) {
		return JsonLDObject.fromJson(DIDDocument.class, reader);
	}

	public static DIDDocument fromJson(String json) {
		return JsonLDObject.fromJson(DIDDocument.class, json);
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static DIDDocument getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(DIDDocument.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(DIDDocument.class, jsonLdObject);
	}

	/*
	 * Getters
	 */

	public List<VerificationMethod> getVerificationMethods() {
		return JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD).stream().map(x -> new VerificationMethod((Map<String, Object>) x)).collect(Collectors.toList());
	}

	public List<Authentication> getAuthentications() {
		return JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_AUTHENTICATION).stream().map(x -> new Authentication((Map<String, Object>) x)).collect(Collectors.toList());
	}

	public List<Service> getServices() {
		return JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_SERVICE).stream().map(x -> new Service((Map<String, Object>) x)).collect(Collectors.toList());
	}
}
