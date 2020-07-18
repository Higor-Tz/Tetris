package trabalhotetris;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.Random;
import utils.Constants;

/**
 *<h1>Projeto Tetris - Classe Piece </h1>
 * 
 * Classe responsável pela manipulação de cada peça do jogo
 * 
 * @author  G7 
 * @version 0.1
 * @since   2018-06-29
 * 
 */
public class Piece extends Element{
    private int shape;   //0-O | 1-I | 2-T, 3-S, 4-Z, 5-J, 6-L
    private int rotation;
    
    //CONSTRUCTOR

    /**
     *Construtor da Classe. Recebe os parâmetros de posição, tipo e imagem.
     * @param X int
     * @param Y int
     * @param type int
     * @param img BufferedImage
     */
    public Piece(int X, int Y, int type, BufferedImage img){
        super(X,Y,img);
        
        this.shape = type;
        
        rotation = (new Random().nextInt(4));
    }
    
    //GETTERS

    /**
     *Método GETshape
     * @return int
     */
    public int getShape(){
        return shape;
    }
    
    /**
     * Método GETrotation
     * @return int
     */
    public int getRotation(){
        return rotation;
    }
    
    //SETTER

    /**
     *Método SETrotation
     * @param r int
     */
    
    public void setRotation(int r){
        if(r >= 0 && r < 4)
            this.rotation = r;
    }
    
    //MOVEMENTS

    /**
     * Método Rotate. Utilizado para atribuir rotação determinada à peça em jogo.
     * @param board Matriz de inteiros bidimensional
     */
    public void rotate(int[][] board){
        int nextRotation;
        if (rotation == 3)
            nextRotation = 0;
        else nextRotation = rotation + 1;
        if(this.checkValidPosition(this.X, this.Y, nextRotation, board))
            rotation = nextRotation;
    }
    
    /**
     *Método para movimentação à esquerda.
     * @param board Matriz bidimensional de inteiros
     */
    public void moveLeft(int[][] board){
        if(this.checkValidPosition(this.X-1, this.Y, rotation, board))
            this.setPosition(this.X-1, this.Y);
    }
    
    /**
     *Método para movimentação à direita.
     * @param board Matriz bidimensional de inteiros
     */
    public void moveRigth(int[][] board){
        if(this.checkValidPosition(this.X+1, this.Y, rotation, board))
            this.setPosition(this.X+1, this.Y);
    }
    
    /**
     *Método para a descida da peça
     *
     */
    public void fall(){
        this.setPosition(this.X, this.Y + 1);
    }
    
    //Verifica se a peça atual pode ocupar uma futura posição

    /**
     *Método de Verificação. Utilizado para checar se a peça em jogo pode ocupar a posição que se segue.
     * @param X int
     * @param Y int
     * @param rot int
     * @param board Matriz bidimensional de inteiros
     * @return
     */
    public boolean checkValidPosition(int X, int Y, int rot, int[][] board){
        
        for(int i = 0; i < Constants.BLOCK_SHAPES[shape][0].length; i++)
            for(int j = 0; j < Constants.BLOCK_SHAPES[shape][0].length; j++)
                if(Constants.BLOCK_SHAPES[shape][rot][i][j] == 1)
                    if((X + i) < 0 || (X + i) >= Constants.WIDTH || (Y + j)< 0 || (Y + j) >= Constants.HEIGHT || board[Y + j][X + i] != 0)
                        return false;
        
        return true;
    }
    
    //RENDER
    /**
     *Métodos de renderização
     *
     */
    void draw(Graphics g){
        
        for(int i = 0; i < Constants.BLOCK_SHAPES[shape][0].length; i++)
            for(int j = 0; j < Constants.BLOCK_SHAPES[shape][0].length; j++)
                if(Constants.BLOCK_SHAPES[shape][rotation][i][j] == 1)
                    g.drawImage(image, Constants.CELL_SIZE*(this.X + i +1) , Constants.CELL_SIZE*(this.Y + j), Constants.CELL_SIZE, Constants.CELL_SIZE, null);
    }
    
    void drawNext(Graphics g){
        for(int i = 0; i < Constants.BLOCK_SHAPES[shape][0].length; i++)
            for(int j = 0; j < Constants.BLOCK_SHAPES[shape][0].length; j++)
                if(Constants.BLOCK_SHAPES[shape][rotation][i][j] == 1)
                    g.drawImage(image, Constants.CELL_SIZE*(i + Constants.WIDTH + 5 - Constants.BLOCK_SHAPES[shape][rotation].length/2) , Constants.CELL_SIZE*(j + 6 - Constants.BLOCK_SHAPES[shape][rotation].length/2), Constants.CELL_SIZE, Constants.CELL_SIZE, null);
    }
}
