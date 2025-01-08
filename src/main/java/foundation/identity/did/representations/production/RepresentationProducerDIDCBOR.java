package foundation.identity.did.representations.production;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upokecenter.cbor.CBORObject;
import foundation.identity.did.DIDDocument;

import java.io.IOException;

public class RepresentationProducerDIDCBOR extends AbstractRepresentationProducer implements RepresentationProducer {

    public static final String MEDIA_TYPE = "application/did+cbor";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationProducerDIDCBOR instance = new RepresentationProducerDIDCBOR();

    public static RepresentationProducerDIDCBOR getInstance() {
        return instance;
    }

    private RepresentationProducerDIDCBOR() {
        super(MEDIA_TYPE);
    }

    @Override
    public byte[] produce(DIDDocument didDocument) throws IOException {
        byte[] jsonRepresentation = RepresentationProducerDIDJSON.getInstance().produce(didDocument);
        CBORObject cborObject = CBORObject.FromJSONBytes(jsonRepresentation);
        byte[] representation = cborObject.EncodeToBytes();
        return representation;
    }
}
