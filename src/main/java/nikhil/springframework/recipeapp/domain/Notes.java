package nikhil.springframework.recipeapp.domain;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    //Recipe has notes and note is a part of a recipe -->foreign key to the notes table
    //No cascade because we want the recipe to own this --> when recipe is removed notes is removed as well but not vice versa
    @OneToOne
    private  Recipe recipe;

    @Lob
    private  String recipeNotes;

    public Notes() {
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Notes;
    }


}
