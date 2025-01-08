package foundation.identity.did.representations.consumption;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.identity.did.DIDDocument;
import foundation.identity.did.representations.Representations;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepresentationConsumerDIDJSON extends AbstractRepresentationConsumer implements RepresentationConsumer {

    public static final String MEDIA_TYPE = "application/did+json";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationConsumerDIDJSON instance = new RepresentationConsumerDIDJSON();

    public static RepresentationConsumerDIDJSON getInstance() {
        return instance;
    }

    private RepresentationConsumerDIDJSON() {
        super(MEDIA_TYPE);
    }

    @Override
    public DIDDocument consume(byte[] representation) throws IOException {
        Map<String, Object> map = objectMapper.readValue(representation, LinkedHashMap.class);
        return DIDDocument.fromMap(map);
    }
}
