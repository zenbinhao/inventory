package com.inventory.rayli.common.service;


import com.inventory.rayli.common.bo.SessionUser;

public interface AuthenticationService {

    String getAuthToken();

    SessionUser getSessionUser();

    SessionUser getSessionUserByIgnoreFilter(String var1);

}
