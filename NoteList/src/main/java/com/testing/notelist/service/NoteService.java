package com.testing.notelist.service;

import com.testing.notelist.model.NoteModel;

import java.util.List;

public interface NoteService {
    public void addNote( NoteModel note);
    public void updateNote(NoteModel note);
    public void removeNote(int id);
    public NoteModel getNoteById(int id);
    public List<NoteModel> listNotes();
}
