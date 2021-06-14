package foundation.identity.did.representations.production;

public abstract class AbstractRepresentationProducer implements RepresentationProducer {

    private final String mediaType;

    public AbstractRepresentationProducer(String mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String getMediaType() {
        return this.mediaType;
    }
}
