package com.vh.springregistrationform.repository;

import com.vh.springregistrationform.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    Note findByUsername(String username);

    List<Note> findAll();
}
