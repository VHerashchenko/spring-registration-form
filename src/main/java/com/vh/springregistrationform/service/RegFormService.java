package com.vh.springregistrationform.service;

import com.vh.springregistrationform.entity.User;

import java.util.List;

public interface RegFormService {
    void save(User note);

    User findByUsername(String username);

    List<User> findAllNotes();
}