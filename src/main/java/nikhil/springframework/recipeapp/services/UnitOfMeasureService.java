package nikhil.springframework.recipeapp.services;

import nikhil.springframework.recipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> getUnitOfMeasures();
}
