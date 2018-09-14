package com.testing.notelist.dao;

import com.testing.notelist.model.NoteModel;

import java.util.List;

public interface NoteDao {
    public void addNote( NoteModel note);
    public void updateNote(NoteModel note);
    public void removeNote(int id);
    public NoteModel getNoteById(int id);
    public List<NoteModel> listNotes();
}
