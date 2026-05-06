package foundation.identity.did.validation;

import foundation.identity.did.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Validation {

    private static void validateTrue(boolean valid) {
        if (! valid) throw new RuntimeException();
    }

    private static void validateNotNull(Object object) {
        if (object == null) throw new RuntimeException();
    }

    private static void validateNotEmpty(Collection<?> collection) {
        if (collection.isEmpty()) throw new RuntimeException();
    }

    private static void validateEquals(Object o1, Object o2) {
        if (! o1.equals(o2)) throw new RuntimeException();
    }

    private static void validateRun(Runnable runnable, String message) throws IllegalStateException {
        try {
            runnable.run();
        } catch (Exception ex) {
            if (ex.getMessage() != null && ! ex.getMessage().isEmpty()) message = message + " (" + ex.getMessage().trim() + ")";
            throw new IllegalStateException(message, ex);
        }
    }

    public static void validate(DIDDocument didDocument) throws IllegalStateException {
        if (didDocument instanceof DIDDocumentV1_0 didDocumentV1_0) {
            validate(didDocumentV1_0);
            return;
        }
        if (didDocument instanceof DIDDocumentV1_1 didDocumentV1_1) {
            validate(didDocumentV1_1);
            return;
        }
        throw new IllegalStateException("Unknown DID document version: " + didDocument);
    }

    public static void validate(DIDDocumentV1_0 didDocument) throws IllegalStateException {
        validateRun(() -> validateNotNull(didDocument.getJsonObject()), "Missing JSON object.");
        validateRun(() -> validateNotNull(didDocument.getContexts()), "Missing '@context': " + didDocument.getContexts());
        validateRun(() -> validateNotEmpty(didDocument.getContexts()), "Empty '@context': " + didDocument.getContexts());
        validateRun(() -> validateEquals(DIDDocumentV1_0.DEFAULT_JSONLD_CONTEXTS[0], didDocument.getContexts().get(0)), "First value of '@context' must be " + DIDDocumentV1_0.DEFAULT_JSONLD_CONTEXTS[0] + ": " + didDocument.getContexts().get(0));
        validateRun(() -> validateNotNull(didDocument.getId()), "Missing 'id': " + didDocument.getId());
        validateRun(() -> validateTrue(didDocument.getId().isAbsolute()), "'Invalid 'id': " + didDocument.getId());
        if (didDocument.getVerificationMethods() != null) didDocument.getVerificationMethods().forEach(Validation::validate);
        if (didDocument.getServices() != null) didDocument.getServices().forEach(Validation::validate);
    }

    public static void validate(DIDDocumentV1_1 didDocument) throws IllegalStateException {
        validateRun(() -> validateNotNull(didDocument.getJsonObject()), "Missing JSON object.");
        validateRun(() -> validateNotNull(didDocument.getContexts()), "Missing '@context': " + didDocument.getContexts());
        validateRun(() -> validateNotEmpty(didDocument.getContexts()), "Empty '@context': " + didDocument.getContexts());
        validateRun(() -> validateEquals(DIDDocumentV1_1.DEFAULT_JSONLD_CONTEXTS[0], didDocument.getContexts().get(0)), "First value of '@context' must be " + DIDDocumentV1_1.DEFAULT_JSONLD_CONTEXTS[0] + ": " + didDocument.getContexts().get(0));
        validateRun(() -> validateTrue(didDocument.getId().isAbsolute()), "'Invalid 'id': " + didDocument.getId());
        if (didDocument.getVerificationMethods() != null) didDocument.getVerificationMethods().forEach(Validation::validate);
        if (didDocument.getServices() != null) didDocument.getServices().forEach(Validation::validate);
    }

    public static void validate(VerificationMethod verificationMethod) {
        validateRun(() -> validateNotNull(verificationMethod.getJsonObject()), "Missing JSON object.");
        validateRun(() -> validateNotNull(verificationMethod.getId()), "Missing verficationMethod 'id': " + verificationMethod.getId());
        validateRun(() -> validateNotNull(verificationMethod.getType()), "Missing verficationMethod 'type': " + verificationMethod.getType());
        validateRun(() -> validateNotNull(verificationMethod.getController()), "Missing verficationMethod 'controller': " + verificationMethod.getController());
        validateRun(() -> validateTrue(verificationMethod.getController().isAbsolute()), "'Invalid verificationMethod 'controller': " + verificationMethod.getController());
    }

    public static void validate(Service service) {
        validateRun(() -> validateNotNull(service.getJsonObject()), "Missing JSON object.");
        validateRun(() -> validateNotNull(service.getId()), "Missing service 'id': " + service.getId());
        validateRun(() -> validateNotNull(service.getType()), "Missing service 'type': " + service.getType());
        validateRun(() -> validateNotNull(service.getServiceEndpoint()), "Missing service 'serviceEndpoint': " + service.getServiceEndpoint());
        validateRun(() -> validateTrue(service.getServiceEndpoint() instanceof String || service.getServiceEndpoint() instanceof Map || service.getServiceEndpoint() instanceof List), "Bad or missing service 'serviceEndpoint': " + service.getServiceEndpoint() + " (" + (service.getServiceEndpoint() == null ? null : service.getServiceEndpoint().getClass().getSimpleName()) + ")");
    }
}
