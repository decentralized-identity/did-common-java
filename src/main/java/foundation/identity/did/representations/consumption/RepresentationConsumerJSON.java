package foundation.identity.did.representations.consumption;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.identity.did.representations.Representations;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class RepresentationConsumerJSON extends AbstractRepresentationConsumer implements RepresentationConsumer {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationConsumerJSON instance = new RepresentationConsumerJSON();

    public static RepresentationConsumerJSON getInstance() {
        return instance;
    }

    private RepresentationConsumerJSON() {
        super(Representations.MEDIA_TYPE_JSON);
    }

    @Override
    public RepresentationConsumer.Result consume(byte[] representation) throws IOException {
        Map<String, Object> map = objectMapper.readValue(representation, LinkedHashMap.class);
        return this.detectRepresentationSpecificEntries(map);
    }
}
