package foundation.identity.did.representations.consumption;

import foundation.identity.did.representations.RepresentationSpecificEntries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractRepresentationConsumer implements RepresentationConsumer {

    private final String mediaType;

    public AbstractRepresentationConsumer(String mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String getMediaType() {
        return this.mediaType;
    }

    public RepresentationConsumer.Result detectRepresentationSpecificEntries(Map<String, Object> map) {

        Map<String, Object> didDocument = new HashMap<>(map);
        Map<String, Map<String, Object>> representationSpecificEntries = new HashMap<>();
        for (String mediaType : RepresentationSpecificEntries.REPRESENTATION_SPECIFIC_ENTRY_NAMES.keySet()) {
            representationSpecificEntries.put(mediaType, new HashMap<>());
        }
        for (Map.Entry<String, List<String>> representationSpecificEntryNames : RepresentationSpecificEntries.REPRESENTATION_SPECIFIC_ENTRY_NAMES.entrySet()) {
            String mediaType = representationSpecificEntryNames.getKey();
            for (String representationSpecificEntryName : representationSpecificEntryNames.getValue()) {
                if (didDocument.containsKey(representationSpecificEntryName)) {
                    Object representationSpecificEntryValue = didDocument.remove(representationSpecificEntryName);
                    Map<String, Object> representationSpecificMap = representationSpecificEntries.get(mediaType);
                    representationSpecificMap.put(representationSpecificEntryName, representationSpecificEntryValue);
                }
            }
        }

        return new RepresentationConsumer.Result(didDocument, representationSpecificEntries);
    }
}
