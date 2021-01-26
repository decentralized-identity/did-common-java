package foundation.identity.did;

import foundation.identity.jsonld.JsonLDObject;

import java.util.Map;

public abstract class VerificationRelationship extends JsonLDObject {

    public VerificationRelationship() {
        super();
    }

    protected VerificationRelationship(Map<String, Object> jsonObject) {
        super(jsonObject);
    }
}
