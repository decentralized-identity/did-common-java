package foundation.identity.did.representations.consumption;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.identity.did.DIDDocument;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepresentationConsumerDIDJSONLD extends AbstractRepresentationConsumer implements RepresentationConsumer {

    public static final String MEDIA_TYPE = "application/did+ld+json";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationConsumerDIDJSONLD instance = new RepresentationConsumerDIDJSONLD();

    public static RepresentationConsumerDIDJSONLD getInstance() {
        return instance;
    }

    private RepresentationConsumerDIDJSONLD() {
        super(MEDIA_TYPE);
    }

    @Override
    public DIDDocument consume(byte[] representation) throws IOException {
        Map<String, Object> map = objectMapper.readValue(representation, LinkedHashMap.class);
        return DIDDocument.fromMap(map);
    }
}
