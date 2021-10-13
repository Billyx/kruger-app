package com.kruger.inventario.security;

public class Constants {

    // SPRING SECURITY
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer";
    // JWT
    public static final String ISSUER_INFO = "KRUGER";
    public static final String SUPER_SECRET_KEY = "5b4dd0N.";
    public static final long TOKEN_EXPIRATION_TIME = 20; // minutes
    public static final long TOKEN_REFRESH_EXPIRATION_TIME = 180;
}
