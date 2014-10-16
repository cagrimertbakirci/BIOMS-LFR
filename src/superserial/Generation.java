package superserial;

import java.util.Arrays;
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Scott Watkins & Carlton Johnson
 *
 */
public class Generation {
    private int generation;
    private Individual[] individualArray;
    private int index;
    private int mutationRate=200;
    private int mutationSeverity=20;
    private int crossoverRate=20;

    Generation(int individuals, int gen) {
        individualArray = new Individual[individuals];
        generation = gen;
        index = 0;
    }

    /**
     * called with try{ ... }catch(){ ... }
     * this will add a new individual to the generation
     * @param individual
     */
    public void addIndividual(Individual individual) throws IndexOutOfBoundsException {
        if (index < individualArray.length) {
            individualArray[index] = individual;
            index++;
        } else {
            throw new IndexOutOfBoundsException(index++ + " is > " + individualArray.length + "\n" + "Generation is full.\n");
        }
    }
    
    /**
     * replaces an individual
     * @param index index of individual to replace
     * @param individual what to replace with
     */
    public void replaceIndividual(Individual individual, int index){
        if(index>=0 && index<individualArray.length){
            individualArray[index] = individual;
        }
    }
    
    /**
     * currently an empty method
     */
    public void removeLastIndividual() {
        
    }
    
    /**
     * 
     * @return number of individuals contained in the generation
     */
    public int getNumIndividuals() {
        return individualArray.length;
    }
    
    /**
     * 
     * @return the generation number is returned
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * 
     * @param individualNumber which individual to return
     * @return the individual designated by the individualNumber
     */
    public Individual getIndividual(int individualNumber) {
        return individualArray[individualNumber];
    }
    
    
    /**
     * This function creates the next generation
     */
    public void nextGen(){
        if(individualArray==null || individualArray[0]==null){
            this.initialGen();
        }else{
            index=0;
            Comparator comp = new IndCompare();
            Arrays.sort(individualArray, comp);
            Individual[] elite=new Individual[5];
            for(int i=0;i<5;i++){
                elite[i]=individualArray[individualArray.length-5+i];
            }
            int k=0, eM=0;
            for(int i=0; i<5;i++){
                for(int j=0; j<5;j++){
                    if(i!=j){
                        individualArray[k]=elite[i].reproduce(elite[j]);
                        individualArray[k].mutate(mutationRate, mutationSeverity);
                    }else if(eM++<3){
                        individualArray[k]=elite[i].reproduce(elite[j]);
                        individualArray[k].mutate(mutationRate, mutationSeverity);
                    }
                    k++;
                }
            }
            swap(0,24);
            swap(1,19);
            swap(2,14);
            swap(3,9);
            generation++;
        }
    }
    
    
    private void swap(int i1, int i2){
        Individual temp=individualArray[i1];
        individualArray[i1]=individualArray[i2];
        individualArray[i2]=temp;
    }
    
    /**
     * generates an initial generation
     */
    public void initialGen(){
        index=0;
        for(int i=0;i<individualArray.length;i++){
            individualArray[i]=new Individual();
        }
        index=individualArray.length;
    }
}
