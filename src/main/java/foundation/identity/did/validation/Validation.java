package foundation.identity.did.validation;

import foundation.identity.did.DIDDocument;

import java.net.URI;
import java.net.URISyntaxException;

public class Validation {

    private static void validateTrue(boolean valid) throws IllegalStateException {

        if (! valid) throw new IllegalStateException();
    }

    private static void validateUrl(URI uri) {

        try {

            if (! uri.isAbsolute()) throw new URISyntaxException("Not absolute.", uri.toString());
        } catch (URISyntaxException ex) {

            throw new RuntimeException(ex.getMessage());
        }
    }

    private static void validateRun(Runnable runnable, String message) throws IllegalStateException {

        try {

            runnable.run();
        } catch (Exception ex) {

            throw new IllegalStateException(message);
        }
    }

    public static void validate(DIDDocument didDocument) throws IllegalStateException {

        validateRun(() -> { validateTrue(didDocument.getJsonObject() != null); }, "Bad or missing JSON object.");
        validateRun(() -> { validateTrue(didDocument.getContexts().size() > 0); }, "Bad or missing '@context'.");
        validateRun(() -> { validateUrl(didDocument.getContexts().get(0)); }, "@context must be a valid URI: " + didDocument.getContexts().get(0));
        validateRun(() -> { validateTrue(DIDDocument.DEFAULT_JSONLD_CONTEXTS[0].equals(didDocument.getContexts().get(0))); }, "First value of @context must be " + DIDDocument.DEFAULT_JSONLD_CONTEXTS[0] + ": " + didDocument.getContexts().get(0));
        validateRun(() -> { if (didDocument.getId() != null) validateUrl(didDocument.getId()); }, "'id' must be a valid URI.");
    }
}
