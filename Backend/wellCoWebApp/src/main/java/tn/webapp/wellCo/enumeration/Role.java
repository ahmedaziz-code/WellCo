package com.springheroes.wellco.enumeration;

import static com.springheroes.wellco.constant.Authrority.*;

public enum Role {
    ROLE_USER(USER_AUTHORITIES),
    ROLE_EVENTMANAGER( EVENTMANAGER_AUTHORITIES ),
    ROLE_FORMMANAGER(FORMMANAGER_AUTHORITIES ),
    ROLE_ADMIN(ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);
    private String[] authorities;

    Role(String... authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }

}
