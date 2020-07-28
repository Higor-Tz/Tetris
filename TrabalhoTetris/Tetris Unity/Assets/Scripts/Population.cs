using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Population
{
    public int popSize; //tamanho da população
    public Indiv[] pop; //todos os individuos da população
    private int best; //index do melhor individuo

    Population(int size)
    {
        this.popSize = size;
        GeneratePopulation();
        this.best = 0; 
    }

    public void GeneratePopulation()
    {
        this.pop = new Indiv[popSize];
        // for(int i = 0; i < this.popSize; i++)
        // {
        //     this.pop[i] = new Indiv();
        // }
    }

    public void FindBest()
    {
        for(int i = 0; i < this.popSize; i++)
        {
            if(this.pop[i].IsBest(this.pop[this.best]))
                this.best = i;
        }
    }

    public void Evolution()
    {
        FindBest();

        for(int i = 0; i < this.popSize; i++)
        {
            if(i != this.best)
            {
                this.pop[i].Breed(this.pop[this.best]);
                this.pop[i].Mutation();
            }
        }
    }
}