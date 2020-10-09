package foundation.identity.did;

import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import foundation.identity.did.jsonld.DIDContexts;
import foundation.identity.did.jsonld.DIDKeywords;
import foundation.identity.did.validation.Validation;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import javax.json.Json;
import javax.json.JsonObject;

public class DIDDocument extends JsonLDObject {

	public static final URI DEFAULT_JSONLD_CONTEXT = DIDContexts.JSONLD_CONTEXT_W3_NS_DID_V1;

	public static final String MIME_TYPE_JSON_LD = "application/did+ld+json";
	public static final String MIME_TYPE_JSON = "application/did+json";
	public static final String MIME_TYPE_CBOR = "application/did+cbor";

	private DIDDocument() {
		super(DIDContexts.DOCUMENT_LOADER);
	}

	private DIDDocument(JsonObject jsonObject, boolean validate) {
		super(DIDContexts.DOCUMENT_LOADER, jsonObject);
		if (validate) Validation.validate(this);
	}

	public DIDDocument(JsonObject jsonObject) {
		this(jsonObject, true);
	}

	/*
	 * Factory methods
	 */

	public static class Builder extends JsonLDObject.Builder<Builder, DIDDocument> {

		private List<VerificationMethod> verificationMethods;
		private List<Authentication> authentications;
		private List<Service> services;

		public Builder() {
			super(new DIDDocument());
		}

		public DIDDocument build() {

			super.build();

			// add JSON-LD properties
			if (this.verificationMethods != null) for (VerificationMethod verificationMethod : this.verificationMethods) verificationMethod.addToJsonLDObject(this.jsonLDObject);
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

	public static Builder builder(boolean addContext) {
		Builder builder = new Builder();
		if (addContext) builder.context(DEFAULT_JSONLD_CONTEXT.toString());
		return builder;
	}

	public static Builder builder() {
		return builder(true);
	}

	/*
	 * Serialization
	 */

	public static DIDDocument fromJson(Reader reader, boolean validate) {
		JsonObject jsonObject = Json.createReader(reader).readObject();
		return new DIDDocument(jsonObject, validate);
	}

	public static DIDDocument fromJson(String json, boolean validate) {
		return fromJson(new StringReader(json), validate);
	}

	public static DIDDocument fromJson(Reader reader) {
		return fromJson(reader, true);
	}

	public static DIDDocument fromJson(String json) {
		return fromJson(json, true);
	}

	/*
	 * Getters
	 */

	public List<VerificationMethod> getVerificationMethods() {
		return JsonLDUtils.jsonLdGetJsonValueList(this.getJsonObject(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD).stream().map(x -> new VerificationMethod((JsonObject) x)).collect(Collectors.toList());
	}

	public List<Authentication> getAuthentications() {
		return JsonLDUtils.jsonLdGetJsonValueList(this.getJsonObject(), DIDKeywords.JSONLD_TERM_AUTHENTICATION).stream().map(x -> new Authentication((JsonObject) x)).collect(Collectors.toList());
	}

	public List<Service> getServices() {
		return JsonLDUtils.jsonLdGetJsonValueList(this.getJsonObject(), DIDKeywords.JSONLD_TERM_SERVICE).stream().map(x -> new Service((JsonObject) x)).collect(Collectors.toList());
	}
}
