package foundation.identity.did;

import foundation.identity.did.parser.ParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DIDTest {

	@Test
	public void testDID() throws Exception {

		DID did = DID.fromString("did:ex:123");
		assertEquals("did", did.toUri().getScheme());
		assertEquals("did:ex:123", did.getDidString());
		assertEquals("ex", did.getMethodName());
		assertEquals("123", did.getMethodSpecificId());
	}

	@Test
	public void testInvalidDID() throws Exception {

		Assertions.assertThrows(ParserException.class, () -> {
			DID.fromString("did:ex");
		});
	}
}
