package foundation.identity.did.representations;

import foundation.identity.did.representations.consumption.*;
import foundation.identity.did.representations.production.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Representations {

    public static final String DEFAULT_MEDIA_TYPE = RepresentationProducerDID.MEDIA_TYPE;

    public static final List<RepresentationProducer> representationProducers = Arrays.asList(
            RepresentationProducerDID.getInstance(),
            RepresentationProducerDIDJSONLD.getInstance(),
            RepresentationProducerDIDJSON.getInstance(),
            RepresentationProducerDIDCBOR.getInstance()
    );

    public static final List<RepresentationConsumer> representationConsumers = Arrays.asList(
            RepresentationConsumerDID.getInstance(),
            RepresentationConsumerDIDJSONLD.getInstance(),
            RepresentationConsumerDIDJSON.getInstance(),
            RepresentationConsumerDIDCBOR.getInstance()
    );

    public static final Map<String, RepresentationProducer> representationProducersByMediaType = new LinkedHashMap<>();
    public static final Map<String, RepresentationConsumer> representationConsumersByMediaType = new LinkedHashMap<>();

    static {
        representationProducers.forEach(x -> representationProducersByMediaType.put(x.getMediaType(), x));
        representationConsumers.forEach(x -> representationConsumersByMediaType.put(x.getMediaType(), x));
    }

    public static boolean isProducibleMediaType(String mediaType) {
        return representationProducersByMediaType.containsKey(mediaType);
    }

    public static boolean isConsumableMediaType(String mediaType) {
        return representationConsumersByMediaType.containsKey(mediaType);
    }

    public static RepresentationProducer getProducer(String mediaType) {
        return representationProducersByMediaType.get(mediaType);
    }
    public static RepresentationConsumer getConsumer(String mediaType) {
        return representationConsumersByMediaType.get(mediaType);
    }
}
