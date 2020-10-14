package foundation.identity.did.jsonld;

import com.apicatalog.jsonld.api.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jsonld.http.media.MediaType;
import com.apicatalog.jsonld.loader.DocumentLoader;
import foundation.identity.jsonld.ConfigurableDocumentLoader;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class DIDContexts {

    public static final URI JSONLD_CONTEXT_W3_NS_DID_V1 = URI.create("https://www.w3.org/ns/did/v1");
    public static final URI JSONLD_CONTEXT_W3_2019_DID_V1 = URI.create("https://www.w3.org/2019/did/v1");
    public static final URI JSONLD_CONTEXT_W3ID_DID_V1 = URI.create("https://w3id.org/did/v1");
    public static final URI JSONLD_CONTEXT_W3ID_DID_V011 = URI.create("https://w3id.org/did/v0.11");
    public static final URI JSONLD_CONTEXT_W3ID_VERESONE_V1 = URI.create("https://w3id.org/veres-one/v1");

    public static final Map<URI, JsonDocument> CONTEXTS;
    public static final DocumentLoader DOCUMENT_LOADER;

    static {

        try {

            CONTEXTS = new HashMap<>();

            CONTEXTS.put(JSONLD_CONTEXT_W3_NS_DID_V1,
                    JsonDocument.of(MediaType.JSON_LD, DIDContexts.class.getResourceAsStream("diddocument-context-w3-ns-did-v1.jsonld")));
            CONTEXTS.put(JSONLD_CONTEXT_W3_2019_DID_V1,
                    JsonDocument.of(MediaType.JSON_LD, DIDContexts.class.getResourceAsStream("diddocument-context-w3-2019-did-v1.jsonld")));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_DID_V1,
                    JsonDocument.of(MediaType.JSON_LD, DIDContexts.class.getResourceAsStream("diddocument-context-w3id-did-v1.jsonld")));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_DID_V011,
                    JsonDocument.of(MediaType.JSON_LD, DIDContexts.class.getResourceAsStream("diddocument-context-w3id-did-v011.jsonld")));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_VERESONE_V1,
                    JsonDocument.of(MediaType.JSON_LD, DIDContexts.class.getResourceAsStream("diddocument-context-w3id-veresone-v1.jsonld")));

            for (Map.Entry<URI, JsonDocument> context : CONTEXTS.entrySet()) {
                context.getValue().setDocumentUrl(context.getKey());
            }
        } catch (JsonLdError ex) {

            throw new ExceptionInInitializerError(ex);
        }

        DOCUMENT_LOADER = new ConfigurableDocumentLoader(CONTEXTS);
    }
}
