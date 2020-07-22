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

public class Evolution
{
    private int popSize; //tamanho da população
    private Indiv[] population; //todos os individuos da população
    private int best; //index do melhor individuo

    Evolution(int size)
    {
        this.popSize = size;
        GeneratePopulation();
        this.best = 0;
    }

    public void GeneratePopulation()
    {
        for(int i = 0; i < this.popSize; i++)
        {
            this.population[i] = new Indiv();
        }
    }

    public void FindBest()
    {
        for(int i = 0; i < this.popSize; i++)
        {
            if(this.population[i].IsBest(this.population[this.best]))
                this.best = i;
        }
    }

    public void Breed()
    {
        FindBest();

        for(int i = 0; i < this.popSize; i++)
        {
            if(i != this.best)
            {
                this.population[i].Crossover(this.population[this.best]);
                this.population[i].Mutation();
            }
        }
    }

    public void Evolução()
    {
        //constroi um evolution
        //for(nGerações)
            //executa o jogo pra cada membro da pop e salva as pontuações
            //Bread();

    }
}