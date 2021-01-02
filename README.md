![DIF Logo](https://raw.githubusercontent.com/decentralized-identity/universal-resolver/master/docs/logo-dif.png)

# did-common-java

## Information

This is a work-in-progress implementation of the DID Core specification:

 - [Decentralized Identifiers v1.0](https://w3c.github.io/did-core/)

Not ready for production use! Use at your own risk! Pull requests welcome.

## Maven

Build:

	mvn clean install

Dependency:

	<repositories>
		<repository>
			<id>danubetech-maven-public</id>
			<url>https://repo.danubetech.com/repository/maven-public/</url>
		</repository>
	</repositories>

	<dependency>
		<groupId>decentralized-identity</groupId>
		<artifactId>did-common-java</artifactId>
		<version>0.3-SNAPSHOT</version>
	</dependency>

## Example

Example code:

    URI did = URI.create("did:ex:1234");

    Service service = Service.builder()
            .type("ServiceEndpointProxyService")
            .serviceEndpoint("https://myservice.com/myendpoint")
            .build();

    VerificationMethod verificationMethod = VerificationMethod.builder()
            .id(URI.create(did + "#key-1"))
            .type("Ed25519VerificationKey2018")
            .publicKeyBase58("FyfKP2HvTKqDZQzvyL38yXH7bExmwofxHf2NR5BrcGf1")
            .build();

    DIDDocument diddoc = DIDDocument.builder()
            .id(did)
            .service(service)
            .verificationMethod(verificationMethod)
            .build();

    System.out.println(diddoc.toJson(true));

Example DID document:

    {
      "@context" : "https://www.w3.org/ns/did/v1",
      "id" : "did:ex:1234",
      "verificationMethod" : {
        "type" : "Ed25519VerificationKey2018",
        "id" : "did:ex:1234#key-1",
        "publicKeyBase58" : "FyfKP2HvTKqDZQzvyL38yXH7bExmwofxHf2NR5BrcGf1"
      },
      "service" : {
        "type" : "ServiceEndpointProxyService",
        "serviceEndpoint" : "https://myservice.com/myendpoint"
      }
    }

## About

<img align="left" src="https://raw.githubusercontent.com/decentralized-identity/universal-resolver/master/docs/logo-dif.png" width="115">

Decentralized Identity Foundation - http://identity.foundation/
