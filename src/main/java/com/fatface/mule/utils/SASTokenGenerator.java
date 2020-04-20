package com.fatface.mule.utils;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Locale;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class SASTokenGenerator {
	
	//private static String generateSASToken(String uri, String deviceKey, String policyName, Long expiry) {
	public static String generateSASToken(final String policyName, final String sharedAccessKey, final String resource, final int tokenLiveInNoOfDays) {
       String token = null;
       try {

	        // new code block
	        final String utf8Encoding = StandardCharsets.UTF_8.name();
			String expiresOn = Long.toString(Instant.now().getEpochSecond() + Duration.ofDays(tokenLiveInNoOfDays).getSeconds() );
			String audienceUri = URLEncoder.encode(resource, utf8Encoding);
			String secretToSign =  audienceUri + "\n" + expiresOn;
	
			final String hashAlgorithm = "HMACSHA256"; 
			Mac hmac = Mac.getInstance(hashAlgorithm);
			byte[] sasKeyBytes = sharedAccessKey.getBytes(utf8Encoding);
			SecretKeySpec finalKey = new SecretKeySpec(sasKeyBytes, hashAlgorithm);
			hmac.init(finalKey);
			byte[] signatureBytes = hmac.doFinal(secretToSign.getBytes(utf8Encoding));
			String signature = Base64.getEncoder().encodeToString(signatureBytes);
			
			token = String.format(Locale.UK, "SharedAccessSignature sr=%s&sig=%s&se=%s&skn=%s",
					audienceUri,
					URLEncoder.encode(signature, utf8Encoding),
					URLEncoder.encode(expiresOn, utf8Encoding),
					URLEncoder.encode(policyName, utf8Encoding));
        
        //End:
       } catch (Exception e) {
          e.printStackTrace();
       }
        return token;
    }

}
