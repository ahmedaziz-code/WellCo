package com.springheroes.wellco.constant;

public class Authrority {
    public static final String[] USER_AUTHORITIES = { "user:read" };
    public static final String[] EVENTMANAGER_AUTHORITIES = { "user:read", "user:update" };
    public static final String[] FORMMANAGER_AUTHORITIES = { "user:read", "user:update" };
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update" };
    public static final String[] SUPER_ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update", "user:delete" };

}
