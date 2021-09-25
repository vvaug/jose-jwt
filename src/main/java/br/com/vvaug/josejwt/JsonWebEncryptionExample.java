package br.com.vvaug.josejwt;

import java.security.Key;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.keys.AesKey;
import org.jose4j.lang.ByteUtil;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component

/*
 * Victor Augusto (vvaug)
 */

public class JsonWebEncryptionExample {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private static final String SERIALIZATION_ERROR = "Error while converting user bean to Json payload";
	
	private static final String JOSE_SERIALIZED_JWE_ERROR = "Error while getting serialized JWE";
	
	private static final String JOSE_DECRYPT_JWE_ERROR = "Error while decrypting JWE";

	private static final Key key = new AesKey(ByteUtil.randomBytes(16));

	
	public String generateJsonWebEncryption() {
		
		
		JsonWebEncryption jwe = new JsonWebEncryption();
		
		jwe.setPayload(getPayload());
		
		jwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.A128KW);
		
		jwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
		
		jwe.setKey(key);
		
		try {
			return jwe.getCompactSerialization();
		} catch (JoseException e) {
			log.error(JOSE_SERIALIZED_JWE_ERROR);
			throw new RuntimeException(JOSE_SERIALIZED_JWE_ERROR, e);
		}
	}
	
	public String decryptJsonWebEncryption(String encryptedJwe) {
				
		JsonWebEncryption jwe = new JsonWebEncryption();
		
		jwe.setAlgorithmConstraints(new AlgorithmConstraints(ConstraintType.PERMIT, 
		        KeyManagementAlgorithmIdentifiers.A128KW));
		
		jwe.setContentEncryptionAlgorithmConstraints(new AlgorithmConstraints(ConstraintType.PERMIT, 
		        ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256));
		
		jwe.setKey(key);
		
		try {
			 jwe.setCompactSerialization(encryptedJwe);
			 return jwe.getPayload();
		} catch (JoseException e) {
			log.error(JOSE_DECRYPT_JWE_ERROR);
			throw new RuntimeException(JOSE_DECRYPT_JWE_ERROR, e);
		}
	}
	
	protected String getPayload() {
		try {
			return objectMapper.writeValueAsString(
					UserFactory.getUser());
		} catch (JsonProcessingException e) {
			log.error(SERIALIZATION_ERROR);
			throw new RuntimeException(SERIALIZATION_ERROR, e);
		}
	}
}
