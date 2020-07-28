using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Population : MonoBehaviour
{
    // public int popSize = 10; //tamanho da população
    public Indiv[] pop; //todos os individuos da população
    [SerializeField] private int indivIndex;
    [SerializeField] private int best; //index do melhor individuo
    public static Population instance;

    void Awake()
    {
		if(instance!=null)
        {
			Debug.LogError("Mais de um Population");
			return;
		}

		instance = this;
	}

    void Start()
    {
        best = 0; 
        indivIndex = -1;
        NextIndividual();
    }

    public void FindBest()
    {
        for(int i = 0; i < pop.Length; i++)
        {
            if(pop[i].IsBest(pop[best]))
                best = i;
        }
    }

    public void Evolution()
    {
        FindBest();

        for(int i = 0; i < pop.Length; i++)
        {
            if(i != best)
            {
                pop[i].Breed(pop[best]);
                pop[i].Mutation();
            }
        }
    }

    public void NextIndividual()
    {
        if(indivIndex < pop.Length-1)
        {
            indivIndex++;
            // sP.NextPlayer(pop[indivIndex]);
            SpawnPecas.instance.NewPecas();
        }
        else
            ShowPop();
    }

    public void Evaluate(int pontuation)
    {
        pop[indivIndex].pontuation = pontuation;
    }

    public void ShowPop()
    {
        for(int i = 0; i < pop.Length; i++)
        {
            Debug.Log(i + ": " + pop[i].pontuation + ", " + pop[i].plays);
        }
    }
}