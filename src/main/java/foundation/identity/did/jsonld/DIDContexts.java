package foundation.identity.did.jsonld;

import com.apicatalog.jsonld.JsonLdError;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jsonld.http.media.MediaType;
import com.apicatalog.jsonld.loader.DocumentLoader;
import foundation.identity.jsonld.ConfigurableDocumentLoader;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DIDContexts {

    public static final URI JSONLD_CONTEXT_W3_NS_DID_V1_1 = URI.create("https://www.w3.org/ns/did/v1.1");
    public static final URI JSONLD_CONTEXT_W3_NS_DID_V1 = URI.create("https://www.w3.org/ns/did/v1");
    public static final URI JSONLD_CONTEXT_W3_2019_DID_V1 = URI.create("https://www.w3.org/2019/did/v1");
    public static final URI JSONLD_CONTEXT_W3ID_DID_V1 = URI.create("https://w3id.org/did/v1");
    public static final URI JSONLD_CONTEXT_W3ID_DID_V011 = URI.create("https://w3id.org/did/v0.11");
    public static final URI JSONLD_CONTEXT_W3ID_VERESONE_V1 = URI.create("https://w3id.org/veres-one/v1");
    public static final URI JSONLD_CONTEXT_W3ID_SUITES_SECP256K1_2019_V1 = URI.create("https://w3id.org/security/suites/secp256k1-2019/v1");
    public static final URI JSONLD_CONTEXT_W3ID_SUITES_ED25519_2018_V1 = URI.create("https://w3id.org/security/suites/ed25519-2018/v1");
    public static final URI JSONLD_CONTEXT_W3ID_SUITES_ED25519_2020_V1 = URI.create("https://w3id.org/security/suites/ed25519-2020/v1");
    public static final URI JSONLD_CONTEXT_W3ID_SUITES_X25519_2019_V1 = URI.create("https://w3id.org/security/suites/x25519-2019/v1");
    public static final URI JSONLD_CONTEXT_W3ID_SUITES_JWS_2020_V1 = URI.create("https://w3id.org/security/suites/jws-2020/v1");
    public static final URI JSONLD_CONTEXT_W3ID_SUITES_SECP256K1RECOVERY2020_V2 = URI.create("https://w3id.org/security/suites/secp256k1recovery-2020/v2");
    public static final URI JSONLD_CONTEXT_W3CGITHUB_VCDATAINTEGRITY_JWK_V1 = URI.create("https://w3c.github.io/vc-data-integrity/contexts/jwk/v1.jsonld");
    public static final URI JSONLD_CONTEXT_DIF_DIDCONFIGURATION_V1 = URI.create("https://identity.foundation/.well-known/did-configuration/v1");

    public static final Map<URI, JsonDocument> CONTEXTS;
    public static final DocumentLoader DOCUMENT_LOADER;

    static {

        try {

            CONTEXTS = new HashMap<>();

            CONTEXTS.put(JSONLD_CONTEXT_W3_NS_DID_V1_1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("diddocument-context-w3-ns-did-v1.1.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3_NS_DID_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("diddocument-context-w3-ns-did-v1.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3_2019_DID_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("diddocument-context-w3-2019-did-v1.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_DID_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("diddocument-context-w3id-did-v1.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_DID_V011,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("diddocument-context-w3id-did-v011.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_VERESONE_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("diddocument-context-w3id-veresone-v1.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_SUITES_SECP256K1_2019_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("suites-secp256k1-2019.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_SUITES_ED25519_2018_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("suites-ed25519-2018.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_SUITES_ED25519_2020_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("suites-ed25519-2020.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_SUITES_X25519_2019_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("suites-x25519-2019.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_SUITES_JWS_2020_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("suites-jws-2020.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3ID_SUITES_SECP256K1RECOVERY2020_V2,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("suites-secp256k1recovery-2020.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_W3CGITHUB_VCDATAINTEGRITY_JWK_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("w3cgithub-vcdataintegrity-jwk-v1.jsonld"))));
            CONTEXTS.put(JSONLD_CONTEXT_DIF_DIDCONFIGURATION_V1,
                    JsonDocument.of(MediaType.JSON_LD, Objects.requireNonNull(DIDContexts.class.getResourceAsStream("dif-didconfiguration-v1.json"))));

            for (Map.Entry<URI, JsonDocument> context : CONTEXTS.entrySet()) {
                context.getValue().setDocumentUrl(context.getKey());
            }
        } catch (JsonLdError ex) {

            throw new ExceptionInInitializerError(ex);
        }

        DOCUMENT_LOADER = new ConfigurableDocumentLoader(CONTEXTS);
    }
}
