package foundation.identity.did;

import foundation.identity.did.jsonld.DIDKeywords;
import foundation.identity.jsonld.JsonLDObject;
import foundation.identity.jsonld.JsonLDUtils;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class VerificationRelationship extends JsonLDObject {

    public VerificationRelationship() {
        super();
    }

    protected VerificationRelationship(Map<String, Object> jsonObject) {
        super(jsonObject);
    }

    /*
     * Getters
     */

    public List<VerificationMethod> getVerificationMethods() {
        List<Object> jsonArray = JsonLDUtils.jsonLdGetJsonArray(this.getJsonObject(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD);
        return jsonArray == null ? null : jsonArray.stream().map(x -> VerificationMethod.fromJsonObject((Map<String, Object>) x)).collect(Collectors.toList());
    }

    public URI getVerificationMethodURI() {
        return JsonLDUtils.stringToUri(JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDKeywords.JSONLD_TERM_VERIFICATIONMETHOD));
    }
}
