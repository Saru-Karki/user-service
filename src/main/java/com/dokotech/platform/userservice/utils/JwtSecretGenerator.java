package com.dokotech.platform.userservice.utils;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class to generate secure JWT secret keys.
 * Run this class to generate a new secret key.
 * 
 * Usage:
 * 1. Run this class: mvn exec:java -Dexec.mainClass="com.dokotech.platform.userservice.utils.JwtSecretGenerator"
 * 2. Copy the generated key
 * 3. Set as environment variable: export JWT_SECRET="<generated-key>"
 * 4. Or update application.properties with the generated key
 */
public class JwtSecretGenerator {

    public static void main(String[] args) {
        // Generate with default 512 bits (64 bytes)
        String secret512 = generateSecretKey(64);
        
        // Generate with 256 bits (32 bytes) - minimum recommended
        String secret256 = generateSecretKey(32);

        System.out.println("=".repeat(80));
        System.out.println("JWT SECRET KEY GENERATOR");
        System.out.println("=".repeat(80));
        System.out.println();
        
        System.out.println("🔐 RECOMMENDED (512 bits / 64 bytes):");
        System.out.println("-".repeat(80));
        System.out.println(secret512);
        System.out.println();
        System.out.println("Length: " + secret512.length() + " characters");
        System.out.println();
        System.out.println("For application.properties:");
        System.out.println("jwt.secret=${JWT_SECRET:" + secret512 + "}");
        System.out.println();
        System.out.println("For environment variable:");
        System.out.println("export JWT_SECRET=\"" + secret512 + "\"");
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println();
        
        System.out.println("🔑 MINIMUM (256 bits / 32 bytes):");
        System.out.println("-".repeat(80));
        System.out.println(secret256);
        System.out.println();
        System.out.println("Length: " + secret256.length() + " characters");
        System.out.println();
        System.out.println("For application.properties:");
        System.out.println("jwt.secret=${JWT_SECRET:" + secret256 + "}");
        System.out.println();
        System.out.println("For environment variable:");
        System.out.println("export JWT_SECRET=\"" + secret256 + "\"");
        System.out.println();
        System.out.println("=".repeat(80));
        System.out.println();
        System.out.println("⚠️  SECURITY NOTES:");
        System.out.println("   - NEVER commit secret keys to version control");
        System.out.println("   - Use environment variables in production");
        System.out.println("   - Rotate keys periodically");
        System.out.println("   - Keep keys secure and confidential");
        System.out.println("=".repeat(80));
    }

    /**
     * Generate a cryptographically secure random key.
     * 
     * @param numBytes Number of random bytes (32 for 256 bits, 64 for 512 bits)
     * @return Base64 encoded secret key
     */
    public static String generateSecretKey(int numBytes) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}

