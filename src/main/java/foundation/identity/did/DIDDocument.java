package foundation.identity.did;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.did.jsonld.DIDContexts;
import foundation.identity.did.jsonld.DIDKeywords;
import foundation.identity.jsonld.JsonLDDereferencer;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.Reader;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DIDDocument extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { DIDContexts.JSONLD_CONTEXT_W3_NS_DID_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { };
	public static final String DEFAULT_JSONLD_PREDICATE = null;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = DIDContexts.DOCUMENT_LOADER;

	public static final String MIME_TYPE_JSON_LD = "application/did+ld+json";
	public static final String MIME_TYPE_JSON = "application/did+json";
	public static final String MIME_TYPE_CBOR = "application/did+cbor";

	@JsonCreator
	public DIDDocument() {
		super();
	}

	protected DIDDocument(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends JsonLDObject.Builder<B> {

		private List<VerificationMethod> verificationMethods;
		private List<PublicKey> publicKeys;
		private List<Authentication> authentications;
		private List<Service> services;

		public Builder(DIDDocument jsonLDObject) {
			super(jsonLDObject);
			this.forceContextsArray(true);
			this.forceTypesArray(true);
			this.defaultContexts(true);
			this.defaultTypes(false);
		}

		@Override
		public DIDDocument build() {

			super.build();

			// add JSON-LD properties
			if (this.verificationMethods != null) for (VerificationMethod verificationMethod : this.verificationMethods) verificationMethod.addToJsonLDObjectAsJsonArray(this.jsonLDObject);
			if (this.publicKeys != null) for (PublicKey publicKey : this.publicKeys) publicKey.addToJsonLDObjectAsJsonArray(this.jsonLDObject);
			if (this.authentications != null) for (Authentication authentication : this.authentications) authentication.addToJsonLDObjectAsJsonArray(this.jsonLDObject);
			if (this.services != null) for (Service service : this.services) service.addToJsonLDObjectAsJsonArray(this.jsonLDObject);

			return (DIDDocument) this.jsonLDObject;
		}

		public B verificationMethods(List<VerificationMethod> verificationMethods) {
			this.verificationMethods = verificationMethods;
			return (B) this;
		}

		public B verificationMethod(VerificationMethod verificationMethod) {
			return this.verificationMethods(verificationMethod == null ? null : Collections.singletonList(verificationMethod));
		}

		public B publicKeys(List<PublicKey> publicKeys) {
			this.publicKeys = publicKeys;
			return (B) this;
		}

		public B publicKey(PublicKey publicKey) {
			return this.publicKeys(publicKey == null ? null : Collections.singletonList(publicKey));
		}

		public B authentications(List<Authentication> authentications) {
			this.authentications = authentications;
			return (B) this;
		}

		public B authentication(Authentication authentication) {
			return this.authentications(authentication == null ? null : Collections.singletonList(authentication));
		}

		public B services(List<Service> services) {
			this.services = services;
			return (B) this;
		}

		public B service(Service service) {
			return this.services(service == null ? null : Collections.singletonList(service));
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder(new DIDDocument());
	}

	public static DIDDocument fromJsonObject(Map<String, Object> jsonObject) {
		return new DIDDocument(jsonObject);
	}

	public static DIDDocument fromJson(Reader reader) {
		return new DIDDocument(readJson(reader));
	}

	public static DIDDocument fromJson(String json) {
		return new DIDDocument(readJson(json));
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
		List<Object> jsonArray = JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD);
		return jsonArray == null ? null : jsonArray.stream().map(x -> VerificationMethod.fromJsonObject((Map<String, Object>) x)).collect(Collectors.toList());
	}

	public List<Authentication> getAuthentications() {
		List<Object> jsonArray = JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_AUTHENTICATION);
		return jsonArray == null ? null : jsonArray.stream().map(new JsonLDDereferencer.Function(this, this.getId())).map(x -> Authentication.fromJsonObject(x.getJsonObject())).collect(Collectors.toList());
	}

	public List<AssertionMethod> getAssertionMethods() {
		List<Object> jsonArray = JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_ASSERTIONMETHOD);
		return jsonArray == null ? null : jsonArray.stream().map(new JsonLDDereferencer.Function(this, this.getId())).map(x -> AssertionMethod.fromJsonObject(x.getJsonObject())).collect(Collectors.toList());
	}

	public List<KeyAgreement> getKeyAgreements() {
		List<Object> jsonArray = JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_KEYAGREEMENT);
		return jsonArray == null ? null : jsonArray.stream().map(new JsonLDDereferencer.Function(this, this.getId())).map(x -> KeyAgreement.fromJsonObject(x.getJsonObject())).collect(Collectors.toList());
	}

	public List<CapabilityInvocation> getCapabilityInvocations() {
		List<Object> jsonArray = JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_CAPABILITYINVOCATION);
		return jsonArray == null ? null : jsonArray.stream().map(new JsonLDDereferencer.Function(this, this.getId())).map(x -> CapabilityInvocation.fromJsonObject(x.getJsonObject())).collect(Collectors.toList());
	}

	public List<CapabilityDelegation> getCapabilityDelegations() {
		List<Object> jsonArray = JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_CAPABILITYDELEGATION);
		return jsonArray == null ? null : jsonArray.stream().map(new JsonLDDereferencer.Function(this, this.getId())).map(x -> CapabilityDelegation.fromJsonObject(x.getJsonObject())).collect(Collectors.toList());
	}

	public List<Service> getServices() {
		List<Object> jsonArray = JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_SERVICE);
		return jsonArray == null ? null : jsonArray.stream().map(x -> Service.fromJsonObject((Map<String, Object>) x)).collect(Collectors.toList());
	}
}
