package foundation.identity.did;

import com.apicatalog.jsonld.loader.DocumentLoader;
import com.fasterxml.jackson.annotation.JsonCreator;
import foundation.identity.did.jsonld.DIDContexts;
import foundation.identity.did.jsonld.DIDKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.io.Reader;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CapabilityInvocation extends VerificationRelationship {

	public static final URI[] DEFAULT_JSONLD_CONTEXTS = { DIDContexts.JSONLD_CONTEXT_W3_NS_DID_V1 };
	public static final String[] DEFAULT_JSONLD_TYPES = { };
	public static final String DEFAULT_JSONLD_PREDICATE = DIDKeywords.JSONLD_TERM_CAPABILITYINVOCATION;
	public static final DocumentLoader DEFAULT_DOCUMENT_LOADER = DIDContexts.DOCUMENT_LOADER;

	@JsonCreator
	public CapabilityInvocation() {
		super();
	}

	protected CapabilityInvocation(Map<String, Object> jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static class Builder<B extends Builder<B>> extends JsonLDObject.Builder<B> {

		private URI verificationMethod;

		public Builder(CapabilityInvocation jsonLDObject) {
			super(jsonLDObject);
		}

		@Override
		public CapabilityInvocation build() {

			super.build();

			// add JSON-LD properties
			if (this.verificationMethod != null) JsonLDUtils.jsonLdAdd(this.jsonLDObject, DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD, JsonLDUtils.uriToString(this.verificationMethod));

			return (CapabilityInvocation) this.jsonLDObject;
		}

		public B verificationMethod(URI verificationMethod) {
			this.verificationMethod = verificationMethod;
			return (B) this;
		}
	}

	public static Builder<? extends Builder<?>> builder() {
		return new Builder(new CapabilityInvocation());
	}

	public static CapabilityInvocation fromJsonObject(Map<String, Object> jsonObject) {
		return new CapabilityInvocation(jsonObject);
	}

	public static CapabilityInvocation fromJson(Reader reader) {
		return new CapabilityInvocation(readJson(reader));
	}

	public static CapabilityInvocation fromJson(String json) {
		return new CapabilityInvocation(readJson(json));
	}

	/*
	 * Adding, getting, and removing the JSON-LD object
	 */

	public static CapabilityInvocation getFromJsonLDObject(JsonLDObject jsonLdObject) {
		return JsonLDObject.getFromJsonLDObject(CapabilityInvocation.class, jsonLdObject);
	}

	public static void removeFromJsonLdObject(JsonLDObject jsonLdObject) {
		JsonLDObject.removeFromJsonLdObject(CapabilityInvocation.class, jsonLdObject);
	}
}