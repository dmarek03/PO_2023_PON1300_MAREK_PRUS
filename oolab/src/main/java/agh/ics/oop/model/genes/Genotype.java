package agh.ics.oop.model.genes;

import java.util.ArrayList;
import java.util.List;

public class Genotype {

    public List<Integer> genes;
    private final int genesRange;

    private final int numberOfGenes;

    public Genotype(int genesRange, int numberOfGenes){

        this.genesRange = genesRange;
        this.numberOfGenes = numberOfGenes;
        genes = getRandomGenotype();
    }

    public Genotype(List<Integer> newGenes){
        this.genesRange = 7;
        this.numberOfGenes = newGenes.size();
        this.genes = newGenes;
    }

    public Genotype(int genesRange, int numberOfGenes, List<Integer> newGenes){
        this.genesRange = genesRange;
        this.numberOfGenes = numberOfGenes;
        this.genes = newGenes;
    }

    public List<Integer> getRandomGenotype(){
        List<Integer> list = new ArrayList<>();
        for(int i =0;i< numberOfGenes;i++){
            list.add((int)(Math.random()*genesRange));
        }
        return list;
    }
    public void changeOneGen(){
        int idx =getRandomNumber(numberOfGenes);
        genes.remove(idx);
        genes.add(idx, getRandomNumber(genesRange));

    }
    public void randomMutations(){
        int numberOfChangedGenes = getRandomNumber(numberOfGenes);
        for ( int  i = 0; i< numberOfChangedGenes ; i++){
            changeOneGen();

        }
    }

    public void genesSubstitution(){
        int idx1 = getRandomNumber(numberOfGenes);
        int idx2 = getRandomNumber(numberOfGenes);
        swap(genes, idx1, idx2);

    }

    private void swap(List<Integer> array, int idx1, int idx2){
        int val1 = array.get(idx1);
        int val2  = array.get(idx2);
        array.remove(idx1);
        array.remove(idx2);
        array.add(idx1,val2);
        array.add(idx2, val1);
    }

    private int getRandomNumber(int range){
        return (int)(Math.random()*range);

    }

    public List<Integer> getGenes(){
        return genes;
    }

    public int getNumberOfGenes() {
        return numberOfGenes;
    }

    @Override
    public String toString(){
        return String.valueOf(genes);
    }
}
