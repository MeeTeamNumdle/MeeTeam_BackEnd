package synk.meeteam.global.util;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Encryption {
    private static String ALGORITHM;
    private static String SECRET_KEY;

    @Value("${encryption.algorithm}")
    private void setALGORITHM(String algorithm){
        ALGORITHM = algorithm;
    }

    @Value("${encryption.secretKey}")
    private void setSECRETKEY(String secretkey){
        SECRET_KEY = secretkey;
    }

    public static String encryptLong(Long value) {
        if (value == null) {
            return null;
        }

        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(String.valueOf(value).getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Long decryptLong(String encryptedValue) {
        if (encryptedValue == null) {
            return null;
        }

        try {

            byte[] decodedValue = Base64.getUrlDecoder().decode(encryptedValue);
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decrypted = cipher.doFinal(decodedValue);
            return Long.parseLong(new String(decrypted));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
