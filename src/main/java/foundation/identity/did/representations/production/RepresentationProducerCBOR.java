package foundation.identity.did.representations.production;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upokecenter.cbor.CBORObject;
import foundation.identity.did.representations.Representations;

import java.io.IOException;
import java.util.Map;

public class RepresentationProducerCBOR extends AbstractRepresentationProducer implements RepresentationProducer {

    public static final String MEDIA_TYPE = "application/did+cbor";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationProducerCBOR instance = new RepresentationProducerCBOR();

    public static RepresentationProducerCBOR getInstance() {
        return instance;
    }

    private RepresentationProducerCBOR() {
        super(Representations.MEDIA_TYPE_CBOR);
    }

    @Override
    public RepresentationProducer.Result produce(Map<String, Object> didDocument, Map<String, Object> representationSpecificEntries) throws IOException {

        RepresentationProducer.Result jsonResult = RepresentationProducerJSON.getInstance().produce(didDocument, representationSpecificEntries);
        CBORObject cborObject = CBORObject.FromJSONBytes(jsonResult.representation);
        byte[] representation = cborObject.EncodeToBytes();
        return new RepresentationProducer.Result(MEDIA_TYPE, representation);
    }
}
