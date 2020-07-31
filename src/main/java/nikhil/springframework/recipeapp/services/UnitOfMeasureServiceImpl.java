package nikhil.springframework.recipeapp.services;

import nikhil.springframework.recipeapp.commands.UnitOfMeasureCommand;
import nikhil.springframework.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import nikhil.springframework.recipeapp.domain.UnitOfMeasure;
import nikhil.springframework.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    UnitOfMeasureRepository unitOfMeasureRepository;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> getUnitOfMeasures() {
        //as the recipeRepository returns a unitOfMeasure but we need Unit of Measure Command
        /*The simple way to do this task is to find all and store Unit of Measures in a Set
        and then loop through them and convert and add to another set*/

        /*
        What we are doing here is converting the iterable of uom to a steam and splitting it(with
        the spliterator and then converting them and storing them (using Collectors.toSet) on the fly.
        */
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }
}
