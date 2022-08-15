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
import java.util.*;
import java.util.stream.Collectors;

public class DIDDocument extends JsonLDObject {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { DIDContexts.JSONLD_CONTEXT_W3_NS_DID_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { };
	public static final String DEFAULT_JSONLD_PREDICATE = null;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = DIDContexts.DOCUMENT_LOADER;

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
		private List<VerificationMethod> authenticationVerificationMethods;
		private List<VerificationMethod> assertionMethodVerificationMethods;
		private List<VerificationMethod> keyAgreementVerificationMethods;
		private List<VerificationMethod> capabilityInvocationVerificationMethods;
		private List<VerificationMethod> capabilityDelegationVerificationMethods;
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
			if (this.verificationMethods != null) for (VerificationMethod verificationMethod : this.verificationMethods) verificationMethod.addToJsonLDObjectAsJsonArray(this.jsonLdObject);
			if (this.authenticationVerificationMethods != null) for (VerificationMethod authenticationVerificationMethod : this.authenticationVerificationMethods) authenticationVerificationMethod.addToJsonLDObjectAsJsonArray(this.jsonLdObject, VerificationRelationships.AUTHENTICATION);
			if (this.assertionMethodVerificationMethods != null) for (VerificationMethod assertionMethodVerificationMethod : this.assertionMethodVerificationMethods) assertionMethodVerificationMethod.addToJsonLDObjectAsJsonArray(this.jsonLdObject, VerificationRelationships.ASSERTIONMETHOD);
			if (this.keyAgreementVerificationMethods != null) for (VerificationMethod keyAgreementVerificationMethod : this.keyAgreementVerificationMethods) keyAgreementVerificationMethod.addToJsonLDObjectAsJsonArray(this.jsonLdObject, VerificationRelationships.KEYAGREEMENT);
			if (this.capabilityInvocationVerificationMethods != null) for (VerificationMethod capabilityInvocationVerificationMethod : this.capabilityInvocationVerificationMethods) capabilityInvocationVerificationMethod.addToJsonLDObjectAsJsonArray(this.jsonLdObject, VerificationRelationships.CAPABILITYINVOCATION);
			if (this.capabilityDelegationVerificationMethods != null) for (VerificationMethod capabilityDelegationVerificationMethod : this.capabilityDelegationVerificationMethods) capabilityDelegationVerificationMethod.addToJsonLDObjectAsJsonArray(this.jsonLdObject, VerificationRelationships.CAPABILITYDELEGATION);
			if (this.services != null) for (Service service : this.services) service.addToJsonLDObjectAsJsonArray(this.jsonLdObject);

			return (DIDDocument) this.jsonLdObject;
		}

		public B verificationMethods(List<VerificationMethod> verificationMethods) {
			this.verificationMethods = verificationMethods;
			return (B) this;
		}

		public B verificationMethod(VerificationMethod verificationMethod) {
			return this.verificationMethods(verificationMethod == null ? null : Collections.singletonList(verificationMethod));
		}

		public B authenticationVerificationMethods(List<VerificationMethod> authenticationVerificationMethods) {
			this.authenticationVerificationMethods = authenticationVerificationMethods;
			return (B) this;
		}

		public B authenticationVerificationMethod(VerificationMethod authenticationVerificationMethod) {
			return this.authenticationVerificationMethods(authenticationVerificationMethod == null ? null : Collections.singletonList(authenticationVerificationMethod));
		}

		public B assertionMethodVerificationMethods(List<VerificationMethod> assertionMethodVerificationMethods) {
			this.assertionMethodVerificationMethods = assertionMethodVerificationMethods;
			return (B) this;
		}

		public B assertionMethodVerificationMethod(VerificationMethod assertionMethodVerificationMethod) {
			return this.assertionMethodVerificationMethods(assertionMethodVerificationMethod == null ? null : Collections.singletonList(assertionMethodVerificationMethod));
		}

		public B keyAgreementVerificationMethods(List<VerificationMethod> keyAgreementVerificationMethods) {
			this.keyAgreementVerificationMethods = keyAgreementVerificationMethods;
			return (B) this;
		}

		public B keyAgreementVerificationMethod(VerificationMethod keyAgreementVerificationMethod) {
			return this.keyAgreementVerificationMethods(keyAgreementVerificationMethod == null ? null : Collections.singletonList(keyAgreementVerificationMethod));
		}

		public B capabilityInvocationVerificationMethods(List<VerificationMethod> capabilityInvocationVerificationMethods) {
			this.capabilityInvocationVerificationMethods = capabilityInvocationVerificationMethods;
			return (B) this;
		}

		public B capabilityInvocationVerificationMethod(VerificationMethod capabilityInvocationVerificationMethod) {
			return this.capabilityInvocationVerificationMethods(capabilityInvocationVerificationMethod == null ? null : Collections.singletonList(capabilityInvocationVerificationMethod));
		}

		public B capabilityDelegationVerificationMethods(List<VerificationMethod> capabilityDelegationVerificationMethods) {
			this.capabilityDelegationVerificationMethods = capabilityDelegationVerificationMethods;
			return (B) this;
		}

		public B capabilityDelegationVerificationMethod(VerificationMethod capabilityDelegationVerificationMethod) {
			return this.capabilityInvocationVerificationMethods(capabilityDelegationVerificationMethod == null ? null : Collections.singletonList(capabilityDelegationVerificationMethod));
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

	public static DIDDocument fromJsonLDObject(JsonLDObject jsonLDObject) { return fromJsonObject(jsonLDObject.getJsonObject()); }

	public static DIDDocument fromJson(Reader reader) {
		return new DIDDocument(readJson(reader));
	}

	public static DIDDocument fromJson(String json) {
		return new DIDDocument(readJson(json));
	}

	public static DIDDocument fromMap(Map<String, Object> map) {
		return new DIDDocument(map);
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
	 * Helper methods
	 */

	public Map<String, Object> toMap() {
		return this.getJsonObject();
	}

	/*
	 * Getters
	 */

	public Map<URI, VerificationMethod> getAllVerificationMethodsAsMap() {
		List<VerificationMethod> allVerificationMethods = new ArrayList<>();
		List<VerificationMethod> verificationMethods = this.getVerificationMethods();
		List<VerificationMethod> authenticationVerificationMethods = this.getAuthenticationVerificationMethodsInline();
		List<VerificationMethod> assertionMethodVerificationMethods = this.getAssertionMethodVerificationMethodsInline();
		List<VerificationMethod> keyAgreementVerificationMethods = this.getKeyAgreementVerificationMethodsInline();
		List<VerificationMethod> capabilityInvocationVerificationMethods = this.getCapabilityInvocationVerificationMethodsInline();
		List<VerificationMethod> capabilityDelegationVerificationMethods = this.getCapabilityDelegationVerificationMethodsInline();
		if (verificationMethods != null) allVerificationMethods.addAll(verificationMethods);
		if (authenticationVerificationMethods != null) allVerificationMethods.addAll(authenticationVerificationMethods);
		if (assertionMethodVerificationMethods != null) allVerificationMethods.addAll(assertionMethodVerificationMethods);
		if (keyAgreementVerificationMethods != null) allVerificationMethods.addAll(keyAgreementVerificationMethods);
		if (capabilityInvocationVerificationMethods != null) allVerificationMethods.addAll(capabilityInvocationVerificationMethods);
		if (capabilityDelegationVerificationMethods != null) allVerificationMethods.addAll(capabilityDelegationVerificationMethods);
		Map<URI, VerificationMethod> allVerificationMethodsAsMap = new HashMap<>();
		for (VerificationMethod verificationMethod : allVerificationMethods) {
			allVerificationMethodsAsMap.put(verificationMethod.getId(), verificationMethod);
		}
		return allVerificationMethodsAsMap;
	}

	public List<VerificationMethod> getAllVerificationMethods() {
		return new ArrayList<>(this.getAllVerificationMethodsAsMap().values());
	}

	private List<Object> getVerificationMethodsJsonArray(String term) {
		return JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), term);
	}

	public List<VerificationMethod> getVerificationMethods() {
		List<Object> jsonArray = this.getVerificationMethodsJsonArray(DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD);
		return jsonArray == null ? null : jsonArray.stream().map(Map.class::cast).map(VerificationMethod::fromJsonObject).collect(Collectors.toList());
	}

	public List<Object> getAuthenticationVerificationMethods() {
		return this.getVerificationMethodsJsonArray(DIDKeywords.JSONLD_TERM_AUTHENTICATION);
	}

	public List<VerificationMethod> getAuthenticationVerificationMethodsInline() {
		List<Object> jsonArray = this.getAuthenticationVerificationMethods();
		return jsonArray == null ? null : jsonArray.stream().filter(Map.class::isInstance).map(Map.class::cast).map(VerificationMethod::fromJsonObject).collect(Collectors.toList());
	}

	public List<VerificationMethod> getAuthenticationVerificationMethodsDereferenced() {
		List<Object> jsonArray = this.getAuthenticationVerificationMethods();
		return jsonArray == null ? null : jsonArray.stream().map(new JsonLDDereferencer.Function(this, this.getId())).filter(Objects::nonNull).map(x -> VerificationMethod.fromJsonObject(x.getJsonObject())).collect(Collectors.toList());
	}

	public List<Object> getAssertionMethodVerificationMethods() {
		return this.getVerificationMethodsJsonArray(DIDKeywords.JSONLD_TERM_ASSERTIONMETHOD);
	}

	public List<VerificationMethod> getAssertionMethodVerificationMethodsInline() {
		List<Object> jsonArray = this.getAssertionMethodVerificationMethods();
		return jsonArray == null ? null : jsonArray.stream().filter(Map.class::isInstance).map(Map.class::cast).map(VerificationMethod::fromJsonObject).collect(Collectors.toList());
	}

	public List<VerificationMethod> getAssertionMethodVerificationMethodsDereferenced() {
		List<Object> jsonArray = this.getAssertionMethodVerificationMethods();
		return jsonArray == null ? null : jsonArray.stream().map(new JsonLDDereferencer.Function(this, this.getId())).filter(Objects::nonNull).map(x -> VerificationMethod.fromJsonObject(x.getJsonObject())).collect(Collectors.toList());
	}

	public List<Object> getKeyAgreementVerificationMethods() {
		return this.getVerificationMethodsJsonArray(DIDKeywords.JSONLD_TERM_KEYAGREEMENT);
	}

	public List<VerificationMethod> getKeyAgreementVerificationMethodsInline() {
		List<Object> jsonArray = this.getKeyAgreementVerificationMethods();
		return jsonArray == null ? null : jsonArray.stream().filter(Map.class::isInstance).map(Map.class::cast).map(VerificationMethod::fromJsonObject).collect(Collectors.toList());
	}

	public List<VerificationMethod> getKeyAgreementVerificationMethodsDereferenced() {
		List<Object> jsonArray = this.getKeyAgreementVerificationMethods();
		return jsonArray == null ? null : jsonArray.stream().map(new JsonLDDereferencer.Function(this, this.getId())).filter(Objects::nonNull).map(x -> VerificationMethod.fromJsonObject(x.getJsonObject())).collect(Collectors.toList());
	}

	public List<Object> getCapabilityInvocationVerificationMethods() {
		return this.getVerificationMethodsJsonArray(DIDKeywords.JSONLD_TERM_CAPABILITYINVOCATION);
	}

	public List<VerificationMethod> getCapabilityInvocationVerificationMethodsInline() {
		List<Object> jsonArray = this.getCapabilityInvocationVerificationMethods();
		return jsonArray == null ? null : jsonArray.stream().filter(Map.class::isInstance).map(Map.class::cast).map(VerificationMethod::fromJsonObject).collect(Collectors.toList());
	}

	public List<VerificationMethod> getCapabilityInvocationVerificationMethodsDereferenced() {
		List<Object> jsonArray = this.getVerificationMethodsJsonArray(DIDKeywords.JSONLD_TERM_CAPABILITYINVOCATION);
		return jsonArray == null ? null : jsonArray.stream().map(new JsonLDDereferencer.Function(this, this.getId())).filter(Objects::nonNull).map(x -> VerificationMethod.fromJsonObject(x.getJsonObject())).collect(Collectors.toList());
	}

	public List<Object> getCapabilityDelegationVerificationMethods() {
		return this.getVerificationMethodsJsonArray(DIDKeywords.JSONLD_TERM_CAPABILITYDELEGATION);
	}

	public List<VerificationMethod> getCapabilityDelegationVerificationMethodsInline() {
		List<Object> jsonArray = this.getCapabilityDelegationVerificationMethods();
		return jsonArray == null ? null : jsonArray.stream().filter(Map.class::isInstance).map(Map.class::cast).map(VerificationMethod::fromJsonObject).collect(Collectors.toList());
	}

	public List<VerificationMethod> getCapabilityDelegationVerificationMethodsDereferenced() {
		List<Object> jsonArray = this.getCapabilityDelegationVerificationMethods();
		return jsonArray == null ? null : jsonArray.stream().map(new JsonLDDereferencer.Function(this, this.getId())).filter(Objects::nonNull).map(x -> VerificationMethod.fromJsonObject(x.getJsonObject())).collect(Collectors.toList());
	}

	public List<Service> getServices() {
		List<Object> jsonArray = JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_SERVICE);
		return jsonArray == null ? null : jsonArray.stream().map(Map.class::cast).map(Service::fromJsonObject).collect(Collectors.toList());
	}
}
