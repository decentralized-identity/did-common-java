package foundation.identity.did.representations.consumption;

public abstract class AbstractRepresentationConsumer implements RepresentationConsumer {

    private final String mediaType;

    public AbstractRepresentationConsumer(String mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String getMediaType() {
        return this.mediaType;
    }
}
