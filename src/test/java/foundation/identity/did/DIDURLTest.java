package foundation.identity.did;

import foundation.identity.did.parser.ParserException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

public class DIDURLTest {

	@Test
	public void testDIDURL() throws Exception {

		DIDURL didUrl = DIDURL.fromString("did:ex:123/my/path?ab=1&cd=2#frag");
		assertFalse(didUrl.isBareDid());
		assertEquals("did", didUrl.toUri().getScheme());
		assertEquals("did", didUrl.getDid().toUri().getScheme());
		assertEquals("did:ex:123/my/path?ab=1&cd=2#frag", didUrl.getDidUrlString());
		assertEquals("did:ex:123", didUrl.getDid().getDidString());
		assertEquals("ex", didUrl.getDid().getMethodName());
		assertEquals("123", didUrl.getDid().getMethodSpecificId());
		assertEquals("/my/path", didUrl.getPath());
		assertEquals("ab=1&cd=2", didUrl.getQuery());
		assertEquals("1", didUrl.getParameters().get("ab"));
		assertEquals("2", didUrl.getParameters().get("cd"));
		assertNull(didUrl.getParameters().get("ef"));
		assertEquals("frag", didUrl.getFragment());
	}

	@Test
	public void testBareDIDURL() throws Exception {

		DIDURL didUrl = DIDURL.fromString("did:ex:123");
		assertTrue(didUrl.isBareDid());
		assertEquals("did", didUrl.toUri().getScheme());
		assertEquals("did", didUrl.getDid().toUri().getScheme());
		assertEquals("did:ex:123", didUrl.getDidUrlString());
		assertEquals("did:ex:123", didUrl.getDid().getDidString());
		assertEquals("ex", didUrl.getDid().getMethodName());
		assertEquals("123", didUrl.getDid().getMethodSpecificId());
		assertNull(didUrl.getPath());
		assertNull(didUrl.getQuery());
		assertNull(didUrl.getParameters());
		assertNull(didUrl.getFragment());
	}

	@Test
	public void testInvalidDIDURL() throws Exception {

		Assertions.assertThrows(ParserException.class, () -> {
			DIDURL.fromString("did:ex/my/path?ab=1&cd=2#frag");
		});
	}

	@Test
	public void testUris() throws Exception {

		DIDURL didUrl1 = DIDURL.fromString("did:ex:123/my/path?ab=1&cd=2#frag");
		assertEquals(URI.create("/my/path?ab=1&cd=2#frag"), didUrl1.getUriWithoutDid());
		assertEquals(URI.create("did:ex:123/my/path?ab=1&cd=2"), didUrl1.getUriWithoutFragment());

		DIDURL didUrl2 = DIDURL.fromString("did:ex:123");
		assertNull(didUrl2.getUriWithoutDid());
		assertEquals(URI.create("did:ex:123"), didUrl2.getUriWithoutFragment());
	}
}
