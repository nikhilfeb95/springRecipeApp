package nikhil.springframework.recipeapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UnitOfMeasureCommand {
    private Long id;
    private String description;
}
