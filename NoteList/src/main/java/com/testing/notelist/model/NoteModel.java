package com.testing.notelist.model;

import javax.persistence.*;



@Entity
@Table(name = "notes")
public class NoteModel {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NOTE_HEADER")
    private String noteHeader;
    @Column(name = "NOTE_TEXT")
    private String noteText;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getNoteHeader() {
        return noteHeader;
    }

    public void setNoteHeader( String noteHeader ) {
        this.noteHeader = noteHeader;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText( String noteText ) {
        this.noteText = noteText;
    }


    @Override
    public String toString() {
        return "NoteModel{" +
                "id=" + id +
                ", noteHeader='" + noteHeader + '\'' +
                ", noteText='" + noteText + '\'' +
                '}';
    }
}
