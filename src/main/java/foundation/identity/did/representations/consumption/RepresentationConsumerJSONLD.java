package foundation.identity.did.representations.consumption;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.identity.did.representations.Representations;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepresentationConsumerJSONLD extends AbstractRepresentationConsumer implements RepresentationConsumer {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationConsumerJSONLD instance = new RepresentationConsumerJSONLD();

    public static RepresentationConsumerJSONLD getInstance() {
        return instance;
    }

    private RepresentationConsumerJSONLD() {
        super(Representations.MEDIA_TYPE_JSONLD);
    }

    @Override
    public RepresentationConsumer.Result consume(byte[] representation) throws IOException {
        Map<String, Object> map = objectMapper.readValue(representation, LinkedHashMap.class);
        return this.detectRepresentationSpecificEntries(map);
    }
}
