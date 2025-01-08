package foundation.identity.did.representations.production;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.identity.did.DIDDocument;

import java.io.IOException;

public class RepresentationProducerDID extends AbstractRepresentationProducer implements RepresentationProducer {

    public static final String MEDIA_TYPE = "application/did";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationProducerDID instance = new RepresentationProducerDID();

    public static RepresentationProducerDID getInstance() {
        return instance;
    }

    private RepresentationProducerDID() {
        super(MEDIA_TYPE);
    }

    @Override
    public byte[] produce(DIDDocument didDocument) throws IOException {
        byte[] representation = objectMapper.writeValueAsBytes(didDocument.getJsonObject());
        return representation;
    }
}
