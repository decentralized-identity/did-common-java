package did;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jsonldjava.core.JsonLdConsts;
import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;

import did.parser.ParserException;

public class DIDDocument {

	public static final String MIME_TYPE = "application/did+ld+json";

	public static final String JSONLD_TERM_ID = "id";
	public static final String JSONLD_TERM_TYPE = "type";
	public static final String JSONLD_TERM_SERVICE = "service";
	public static final String JSONLD_TERM_SERVICEENDPOINT = "serviceEndpoint";
	public static final String JSONLD_TERM_PUBLICKEY = "publicKey";
	public static final String JSONLD_TERM_PUBLICKEYBASE64 = "publicKeyBase64";
	public static final String JSONLD_TERM_PUBLICKEYBASE58 = "publicKeyBase58";
	public static final String JSONLD_TERM_PUBLICKEYHEX = "publicKeyHex";
	public static final String JSONLD_TERM_PUBLICKEYPEM = "publicKeyPem";
	public static final String JSONLD_TERM_AUTHENTICATION = "authentication";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private final Map<String, Object> jsonLdObject;

	private DIDDocument(Map<String, Object> jsonLdObject) {

		this.jsonLdObject = jsonLdObject;
	}

	/*
	 * Factory methods
	 */

	public static DIDDocument build(Map<String, Object> jsonLdObject) {

		return new DIDDocument(jsonLdObject);
	}

	@JsonCreator
	public static DIDDocument build() {

		return new DIDDocument(newJsonLdObject(false));
	}

	public static DIDDocument build(Object context, String id, List<PublicKey> publicKeys, List<Authentication> authentications, List<Service> services) {

		Map<String, Object> jsonLdObject = newJsonLdObject(context == null);

		// add '@context'

		if (context != null) {

			jsonLdObject.put(JsonLdConsts.CONTEXT, context);
		}

		// add 'id'

		jsonLdObject.put(JSONLD_TERM_ID, id);

		// add 'publicKey'

		if (publicKeys != null) {

			LinkedList<Object> publicKeysJsonLdArray = new LinkedList<Object> ();

			for (PublicKey publicKey : publicKeys) {

				Map<String, Object> publicKeyJsonLdObject = publicKey.getJsonLdObject();

				publicKeysJsonLdArray.add(publicKeyJsonLdObject);
			}

			jsonLdObject.put(JSONLD_TERM_PUBLICKEY, publicKeysJsonLdArray);
		}

		// add 'publicKey'

		if (publicKeys != null) {

			LinkedList<Object> publicKeysJsonLdArray = new LinkedList<Object> ();

			for (PublicKey publicKey : publicKeys) {

				Map<String, Object> publicKeyJsonLdObject = publicKey.getJsonLdObject();

				publicKeysJsonLdArray.add(publicKeyJsonLdObject);
			}

			jsonLdObject.put(JSONLD_TERM_PUBLICKEY, publicKeysJsonLdArray);
		}

		// add 'service'

		if (services != null) {

			LinkedList<Object> servicesJsonLdArray = new LinkedList<Object> ();

			for (Service service : services) {

				Map<String, Object> serviceJsonLdObject = service.getJsonLdObject();

				servicesJsonLdArray.add(serviceJsonLdObject);
			}

			jsonLdObject.put(JSONLD_TERM_SERVICE, servicesJsonLdArray);
		}

		// add 'authentication'

		if (authentications != null && authentications.size() > 0) {

			LinkedList<Object> authenticationsJsonLdArray = new LinkedList<Object> ();

			for (Authentication authentication : authentications) {

				Map<String, Object> authenticationJsonLdObject = authentication.getJsonLdObject();

				authenticationsJsonLdArray.add(authenticationJsonLdObject);
			}

			jsonLdObject.put(JSONLD_TERM_AUTHENTICATION, authenticationsJsonLdArray);
		}

		// done

		return new DIDDocument(jsonLdObject);
	}

	public static DIDDocument build(String id, List<PublicKey> publicKeys, List<Authentication> authentications, List<Service> services) {

		return build(null, id, publicKeys, authentications, services);
	}

	/*
	 * Serialization
	 */

	public static DIDDocument fromJson(String json) throws IOException {

		return objectMapper.readValue(json, DIDDocument.class);
	}

	public static DIDDocument fromJson(Reader reader) throws JsonParseException, JsonMappingException, IOException {

		return objectMapper.readValue(reader, DIDDocument.class);
	}

	public String toJson() throws IOException, JsonLdError {

		if (this.jsonLdObject == null) return "null";

		Object context = this.jsonLdObject.get(JsonLdConsts.CONTEXT);
		if (context == null) throw new IllegalStateException("No @context.");

		JsonLdOptions options = new JsonLdOptions();

		HashMap<String, Object> compacted = (HashMap<String, Object>) JsonLdProcessor.compact(this.jsonLdObject, context, options);
		compacted.remove(JsonLdConsts.CONTEXT);

		LinkedHashMap<String, Object> json = new LinkedHashMap<String, Object> ();
		json.put(JsonLdConsts.CONTEXT, context);
		json.putAll(compacted);

		// done

		String result = JsonUtils.toPrettyString(json);

		return result;
	}

	/*
	 * Service selection
	 */

	public Map<Integer, Service> selectServices(String selectServiceName, String selectServiceType) {

		int i = -1;
		Map<Integer, Service> selectedServices = new HashMap<Integer, Service> ();
		if (this.getServices() == null) return selectedServices;

		for (Service service : this.getServices()) {

			i++;

			if (selectServiceName != null) {

				DIDURL serviceDidUrl;
				try { serviceDidUrl = DIDURL.fromString(service.getId()); } catch (ParserException ex) { serviceDidUrl = null; }
				String serviceName = serviceDidUrl == null ? null : serviceDidUrl.getFragment();

				if (serviceName == null) continue;
				if (! serviceName.equals(selectServiceName)) continue;
			}

			if (selectServiceType != null) {

				if (service.getTypes() == null) continue;
				if (! Arrays.asList(service.getTypes()).contains(selectServiceType)) continue;
			}

			selectedServices.put(Integer.valueOf(i), service);
		}

		return selectedServices;
	}

	public Map<Integer, PublicKey> selectKeys(String selectKeyName, String selectKeyType) {

		int i = -1;
		Map<Integer, PublicKey> selectedKeys = new HashMap<Integer, PublicKey> ();
		if (this.getPublicKeys() == null) return selectedKeys;

		for (PublicKey publicKey : this.getPublicKeys()) {

			i++;

			if (selectKeyName != null) {

				DIDURL publicKeyDidUrl;
				try { publicKeyDidUrl = DIDURL.fromString(publicKey.getId()); } catch (ParserException ex) { publicKeyDidUrl = null; }
				String publicKeyName = publicKeyDidUrl == null ? null : publicKeyDidUrl.getFragment();

				if (publicKeyName == null) continue;
				if (! publicKeyName.equals(selectKeyName)) continue;
			}

			if (selectKeyType != null) {

				if (publicKey.getTypes() == null) continue;
				if (! Arrays.asList(publicKey.getTypes()).contains(selectKeyType)) continue;
			}

			selectedKeys.put(Integer.valueOf(i), publicKey);
		}

		return selectedKeys;
	}

	/*
	 * Helper methods
	 */

	public static final Object DID_DOCUMENT_SKELETON;

	static {

		try {

			DID_DOCUMENT_SKELETON = JsonUtils.fromInputStream(DIDDocument.class.getResourceAsStream("diddocument-skeleton.jsonld"));
		} catch (IOException ex) {

			throw new ExceptionInInitializerError(ex);
		}
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, Object> newJsonLdObject(boolean defaultContext) {

		if (defaultContext) {

			return new LinkedHashMap<String, Object> ((Map<String, Object>) DID_DOCUMENT_SKELETON);
		} else {

			return new LinkedHashMap<String, Object> ();

		}
	}

	/*
	 * Getters and setters
	 */

	@JsonValue
	public Map<String, Object> getJsonLdObject() {

		return this.jsonLdObject;
	}

	@JsonAnySetter
	public void setJsonLdObjectKeyValue(String key, Object value) {

		this.jsonLdObject.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public List<Object> getContexts() {

		Object entry = this.jsonLdObject.get(JsonLdConsts.CONTEXT);
		if (entry == null) return null;
		if (entry instanceof URI) entry = Collections.singletonList(entry);
		if (entry instanceof String) entry = Collections.singletonList(entry);
		if (! (entry instanceof List<?>)) return null;

		return (List<Object>) entry;
	}

	public String getId() {

		Object entry = this.jsonLdObject.get(JSONLD_TERM_ID);
		if (entry instanceof URI) return ((URI) entry).toString();
		if (entry instanceof String) return (String) entry;
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Service> getServices() {

		Object entry = this.jsonLdObject.get(JSONLD_TERM_SERVICE);
		if (entry == null) return null;
		if (entry instanceof LinkedHashMap<?, ?>) entry = Collections.singletonList(entry);
		if (! (entry instanceof List<?>)) return null;

		List<Object> servicesJsonLdArray = (List<Object>) entry;

		List<Service> services = new ArrayList<Service> ();

		for (Object entry2 : servicesJsonLdArray) {

			if (! (entry2 instanceof LinkedHashMap<?, ?>)) continue;

			Map<String, Object> serviceJsonLdObject = (Map<String, Object>) entry2;

			services.add(Service.build(serviceJsonLdObject));
		}

		return services;
	}

	@SuppressWarnings("unchecked")
	public List<PublicKey> getPublicKeys() {

		Object entry = this.jsonLdObject.get(JSONLD_TERM_PUBLICKEY);
		if (entry == null) return null;
		if (entry instanceof LinkedHashMap<?, ?>) entry = Collections.singletonList(entry);
		if (! (entry instanceof List<?>)) return null;

		List<Object> publicKeysJsonLdArray = (List<Object>) entry;

		List<PublicKey> publicKeys = new ArrayList<PublicKey> ();

		for (Object entry2 : publicKeysJsonLdArray) {

			if (! (entry2 instanceof LinkedHashMap<?, ?>)) continue;

			LinkedHashMap<String, Object> publicKeyJsonLdObject = (LinkedHashMap<String, Object>) entry2;

			publicKeys.add(PublicKey.build(publicKeyJsonLdObject));
		}

		return publicKeys;
	}

	@SuppressWarnings("unchecked")
	public List<Authentication> getAuthentications() {

		Object entry = this.jsonLdObject.get(JSONLD_TERM_AUTHENTICATION);
		if (entry == null) return null;
		if (entry instanceof LinkedHashMap<?, ?>) entry = Collections.singletonList(entry);
		if (! (entry instanceof List<?>)) return null;

		List<Object> authenticationsJsonLdArray = (List<Object>) entry;

		List<Authentication> authentications = new ArrayList<Authentication> ();

		for (Object entry2 : authenticationsJsonLdArray) {

			if (! (entry2 instanceof LinkedHashMap<?, ?>)) continue;

			LinkedHashMap<String, Object> authenticationJsonLdObject = (LinkedHashMap<String, Object>) entry2;

			authentications.add(Authentication.build(authenticationJsonLdObject));
		}

		return authentications;
	}

	/*
	 * Object methods
	 */

	@Override
	public String toString() {

		try {

			return this.toJson();
		} catch (IOException | JsonLdError ex) {

			throw new RuntimeException(ex.getMessage(), ex);
		}
	}
}
