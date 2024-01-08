package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Random;

public class Genotype {

    public ArrayList<Integer> genes;
    private final int genesRange;

    private final int numberOfGenes;

    public Genotype(int genesRange, int numberOfGenes){

        this.genesRange = genesRange;
        this.numberOfGenes = numberOfGenes;
        genes = getRandomGenotype();
    }

    public Genotype(ArrayList<Integer> newGenes){
        this.genesRange = 7;
        this.numberOfGenes = newGenes.size();
        this.genes = newGenes;
        System.out.println("Assigned numberOfGenes as " + numberOfGenes + " and should " + newGenes.size());
    }

    public Genotype(int genesRange, int numberOfGenes, ArrayList<Integer> newGenes){
        this.genesRange = genesRange;
        this.numberOfGenes = numberOfGenes;
        this.genes = newGenes;
    }

    public ArrayList<Integer> getRandomGenotype(){
        ArrayList<Integer> list = new ArrayList<>();
        for(int i =0;i< numberOfGenes;i++){
//            list.add(getRandomNumber(genesRange));
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

    private void swap(ArrayList<Integer> array, int idx1, int idx2){
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

    public int getGenesRange() {
        return genesRange;
    }

    public ArrayList<Integer> getGenes(){
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
