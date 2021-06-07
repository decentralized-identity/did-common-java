package foundation.identity.did.representations.consumption;

import foundation.identity.did.representations.Representations;

import java.io.IOException;
import java.util.Map;

public interface RepresentationConsumer {

    public static class Result {

        public Result(Map<String, Object> didDocument, Map<String, Map<String, Object>> representationSpecificEntries) { this.didDocument = didDocument; this.representationSpecificEntries = representationSpecificEntries; }
        public Map<String, Object> didDocument;
        public Map<String, Map<String, Object>> representationSpecificEntries;
    }

    public static Result consume(byte[] representation, String mediaType) throws IOException {

        RepresentationConsumer didDocumentConsumer = Representations.getConsumer(mediaType);
        if (didDocumentConsumer == null) throw new IllegalArgumentException("No consumer for media type " + mediaType);
        return didDocumentConsumer.consume(representation);
    }

    public String getMediaType();
    public Result consume(byte[] representation) throws IOException;
}
