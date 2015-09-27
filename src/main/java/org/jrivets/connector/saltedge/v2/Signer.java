package org.jrivets.connector.saltedge.v2;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.encoders.Base64;

final class Signer {

    private final PEMKeyPair privateKey;
    
    private final Signature signature;
    
    Signer(String prvtKeyFileName) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        privateKey = readPrivateKey(prvtKeyFileName);
        signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(new JcaPEMKeyConverter().getPrivateKey(privateKey.getPrivateKeyInfo()));
    }
    
    String sign(String data) throws SignatureException, PEMException {
        byte[] bytes = data.getBytes();
        byte[] shaSignature = sign(bytes);
        return Base64.toBase64String(shaSignature);
    }
    
    byte[] sign(byte[] bytes) throws SignatureException, PEMException {
        signature.update(bytes);
        return signature.sign();
    }
    
    private PEMKeyPair readPrivateKey(String prvtKeyFileName) throws IOException {
        FileReader fileReader = new FileReader(new File(prvtKeyFileName));
        try (PEMParser parser = new PEMParser(fileReader)) {
            return (PEMKeyPair) parser.readObject();
        }

    }
    
}
