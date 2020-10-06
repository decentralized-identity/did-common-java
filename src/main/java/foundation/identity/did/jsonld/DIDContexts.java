package foundation.identity.did.jsonld;

import com.apicatalog.jsonld.api.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jsonld.http.media.MediaType;
import com.apicatalog.jsonld.loader.DocumentLoader;
import foundation.identity.jsonld.ConfigurableDocumentLoader;
import foundation.identity.jsonld.JsonLDObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class DIDContexts {

    public static DocumentLoader DOCUMENT_LOADER = new ConfigurableDocumentLoader(DIDContexts.CONTEXTS);

    public static Map<URI, JsonDocument> CONTEXTS = new HashMap<URI, JsonDocument>();

    static {

        try {

            CONTEXTS.put(URI.create("https://www.w3.org/ns/did/v1"),
                    JsonDocument.of(MediaType.JSON_LD, JsonLDObject.class.getResourceAsStream("diddocument-context-w3org-ns-did-v1.jsonld")));
            CONTEXTS.put(URI.create("https://www.w3.org/2019/did/v1"),
                    JsonDocument.of(MediaType.JSON_LD, JsonLDObject.class.getResourceAsStream("diddocument-context-w3org-did-v1.jsonld")));
            CONTEXTS.put(URI.create("https://w3id.org/did/v1"),
                    JsonDocument.of(MediaType.JSON_LD, JsonLDObject.class.getResourceAsStream("diddocument-context-w3idorg-did-v1.jsonld")));
            CONTEXTS.put(URI.create("https://w3id.org/did/v0.11"),
                    JsonDocument.of(MediaType.JSON_LD, JsonLDObject.class.getResourceAsStream("diddocument-context-w3idorg-did-v0.11.jsonld")));
            CONTEXTS.put(URI.create("https://w3id.org/veres-one/v1"),
                    JsonDocument.of(MediaType.JSON_LD, JsonLDObject.class.getResourceAsStream("diddocument-context-w3idorg-veresone-v1.jsonld")));

            for (Map.Entry<URI, JsonDocument> context : CONTEXTS.entrySet()) {
                context.getValue().setDocumentUrl(context.getKey());
            }
        } catch (JsonLdError ex) {

            throw new ExceptionInInitializerError(ex);
        }
    }
}
