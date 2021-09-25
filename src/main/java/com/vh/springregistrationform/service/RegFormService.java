package com.vh.springregistrationform.service;

import com.vh.springregistrationform.entity.Note;

import java.util.List;

public interface RegFormService {
    void save(Note note);

    Note findByUsername(String username);

    List<Note> findAllNotes();
}