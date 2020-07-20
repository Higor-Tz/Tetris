
package trabalhotetris;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import javax.imageio.ImageIO;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Constants;

/**
 *<h1>Projeto Tetris - Classe Stage </h1>
 * 
 * 
 * 
 * @author  G7 
 * @version 0.1
 * @since   2018-06-29
 * 
 */
public class Stage extends JPanel implements Serializable, KeyListener, ActionListener{
    
    //Game Elements
    private Piece currentPiece;
    private Piece nextPiece;
    private BackgroundElement background;
    private int[][] board = new int[Constants.HEIGHT][Constants.WIDTH];
    
    //TextElements
    private JLabel tetrisText;
    private JLabel nextPieceText;
    private JLabel scoreLabel;
    private JLabel score;
    private JLabel timeText;
    private JLabel saveText;
    
    //Imagens

    /**
     *
     */
    public static BufferedImage[] blockImages = new BufferedImage[Constants.NUM_OF_PIECES];
    
    //Time Atributes
    private Timer timer;
    private int ticks;
    private int totalTicks;
    private int saveTimer;
    
    //Control Atributes
    private boolean isAcelerated;   //Down Key Pressed
    private boolean isInGame;
    private Random rand;
    private int pontuation;
    
    //numero de peças
    private static int numpecas = 5;
    
    //CONSTRUTOR: Utilizado para inicialização da fase.

     /**
     *Construtor da Classe Stage. Utilizado na inicialização de fase
     *@param layout LayoutManager
     *
     */
    Stage(LayoutManager layout){
        super(layout);
        
        rand = new Random();
        
        //Dimensionamento e aparência da tela de jogo. Alterar de acordo com o design final.
        this.setPreferredSize(new Dimension(Constants.CELL_SIZE*(Constants.WIDTH + 8),Constants.CELL_SIZE*(Constants.HEIGHT + 2)));
        this.setMaximumSize(this.getPreferredSize());
        this.setBounds(0, 0, Constants.CELL_SIZE*(Constants.WIDTH + 8), Constants.CELL_SIZE*(Constants.HEIGHT + 2));
        this.setBackground(Color.GRAY);
        
        //Text elements        
        nextPieceText = new JLabel("Next Shape");
        nextPieceText.setLayout(null);
        nextPieceText.setVerticalAlignment(JLabel.CENTER);
        nextPieceText.setHorizontalAlignment(JLabel.CENTER);
        nextPieceText.setFont(new java.awt.Font("Verdana", 1, 16));
        nextPieceText.setForeground(new java.awt.Color(0, 0, 0));
        nextPieceText.setPreferredSize(new Dimension(Constants.CELL_SIZE*4, Constants.CELL_SIZE));
        nextPieceText.setBounds(Constants.CELL_SIZE*(Constants.WIDTH +3), Constants.CELL_SIZE*8, Constants.CELL_SIZE*4, Constants.CELL_SIZE);
        this.add(nextPieceText);
        
        timeText = new JLabel("Time: 0000 s");
        timeText.setLayout(null);
        timeText.setVerticalAlignment(JLabel.CENTER);
        timeText.setHorizontalAlignment(JLabel.CENTER);
        timeText.setFont(new java.awt.Font("Verdana", 1, 14));
        timeText.setForeground(new java.awt.Color(200, 200, 200));
        timeText.setPreferredSize(new Dimension(Constants.CELL_SIZE*4, Constants.CELL_SIZE));
        timeText.setBounds(Constants.CELL_SIZE*(Constants.WIDTH +3), Constants.CELL_SIZE*(Constants.HEIGHT -1), Constants.CELL_SIZE*4, Constants.CELL_SIZE);
        this.add(timeText);
       
        scoreLabel = new JLabel("Score");//label do ponto
        scoreLabel.setLayout(null);
        scoreLabel.setVerticalAlignment(JLabel.CENTER);
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setFont(new java.awt.Font("Verdana", 1, 24));
        scoreLabel.setForeground(new java.awt.Color(0, 0, 0));
        scoreLabel.setPreferredSize(new Dimension(Constants.CELL_SIZE*4, Constants.CELL_SIZE*2));
        scoreLabel.setBounds(Constants.CELL_SIZE*(Constants.WIDTH +3), Constants.CELL_SIZE*11, Constants.CELL_SIZE*4, Constants.CELL_SIZE*2);
        this.add(scoreLabel);
        
        score = new JLabel("0");//pontos
        score.setLayout(null);
        score.setVerticalAlignment(JLabel.CENTER);
        score.setHorizontalAlignment(JLabel.CENTER);
        score.setFont(new java.awt.Font("Verdana", 1, 24));
        score.setForeground(new java.awt.Color(0, 0, 0));
        score.setPreferredSize(new Dimension(Constants.CELL_SIZE*4, Constants.CELL_SIZE*2));
        score.setBounds(Constants.CELL_SIZE*(Constants.WIDTH +3), Constants.CELL_SIZE*13, Constants.CELL_SIZE*4, Constants.CELL_SIZE*2);
        this.add(score);
        
        saveText = new JLabel("JOGO SALVO");
        saveText.setLayout(null);
        saveText.setVerticalAlignment(JLabel.CENTER);
        saveText.setHorizontalAlignment(JLabel.CENTER);
        saveText.setFont(new java.awt.Font("Verdana", 1, 20));
        saveText.setForeground(new java.awt.Color(255, 255, 255));
        saveText.setPreferredSize(new Dimension(Constants.CELL_SIZE*3, Constants.CELL_SIZE));
        saveText.setBounds(Constants.CELL_SIZE*(Constants.WIDTH -9), Constants.CELL_SIZE*(Constants.HEIGHT -10), Constants.CELL_SIZE*10, Constants.CELL_SIZE);        
        
        //Carregamento de imagens
        try {
            blockImages[0] = ImageIO.read(new File(new File(".").getCanonicalPath() + File.separator+"Textures"+File.separator +"Blocks"+File.separator+"Gray.png"));
            blockImages[1] = ImageIO.read(new File(new File(".").getCanonicalPath() + File.separator+"Textures"+File.separator +"Blocks"+File.separator+"Red.png"));
            blockImages[2] = ImageIO.read(new File(new File(".").getCanonicalPath() + File.separator+"Textures"+File.separator +"Blocks"+File.separator+"Blue.png"));
            blockImages[3] = ImageIO.read(new File(new File(".").getCanonicalPath() + File.separator+"Textures"+File.separator +"Blocks"+File.separator+"Cyan.png"));
            blockImages[4] = ImageIO.read(new File(new File(".").getCanonicalPath() + File.separator+"Textures"+File.separator +"Blocks"+File.separator+"Yellow.png"));
            blockImages[5] = ImageIO.read(new File(new File(".").getCanonicalPath() + File.separator+"Textures"+File.separator +"Blocks"+File.separator+"Orange.png"));
            blockImages[6] = ImageIO.read(new File(new File(".").getCanonicalPath() + File.separator+"Textures"+File.separator +"Blocks"+File.separator+"Purple.png"));
            blockImages[7] = ImageIO.read(new File(new File(".").getCanonicalPath() + File.separator+"Textures"+File.separator +"Blocks"+File.separator+"Green.png"));
            
            background = new BackgroundElement(ImageIO.read(new File(new File(".").getCanonicalPath() + File.separator+"Textures"+File.separator+"Background4.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Tabuleiro
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[j].length; j++)
                board[i][j] = 0;
        
        //Peças
        int pieceIndex = rand.nextInt(numpecas);
        currentPiece = new Piece(Constants.WIDTH/2 - 1, 0, pieceIndex, blockImages[pieceIndex+1]);
        pieceIndex = rand.nextInt(numpecas);
        nextPiece = new Piece(Constants.WIDTH/2 - 1, 0, pieceIndex, blockImages[pieceIndex+1]);
        
        //Timer
        timer = new Timer(Constants.DELAY_SCREEN_UPDATE, this);
        timer.setActionCommand("TIMER");
        timer.setInitialDelay(0);
        timer.start();
        ticks = 0;
        totalTicks = 0;
        
        //Controle
        isAcelerated = false;
        isInGame = true;
        pontuation = 0;
    }
    
    //Imprime a tela a cada instante(chamado por repaint())
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //Impressão da grade de jogo e fundo
        background.draw(g);
        g.setColor(Color.BLACK);
        for(int i = 0; i <= Constants.HEIGHT; i++){
            g.drawLine(Constants.CELL_SIZE, i*Constants.CELL_SIZE, (Constants.WIDTH + 1)*Constants.CELL_SIZE, i*Constants.CELL_SIZE);
            g.drawImage(blockImages[0], 0, i*Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE, null);
            g.drawImage(blockImages[0], (Constants.WIDTH + 1)*Constants.CELL_SIZE, i*Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE, null);
            g.drawImage(blockImages[0], (Constants.WIDTH + 2)*Constants.CELL_SIZE, i*Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE, null);
            g.drawImage(blockImages[0], (Constants.WIDTH + 7)*Constants.CELL_SIZE, i*Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE, null);
        }
        for(int j = 0; j <= Constants.WIDTH; j++){
            g.drawLine((j+1)*Constants.CELL_SIZE, 0, (j+1)*Constants.CELL_SIZE, Constants.HEIGHT*Constants.CELL_SIZE);
            g.drawImage(blockImages[0], (j+1)*Constants.CELL_SIZE,Constants.CELL_SIZE*Constants.HEIGHT , Constants.CELL_SIZE, Constants.CELL_SIZE, null);
        }
        for(int i = 0; i < 4; i++){
            g.drawImage(blockImages[0], (i+Constants.WIDTH+3)*Constants.CELL_SIZE, 0, Constants.CELL_SIZE, Constants.CELL_SIZE, null);
            g.drawImage(blockImages[0], (i+Constants.WIDTH+3)*Constants.CELL_SIZE,Constants.CELL_SIZE*Constants.HEIGHT , Constants.CELL_SIZE, Constants.CELL_SIZE, null);
            g.drawImage(blockImages[0], (i+Constants.WIDTH+3)*Constants.CELL_SIZE,Constants.CELL_SIZE*3 , Constants.CELL_SIZE, Constants.CELL_SIZE, null);
            g.drawImage(blockImages[0], (i+Constants.WIDTH+3)*Constants.CELL_SIZE,Constants.CELL_SIZE*9 , Constants.CELL_SIZE, Constants.CELL_SIZE, null);
            g.drawImage(blockImages[0], (i+Constants.WIDTH+3)*Constants.CELL_SIZE,Constants.CELL_SIZE*10 , Constants.CELL_SIZE, Constants.CELL_SIZE, null);
            g.drawImage(blockImages[0], (i+Constants.WIDTH+3)*Constants.CELL_SIZE,Constants.CELL_SIZE*15 , Constants.CELL_SIZE, Constants.CELL_SIZE, null);
            g.drawImage(blockImages[0], (i+Constants.WIDTH+3)*Constants.CELL_SIZE,Constants.CELL_SIZE*16 , Constants.CELL_SIZE, Constants.CELL_SIZE, null);
        }
        
        //Impressão de obstáculos e peças mortas
        for(int i = 0; i < Constants.HEIGHT; i++)
            for(int j = 0; j < Constants.WIDTH; j++)
                if(board[i][j] > 0 && board[i][j] < 8)
                    g.drawImage(blockImages[board[i][j]], (j+1)*Constants.CELL_SIZE, i*Constants.CELL_SIZE, Constants.CELL_SIZE,Constants.CELL_SIZE,null);
                else if(board[i][j] == 8)
                    g.drawImage(blockImages[0], (j+1)*Constants.CELL_SIZE, i*Constants.CELL_SIZE, Constants.CELL_SIZE,Constants.CELL_SIZE,null);

        
        //Impressão da peça atual e da próxima
        currentPiece.draw(g);
        nextPiece.drawNext(g);
        
    }
    
    //Eventos de Teclado
    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_DOWN:
                isAcelerated = true;
                break;
            case KeyEvent.VK_LEFT:
                if(isInGame)
                    currentPiece.moveLeft(board);
                break;
            case KeyEvent.VK_RIGHT:
                if(isInGame)
                    currentPiece.moveRigth(board);
                break;
            case KeyEvent.VK_SPACE:
                if(isInGame)
                    currentPiece.rotate(board);
                ticks = 0;
                break;
            case KeyEvent.VK_S:
                ObjectOutputStream tetrisOut;
                try {
                    tetrisOut = new ObjectOutputStream(new FileOutputStream(new File(new File(".").getCanonicalPath() + File.separator+"Save"+File.separator +"save.txt")));
                    this.writeObject(tetrisOut);
                    this.add(saveText);
                    tetrisOut.close();
                    saveTimer= 0;                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(Constants.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case KeyEvent.VK_ESCAPE:
                if(isInGame)
                    isInGame = false;
                break;
        }
    }
    @Override
    public void keyReleased(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_DOWN:
                isAcelerated = false;
                break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e){
        
    }
    
    //Update de tempo. Verifica a queda da peça e reimprime a tela.
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("TIMER") && isInGame){
            ticks++;
            totalTicks++;
            saveTimer++;
            if(saveTimer == 20)
                this.remove(saveText);
            timeText.setText("Time: "+(totalTicks*timer.getDelay())/1000+"s");
            if(ticks > getFallTicksRequired()){
                if(currentPiece.checkValidPosition(currentPiece.X, currentPiece.Y + 1, currentPiece.getRotation(), board))
                    currentPiece.fall();
                else addPieceToBoard();
                
                ticks = 0;
            }
            repaint();
        }
    }
    
    //transforma uma peça móvel em obstáculo e cria uma nova peça. Chamado quando a peça atual colide inferiormente.
    /**
     *Método addPieceToBoard chamado quando a peça em jogo colide inferiormente.
     *
     *
     */
    private void addPieceToBoard(){
        //Adiciona a peça ao tabuleiro como peça morta        
        for(int i = 0; i < Constants.BLOCK_SHAPES[currentPiece.getShape()][0].length; i++)
            for(int j = 0; j < Constants.BLOCK_SHAPES[currentPiece.getShape()][0].length; j++)
                if(Constants.BLOCK_SHAPES[currentPiece.getShape()][currentPiece.getRotation()][i][j] == 1)
                    board[currentPiece.getY() + j][currentPiece.getX() + i] = currentPiece.getShape() + 1;
        
        //Verifica se existem linhas a serem removidas e altera a pontuação
        int partialPontuation = 0;
        for(int i = 0; i < Constants.HEIGHT; i++){
            int j = 0;
            while( j < Constants.WIDTH && board[i][j] > 0)
                j++;
            if(j == Constants.WIDTH){
                removeLine(i);
                partialPontuation++;
            }
        }
        switch(partialPontuation){
            case 1:
                pontuation += 50;
                break;
            case 2:
                pontuation += 200;
                break;
            case 3:
                pontuation += 800;
                break;
            case 4:
                pontuation += 3200;
                break;
        }
        
        score.setText(Integer.toString(pontuation));
        
        //Cria proximas peças
        int pieceIndex = rand.nextInt(numpecas);
        currentPiece = nextPiece;
        nextPiece = new Piece(Constants.WIDTH/2 - 1, 0, pieceIndex, blockImages[pieceIndex+1]);
        
        //Verifica se o jogo deve ser continuado
        if(!currentPiece.checkValidPosition(currentPiece.getX(), currentPiece.getY(), currentPiece.getRotation(), board))
            isInGame = false;
    }
    
    //Remove a linha do tabuleiro. Preserva obstaculos.
    /**
     *Método para a remoção de linha completa.
     *@param line int
     *
     */
    private void removeLine(int line){
       for(int i = line; i > 0; i--)
            for(int j = 0; j < Constants.WIDTH; j++)
                if(board[i][j] != 8)
                    if(board[i-1][j] == 8)
                         board[i][j] = 0;
                    else board[i][j] = board[i-1][j];

    
        for(int j = 0; j < Constants.WIDTH; j++)
            board[0][j] = 0;
    }
    
    //Retorna o intervalo de tempo (em Ticks) para que a peça atual caia 1 bloco. É aumentado de acordo com a pontuação

    /**
     *Método para aquisição de tempo necessário para que a peça em jogo caia 1 bloco.
     *<p>
     *Utilizada para aumentar a velocidade de queda
     * @return
     */
    public int getFallTicksRequired(){
        if(isAcelerated)
            return Math.toIntExact(Math.round(Constants.BASE_FALL_TICKS*Math.pow(Constants.FALL_TICKS_REDUCTION_RATE,(double)(pontuation/1000))/Constants.ACELERATION_RATE));
        
        return Math.toIntExact(Math.round(Constants.BASE_FALL_TICKS*Math.pow(Constants.FALL_TICKS_REDUCTION_RATE,(double)(pontuation/1000))));
    }
    
    //adiciona obstáculo na posição dada

    /**
     *Método Utilizado para adicionar obstáculo numa dada posição
     * @param x int
     * @param y int
     */
    public void addObstacle(int x, int y){
        board[y][x] = 8;
    }
    
    //altera o título da fase a entrar

    /**
     *Método para mudança de título da fase
     * @param name String
     */
    public void stageName(String name){
        tetrisText = new JLabel(name);
        tetrisText.setLayout(null);
        tetrisText.setVerticalAlignment(JLabel.CENTER);
        tetrisText.setHorizontalAlignment(JLabel.CENTER);
        tetrisText.setFont(new java.awt.Font("Broadway", 1, 24));
        tetrisText.setForeground(new java.awt.Color(200, 200, 200));
        tetrisText.setPreferredSize(new Dimension(Constants.CELL_SIZE*4, Constants.CELL_SIZE*2));
        tetrisText.setBounds(Constants.CELL_SIZE*(Constants.WIDTH +3), Constants.CELL_SIZE, Constants.CELL_SIZE*4, Constants.CELL_SIZE*2);
        this.add(tetrisText);
    }
    
    //GETTERS

    /**
     *Método GETisInGame
     * @return boolean
     */
    public boolean getIsInGame(){
        return isInGame;
    }
    
    /**
     *Método GETscore
     * @return int
     */
    public int getScore(){
        return pontuation;
    }
    
    //Serializable
    /**
     *Método que se utiliza da interface serializável para salvar estado do jogo
     * @param o
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream o) throws IOException{
        int boardHeight = board.length, boardWidth = board[0].length;
        //Peça Atual
        o.writeInt(currentPiece.getShape());
        o.writeInt(currentPiece.getRotation());
        o.writeInt(currentPiece.getX());
        o.writeInt(currentPiece.getY());
        
        //Proxima Peça
        o.writeInt(nextPiece.getShape());
        o.writeInt(nextPiece.getRotation());
        o.writeInt(nextPiece.getX());
        o.writeInt(nextPiece.getY());
        
        //Tabuleiro
        o.writeInt(boardHeight);
        o.writeInt(boardWidth);
        o.writeInt(pontuation);
        o.writeInt(totalTicks);
        
        
        for(int i = 0; i < boardHeight; i++)
            for(int j = 0; j < boardWidth; j++)
                o.writeInt(board[i][j]);
    }
    
    /**
     *Método que se utiliza da interface serializável para carregar jogo salvo
     * @param o
     * @throws IOException
     */
    public void readObject(ObjectInputStream o) throws IOException{
        int curShape, curRot, curX, curY, 
            nextShape, nextRot, nextX, nextY,
            boardHeight, boardWidth;
        //Peça Atual
        curShape = o.readInt();
        curRot = o.readInt();
        curX = o.readInt();
        curY = o.readInt();
        
        currentPiece = new Piece(curX, curY, curShape, blockImages[curShape + 1]);
        currentPiece.setRotation(curRot);
        
        //Proxima Peça
        nextShape = o.readInt();
        nextRot = o.readInt();
        nextX = o.readInt();
        nextY = o.readInt();
        
        nextPiece = new Piece(nextX, nextY, nextShape, blockImages[nextShape +1]);
        nextPiece.setRotation(nextRot);
        
        //Tabuleiro
        boardHeight = o.readInt();
        boardWidth = o.readInt();
        pontuation = o.readInt();
        totalTicks = o.readInt();
        
        score.setText(Integer.toString(pontuation));
        
        board = new int[boardHeight][boardWidth];
        
        for(int i = 0; i < boardHeight; i++)
            for(int j = 0; j < boardWidth; j++)
                board[i][j] = o.readInt();
    }
    
    /**
     *Método utilizado para carregar dados do jogo salvo
     */
    public void load(){
        try {
            ObjectInputStream tetrisIn;
            tetrisIn = new ObjectInputStream(new FileInputStream(new File(new File(".").getCanonicalPath() + File.separator+"Save"+File.separator +"save.txt")));
                this.readObject(tetrisIn);
                tetrisIn.close();
            } catch (IOException ex) {
               Logger.getLogger(Constants.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
