package did.jsonld;

import com.apicatalog.jsonld.JsonLd;
import com.apicatalog.jsonld.api.JsonLdError;
import com.apicatalog.jsonld.api.impl.ToRdfApi;
import com.apicatalog.jsonld.document.JsonDocument;
import com.apicatalog.jsonld.http.media.MediaType;
import com.apicatalog.jsonld.lang.Keywords;
import com.apicatalog.jsonld.loader.DocumentLoader;
import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.io.nquad.NQuadsWriter;
import did.DIDDocumentKeywords;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

public abstract class JsonLDObject {

	private JsonObjectBuilder jsonObjectBuilder;
	private JsonObject jsonObject;

	protected DocumentLoader documentLoader = new DIDContextDocumentLoader();

	protected JsonLDObject() {

		this.jsonObjectBuilder = Json.createObjectBuilder();
		this.jsonObject = null;
	}

	protected JsonLDObject(JsonObject jsonObject) {

		this.jsonObjectBuilder = null;
		this.jsonObject = jsonObject;
	}

	protected void build(List<String> contexts, String id, List<String> types) {

		// add JSON-LD properties

		if (contexts != null) JsonLDUtils.jsonLdAddStringList(this.getJsonObjectBuilder(), Keywords.CONTEXT, contexts);
		if (id != null) JsonLDUtils.jsonLdAddString(this.getJsonObjectBuilder(), DIDDocumentKeywords.JSONLD_TERM_ID, id);
		if (types != null) JsonLDUtils.jsonLdAddStringList(this.getJsonObjectBuilder(), DIDDocumentKeywords.JSONLD_TERM_TYPE, types);
	}

	/*
	 * Building
	 */

	public boolean isBuilt() {
		return this.jsonObject != null;
	}

	public void build() {
		this.jsonObject = this.jsonObjectBuilder.build();
		this.jsonObjectBuilder = null;
	}

	/*
	 * Getters and setters
	 */

	public DocumentLoader getDocumentLoader() {
		return this.documentLoader;
	}

	public void setDocumentLoader(DocumentLoader documentLoader) {
		this.documentLoader = documentLoader;
	}

	public JsonObjectBuilder getJsonObjectBuilder() {
		if (this.isBuilt()) throw new IllegalStateException("JSON-LD object has already been built.");
		return this.jsonObjectBuilder;
	}

	public JsonObject getJsonObject() {
		if (! this.isBuilt()) throw new IllegalStateException("JSON-LD object has not been built yet.");
		return this.jsonObject;
	}

	public List<String> getContexts() {
		return JsonLDUtils.jsonLdGetStringList(this.getJsonObject(), Keywords.CONTEXT);
	}

	public final String getId() {
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_ID);
	}

	public final List<String> getTypes() {
		return JsonLDUtils.jsonLdGetStringList(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_TYPE);
	}

	public final String getType() {
		return JsonLDUtils.jsonLdGetString(this.getJsonObject(), DIDDocumentKeywords.JSONLD_TERM_TYPE);
	}

	public final boolean isType(String type) {
		return JsonLDUtils.jsonLdContainsString(this.getJsonObject(), type);

	}

	/*
	 * Serialization
	 */

	private static final JsonWriterFactory jsonWriterFactory;
	private static final JsonWriterFactory jsonWriterFactoryPretty;

	static {

		Map<String, Object> properties = new HashMap<>(1);
		Map<String, Object> propertiesPretty = new HashMap<>(1);
		propertiesPretty.put(JsonGenerator.PRETTY_PRINTING, true);
		jsonWriterFactory = Json.createWriterFactory(properties);
		jsonWriterFactoryPretty = Json.createWriterFactory(propertiesPretty);
	}

	public String toRDF() throws JsonLdError, IOException {

		JsonDocument jsonDocument = JsonDocument.of(MediaType.JSON_LD, this.getJsonObject());
		ToRdfApi toRdfApi = JsonLd.toRdf(jsonDocument);
		toRdfApi.loader(this.getDocumentLoader());
//		toRdfApi.ordered(true);
		RdfDataset rdfDataset = toRdfApi.get();

		StringWriter stringWriter = new StringWriter();
		NQuadsWriter nQuadsWriter = new NQuadsWriter(stringWriter);
		nQuadsWriter.write(rdfDataset);

		return stringWriter.toString();
	}

	public String toJson(boolean pretty) {

		JsonWriterFactory jsonWriterFactory = pretty ? JsonLDObject.jsonWriterFactoryPretty : JsonLDObject.jsonWriterFactory;

		StringWriter stringWriter = new StringWriter();
		JsonWriter jsonWriter = jsonWriterFactory.createWriter(stringWriter);
		jsonWriter.writeObject(this.getJsonObject());
		jsonWriter.close();

		return stringWriter.toString();
	}

	public String toJson() throws JsonLdError {

		return toJson(false);
	}

	/*
	 * Object methods
	 */

	@Override
	public String toString() {
		if (! this.isBuilt()) return super.toString();
		return this.toJson(false);
	}

	@Override
	public boolean equals(Object o) {
		if (! this.isBuilt()) return super.equals(o);
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		JsonLDObject that = (JsonLDObject) o;
		return Objects.equals(this.getJsonObject(), that.getJsonObject());
	}

	@Override
	public int hashCode() {
		if (! this.isBuilt()) return super.hashCode();
		return Objects.hash(this.getJsonObject());
	}
}