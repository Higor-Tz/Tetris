using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;

public class Indiv : MonoBehaviour
{
    private static int numPieces = 5;
    private static int surfaceSize = 10;
    // private static final int nRotations = 3;
    // private static final int nRMovements = 4;
    // private static final int nLMovements = 5;

    [SerializeField] private double[] weightRotate1;
    // private double[] weightRotate2;
    // private double[] weightRotate3;
    [SerializeField] private double[] weightMoveR1;
    // private double[] weightMoveR2;
    // private double[] weightMoveR3;
    // private double[] weightMoveR4;
    // private double[] weightMoveL1;
    // private double[] weightMoveL2;
    // private double[] weightMoveL3;
    // private double[] weightMoveL4;
    // private double[] weightMoveL5;

    public int pontuation;
    public int plays;

    private System.Random rand;    

    Indiv()
    {
        weightRotate1 = new double[numPieces + surfaceSize];
        // this.weightRotate2 = new double[this.numPieces];
        // this.weightRotate3 = new double[this.numPieces];
        weightMoveR1 = new double[numPieces + surfaceSize];
        // this.weightMoveR2 = new double[this.numPieces];
        // this.weightMoveR3 = new double[this.numPieces];
        // this.weightMoveR4 = new double[this.numPieces];
        // this.weightMoveL1 = new double[this.numPieces];
        // this.weightMoveL2 = new double[this.numPieces];
        // this.weightMoveL3 = new double[this.numPieces];
        // this.weightMoveL4 = new double[this.numPieces];
        // this.weightMoveL5 = new double[this.numPieces];
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
        for(int i = 0; i < numPieces + surfaceSize; i++)
        {
            this.weightRotate1[i] = GenerateWeight();
            // this.weightRotate2[i] = GenerateWeight();
            // this.weightRotate3[i] = GenerateWeight();
            this.weightMoveR1[i] = GenerateWeight();
            // this.weightMoveR2[i] = GenerateWeight();
            // this.weightMoveR3[i] = GenerateWeight();
            // this.weightMoveR4[i] = GenerateWeight();
            // this.weightMoveL1[i] = GenerateWeight();
            // this.weightMoveL2[i] = GenerateWeight();
            // this.weightMoveL3[i] = GenerateWeight();
            // this.weightMoveL4[i] = GenerateWeight();
            // this.weightMoveL5[i] = GenerateWeight();
        }
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
        for(int i = 0; i < numPieces + surfaceSize; i++)
        {
            this.weightRotate1[i] += GenerateWeight() * 0.01;
            // this.weightRotate2[i] += GenerateWeight() * 0.01;
            // this.weightRotate3[i] += GenerateWeight() * 0.01;
            this.weightMoveR1[i] += GenerateWeight() * 0.01;
            // this.weightMoveR2[i] += GenerateWeight() * 0.01;
            // this.weightMoveR3[i] += GenerateWeight() * 0.01;
            // this.weightMoveR4[i] += GenerateWeight() * 0.01;
            // this.weightMoveL1[i] += GenerateWeight() * 0.01;
            // this.weightMoveL2[i] += GenerateWeight() * 0.01;
            // this.weightMoveL3[i] += GenerateWeight() * 0.01;
            // this.weightMoveL4[i] += GenerateWeight() * 0.01;
            // this.weightMoveL5[i] += GenerateWeight() * 0.01;
        }
    }

    // crossover: media com o best
    public void Breed(Indiv best)
    {
        // avalicao nao realizada
        this.pontuation = -1; 
        this.plays = 0;

        for (int i = 0; i < numPieces + surfaceSize; i++)
        {
            this.weightRotate1[i] = (this.weightRotate1[i] + best.weightRotate1[i]) / 2; // novo parametro
            // this.weightRotate2[i] = (this.weightRotate2[i] + best.weightRotate2[i]) / 2;
            // this.weightRotate3[i] = (this.weightRotate3[i] + best.weightRotate3[i]) / 2;
            this.weightMoveR1[i] = (this.weightMoveR1[i] + best.weightMoveR1[i]) / 2;
            // this.weightMoveR2[i] = (this.weightMoveR2[i] + best.weightMoveR2[i]) / 2;
            // this.weightMoveR3[i] = (this.weightMoveR3[i] + best.weightMoveR3[i]) / 2;
            // this.weightMoveR4[i] = (this.weightMoveR4[i] + best.weightMoveR4[i]) / 2;
            // this.weightMoveL1[i] = (this.weightMoveL1[i] + best.weightMoveL1[i]) / 2;
            // this.weightMoveL2[i] = (this.weightMoveL2[i] + best.weightMoveL2\[i]) / 2;
            // this.weightMoveL3[i] = (this.weightMoveL3[i] + best.weightMoveL3[i]) / 2;
            // this.weightMoveL4[i] = (this.weightMoveL4[i] + best.weightMoveL4[i]) / 2;
            // this.weightMoveL5[i] = (this.weightMoveL5[i] + best.weightMoveL5[i]) / 2;
        }
    }

    // public boolean Decision(double weight/*, float[] distance*/)
    // {
    //     double decision = weight;

    //     // for(int i = 0; i < 10; i++)
    //     // {
    //     //     decision += this.weightDistanceRotate[i] * distance[i];
    //     // }
        
    //     return (decision > 0);
    // }

    public double Decision(double[] weights, int typePiece, int[] surface)
    {
        double decision = weights[typePiece];

        for(int i = numPieces; i < numPieces + surfaceSize; i++)
        {
            decision += weights[i] * surface[i-numPieces];
        }
        
        return decision;
    }

    /*
    0  - Não faz nenhum movimento
    1  - Move pra esquerda 1 vez
    2  - Move pra esquerda 2 vezes
    3  - Move pra esquerda 3 vezes
    4  - Move pra esquerda 4 vezes
    5  - Move pra esquerda 5 vezes
    6  - Move pra direita 1 vez
    12  - Move pra direita 2 vezes
    18  - Move pra direita 3 vezes
    24  - Move pra direita 4 vezes
    25 - Rotaciona 1 vez
    50 - Rotaciona 2 vezes
    75 - Rotaciona 3 vezes
    */



    /*
    0  - Não faz nenhum movimento
    1  - Move pra esquerda 1 vez
    2  - Move pra esquerda 2 vezes
    3  - Move pra esquerda 3 vezes
    4  - Move pra esquerda 4 vezes
    5  - Move pra esquerda 5 vezes
    6  - Move pra direita 1 vez
    7  - Move pra direita 2 vezes
    8  - Move pra direita 3 vezes
    9  - Move pra direita 4 vezes
    ---------------------------
    10 - Rotaciona 1 vez
    20 - Rotaciona 2 vezes
    30 - Rotaciona 3 vezes
    */
    public int ComandDecision(int typePiece, int[] surface)
    {
        int decision;
        double movement;

        plays++;
        
        movement = Decision(weightMoveR1, typePiece, surface);
        movement += 1000;
        decision = (int) (movement/200);
        if(decision > 9)
            decision = 9;

        movement = Decision(weightRotate1, typePiece, surface);
        movement += 1000;
        if(movement >= 2000)
            decision += 30;
        else
            decision += (int) (movement/500)*10;
    
        return decision;
    }
}