package com.vh.springregistrationform.service;

import com.vh.springregistrationform.model.entity.User;

import java.util.List;

public interface RegFormService {
    void save(User note);

    User findByUsername(String username);

    void deleteById(Integer id);

    List<User> findAllNotes();
}