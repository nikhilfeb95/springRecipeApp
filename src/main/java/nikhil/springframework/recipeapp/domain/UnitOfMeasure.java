package nikhil.springframework.recipeapp.domain;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class UnitOfMeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    public String getUom() {
        return description;
    }

    public void setUom(String uom) {
        this.description = uom;
    }
}
