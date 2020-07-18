package trabalhotetris;

import java.awt.image.BufferedImage;

/**
 *<h1>Projeto Tetris - Classe Abstrata Element </h1>
 * 
 * Classe responsável pelo tratamento do posicionamento de cada imagem no jogo
 * 
 * @author  G7 
 * @version 0.1
 * @since   2018-06-29
 * 
 */
public abstract class Element {

    
    protected int X,Y;

    
    protected BufferedImage image;
    
    /**
     *Método GET da posição X
     * @return int da posição X
     */
    public int getX(){
        return X;
    }
    
    /**
     * Método SET da posicao X
     * @param posX int 
     */
    public void setX(int posX){
        X = posX;
    }
    
    /**
     * Método GET da posição Y
     * @return int
     */
    public int getY(){
        return Y;
    }
    
    /**
     * Método SET da posicao Y da peça
     * @param posY int
     */
    public void setY(int posY){
        Y = posY;
    }
    
    /**
     * Método SET para posicionamento cartesiano
     * @param posX parâmetro int X
     * @param posY parâmetro int Y
     */
    public void setPosition(int posX, int posY){
        X = posX;
        Y = posY;
    }
        
    /**
     * Construtor da Classe Element
     * @param X int
     * @param Y int 
     * @param img imagem da peça
     */
    public Element(int X, int Y, BufferedImage img){
        this.X = X;
        this.Y = Y;
        this.image = img;
    }

}
