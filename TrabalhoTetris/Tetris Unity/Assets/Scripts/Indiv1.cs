using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;

public class Indiv1 : MonoBehaviour
{
    [SerializeField] private double weightVoid;
    [SerializeField] private double weightBumpiness;
    [SerializeField] private double weightHeight;

    public int pontuation;
    public int plays;

    private System.Random rand;    

    void Start()
    {
        pontuation = -1;
        plays = 0;
        rand = new System.Random(); // Inicia um random
        GenerateAttributes();
    }

    //verifica se esse individuo é melhor que outro
    public bool IsBest(Indiv best)
    {
        if(this.pontuation >= best.pontuation)
        {
            if(this.pontuation == best.pontuation && this.plays < best.plays)
                return false;

            return true;
        }

        return false;
    }

    //gera um peso aleatorio dentro do intervalo [-100000, 100000]
    public double GenerateWeight()
    {
        return (rand.NextDouble() * 2000 - 1000);
    }

    //gera todos os pesos de atributos do individuo
    public void GenerateAttributes()
    {
        weightVoid = GenerateWeight();
        weightBumpiness = GenerateWeight();
        weightHeight = GenerateWeight();
    }

    public double MutateWeight(double weight)
    {
        return weight + GenerateWeight() * 0.01;
    }

    //Funcao que realiza mutacao em cima do individuo
    public void Mutation() 
    {
        // prob de mutacao de genes -> 100%
        // taxa de mutacao -> 1%
        weightVoid += GenerateWeight() * 0.01;
        weightBumpiness += GenerateWeight() * 0.01;
        weightHeight += GenerateWeight() * 0.01;
    }

    // crossover: media com o best
    public void Breed(Indiv1 best)
    {
        // avalicao nao realizada
        this.pontuation = -1; 
        this.plays = 0;

        weightVoid = (weightVoid + best.weightVoid) / 2; // novo parametro
        weightBumpiness = (weightBumpiness + best.weightBumpiness) / 2;
        weightHeight = (weightHeight + best.weightHeight) / 2;
    }

    public double Decision(int holes, int bumpiness, int height)
    {
        return (holes*weightVoid) + (bumpiness*weightBumpiness) + (height*weightHeight);
    }
}