package foundation.identity.did.representations.production;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.identity.did.DIDDocument;
import foundation.identity.did.representations.Representations;

import java.io.IOException;

public class RepresentationProducerDIDJSONLD extends AbstractRepresentationProducer implements RepresentationProducer {

    public static final String MEDIA_TYPE = "application/did+ld+json";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationProducerDIDJSONLD instance = new RepresentationProducerDIDJSONLD();

    public static RepresentationProducerDIDJSONLD getInstance() {
        return instance;
    }

    private RepresentationProducerDIDJSONLD() {
        super(MEDIA_TYPE);
    }

    @Override
    public byte[] produce(DIDDocument didDocument) throws IOException {
        byte[] representation = objectMapper.writeValueAsBytes(didDocument.getJsonObject());
        return representation;
    }
}
