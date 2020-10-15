![DIF Logo](https://raw.githubusercontent.com/decentralized-identity/universal-resolver/master/docs/logo-dif.png)

# did-common-java

## Information

This is a work-in-progress implementation of the DID Core specification:

 - [Decentralized Identifiers v1.0](https://w3c.github.io/did-core/)

Work-in-progress implementation of DIDs and DID documents! Use at your own risk! Pull requests welcome.

## Maven

Build:

	mvn clean install

Dependency:

	<dependency>
        <groupId>decentralized-identity</groupId>
        <artifactId>did-common-java</artifactId>
        <version>0.2-SNAPSHOT</version>
	</dependency>

## Example

Example code:

    URI did = URI.create("did:ex:1234");

    Service service = Service.builder()
            .type("XdiService")
            .serviceEndpoint("https://myservice.com/myxdi")
            .build();

    VerificationMethod verificationMethod = VerificationMethod.builder()
            .id(URI.create(did + "#key-1"))
            .type("Ed25519VerificationKey")
            .publicKeyBase58("FyfKP2HvTKqDZQzvyL38yXH7bExmwofxHf2NR5BrcGf1")
            .build();

    DIDDocument diddoc = DIDDocument.builder()
            .id(did)
            .service(service)
            .verificationMethod(verificationMethod)
            .build();

    ((ConfigurableDocumentLoader) diddoc.getDocumentLoader()).setEnableLocalCache(true);
    ((ConfigurableDocumentLoader) diddoc.getDocumentLoader()).setEnableHttps(false);

    System.out.println(diddoc.toJson(true));

## About

<img align="left" src="https://raw.githubusercontent.com/decentralized-identity/universal-resolver/master/docs/logo-dif.png" width="115">

Decentralized Identity Foundation - http://identity.foundation/
