package com.testing.notelist.service;

import com.testing.notelist.dao.NoteDao;
import com.testing.notelist.model.NoteModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteDao noteDao;

    public void setNoteDao( NoteDao noteDao ) {
        this.noteDao = noteDao;
    }

    @Override
    @Transactional
    public void addNote( NoteModel note ) {
        this.noteDao.addNote(note);
    }

    @Override
    @Transactional
    public void updateNote( NoteModel note ) {
        this.noteDao.updateNote(note);
    }

    @Override
    @Transactional
    public void removeNote( int id ) {
        this.noteDao.removeNote(id);
    }

    @Override
    @Transactional
    public NoteModel getNoteById( int id ) {
        return this.noteDao.getNoteById(id);
    }

    @Override
    @Transactional
    public List<NoteModel> listNotes() {
        return this.noteDao.listNotes();
    }
}
