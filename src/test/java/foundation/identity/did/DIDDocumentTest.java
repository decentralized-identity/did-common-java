package foundation.identity.did;

import foundation.identity.jsonld.JsonLDDereferencer;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DIDDocumentTest {

	@Test
	public void testDIDDocument() throws Exception {

		DIDDocument didDocument = create();
		test(didDocument);
	}

	@Test
	public void testDIDDocumentFromJson() throws Exception {

		DIDDocument didDocument = DIDDocument.fromJson(create().toJson());
		test(didDocument);
	}

	@Test
	public void testDIDDocumentFromMap() throws Exception {

		DIDDocument didDocument = DIDDocument.fromMap(create().toMap());
		test(didDocument);
	}

	private DIDDocument create() {

		// DID DOCUMENT verificationMethods

		List<VerificationMethod> verificationMethods = new ArrayList<>();

		VerificationMethod verificationMethod = VerificationMethod.builder()
				.id(URI.create("did:ex:123#key-1"))
				.types(List.of("Ed25519VerificationKey2018"))
				.publicKeyBase58("000000000")
				.build();

		verificationMethods.add(verificationMethod);

		// DID DOCUMENT services

		List<Service> services = new ArrayList<>();

		Service service = Service.builder()
				.type("DIDCommService")
				.serviceEndpoint("http://localhost:8080/")
				.build();

		services.add(service);

		// create DID DOCUMENT

		return DIDDocument.builder()
				.id(URI.create("did:ex:123"))
				.controller(URI.create("did:ex:456"))
				.alsoKnownAs(URI.create("did:ex:789"))
				.verificationMethods(verificationMethods)
				.authenticationVerificationMethods(Collections.singletonList(VerificationMethod.builder().id(verificationMethod.getId()).build()))
				.assertionMethodVerificationMethods(Collections.singletonList(VerificationMethod.builder().id(verificationMethod.getId()).build()))
				.services(services)
				.build();
	}

	private void test(DIDDocument didDocument) {

		assertEquals(URI.create("did:ex:123"), didDocument.getId());
		assertEquals(didDocument.getControllers().get(0), URI.create("did:ex:456"));
		assertEquals(didDocument.getAlsoKnownAses().get(0), URI.create("did:ex:789"));
		assertEquals(URI.create("did:ex:123#key-1"), didDocument.getVerificationMethods().get(0).getId());
		assertEquals("Ed25519VerificationKey2018", didDocument.getVerificationMethods().get(0).getType());
		assertEquals("000000000", didDocument.getVerificationMethods().get(0).getPublicKeyBase58());
		assertEquals(URI.create("did:ex:123#key-1"), didDocument.getAuthenticationVerificationMethodsDereferenced().get(0).getId());
		assertEquals(URI.create("did:ex:123#key-1"), didDocument.getAssertionMethodVerificationMethodsDereferenced().get(0).getId());
		assertEquals("did:ex:123#key-1", ((List<String>) didDocument.getJsonObject().get("authentication")).get(0));
		assertEquals("did:ex:123#key-1", ((List<String>) didDocument.getJsonObject().get("assertionMethod")).get(0));
		assertEquals(URI.create("did:ex:123#key-1"), didDocument.getAssertionMethodVerificationMethodsDereferenced().get(0).getId());
		assertEquals("DIDCommService", didDocument.getServices().get(0).getType());
		assertEquals("http://localhost:8080/", didDocument.getServices().get(0).getServiceEndpoint());
		assertEquals("http://localhost:8080/", ((List<Map<String, Object>>) didDocument.getJsonObject().get("service")).get(0).get("serviceEndpoint"));

		assertEquals("000000000", VerificationMethod.fromJsonObject(JsonLDDereferencer.findByIdInJsonLdObject(didDocument, didDocument.getAuthenticationVerificationMethodsDereferenced().get(0).getId(), null).getJsonObject()).getPublicKeyBase58());
		assertEquals("000000000", VerificationMethod.fromJsonObject(JsonLDDereferencer.findByIdInJsonLdObject(didDocument, didDocument.getAssertionMethodVerificationMethodsDereferenced().get(0).getId(), null).getJsonObject()).getPublicKeyBase58());
	}
}
