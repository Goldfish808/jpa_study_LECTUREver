package site.metacoding.white.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

// https://bamdule.tistory.com/233

@Component // IOC 컨테이너에 등록
public class SHA256 {

    public String encrypt(String text) throws RuntimeException {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());

            return bytesToHex(md.digest());

        } catch (Exception e) {
            throw new RuntimeException("비밀번호 해싱에 실패하였음");
        }

    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

}