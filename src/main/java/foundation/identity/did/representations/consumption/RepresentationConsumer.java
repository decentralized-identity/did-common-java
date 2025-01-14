package foundation.identity.did.representations.consumption;

import foundation.identity.did.DIDDocument;
import foundation.identity.did.representations.Representations;

import java.io.IOException;

public interface RepresentationConsumer {

    public static DIDDocument consume(byte[] representation, String mediaType) throws IOException {
        RepresentationConsumer didDocumentConsumer = Representations.getConsumer(mediaType);
        if (didDocumentConsumer == null) throw new IOException("No DID document consumer for media type: " + mediaType);

        return didDocumentConsumer.consume(representation);
    }

    public String getMediaType();
    public DIDDocument consume(byte[] representation) throws IOException;
}
