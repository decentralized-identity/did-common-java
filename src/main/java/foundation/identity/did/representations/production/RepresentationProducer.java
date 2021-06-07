package foundation.identity.did.representations.production;

import foundation.identity.did.representations.Representations;

import java.io.IOException;
import java.util.Map;

public interface RepresentationProducer {

    public static class Result {

        public Result(String mediaType, byte[] representation) { this.mediaType = mediaType; this.representation = representation; }
        public String mediaType;
        public byte[] representation;
    }

    public static RepresentationProducer.Result produce(Map<String, Object> didDocument, Map<String, Object> representationSpecificEntries, String mediaType) throws IOException {

        RepresentationProducer didDocumentConsumer = Representations.getProducer(mediaType);
        if (didDocumentConsumer == null) throw new IllegalArgumentException("No producer for media type " + mediaType);
        return didDocumentConsumer.produce(didDocument, representationSpecificEntries);
    }

    public String getMediaType();
    public Result produce(Map<String, Object> didDocument, Map<String, Object> representationSpecificEntries) throws IOException;
}
