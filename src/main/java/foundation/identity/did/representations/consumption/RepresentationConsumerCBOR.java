package foundation.identity.did.representations.consumption;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upokecenter.cbor.CBORObject;
import foundation.identity.did.representations.Representations;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepresentationConsumerCBOR extends AbstractRepresentationConsumer implements RepresentationConsumer {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationConsumerCBOR instance = new RepresentationConsumerCBOR();

    public static RepresentationConsumerCBOR getInstance() {
        return instance;
    }

    private RepresentationConsumerCBOR() {
        super(Representations.MEDIA_TYPE_CBOR);
    }

    @Override
    public RepresentationConsumer.Result consume(byte[] representation) throws IOException {
        CBORObject cborObject = CBORObject.DecodeFromBytes(representation);
        Map<String, Object> map = cborObject.ToObject(LinkedHashMap.class);
        return this.detectRepresentationSpecificEntries(map);
    }
}
