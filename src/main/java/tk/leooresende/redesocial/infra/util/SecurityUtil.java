package tk.leooresende.redesocial.infra.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.google.gson.Gson;

import tk.leooresende.redesocial.infra.advice.exception.GenericaException;
import tk.leooresende.redesocial.infra.dto.v1.CryptoRequestForm;

public class SecurityUtil {
	private static final String SECRET_KEY_OUT = "KXI4RpZ7";
	private static final String SECRET_KEY_IN = "sfOxW4F";

	public static String descriptografarAESComAKey(String payload) {
		try {
			byte[] cipherData = Base64.getDecoder().decode(payload);
			byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

			MessageDigest md5 = MessageDigest.getInstance("MD5");
			final byte[][] keyAndIV = SecurityUtil.GenerateKeyAndIVToDescript(32, 16, 1, saltData, SECRET_KEY_OUT.getBytes(StandardCharsets.UTF_8),
					md5);
			SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
			IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

			byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
			Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
			byte[] decryptedData = aesCBC.doFinal(encrypted);
			String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

			return decryptedText;
		} catch (Exception ex) {
			throw new GenericaException("Parametros invalidos", HttpStatus.BAD_REQUEST);
		}
	}

	private static byte[][] GenerateKeyAndIVToDescript(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password,
			MessageDigest md) {

		int digestLength = md.getDigestLength();
		int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
		byte[] generatedData = new byte[requiredLength];
		int generatedLength = 0;

		try {
			md.reset();

			// Repeat process until sufficient data has been generated
			while (generatedLength < keyLength + ivLength) {

				// Digest data (last digest if available, password data, salt if available)
				if (generatedLength > 0)
					md.update(generatedData, generatedLength - digestLength, digestLength);
				md.update(password);
				if (salt != null)
					md.update(salt, 0, 8);
				md.digest(generatedData, generatedLength, digestLength);

				// additional rounds
				for (int i = 1; i < iterations; i++) {
					md.update(generatedData, generatedLength, digestLength);
					md.digest(generatedData, generatedLength, digestLength);
				}

				generatedLength += digestLength;
			}

			// Copy key and IV into separate byte arrays
			byte[][] result = new byte[2][];
			result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
			if (ivLength > 0)
				result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

			return result;

		} catch (DigestException e) {
			throw new RuntimeException(e);

		} finally {
			// Clean out temporary data
			Arrays.fill(generatedData, (byte) 0);
		}
	}
	
	
	public static String criptografar(String payload) {
        try {
            String password = SecurityUtil.SECRET_KEY_IN;
            SecureRandom sr = new SecureRandom();
            byte[] salt = new byte[8];
            sr.nextBytes(salt);
            final byte[][] keyAndIV = SecurityUtil.generateKeyAndIVToEncrypt(32, 16, 1, salt, password.getBytes(StandardCharsets.UTF_8),
                    MessageDigest.getInstance("MD5"));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyAndIV[0], "AES"), new IvParameterSpec(keyAndIV[1]));
            byte[] encryptedData = cipher.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            byte[] prefixAndSaltAndEncryptedData = new byte[16 + encryptedData.length];
            // Copy prefix (0-th to 7-th bytes)
            System.arraycopy("Salted__".getBytes(StandardCharsets.UTF_8), 0, prefixAndSaltAndEncryptedData, 0, 8);
            // Copy salt (8-th to 15-th bytes)
            System.arraycopy(salt, 0, prefixAndSaltAndEncryptedData, 8, 8);
            // Copy encrypted data (16-th byte and onwards)
            System.arraycopy(encryptedData, 0, prefixAndSaltAndEncryptedData, 16, encryptedData.length);
            return Base64.getEncoder().encodeToString(prefixAndSaltAndEncryptedData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	public static byte[][] generateKeyAndIVToEncrypt(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

        int digestLength = md.getDigestLength();
        int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
        byte[] generatedData = new byte[requiredLength];
        int generatedLength = 0;

        try {
            md.reset();

            // Repeat process until sufficient data has been generated
            while (generatedLength < keyLength + ivLength) {

                // Digest data (last digest if available, password data, salt if available)
                if (generatedLength > 0)
                    md.update(generatedData, generatedLength - digestLength, digestLength);
                md.update(password);
                if (salt != null)
                    md.update(salt, 0, 8);
                md.digest(generatedData, generatedLength, digestLength);

                // additional rounds
                for (int i = 1; i < iterations; i++) {
                    md.update(generatedData, generatedLength, digestLength);
                    md.digest(generatedData, generatedLength, digestLength);
                }

                generatedLength += digestLength;
            }

            // Copy key and IV into separate byte arrays
            byte[][] result = new byte[2][];
            result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
            if (ivLength > 0)
                result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

            return result;

        } catch (DigestException e) {
            throw new RuntimeException(e);

        } finally {
            // Clean out temporary data
            Arrays.fill(generatedData, (byte)0);
        }
    }
	
	public static String pegarEDescriptografarRequisicao(HttpServletRequest request) {
		CryptoRequestForm corpoDaRequisicao = SecurityUtil.pegarCorpoDaRequisicao(request);
		return SecurityUtil.descriptografarAESComAKey(corpoDaRequisicao.getPayload());
	}
	
	private static CryptoRequestForm pegarCorpoDaRequisicao(HttpServletRequest request) {
			String reqJsonCrypt = SecurityUtil.pegarCorpoDaRequisicaoEmString(request);
			Gson gson = new Gson();
			return gson.fromJson(reqJsonCrypt, CryptoRequestForm.class);
	}

	private static String pegarCorpoDaRequisicaoEmString(HttpServletRequest request) {
		try {
			return request.getReader().lines().collect(Collectors.joining());
		} catch (IOException e) {
			throw new GenericaException("Dados invalidos", HttpStatus.BAD_REQUEST);
		}
	}

	public static String pegarParametroDescriptografado(String nomeDoParametro, HttpServletRequest request) {
		String parametroValor = request.getParameter(nomeDoParametro);
		return SecurityUtil.descriptografarAESComAKey(parametroValor);
	}

	public static MultipartFile pegarImagemDaRequisicao(HttpServletRequest request) {
		MultipartRequest reqPart = new StandardMultipartHttpServletRequest(request);
		return reqPart.getFile("file");
	}

}
