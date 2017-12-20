package com.znap.lmr.lmr_znap;

import android.util.Log;
import android.widget.TextView;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Zava on 16.12.2017.
 */

public class AESEncryption extends SignInActivity {
    static final String TAG = "SymmetricAlgorithmAES";
    String theTestText = "This is just a simple test";
    public SignInActivity activity;

    public AESEncryption( SignInActivity _signInActivity){
        this.activity = _signInActivity;
    }
    public  void decrypt() {
        TextView originalField = (TextView) this.activity.findViewById(R.id.originalField);
        //originalField.setText("\n[ORIGINAL]:\n" + theTestText + "\n");
        // Set up secret key spec for 128-bit AES encryption and decryption
        SecretKeySpec sks = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128, sr);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (Exception e) {
            Log.e(TAG, "AES secret key spec error");
        }

        // Encode the original data with AES
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, sks);
            encodedBytes = c.doFinal(theTestText.getBytes());
        } catch (Exception e) {
            Log.e(TAG, "AES encryption error");
        }
        TextView encodedField = (TextView)this.activity.findViewById(R.id.encodedField);
        //encodedField.setText("[ENCODED]:\n" +Base64.encodeToString(encodedBytes, Base64.DEFAULT) + "\n");

        // Decode the encoded data with AES
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, sks);
            decodedBytes = c.doFinal(encodedBytes);
        } catch (Exception e) {
            Log.e(TAG, "AES decryption error");
        }
        TextView decodedField = (TextView)this.activity.findViewById(R.id.decodedField);
        //decodedField.setText("[DECODED]:\n" + new String(decodedBytes) + "\n");
    }
}
