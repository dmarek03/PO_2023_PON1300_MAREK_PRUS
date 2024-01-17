package agh.ics.oop;

import agh.ics.oop.model.AllAnimalDescendants;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Grass;
import agh.ics.oop.model.StandardMap;
import net.smoofyuniverse.map.WorldMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    private Animal animal;
    private List<Animal> animals;

    private StandardMap map;

    private List<Grass> grasses;

    private int numberOfAnimals;

    private int numberOfGrasses;

    private int numberOfFreePositions;
    private List<Integer> genes;

    private int activeGen;
    private int age;

    private int energy;

    private int numberOfConsumedGrass;

    private int numberOfChildren;

    private int numberOfDescendants;

    // Trzeba jakoś dostać informację o pozycjach, na których jest wiekszę prawdopodobieństwo
    private List<Grass> GrassPreferPositions;

    private List<Animal> animalsWithTheMostPopularGenotype;

    private int dateOfDeath = 0;





    public Statistics(Animal animal){
        this.animal = animal;
        this.activeGen = animal.getActivatedGen();
        this.genes = animal.getGenotype().getGenes();
        this.age = animal.getAge();
        this.energy = animal.getAnimalEnergy();
        this.numberOfConsumedGrass = animal.getNumberOfConsumedGrass();
        this.numberOfChildren = animal.getChildren();
        AllAnimalDescendants animalDescendants = new AllAnimalDescendants(animal);
        this.numberOfDescendants = animalDescendants.countAllDescendants();
        if(animal.isDead()){
            this.dateOfDeath = animal.getAge();
        }
    }

    public Statistics(StandardMap map, List<Grass> grasses, ArrayList<Animal> animals){
        this.grasses = grasses;
        this.map = map;
        this.animals = animals;
        this.numberOfAnimals = animals.size();
        this.numberOfGrasses = grasses.size();
        this.numberOfFreePositions = map.countFreePositions();


    }


    public double getAverageAnimalEnergy(){
        int totalEnergy = 0;
        for(Animal a: animals){
            totalEnergy += a.getAnimalEnergy();
        }
        return (double) totalEnergy /animals.size();
    }

    public double getAverageAnimalAge(){
        int totalAge = 0;
        for(Animal a: animals){
            totalAge += a.getAge();
        }
        return (double) totalAge  /animals.size();
    }



    public double getAverageNumberOfChild(){
        int totalNumberOfChild = 0;
        for(Animal a: animals){
            totalNumberOfChild += a.getChildren();
        }
        return (double) totalNumberOfChild /animals.size();
    }





    public List<Animal> findAnimalsWithTheMostPopularGenotype(){
        Map<List<Integer>, Animal> animalsWithGenotype = new HashMap<>();
        Map<List<Integer>, Integer> genotypeCounter = new HashMap<>();
        List<Animal> animalWithTheMostPopularGenotype = new ArrayList<>();


        for(Animal animal : animals){
            animalsWithGenotype.put(animal.getGenotype().getGenes(), animal);
        }

        for(List<Integer> genotype : animalsWithGenotype.keySet()){
            genotypeCounter.put(genotype, genotypeCounter.getOrDefault(genotype,0 ) + 1);
        }

        int MaxOccurrence = 0;
        List<Integer> mostOftenGenotype = new ArrayList<>();

        for(Map.Entry<List<Integer>, Integer> entry :genotypeCounter .entrySet()){
            int currentOccurrence = entry.getValue();
            if(currentOccurrence > MaxOccurrence){
                MaxOccurrence = currentOccurrence;
                mostOftenGenotype = entry.getKey();
            }
        }

        for(Map.Entry< List<Integer>, Animal> entry : animalsWithGenotype.entrySet()){
            if(entry.getKey() == mostOftenGenotype){
                animalWithTheMostPopularGenotype.add(entry.getValue());
            }
        }

         return animalWithTheMostPopularGenotype;

    }
    private String animalDateOfDeath() {
        if (dateOfDeath == 0) {
            return "Animal is still alive";
        }
        return "Date of death:"+dateOfDeath+'\n';

    }

    public String showAnimalStatistics(){

        return ("Animal:%s\nThe genome of the animal:%s\nActive gen of genome:%d\n" +
                "Energy of the animal:%d\nNumber of consumed grass:%d\nNumber of children:%d\n" +
                "Number of descendants:%d\nAge:%d\n%s").formatted(animal, genes, activeGen, energy,
                numberOfConsumedGrass, numberOfChildren, numberOfDescendants, age, animalDateOfDeath());
    }

    public List<Animal> getAnimalsWithTheMostPopularGenotype() {
        return animalsWithTheMostPopularGenotype;
    }
}
