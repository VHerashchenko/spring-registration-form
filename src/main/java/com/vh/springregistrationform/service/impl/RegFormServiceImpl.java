package com.vh.springregistrationform.service.impl;

import com.vh.springregistrationform.entity.Note;
import com.vh.springregistrationform.repository.NoteRepository;
import com.vh.springregistrationform.service.RegFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegFormServiceImpl implements RegFormService {

    private final NoteRepository noteRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Note note) {
//        note.setPassword(bCryptPasswordEncoder.encode(note.getPassword()));
        noteRepository.save(note);
    }

    @Override
    public Note findByUsername(String username) {
        return noteRepository.findByUsername(username);
    }

    @Override
    public List<Note> findAllNotes() {
        return noteRepository.findAll();
    }
}