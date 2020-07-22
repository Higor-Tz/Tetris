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
    private int[] weightPieceRotate;
    private int[] weightPieceMoveRight;
    private int[] weightPieceMoveLeft;
    private int[] weightDistanceRotate = new int[10];
    private int[] weightDistanceMoveRight = new int[10];
    private int[] weightDistanceMoveLeft = new int[10];
    private int pontuation;
    private Random rand;

    Indiv(int numPieces)
    {
        this.numPieces = numPieces;
        this.weightPieceRotate = new int[this.numPieces];
        this.weightPieceMoveRight = new int[this.numPieces];
        this.weightPieceMoveLeft = new int[this.numPieces];
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
    public int GenerateWeight()
    {
        return (this.rand.nextInt(200001) - 100000) * 0.001;
    }

    //gera todos os pesos de atributos do individuo
    public void GenerateAttributes()
    {
        for(int i = 0; i < this.numPieces; i++)
        {
            this.weightPieceRotate[i] = GenerateWeight();
            this.weightPieceMoveRight[i] = GenerateWeight();
            this.weightPieceMoveLeft[i] = GenerateWeight();
        }
        for(int i = 0; i < 10; i++)
        {
            this.weightDistanceRotate[i] = GenerateWeight();
            this.weightDistanceMoveRight[i] = GenerateWeight();
            this.weightDistanceMoveLeft[i] = GenerateWeight();

        }
    }

    //Funcao que realiza mutacao em cima do individuo
    public void Mutation() 
    {
        // prob de mutacao de genes -> 100%
        // taxa de mutacao -> 1%
        for(int i = 0; i < this.numPieces; i++)
        {
            this.weightPieceRotate[i] += GenerateWeight() * 0.01;
            this.weightPieceMoveRight[i] += GenerateWeight() * 0.01;
            this.weightPieceMoveLeft[i] += GenerateWeight() * 0.01;
        }
        for(int i = 0; i < 10; i++)
        {
            this.weightDistanceRotate[i] += GenerateWeight() * 0.01;
            this.weightDistanceMoveRight[i] += GenerateWeight() * 0.01;
            this.weightDistanceMoveLeft[i] += GenerateWeight() * 0.01;

        }
    }

    // crossover: media com o best
    public void Crossover(Indiv best)
    {
        this.pontuation = -1; // avalicao nao realizada
        for (i = 0; i < this.numPieces; i++)
        {
            this.weightPieceRotate[i] = (this.weightPieceRotate[i] + best.weightPieceRotate[i]) / 2; // novo parametro
            this.weightPieceMoveRight[i] = (this.weightPieceMoveRight[i] + best.weightPieceMoveRight[i]) / 2;
            this.weightPieceMoveLeft[i] = (this.weightPieceMoveLeft[i] + best.weightPieceMoveLeft[i]) / 2; 
        }
        for (i = 0; i < 10; i++)
        {
            this.weightDistanceRotate[i] = (this.weightDistanceRotate[i] + best.weightDistanceRotate[i]) / 2; // novo parametro
            this.weightDistanceMoveRight[i] = (this.weightDistanceMoveRight[i] + best.weightDistanceMoveRight[i]) / 2;
            this.weightDistanceMoveLeft[i] = (this.weightDistanceMoveLeft[i] + best.weightDistanceMoveLeft[i]) / 2; 
        }


    }

    public boolean Rotate(int typePiece, float[] distance)
    {
        int decision = this.weightPieceRotate[typePiece];

        for(int i = 0; i < 10; i++)
        {
            decision += this.weightDistanceRotate[i] * distance[i];
        }
        
        return (decision > 0);
    }

    public boolean MoveRight(int typePiece, float[] distance)
    {
        int decision = this.weightPieceMoveRight[typePiece];

        for(int i = 0; i < 10; i++)
        {
            decision += this.weightDistanceMoveRight[i] * distance[i];
        }
        
        return (decision > 0);
    }

    public boolean MoveLeft(int typePiece, float[] distance)
    {
        int decision = this.weightPieceMoveLeft[typePiece];

        for(int i = 0; i < 10; i++)
        {
            decision += this.weightDistanceMoveLeft[i] * distance[i];
        }
        
        return (decision > 0);
    }

    /*
    0 - Move para baixo
    1 - Rotaciona
    2 - Move pra direita
    3 - Rotaciona e move pra direita
    4 - Move pra esquerda
    5 - Rotaciona e Move pra esquerda
    6 - Move pra esquerda e pra direita
    7 - Rotaciona, move pra direita e pra esquerda
    */
    public int ComandDecision(int typePiece, float[] distance)
    {
        int decision = 0;

        if(Rotate(typePiece, distance))
            decision += 1;
        if(MoveRight(typePiece, distance))
            decision += 2;
        if(MoveLeft(typePiece, distance))
            decision += 4;
        
        return decision;
    }
}