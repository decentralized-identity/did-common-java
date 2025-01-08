package foundation.identity.did.representations.consumption;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upokecenter.cbor.CBORObject;
import foundation.identity.did.DIDDocument;
import foundation.identity.did.representations.Representations;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepresentationConsumerDIDCBOR extends AbstractRepresentationConsumer implements RepresentationConsumer {

    public static final String MEDIA_TYPE = "application/did+cbor";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationConsumerDIDCBOR instance = new RepresentationConsumerDIDCBOR();

    public static RepresentationConsumerDIDCBOR getInstance() {
        return instance;
    }

    private RepresentationConsumerDIDCBOR() {
        super(MEDIA_TYPE);
    }

    @Override
    public DIDDocument consume(byte[] representation) throws IOException {
        CBORObject cborObject = CBORObject.DecodeFromBytes(representation);
        Map<String, Object> map = cborObject.ToObject(LinkedHashMap.class);
        return DIDDocument.fromMap(map);
    }
}
