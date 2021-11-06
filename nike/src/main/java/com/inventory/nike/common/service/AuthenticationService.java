package com.inventory.nike.common.service;


import com.inventory.nike.common.bo.SessionUser;

public interface AuthenticationService {

    String getAuthToken();

    SessionUser getSessionUser();

    SessionUser getSessionUserByIgnoreFilter(String var1);

}
