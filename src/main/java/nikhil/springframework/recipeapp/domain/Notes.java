package nikhil.springframework.recipeapp.domain;

import javax.persistence.*;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeNotes() {
        return recipeNotes;
    }

    public void setRecipeNotes(String recipeNotes) {
        this.recipeNotes = recipeNotes;
    }
}
