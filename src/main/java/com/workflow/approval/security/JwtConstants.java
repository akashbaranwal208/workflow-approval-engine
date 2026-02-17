package com.workflow.approval.security;

public class JwtConstants {

    public static final String SECRET_KEY =
            "thisisaverylongsecretkeyforjwtauthentication12345";

    public static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
}
