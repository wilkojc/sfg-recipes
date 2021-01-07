package guru.springframework.recipes.commands;

import lombok.NoArgsConstructor;



@NoArgsConstructor
public class NotesCommand {
    private Long id;
    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
