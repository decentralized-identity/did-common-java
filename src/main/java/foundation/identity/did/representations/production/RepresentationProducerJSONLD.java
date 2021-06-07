package foundation.identity.did.representations.production;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundation.identity.did.representations.Representations;

import java.io.IOException;
import java.util.Map;

public class RepresentationProducerJSONLD extends AbstractRepresentationProducer implements RepresentationProducer {

    public static final String MEDIA_TYPE = "application/did+ld+json";

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RepresentationProducerJSONLD instance = new RepresentationProducerJSONLD();

    public static RepresentationProducerJSONLD getInstance() {
        return instance;
    }

    private RepresentationProducerJSONLD() {
        super(Representations.MEDIA_TYPE_JSONLD);
    }

    @Override
    public RepresentationProducer.Result produce(Map<String, Object> didDocument, Map<String, Object> representationSpecificEntries) throws IOException {

        byte[] representation = objectMapper.writeValueAsBytes(didDocument);
        return new RepresentationProducer.Result(MEDIA_TYPE, representation);
    }
}
