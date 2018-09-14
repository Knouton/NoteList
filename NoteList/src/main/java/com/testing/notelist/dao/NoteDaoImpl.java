package com.testing.notelist.dao;

import com.testing.notelist.model.NoteModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class NoteDaoImpl implements NoteDao {
    private static final Logger logger = LoggerFactory.getLogger(NoteDaoImpl.class);

    //Создаём соединение с БД
    private SessionFactory sessionFactory;

    public void setSessionFactory( SessionFactory sessionFactory ) {
        this.sessionFactory = sessionFactory;
    }
    private Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public void addNote( NoteModel note ) {
        Session session = getSession();
        session.persist(note);
        logger.info("Note successfully added. Note details: " + note);
    }

    @Override
    public void updateNote( NoteModel note ) {
        Session session = getSession();
        session.update(note);
        logger.info("Note successfully update. Note details: " + note);
    }

    @Override
    public void removeNote( int id ) {
        Session session = getSession();
        NoteModel note = (NoteModel) session.load(NoteModel.class, new Integer(id));

        if(note != null) {
            session.delete(note);
            logger.info("Note successfully delete. Note details: " + note);
        } else {
            logger.info("Note unsuccessfully delete.");
        }

    }

    @Override
    public NoteModel getNoteById( int id ) {
        Session session = getSession();
        NoteModel note = (NoteModel) session.load(NoteModel.class, new Integer(id));
        logger.info("Note successfully loaded. Note details: " + note);

        return note;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<NoteModel> listNotes() {
        Session session = getSession();
        List<NoteModel> noteList = session.createQuery("from NoteModel").list();

        logger.info("List successfully loaded");
        return noteList;
    }



}
