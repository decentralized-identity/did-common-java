package foundation.identity.did.representations.consumption;

import foundation.identity.did.representations.Representations;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface RepresentationConsumer {

    public class Result {
        private final Map<String, Object> didDocument;
        private final Map<String, Map<String, Object>> representationSpecificEntries;

        public Result(Map<String, Object> didDocument,
            Map<String, Map<String, Object>> representationSpecificEntries) {
            this.didDocument = Map.copyOf(didDocument);
            Map<String, Map<String, Object>> tempMap = new HashMap<>();
            representationSpecificEntries.forEach((key, value) ->
                tempMap.put(key, Map.copyOf(value))
            );
            this.representationSpecificEntries = Collections.unmodifiableMap(tempMap);
        }

        public Map<String, Object> getDidDocument() {
            return didDocument;
        }

        public Map<String, Map<String, Object>> getRepresentationSpecificEntries() {
            return representationSpecificEntries;
        }

        // Implement equals and hashCode to mimic the behavior of a record
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Result result = (Result) o;

            if (!didDocument.equals(result.didDocument)) return false;
            return representationSpecificEntries.equals(result.representationSpecificEntries);
        }

        @Override
        public int hashCode() {
            int result = didDocument.hashCode();
            result = 31 * result + representationSpecificEntries.hashCode();
            return result;
        }
    }

    public static Result consume(byte[] representation, String mediaType) throws IOException {

        RepresentationConsumer didDocumentConsumer = Representations.getConsumer(mediaType);
        if (didDocumentConsumer == null) throw new IllegalArgumentException("No consumer for media type " + mediaType);
        return didDocumentConsumer.consume(representation);
    }

    public String getMediaType();
    public Result consume(byte[] representation) throws IOException;
}
