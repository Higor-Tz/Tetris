package trabalhotetris;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import utils.Constants;

/**
 *<h1>Projeto Tetris - Classe BackgroundElement </h1>
 * 
 * 
 * 
 * @author  G7 
 * @version 0.1
 * @since   2018-06-29
 * 
 */
public class BackgroundElement extends Element{

    /**
     * Construtor da Classe
     * @param img BufferedImage
     */
    public BackgroundElement(BufferedImage img){
        super(0,0,img);
    }
    
    /**
     * Método de desenho em tela
     * @param g Graphic
     */
    public void draw(Graphics g){
        g.drawImage(image, Constants.CELL_SIZE, 0, Constants.CELL_SIZE*Constants.WIDTH, Constants.CELL_SIZE*Constants.HEIGHT, null);
        g.drawImage(image, Constants.CELL_SIZE*(Constants.WIDTH+1), 0, Constants.CELL_SIZE*6, Constants.CELL_SIZE*Constants.HEIGHT, null);

    }
}
