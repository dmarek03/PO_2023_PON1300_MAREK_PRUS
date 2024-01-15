package agh.ics.oop.model;
import java.util.HashSet;
import java.util.Set;

public class AllAnimalDescendants {

    private final Animal choosenAnimal;

    public AllAnimalDescendants(Animal animal){
        this.choosenAnimal= animal;
    }

    public int countAllDescendants(){
        Set<Animal> visited = new HashSet<>();
        return dfs(choosenAnimal,visited);

    }

    private int dfs(Animal curentAnimal, Set<Animal> visited){
        if(visited.contains(curentAnimal)){
            return 0;
        }
        visited.add(curentAnimal);
        int numberOfDescendants = curentAnimal.getChildren();
        for(Animal a : curentAnimal.allChildren){
            numberOfDescendants += dfs(a, visited);
        }
        return numberOfDescendants;
    }
}
