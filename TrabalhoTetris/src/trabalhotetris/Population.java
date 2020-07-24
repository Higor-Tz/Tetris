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

public class Population
{
    public int popSize; //tamanho da população
    public Indiv[] pop; //todos os individuos da população
    private int best; //index do melhor individuo

    Population(int size, int numPieces)
    {
        this.popSize = size;
        GeneratePopulation(numPieces);
        this.best = 0;
    }

    public void GeneratePopulation(int numPieces)
    {
        this.pop = new Indiv[popSize];
        for(int i = 0; i < this.popSize; i++)
        {
            this.pop[i] = new Indiv(numPieces);
        }
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