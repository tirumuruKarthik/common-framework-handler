package com.fatface.mule.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

public class EmarsysPasswordDigestGenerator {

	public EmarsysPasswordDigestGenerator() {
		super();
		// TODO Auto-generated constructor stub
	}

	final protected static char[] hexArray = "0123456789abcdef".toCharArray();
	// private String environment;
	private String apiUsername;
	private String apiSecretKey;

	/*
	 * public static void main(String[] args) throws Exception {
	 * 
	 * Scanner reader = new Scanner(System.in);
	 * 
	 * try {
	 * 
	 * // System.out.println("Enter username: "); String userName = "fatface003";
	 * //reader.next(); // System.out.println("Enter secret key: "); String key =
	 * "CUGGzcMuxdzwMuWH8Fpf"; //reader.next(); EmarsysPasswordDigestGenerator mdg =
	 * new EmarsysPasswordDigestGenerator(userName, key); String sig =
	 * mdg.getSignature(); System.out.println("Your signature is: " + sig); }
	 * finally { reader.close(); }
	 * 
	 * }
	 */

	/*
	 * public EmarsysPasswordDigestGenerator(String apiUsername, String
	 * apiSecretKey) { super();
	 * 
	 * this.apiUsername = apiUsername; this.apiSecretKey = apiSecretKey; }
	 */

	public String getSignature(String apiUsername, String apiSecretKey) {
		this.apiUsername = apiUsername;
		this.apiSecretKey = apiSecretKey;
		String timestamp = getUTCTimestamp();
		String nonce = getNonce();
		String digest = getPasswordDigest(nonce, timestamp);

		return String.format("UsernameToken Username=\"%s\", PasswordDigest=\"%s\", Nonce=\"%s\", Created=\"%s\"",
				apiUsername, digest, nonce, timestamp);
	}

	private String getUTCTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC+1"));

		return sdf.format(new Date());
	}

	private String getNonce() {
		byte[] nonceBytes = new byte[16];
		new Random().nextBytes(nonceBytes);

		return bytesToHex(nonceBytes);
	}

	private String getPasswordDigest(String nonce, String timestamp) {
		String digest = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.reset();
			String hashedString = String.format("%s%s%s", nonce, timestamp, apiSecretKey);
			messageDigest.update(hashedString.getBytes("UTF-8"));
			String sha1Sum = bytesToHex(messageDigest.digest());

			digest = DatatypeConverter.printBase64Binary(sha1Sum.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("No SHA-1 algorithm was found!");
		} catch (UnsupportedEncodingException ex) {
			System.out.println("Cannot use UTF-8 encoding.");
		}

		return digest;
	}

	private String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

}
