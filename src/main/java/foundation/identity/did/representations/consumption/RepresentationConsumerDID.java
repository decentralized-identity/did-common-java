package foundation.identity.did.representations.consumption;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.identity.did.DIDDocument;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepresentationConsumerDID extends AbstractRepresentationConsumer implements RepresentationConsumer {

    public static final String MEDIA_TYPE = "application/did";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationConsumerDID instance = new RepresentationConsumerDID();

    public static RepresentationConsumerDID getInstance() {
        return instance;
    }

    private RepresentationConsumerDID() {
        super(MEDIA_TYPE);
    }

    @Override
    public DIDDocument consume(byte[] representation) throws IOException {
        Map<String, Object> map = objectMapper.readValue(representation, LinkedHashMap.class);
        return DIDDocument.fromMap(map);
    }
}
