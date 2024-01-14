package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Grass;
import net.smoofyuniverse.map.WorldMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    private Animal animal;
    private ArrayList<Animal> animals;
    private ArrayList<Integer> genes;

    private int activeGen;
    private int age;

    private int energy;

    private int numberOfConsumedGrass;

    private int numberOfChildren;

    // Ustawić po napisaniu DFS
    private int numberOfDescendants;

    // Trzeba jakoś dostać informację o pozycjach, na których jest wiekszę prawdopodobieństwo
    private ArrayList<Grass> GrassPreferPositions;

    private List<Animal> animalsWithTheMostPopularGenotype;

    private int dateOfDeath;





    public Statistics(Animal animal){
        this.animal = animal;
        this.activeGen = animal.getActivatedGen();
        this.genes = animal.getGenotype().getGenes();
        this.age = animal.getAge();
        this.energy = animal.getAnimalEnergy();
        this.numberOfConsumedGrass = animal.getNumberOfConsumedGrass();
        this.numberOfChildren = animal.getChildren();
        if(animal.isDead()){
            this.dateOfDeath = animal.getAge();
        }
    }

    public Statistics(ArrayList<Animal> animals){
        this.animals = animals;
    }
    // moim zdaniem chyba trzeba będzie zrobić tak zeby konstruktor przyjmował mape albo od razu listę
    // z preferowanymi  pozycjami, tylko wtedy taki getter musi być w Standard Map
    public Statistics(WorldMap map){


    }



    public void findAnimalsWithTheMostPopularGenotype(){
        Map<ArrayList<Integer>, Animal> animalsWithGenotype = new HashMap<>();
        Map<ArrayList<Integer>, Integer> genotypeCounter = new HashMap<>();
        List<Animal> animalWithTheMostPopularGenotype = new ArrayList<>();


        for(Animal animal : animals){
            animalsWithGenotype.put(animal.getGenotype().getGenes(), animal);
        }

        for(ArrayList<Integer> genotype : animalsWithGenotype.keySet()){
            genotypeCounter.put(genotype, genotypeCounter.getOrDefault(genotype,0 ) + 1);
        }

        int MaxOccurrence = 0;
        ArrayList<Integer> mostOftenGenotype = null;

        for(Map.Entry<ArrayList<Integer>, Integer> entry :genotypeCounter .entrySet()){
            int currentOccurrence = entry.getValue();
            if(currentOccurrence > MaxOccurrence){
                MaxOccurrence = currentOccurrence;
                mostOftenGenotype = entry.getKey();
            }
        }

        for(Map.Entry<ArrayList<Integer>, Animal> entry : animalsWithGenotype.entrySet()){
            if(entry.getKey() == mostOftenGenotype){
                animalWithTheMostPopularGenotype.add(entry.getValue());
            }
        }

        this.animalsWithTheMostPopularGenotype =  animalWithTheMostPopularGenotype;

    }

    public List<Animal> getAnimalsWithTheMostPopularGenotype() {
        return animalsWithTheMostPopularGenotype;
    }
}
