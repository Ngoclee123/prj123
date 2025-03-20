/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ultils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 *
 * @author ASUS
 */
public class PasswordHasher {
   public static String toSHA1(String str) {
    String salt = "sadjfehefnjdsf=dsfjef";
    str += salt;
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hashedBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
        
        // Convert byte array to hex
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

    public static void main(String[] args) {
        System.out.println(toSHA1("12345"));
    }
}
