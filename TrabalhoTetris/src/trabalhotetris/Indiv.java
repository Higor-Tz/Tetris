package trabalhotetris;


import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Constants;
import java.util.Random;

public class Indiv
{
    private int numPieces;
    private double[] weightPieceRotate1;
    private double[] weightPieceRotate2;
    private double[] weightPieceRotate3;
    private double[] weightPieceMoveRight1;
    private double[] weightPieceMoveRight2;
    private double[] weightPieceMoveRight3;
    private double[] weightPieceMoveRight4;
    private double[] weightPieceMoveLeft1;
    private double[] weightPieceMoveLeft2;
    private double[] weightPieceMoveLeft3;
    private double[] weightPieceMoveLeft4;
    private double[] weightPieceMoveLeft5;
    // private double[] weightDistanceRotate = new double[10];
    // private double[] weightDistanceMoveRight = new double[10];
    // private double[] weightDistanceMoveLeft = new double[10];
    public int pontuation;
    private Random rand;

    Indiv(int numPieces)
    {
        this.numPieces = numPieces;
        this.weightPieceRotate1 = new double[this.numPieces];
        // this.weightPieceRotate2 = new double[this.numPieces];
        // this.weightPieceRotate3 = new double[this.numPieces];
        this.weightPieceMoveRight1 = new double[this.numPieces];
        // this.weightPieceMoveRight2 = new double[this.numPieces];
        // this.weightPieceMoveRight3 = new double[this.numPieces];
        // this.weightPieceMoveRight4 = new double[this.numPieces];
        // this.weightPieceMoveLeft1 = new double[this.numPieces];
        // this.weightPieceMoveLeft2 = new double[this.numPieces];
        // this.weightPieceMoveLeft3 = new double[this.numPieces];
        // this.weightPieceMoveLeft4 = new double[this.numPieces];
        // this.weightPieceMoveLeft5 = new double[this.numPieces];
        this.pontuation = -1;
        this.rand = new Random(); // Inicia um random
        GenerateAttributes();
    }

    //verifica se esse individuo é melhor que outro
    public boolean IsBest(Indiv best)
    {
        return this.pontuation > best.pontuation;
    }

    //gera um peso aleatorio dentro do intervalo [-100000, 100000]
    public double GenerateWeight()
    {
        return (this.rand.nextDouble() * 2000 - 1000);
    }

    //gera todos os pesos de atributos do individuo
    public void GenerateAttributes()
    {
        for(int i = 0; i < this.numPieces; i++)
        {
            this.weightPieceRotate1[i] = GenerateWeight();
            this.weightPieceMoveRight1[i] = GenerateWeight();
            // this.weightPieceMoveLeft[i] = GenerateWeight();
        }
        // for(int i = 0; i < 10; i++)
        // {
        //     this.weightDistanceRotate[i] = GenerateWeight();
        //     this.weightDistanceMoveRight[i] = GenerateWeight();
        //     this.weightDistanceMoveLeft[i] = GenerateWeight();

        // }
    }

    //Funcao que realiza mutacao em cima do individuo
    public void Mutation() 
    {
        // prob de mutacao de genes -> 100%
        // taxa de mutacao -> 1%
        for(int i = 0; i < this.numPieces; i++)
        {
            this.weightPieceRotate1[i] += GenerateWeight() * 0.01;
            this.weightPieceMoveRight1[i] += GenerateWeight() * 0.01;
            // this.weightPieceMoveLeft[i] += GenerateWeight() * 0.01;
        }
        // for(int i = 0; i < 10; i++)
        // {
        //     this.weightDistanceRotate[i] += GenerateWeight() * 0.01;
        //     this.weightDistanceMoveRight[i] += GenerateWeight() * 0.01;
        //     this.weightDistanceMoveLeft[i] += GenerateWeight() * 0.01;

        // }
    }

    // crossover: media com o best
    public void Breed(Indiv best)
    {
        this.pontuation = -1; // avalicao nao realizada
        for (int i = 0; i < this.numPieces; i++)
        {
            this.weightPieceRotate1[i] = (this.weightPieceRotate1[i] + best.weightPieceRotate1[i]) / 2; // novo parametro
            this.weightPieceMoveRight1[i] = (this.weightPieceMoveRight1[i] + best.weightPieceMoveRight1[i]) / 2;
            // this.weightPieceMoveLeft[i] = (this.weightPieceMoveLeft[i] + best.weightPieceMoveLeft[i]) / 2; 
        }
        // for (int i = 0; i < 10; i++)
        // {
        //     this.weightDistanceRotate[i] = (this.weightDistanceRotate[i] + best.weightDistanceRotate[i]) / 2; // novo parametro
        //     this.weightDistanceMoveRight[i] = (this.weightDistanceMoveRight[i] + best.weightDistanceMoveRight[i]) / 2;
        //     this.weightDistanceMoveLeft[i] = (this.weightDistanceMoveLeft[i] + best.weightDistanceMoveLeft[i]) / 2; 
        // }


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

    public double Decision(double weight/*, float[] distance*/)
    {
        double decision = weight;

        // for(int i = 0; i < 10; i++)
        // {
        //     decision += this.weightDistanceRotate[i] * distance[i];
        // }
        
        return decision;
    }

    // public double MoveRight(int typePiece/*, float[] distance*/)
    // {
    //     double decision = this.weightPieceMoveRight[typePiece]; 

    //     // for(int i = 0; i < 10; i++)
    //     // {
    //     //     decision += this.weightDistanceMoveRight[i] * distance[i];
    //     // }
        
    //     return decision;
    // }

    // public boolean MoveLeft(int typePiece/*, float[] distance*/)
    // {
    //     double decision = this.weightPieceMoveLeft[typePiece];

    //     // for(int i = 0; i < 10; i++)
    //     // {
    //     //     decision += this.weightDistanceMoveLeft[i] * distance[i];
    //     // }
        
    //     return (decision > 0);
    // }

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
    10 - Rotaciona 1 vez
    20 - Rotaciona 2 vezes
    30 - Rotaciona 3 vezes
    */
    public int ComandDecision(int typePiece/*, float[] distance*/)
    {
        int decision = 0;
        double movement;

        // decision += Decision(weightPieceRotate1[typePiece]);
        
        movement = Decision(weightPieceMoveRight1[typePiece]);
        movement += 1000;
        decision = (int) (movement/200);
        if(decision > 9)
            decision = 9;

        movement = Decision(weightPieceRotate1[typePiece]);
        movement += 1000;
        if(movement >= 2000)
            decision += 30;
        else
            decision += (int) (movement/500)*10;
    
        return decision;
    }
}