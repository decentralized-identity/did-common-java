package foundation.identity.did.representations.production;

import foundation.identity.did.representations.Representations;

import java.io.IOException;
import java.util.Map;

public interface RepresentationProducer {

    public class Result {
        private final String mediaType;
        private final byte[] representation;

        public Result(String mediaType, byte[] representation) {
            this.mediaType = mediaType;
            // It's important to clone the array to maintain immutability
            this.representation = representation.clone();
        }

        public String getMediaType() {
            return mediaType;
        }

        public byte[] getRepresentation() {
            // Also clone the array on getter to prevent external modification
            return representation.clone();
        }

        // Implement equals and hashCode to mimic the behavior of a record
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Result result = (Result) o;

            if (!mediaType.equals(result.mediaType)) return false;
            return java.util.Arrays.equals(representation, result.representation);
        }

        @Override
        public int hashCode() {
            int result = mediaType.hashCode();
            result = 31 * result + java.util.Arrays.hashCode(representation);
            return result;
        }
    }

    public static RepresentationProducer.Result produce(Map<String, Object> didDocument, Map<String, Object> representationSpecificEntries, String mediaType) throws IOException {

        RepresentationProducer didDocumentConsumer = Representations.getProducer(mediaType);
        if (didDocumentConsumer == null) throw new IllegalArgumentException("No producer for media type " + mediaType);
        return didDocumentConsumer.produce(didDocument, representationSpecificEntries);
    }

    public String getMediaType();
    public Result produce(Map<String, Object> didDocument, Map<String, Object> representationSpecificEntries) throws IOException;
}
