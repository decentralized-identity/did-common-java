package foundation.identity.did.representations.production;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.identity.did.DIDDocument;

import java.io.IOException;

public class RepresentationProducerDIDJSON extends AbstractRepresentationProducer implements RepresentationProducer {

    public static final String MEDIA_TYPE = "application/did+json";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationProducerDIDJSON instance = new RepresentationProducerDIDJSON();

    public static RepresentationProducerDIDJSON getInstance() {
        return instance;
    }

    private RepresentationProducerDIDJSON() {
        super(MEDIA_TYPE);
    }

    @Override
    public byte[] produce(DIDDocument didDocument) throws IOException {
        byte[] representation = objectMapper.writeValueAsBytes(didDocument.getJsonObject());
        return representation;
    }
}
