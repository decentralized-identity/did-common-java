package did;

import did.jsonld.JsonLDObject;
import did.jsonld.JsonLDUtils;

import javax.json.JsonObject;
import java.util.Collections;
import java.util.List;

public class Service extends JsonLDObject {

	private Service() {
		super();
	}

	private Service(JsonObject jsonObject) {
		super(jsonObject);
	}

	/*
	 * Factory methods
	 */

	public static Service build(String id, List<String> types, String serviceEndpoint) {

		Service service = new Service();
		service.build(null, id, types);

		// add JSON-LD properties

		if (serviceEndpoint != null) JsonLDUtils.jsonLdAddString(service.getJsonObjectBuilder(), DIDDocumentKeywords.JSONLD_TERM_SERVICEENDPOINT, serviceEndpoint);

		service.build();
		return service;
	}

	public static Service build(String id, String type, String serviceEndpoint) {

		return build(id, Collections.singletonList(type), serviceEndpoint);
	}

	public static Service build(List<String> types, String serviceEndpoint) {

		return build(null, types, serviceEndpoint);
	}

	public static Service build(String type, String serviceEndpoint) {

		return build(null, Collections.singletonList(type), serviceEndpoint);
	}

	public static Service build(JsonObject jsonObject) {

		return new Service(jsonObject);
	}

	/*
	 * Getters
	 */

	public String getServiceEndpoint() {

		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_SERVICEENDPOINT);
	}
}