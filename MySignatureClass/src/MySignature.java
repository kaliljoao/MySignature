import java.security.*;
import javax.crypto.*;

public class MySignature {

    protected static PrivateKey privateKey;
    protected static PublicKey publicKey;
    protected static byte[] text;
    protected static String algorithm;
    protected static Cipher cipher;
    protected static byte[] Digest;

    public MySignature(String _algorithm)
    {
        algorithm = _algorithm;
    }

    public static MySignature getInstance(String algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = Cipher.getInstance("RSA");
        return new MySignature(algorithm);
    }

    public static void initSign(PrivateKey _privateKey)
    {
        privateKey = _privateKey;
    }

    public static void update(byte[] PlainText)
    {
        text = PlainText;
    }

    public static byte[] sign() throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException {
        byte[] mySign = null;
        String[] algorithms = algorithm.split("With");
        MessageDigest messageDigest = null;
        switch (algorithms[0])
        {
            case "MD5":
                messageDigest = MessageDigest.getInstance("MD5");
                break;
            case "SHA1":
                messageDigest = MessageDigest.getInstance("SHA1");
                break;
            case "SHA256":
                messageDigest = MessageDigest.getInstance("SHA256");
                break;
            case "SHA512":
                messageDigest = MessageDigest.getInstance("SHA512");
                break;
        }
        messageDigest.update(text);
        Digest = messageDigest.digest();
        System.out.println(Digest.toString());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        mySign = cipher.doFinal(Digest);

        return mySign;
    }

    public static void initVerify(PublicKey _publicKey)
    {
        publicKey = _publicKey;
    }

    public static boolean verify(byte[] signature) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] newPlainText = cipher.doFinal(signature);

        System.out.println(newPlainText.toString());

        if(newPlainText.equals(Digest)) return true;
        else return false;
    }
}