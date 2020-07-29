package nikhil.springframework.recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
    //They do not contain foreign key like in domain POJOs
    private Long id;
    private String recipeNotes;
}
