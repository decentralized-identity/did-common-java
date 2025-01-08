package foundation.identity.did.representations.production;

import foundation.identity.did.DIDDocument;
import foundation.identity.did.representations.Representations;

import java.io.IOException;

public interface RepresentationProducer {

    public static byte[] produce(DIDDocument didDocument, String mediaType) throws IOException {
        RepresentationProducer didDocumentConsumer = Representations.getProducer(mediaType);
        if (didDocumentConsumer == null) throw new IllegalArgumentException("No producer for media type " + mediaType);
        return didDocumentConsumer.produce(didDocument);
    }

    public String getMediaType();
    public byte[] produce(DIDDocument didDocument) throws IOException;
}
