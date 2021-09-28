package com.vh.springregistrationform.service;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
