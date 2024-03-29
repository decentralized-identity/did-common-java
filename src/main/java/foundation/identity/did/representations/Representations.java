package foundation.identity.did.representations;

import foundation.identity.did.representations.consumption.RepresentationConsumer;
import foundation.identity.did.representations.consumption.RepresentationConsumerCBOR;
import foundation.identity.did.representations.consumption.RepresentationConsumerJSON;
import foundation.identity.did.representations.consumption.RepresentationConsumerJSONLD;
import foundation.identity.did.representations.production.RepresentationProducer;
import foundation.identity.did.representations.production.RepresentationProducerCBOR;
import foundation.identity.did.representations.production.RepresentationProducerJSON;
import foundation.identity.did.representations.production.RepresentationProducerJSONLD;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Representations {

    public static final String MEDIA_TYPE_JSONLD = "application/did+ld+json";
    public static final String MEDIA_TYPE_JSON = "application/did+json";
    public static final String MEDIA_TYPE_CBOR = "application/did+cbor";

    public static final String DEFAULT_MEDIA_TYPE = MEDIA_TYPE_JSONLD;

    public static final List<String> MEDIA_TYPES = Arrays.asList(
            MEDIA_TYPE_JSONLD,
            MEDIA_TYPE_JSON,
            MEDIA_TYPE_CBOR
    );

    public static final Map<String, RepresentationProducer> didDocumentProducers = new HashMap<>();
    public static final Map<String, RepresentationConsumer> didDocumentConsumers = new HashMap<>();

    static {

        didDocumentProducers.put(RepresentationProducerJSONLD.getInstance().getMediaType(), RepresentationProducerJSONLD.getInstance());
        didDocumentProducers.put(RepresentationProducerJSON.getInstance().getMediaType(), RepresentationProducerJSON.getInstance());
        didDocumentProducers.put(RepresentationProducerCBOR.getInstance().getMediaType(), RepresentationProducerCBOR.getInstance());
        didDocumentConsumers.put(RepresentationConsumerJSONLD.getInstance().getMediaType(), RepresentationConsumerJSONLD.getInstance());
        didDocumentConsumers.put(RepresentationConsumerJSON.getInstance().getMediaType(), RepresentationConsumerJSON.getInstance());
        didDocumentConsumers.put(RepresentationConsumerCBOR.getInstance().getMediaType(), RepresentationConsumerCBOR.getInstance());
    }

    public static boolean isRepresentationMediaType(String mediaType) {
        return MEDIA_TYPES.contains(mediaType);
    }

    public static RepresentationProducer getProducer(String mediaType) {
        return didDocumentProducers.get(mediaType);
    }
    public static RepresentationConsumer getConsumer(String mediaType) {
        return didDocumentConsumers.get(mediaType);
    }
}
