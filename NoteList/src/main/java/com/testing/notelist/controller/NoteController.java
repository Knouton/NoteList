package com.testing.notelist.controller;


import com.testing.notelist.model.NoteModel;
import com.testing.notelist.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class NoteController {
    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);
    private NoteService noteService;

    @Autowired(required = true)
    @Qualifier(value = "noteService")
    public void setNoteService(NoteService noteService){
        this.noteService = noteService;
    }

    @RequestMapping(value = "notes", method = RequestMethod.GET)
    public String listNotes( Model model){
        List<NoteModel> listOfNotes = cutNoteTextForListNotesOnWebPage(this.noteService.listNotes());
        model.addAttribute("note", new NoteModel());
        model.addAttribute("listNotes", listOfNotes);

        return "notes";
    }

    //В случае если заголовок не указан, а текст Заметки слишком длинный,
    // строка автоматически обрезается до 100 символов, для отображения на страице с заметками
    private List<NoteModel> cutNoteTextForListNotesOnWebPage(List<NoteModel> noteList){
        List<NoteModel> listOfNotes = new ArrayList<NoteModel>();

        for (NoteModel note: noteList
                ) {
            if(note.getNoteHeader() == null || note.getNoteHeader().equals("")){
                if (note.getNoteText().length() > 100){
                    note.setNoteText(note.getNoteText().substring(0, 99) + "...");
                }
            }else {
                if (note.getNoteHeader().length() > 100) {
                    note.setNoteHeader(note.getNoteHeader().substring(0, 99)+ "...");
                }
            }
            listOfNotes.add(note);
        }
        return listOfNotes;
    }

   @RequestMapping(value = "notes/filter")
   public String noteListFiltered(@ModelAttribute("filter") String filter, Model model){
       List<NoteModel> listOfNotes = new ArrayList<NoteModel>();
       try{
           byte[] ptext = filter.getBytes("ISO_8859_1");
           filter = new String(ptext, "UTF-8").toLowerCase();
       } catch (UnsupportedEncodingException e){
           logger.error("UnsupportedEncodingException: " + e);
       }
        //Делаем проверку на соответствие фильтру
       Pattern pattern = Pattern.compile("\\b+"+filter+"\\b+");
       Matcher matcher;
       //Чтобы получить небоольшую прибавку по производительности сначала будем проверять на соответствие Заголовок
       // а затем сам текст заметки(который в большинстве случаев больше)
       for (NoteModel note: this.noteService.listNotes()
            ) {
           matcher = pattern.matcher(note.getNoteHeader().toLowerCase());
           boolean isFound = matcher.find();
           if(isFound){
               listOfNotes.add(note);
           } else {
                matcher = pattern.matcher(note.getNoteText().toLowerCase());
                isFound = matcher.find();
                if(isFound){
                    listOfNotes.add(note);
                }
           }

       }
       listOfNotes = cutNoteTextForListNotesOnWebPage(listOfNotes);
       model.addAttribute("note", new NoteModel());
       model.addAttribute("listNotes", listOfNotes);

       return "notes";
   }

    @RequestMapping(value = "notes/add", method = RequestMethod.POST)
    public String addNote( @ModelAttribute("note") NoteModel note) throws UnsupportedEncodingException {
        //т.к. прописание настройки кодировки Tomcat'а и в формах jsp не дали результата, то на данный момент это единственный
        // найденный рабочий способ для отображения русских букв после отправки с формы.
        byte[] ptext = note.getNoteHeader().getBytes("ISO_8859_1");
        note.setNoteHeader(new String(ptext, "UTF-8"));
        ptext = note.getNoteText().getBytes("ISO_8859_1");
        note.setNoteText(new String(ptext, "UTF-8"));

        //Автоматически добавляем номер заголовка из базы, в случае если уже есть заметка с таким же заголовком
        for (NoteModel noteFromList: this.noteService.listNotes()
             ) {
            if(noteFromList.getNoteHeader().equals(note.getNoteHeader())){
                note.setNoteHeader(note.getNoteHeader() + note.getId());
            }
        }
        if((note.getNoteText().equals("") && note.getNoteHeader().equals("") ) ||(note.getNoteText() == null && note.getNoteHeader() == null)) {
            note.setNoteHeader("Пустая заметка");
            this.noteService.addNote(note);
        } else {
            this.noteService.addNote(note);
        }

        return "redirect:/notes";
    }

    @RequestMapping("/remove/{id}")
    public String removeNote(@PathVariable("id") int id) {
            this.noteService.removeNote(id);

            return "redirect:/notes";
    }

    @RequestMapping("/edit/{id}")
    public String editNote(@PathVariable("id") int id, Model model) {
        model.addAttribute("note", this.noteService.getNoteById(id));
        model.addAttribute("listNotes", this.noteService.listNotes());

        return "notes";
    }
    //переход на страницу заметок по клику на странице
    @RequestMapping("notedata/{id}")
    public String noteData(@PathVariable("id") int id, Model model) {
        model.addAttribute("note", this.noteService.getNoteById(id));

        return "notedata";
    }
}
