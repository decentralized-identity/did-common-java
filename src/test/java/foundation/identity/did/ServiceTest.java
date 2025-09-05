package foundation.identity.did;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

	@Test
	public void testService() throws Exception {

		Service service = create();
		test(service);
	}

	@Test
	public void testServiceFromJson() throws Exception {

		Service service = Service.fromJson(create().toJson());
		test(service);
	}

	@Test
	public void testServiceFromMap() throws Exception {

		Service service = Service.fromMap(create().toMap());
		test(service);
	}

	private Service create() {

		return Service.builder()
				.type("DIDCommService")
				.serviceEndpoint(URI.create("http://localhost:8080/"))
				.build();
	}

	private void test(Service service) {

		assertEquals("DIDCommService", service.getType());
		assertEquals(URI.create("http://localhost:8080/"), service.getServiceEndpoint());
	}
}
