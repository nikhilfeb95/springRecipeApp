package nikhil.springframework.recipeapp.commands;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//the command class is used for object binding
@Getter
@Setter
@NoArgsConstructor
public class CategoryCommand {
    private Long id;
    private String description;
}
