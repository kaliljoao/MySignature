import java.security.*;
import javax.crypto.*;


public class MySignatureTest {
    public static void main(String[] args) throws Exception {

//        if (args.length != 1) {
//            System.err.println("Usage: java MySignature text");
//            System.exit(1);
//        }
        String text = "Isso eh um teste!";
        byte[] plainText = text.getBytes("UTF8");


        // gera o par de chaves RSA
        System.out.println("\nStart generating RSA key");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair key = keyGen.generateKeyPair();
        System.out.println("Finish generating RSA key");


        System.out.println("Starting generating signature");
        MySignature sig = MySignature.getInstance("SHA1WithRSA");
        sig.initSign(key.getPrivate());
        sig.update(plainText);
        byte[] signature = sig.sign();
        System.out.println("Finish generating signature");


        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < signature.length; i++) {
            String hex = Integer.toHexString(0x0100 + (signature[i] & 0x00FF)).substring(1);
            buf.append((hex.length() < 2 ? "0" : "") + hex);
        }

        // imprime o signature em hexadecimal
        System.out.println( buf.toString() );


        sig.initVerify(key.getPublic());
        if (sig.verify(signature)) {
            System.out.println( "Signature verified" );
        } else System.out.println( "Signature failed" );
    }
}
