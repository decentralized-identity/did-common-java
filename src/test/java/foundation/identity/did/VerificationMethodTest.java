package foundation.identity.did;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VerificationMethodTest {

	@Test
	public void testVerificationMethod() throws Exception {

		VerificationMethod verificationMethod = create();
		test(verificationMethod);
	}

	@Test
	public void testVerificationMethodFromJson() throws Exception {

		VerificationMethod verificationMethod = VerificationMethod.fromJson(create().toJson());
		test(verificationMethod);
	}

	@Test
	public void testVerificationMethodFromMap() throws Exception {

		VerificationMethod verificationMethod = VerificationMethod.fromMap(create().toMap());
		test(verificationMethod);
	}

	private VerificationMethod create() {

		return VerificationMethod.builder()
				.id(URI.create("did:ex:123#key-1"))
				.types(List.of("Ed25519VerificationKey2018"))
				.publicKeyBase58("000000000")
				.build();
	}

	private void test(VerificationMethod verificationMethod) {

		assertEquals(URI.create("did:ex:123#key-1"), verificationMethod.getId());
		assertEquals("Ed25519VerificationKey2018", verificationMethod.getType());
		assertEquals("000000000", verificationMethod.getPublicKeyBase58());
	}
}
