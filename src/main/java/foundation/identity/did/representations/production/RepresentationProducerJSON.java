package foundation.identity.did.representations.production;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.identity.did.representations.Representations;

import java.io.IOException;
import java.util.Map;

public class RepresentationProducerJSON extends AbstractRepresentationProducer implements RepresentationProducer {

    public static final String MEDIA_TYPE = "application/did+json";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationProducerJSON instance = new RepresentationProducerJSON();

    public static RepresentationProducerJSON getInstance() {
        return instance;
    }

    private RepresentationProducerJSON() {
        super(Representations.MEDIA_TYPE_JSON);
    }

    @Override
    public RepresentationProducer.Result produce(Map<String, Object> didDocument, Map<String, Object> representationSpecificEntries) throws IOException {

        byte[] representation = objectMapper.writeValueAsBytes(didDocument);
        return new RepresentationProducer.Result(MEDIA_TYPE, representation);
    }
}
