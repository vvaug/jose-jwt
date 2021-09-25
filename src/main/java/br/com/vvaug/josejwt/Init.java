package br.com.vvaug.josejwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class Init implements CommandLineRunner {

	private final JsonWebEncryptionExample jsonWebEncryptionExample;
	
	@Override
	public void run(String... args) throws Exception {
		
		var jwe = jsonWebEncryptionExample.generateJsonWebEncryption();
		
		log.info("JWE: {}", jwe);
		
		var decryptedJwe = jsonWebEncryptionExample.decryptJsonWebEncryption(jwe);
		
		log.info("Decrypted JWE: {}", decryptedJwe);

	}

}
