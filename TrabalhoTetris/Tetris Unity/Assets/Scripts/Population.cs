using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;

public class Population : MonoBehaviour
{
    public Indiv1[] pop; //todos os individuos da população
    [SerializeField] private int indivIndex;
    [SerializeField] private int best; //index do melhor individuo
    public System.Random rand; 
    public WindowGraph bestGraph;
    public WindowGraph1 averageGraph;
    public static Population instance;

    void Awake()
    {
		if(instance!=null)
        {
			Debug.LogError("Mais de um Population");
			return;
		}

		instance = this;

        rand = new System.Random(); // Inicia um random
	}

    void Start()
    {
        best = 0; 
        indivIndex = -1;
        NextIndividual();
    }

    void Update()
    {
        if(!Input.GetKeyDown(KeyCode.UpArrow))
            return;

        Evolution();
        indivIndex = -1;
        NextIndividual();
    }

    public void FindBest()
    {
        for(int i = 0; i < pop.Length; i++)
        {
            // Debug.Log(i + ":" + pop[i].pontuation + "> best:" + pop[best].pontuation);
            if(pop[i].IsBest(pop[best]))
            {
                best = i;
                // Debug.Log("yes");

            }
        }
    }

    public void Evolution()
    { 
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
            SpawnPecas.instance.NextPlayer(pop[indivIndex]);
            SpawnPecas.instance.Reset();
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
        FindBest();

        int average = 0;

        for(int i = 0; i < pop.Length; i++)
        {
            Debug.Log(i + ": " + pop[i].pontuation);
            average += pop[i].pontuation;
        }
        average /= pop.Length; 

        Debug.Log("best:" + pop[best].pontuation + ", average:" + average);
        bestGraph.AddPoint(pop[best].pontuation);
        averageGraph.AddPoint(average);
    }
}