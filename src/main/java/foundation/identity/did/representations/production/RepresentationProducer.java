package foundation.identity.did.representations.production;

import foundation.identity.did.DIDDocument;
import foundation.identity.did.representations.Representations;

import java.io.IOException;

public interface RepresentationProducer {

    public static byte[] produce(DIDDocument didDocument, String mediaType) throws IOException {
        RepresentationProducer representationProducer = Representations.getProducer(mediaType);
        if (representationProducer == null) throw new IOException("No DID document producer for media type: " + mediaType);

        return representationProducer.produce(didDocument);
    }

    public String getMediaType();
    public byte[] produce(DIDDocument didDocument) throws IOException;
}
