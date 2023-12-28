package agh.ics.oop.model;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public record Competition(ArrayList<Animal> animals) {

    public List<Animal> getTheStrongestCouple() {

        animals.sort(
                Comparator.comparing(Animal::getAnimalEnergy)
                        .thenComparing(Animal::getAge)
                        .thenComparing(Animal::getChildren).reversed()
        );
        return List.of(animals.get(0), animals.get(1));
    }

}
