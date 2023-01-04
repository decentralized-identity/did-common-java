package foundation.identity.did;

import foundation.identity.jsonld.JsonLDDereferencer;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DIDDocumentTest {

	@Test
	public void testDIDDocument() throws Exception {

		// DID DOCUMENT verificationMethods

		List<VerificationMethod> verificationMethods = new ArrayList<>();

		VerificationMethod verificationMethod = VerificationMethod.builder()
				.id(URI.create("did:ex:123#key-1"))
				.types(Arrays.asList("Ed25519VerificationKey2018"))
				.publicKeyBase58("000000000")
				.build();

		verificationMethods.add(verificationMethod);

		// DID DOCUMENT services

		List<Service> services = new ArrayList<Service>();

		Service service = Service.builder()
				.type("DIDCommService")
				.serviceEndpoint("http://localhost:8080/")
				.build();

		services.add(service);

		// create DID DOCUMENT

		DIDDocument didDocument = DIDDocument.builder()
				.id(URI.create("did:ex:123"))
				.controller(URI.create("did:ex:456"))
				.alsoKnownAs(URI.create("did:ex:789"))
				.verificationMethods(verificationMethods)
				.authenticationVerificationMethods(Collections.singletonList(VerificationMethod.builder().id(verificationMethod.getId()).build()))
				.assertionMethodVerificationMethods(Collections.singletonList(VerificationMethod.builder().id(verificationMethod.getId()).build()))
				.services(services)
				.build();

		// tests

		assertEquals(URI.create("did:ex:123"), didDocument.getId());
		assertEquals(didDocument.getControllers().get(0), URI.create("did:ex:456"));
		assertEquals(didDocument.getAlsoKnownAses().get(0), URI.create("did:ex:789"));
		assertEquals("Ed25519VerificationKey2018", didDocument.getVerificationMethods().get(0).getType());
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
