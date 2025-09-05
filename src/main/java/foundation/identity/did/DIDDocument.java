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

		private List<URI> controllers;
		private List<URI> alsoKnownAses;
		private List<VerificationMethod> verificationMethods;
		private List<VerificationMethod> authenticationVerificationMethods;
		private List<VerificationMethod> assertionMethodVerificationMethods;
		private List<VerificationMethod> keyAgreementVerificationMethods;
		private List<VerificationMethod> capabilityInvocationVerificationMethods;
		private List<VerificationMethod> capabilityDelegationVerificationMethods;
		private List<URI> authenticationVerificationMethodReferences;
		private List<URI> assertionMethodVerificationMethodReferences;
		private List<URI> keyAgreementVerificationMethodReferences;
		private List<URI> capabilityInvocationVerificationMethodReferences;
		private List<URI> capabilityDelegationVerificationMethodReferences;
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

			if (this.controllers != null) {
				JsonLDUtils.jsonLdAddAsJsonArray(this.jsonLdObject, DIDKeywords.JSONLD_TERM_CONTROLLER, this.controllers.stream().map(JsonLDUtils::uriToString).toList());
			}
			if (this.alsoKnownAses != null) {
				JsonLDUtils.jsonLdAddAsJsonArray(this.jsonLdObject, DIDKeywords.JSONLD_TERM_ALSOKNOWNAS, this.alsoKnownAses.stream().map(JsonLDUtils::uriToString).toList());
			}
			if (this.verificationMethods != null) {
				for (VerificationMethod verificationMethod : this.verificationMethods) verificationMethod.addToJsonLDObjectAsJsonArray(this.jsonLdObject);
			}
			if (this.authenticationVerificationMethods != null) {
				for (VerificationMethod authenticationVerificationMethod : this.authenticationVerificationMethods) authenticationVerificationMethod.addToJsonLDObjectAsJsonArray(this.jsonLdObject, VerificationRelationships.AUTHENTICATION);
			}
			if (this.assertionMethodVerificationMethods != null) {
				for (VerificationMethod assertionMethodVerificationMethod : this.assertionMethodVerificationMethods) assertionMethodVerificationMethod.addToJsonLDObjectAsJsonArray(this.jsonLdObject, VerificationRelationships.ASSERTIONMETHOD);
			}
			if (this.keyAgreementVerificationMethods != null) {
				for (VerificationMethod keyAgreementVerificationMethod : this.keyAgreementVerificationMethods) keyAgreementVerificationMethod.addToJsonLDObjectAsJsonArray(this.jsonLdObject, VerificationRelationships.KEYAGREEMENT);
			}
			if (this.capabilityInvocationVerificationMethods != null) {
				for (VerificationMethod capabilityInvocationVerificationMethod : this.capabilityInvocationVerificationMethods) capabilityInvocationVerificationMethod.addToJsonLDObjectAsJsonArray(this.jsonLdObject, VerificationRelationships.CAPABILITYINVOCATION);
			}
			if (this.capabilityDelegationVerificationMethods != null) {
				for (VerificationMethod capabilityDelegationVerificationMethod : this.capabilityDelegationVerificationMethods) capabilityDelegationVerificationMethod.addToJsonLDObjectAsJsonArray(this.jsonLdObject, VerificationRelationships.CAPABILITYDELEGATION);
			}
			if (this.authenticationVerificationMethodReferences != null) {
				for (URI authenticationVerificationMethodReference : this.authenticationVerificationMethodReferences) JsonLDUtils.jsonLdAddAsJsonArray(this.jsonLdObject, VerificationRelationships.AUTHENTICATION, JsonLDUtils.uriToString(authenticationVerificationMethodReference));
			}
			if (this.assertionMethodVerificationMethodReferences != null) {
				for (URI assertionMethodVerificationMethodReference : this.assertionMethodVerificationMethodReferences) JsonLDUtils.jsonLdAddAsJsonArray(this.jsonLdObject, VerificationRelationships.ASSERTIONMETHOD, JsonLDUtils.uriToString(assertionMethodVerificationMethodReference));
			}
			if (this.keyAgreementVerificationMethodReferences != null) {
				for (URI keyAgreementVerificationMethodReference : this.keyAgreementVerificationMethodReferences) JsonLDUtils.jsonLdAddAsJsonArray(this.jsonLdObject, VerificationRelationships.KEYAGREEMENT, JsonLDUtils.uriToString(keyAgreementVerificationMethodReference));
			}
			if (this.capabilityInvocationVerificationMethodReferences != null) {
				for (URI capabilityInvocationVerificationMethodReference : this.capabilityInvocationVerificationMethodReferences) JsonLDUtils.jsonLdAddAsJsonArray(this.jsonLdObject, VerificationRelationships.CAPABILITYINVOCATION, JsonLDUtils.uriToString(capabilityInvocationVerificationMethodReference));
			}
			if (this.capabilityDelegationVerificationMethodReferences != null) {
				for (URI capabilityDelegationVerificationMethodReference : this.capabilityDelegationVerificationMethodReferences) JsonLDUtils.jsonLdAddAsJsonArray(this.jsonLdObject, VerificationRelationships.CAPABILITYDELEGATION, JsonLDUtils.uriToString(capabilityDelegationVerificationMethodReference));
			}
			if (this.services != null) {
				for (Service service : this.services) service.addToJsonLDObjectAsJsonArray(this.jsonLdObject);
			}

			return (DIDDocument) this.jsonLdObject;
		}

		public B controllers(List<URI> controllers) {
			this.controllers = controllers;
			return (B) this;
		}

		public B controller(URI controller) {
			return this.controllers(controller == null ? null : Collections.singletonList(controller));
		}

		public B alsoKnownAses(List<URI> alsoKnownAses) {
			this.alsoKnownAses = alsoKnownAses;
			return (B) this;
		}

		public B alsoKnownAs(URI alsoKnownAs) {
			return this.alsoKnownAses(alsoKnownAs == null ? null : Collections.singletonList(alsoKnownAs));
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
			return this.capabilityDelegationVerificationMethods(capabilityDelegationVerificationMethod == null ? null : Collections.singletonList(capabilityDelegationVerificationMethod));
		}

		public B authenticationVerificationMethodReferences(List<URI> authenticationVerificationMethodReferences) {
			this.authenticationVerificationMethodReferences = authenticationVerificationMethodReferences;
			return (B) this;
		}

		public B authenticationVerificationMethodReference(URI authenticationVerificationMethodReference) {
			return this.authenticationVerificationMethodReferences(authenticationVerificationMethodReference == null ? null : Collections.singletonList(authenticationVerificationMethodReference));
		}

		public B assertionMethodVerificationMethodReferences(List<URI> assertionMethodVerificationMethodReferences) {
			this.assertionMethodVerificationMethodReferences = assertionMethodVerificationMethodReferences;
			return (B) this;
		}

		public B assertionMethodVerificationMethodReference(URI assertionMethodVerificationMethodReference) {
			return this.assertionMethodVerificationMethodReferences(assertionMethodVerificationMethodReference == null ? null : Collections.singletonList(assertionMethodVerificationMethodReference));
		}

		public B keyAgreementVerificationMethodReferences(List<URI> keyAgreementVerificationMethodReferences) {
			this.keyAgreementVerificationMethodReferences = keyAgreementVerificationMethodReferences;
			return (B) this;
		}

		public B keyAgreementVerificationMethodReference(URI keyAgreementVerificationMethodReference) {
			return this.keyAgreementVerificationMethodReferences(keyAgreementVerificationMethodReference == null ? null : Collections.singletonList(keyAgreementVerificationMethodReference));
		}

		public B capabilityInvocationVerificationMethodReferences(List<URI> capabilityInvocationVerificationMethodReferences) {
			this.capabilityInvocationVerificationMethodReferences = capabilityInvocationVerificationMethodReferences;
			return (B) this;
		}

		public B capabilityInvocationVerificationMethodReference(URI capabilityInvocationVerificationMethodReference) {
			return this.capabilityInvocationVerificationMethodReferences(capabilityInvocationVerificationMethodReference == null ? null : Collections.singletonList(capabilityInvocationVerificationMethodReference));
		}

		public B capabilityDelegationVerificationMethodReferences(List<URI> capabilityDelegationVerificationMethodReferences) {
			this.capabilityDelegationVerificationMethodReferences = capabilityDelegationVerificationMethodReferences;
			return (B) this;
		}

		public B capabilityDelegationVerificationMethodReference(URI capabilityDelegationVerificationMethodReference) {
			return this.capabilityDelegationVerificationMethodReferences(capabilityDelegationVerificationMethodReference == null ? null : Collections.singletonList(capabilityDelegationVerificationMethodReference));
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
		return new Builder<>(new DIDDocument());
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
	 * Getters
	 */

	public List<URI> getControllers() {
		List<Object> jsonArray = JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_CONTROLLER);
		return jsonArray == null ? null : jsonArray.stream().map(String.class::cast).map(JsonLDUtils::stringToUri).toList();
	}

	public List<URI> getAlsoKnownAses() {
		List<Object> jsonArray = JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_ALSOKNOWNAS);
		return jsonArray == null ? null : jsonArray.stream().map(String.class::cast).map(JsonLDUtils::stringToUri).toList();
	}

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
		return jsonArray == null ? null : jsonArray.stream().map(Map.class::cast).map(VerificationMethod::fromJsonObject).toList();
	}

	public List<Object> getVerificationMethods(String verificationRelationship) {
		return this.getVerificationMethodsJsonArray(verificationRelationship);
	}

	public List<VerificationMethod> getVerificationMethodsInline(String verificationRelationship) {
		List<Object> jsonArray = this.getVerificationMethods(verificationRelationship);
		return jsonArray == null ? null : jsonArray.stream().filter(Map.class::isInstance).map(Map.class::cast).map(VerificationMethod::fromJsonObject).toList();
	}

	public List<VerificationMethod> getVerificationMethodsDereferenced(String verificationRelationship) {
		List<Object> jsonArray = this.getVerificationMethods(verificationRelationship);
		return jsonArray == null ? null : jsonArray.stream().map(new JsonLDDereferencer.Function(this, this.getId(), true)).filter(Objects::nonNull).map(x -> VerificationMethod.fromJsonObject(x.getJsonObject())).toList();
	}

	public List<Object> getAuthenticationVerificationMethods() {
		return this.getVerificationMethods(DIDKeywords.JSONLD_TERM_AUTHENTICATION);
	}

	public List<VerificationMethod> getAuthenticationVerificationMethodsInline() {
		return this.getVerificationMethodsInline(DIDKeywords.JSONLD_TERM_AUTHENTICATION);
	}

	public List<VerificationMethod> getAuthenticationVerificationMethodsDereferenced() {
		return this.getVerificationMethodsDereferenced(DIDKeywords.JSONLD_TERM_AUTHENTICATION);
	}

	public List<Object> getAssertionMethodVerificationMethods() {
		return this.getVerificationMethods(DIDKeywords.JSONLD_TERM_ASSERTIONMETHOD);
	}

	public List<VerificationMethod> getAssertionMethodVerificationMethodsInline() {
		return this.getVerificationMethodsInline(DIDKeywords.JSONLD_TERM_ASSERTIONMETHOD);
	}

	public List<VerificationMethod> getAssertionMethodVerificationMethodsDereferenced() {
		return this.getVerificationMethodsDereferenced(DIDKeywords.JSONLD_TERM_ASSERTIONMETHOD);
	}

	public List<Object> getKeyAgreementVerificationMethods() {
		return this.getVerificationMethods(DIDKeywords.JSONLD_TERM_KEYAGREEMENT);
	}

	public List<VerificationMethod> getKeyAgreementVerificationMethodsInline() {
		return this.getVerificationMethodsInline(DIDKeywords.JSONLD_TERM_KEYAGREEMENT);
	}

	public List<VerificationMethod> getKeyAgreementVerificationMethodsDereferenced() {
		return this.getVerificationMethodsDereferenced(DIDKeywords.JSONLD_TERM_KEYAGREEMENT);
	}

	public List<Object> getCapabilityInvocationVerificationMethods() {
		return this.getVerificationMethods(DIDKeywords.JSONLD_TERM_CAPABILITYINVOCATION);
	}

	public List<VerificationMethod> getCapabilityInvocationVerificationMethodsInline() {
		return this.getVerificationMethodsInline(DIDKeywords.JSONLD_TERM_CAPABILITYINVOCATION);
	}

	public List<VerificationMethod> getCapabilityInvocationVerificationMethodsDereferenced() {
		return this.getVerificationMethodsDereferenced(DIDKeywords.JSONLD_TERM_CAPABILITYINVOCATION);
	}

	public List<Object> getCapabilityDelegationVerificationMethods() {
		return this.getVerificationMethods(DIDKeywords.JSONLD_TERM_CAPABILITYDELEGATION);
	}

	public List<VerificationMethod> getCapabilityDelegationVerificationMethodsInline() {
		return this.getVerificationMethodsInline(DIDKeywords.JSONLD_TERM_CAPABILITYDELEGATION);
	}

	public List<VerificationMethod> getCapabilityDelegationVerificationMethodsDereferenced() {
		return this.getVerificationMethodsDereferenced(DIDKeywords.JSONLD_TERM_CAPABILITYDELEGATION);
	}

	public List<Service> getServices() {
		List<Object> jsonArray = JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_SERVICE);
		return jsonArray == null ? null : jsonArray.stream().map(Map.class::cast).map(Service::fromJsonObject).toList();
	}
}
