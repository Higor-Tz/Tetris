
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



/**
 *<h1>Projeto Tetris - Classe Window e Método Main </h1>
 * Projeto desenvolvido para a disciplina de Programação Orientada a Objetos
 * sob a tutela do Prof. Luiz Eduardo Virgilio - ICMC/USP.
 * <p>
 * Utilizando os paradigmas de orientação a objetos, desenvolvemos o famoso
 * jogo "Tetris" com a maioria de suas funcionalidades, incluindo as funções
 * SAVE e LOAD da sessão de jogo.
 * 
 * @author  G7 
 * @version 0.1
 * @since   2018-06-29
 * 
 */
public class Window extends JFrame implements ActionListener{
    
    private final JPanel menu = new JPanel(null);
    private final JButton newGame1 = new JButton("Fase 1");
    private final JButton newGame2 = new JButton("Fase 2");
    private final JButton loadGame = new JButton("Load Game");
    private final JLabel maxPoints = new JLabel("Max Score: 0");
    private JLabel tetris; 
    private JLabel background;
    
    private final Timer gameChecker = new Timer(Constants.DELAY_SCREEN_UPDATE, this);
    
    private Stage curentStage;
    
    private int maxScore;
    
    
/**
 * Classe Main. Executa os parâmetros gerais do jogo.
 * 
 * @param args não é utilizado
 * 
 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Window window = new Window("Tetris");
                try {
                    window.background = new JLabel(new ImageIcon(new File(".").getCanonicalPath() + File.separator+"Textures"+File.separator+"Background.png"));
                } catch (IOException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setLayout(null);
                window.setSize(Constants.CELL_SIZE*(Constants.WIDTH + 8),Constants.CELL_SIZE*(Constants.HEIGHT + 2));
                window.setResizable(false);
                window.setLocationRelativeTo(null);
                window.setVisible(true);
        
                window.background.setLayout(null);
                window.background.setBounds(0, 0, window.getWidth(), window.getHeight());
        
                //Elementos de Texto
                window.maxScore = 0;
        
                window.maxPoints.setLayout(null);
                window.maxPoints.setVerticalAlignment(JLabel.CENTER);
                window.maxPoints.setHorizontalAlignment(JLabel.LEFT);
                window.maxPoints.setFont(new java.awt.Font("Broadway", 1, 18));
                window.maxPoints.setForeground(new java.awt.Color(255, 255, 255));
                window.maxPoints.setPreferredSize(new Dimension(Constants.CELL_SIZE*5, Constants.CELL_SIZE));
                window.maxPoints.setBounds(5,Constants.CELL_SIZE*18, Constants.CELL_SIZE*7, Constants.CELL_SIZE);
                window.add(window.maxPoints);
        
                window.tetris = new JLabel("TETRIS");
                window.tetris.setLayout(null);
                window.tetris.setVerticalAlignment(JLabel.CENTER);
                window.tetris.setHorizontalAlignment(JLabel.CENTER);
                window.tetris.setFont(new java.awt.Font("Broadway", 1, 60));
                window.tetris.setForeground(new java.awt.Color(255, 255, 255));
                window.tetris.setPreferredSize(new Dimension(Constants.CELL_SIZE*6, Constants.CELL_SIZE*2));
                window.tetris.setBounds((window.getWidth()-Constants.CELL_SIZE*6)/4, Constants.CELL_SIZE, Constants.CELL_SIZE*12, Constants.CELL_SIZE*2);
                window.add(window.tetris);
        
                //Botões
                window.newGame1.setBounds(window.getWidth()/2 - 50, 200, 100, 50);
                window.newGame1.addActionListener(window);
                window.newGame1.setActionCommand("NewGame1");
                
                window.newGame2.setBounds(window.getWidth()/2 - 50, 300, 100, 50);
                window.newGame2.addActionListener(window);
                window.newGame2.setActionCommand("NewGame2");
        
                window.loadGame.setBounds(window.getWidth()/2 - 50, 400, 100, 50);
                window.loadGame.addActionListener(window);
                window.loadGame.setActionCommand("LoadGame");
        
                //Timer verifica fim de jogo
                window.gameChecker.setActionCommand("GameChecker");
                window.gameChecker.addActionListener(window);
                window.gameChecker.setInitialDelay(0);
        
                window.menu.setBounds(0,0, 1000, 900);
                window.menu.setBackground(Color.BLACK);
                window.menu.add(window.newGame1);
                window.menu.add(window.newGame2);
                window.menu.add(window.loadGame);    
        
                //RECEBER INPUT DE EVENTOS
                window.setFocusable(true);
                window.requestFocusInWindow();
        
                window.add(window.menu);
                window.menu.add(window.background);
            }
        });
    }
    
    /**
     * Construtor da classe Window utilizada para tratar os gráficos do jogo
     * 
     * @param s String
     */
    public Window(String s){
        super(s); 
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        
        //Ações Botões
        //Botão Fase 1
        if(command.equals("NewGame1")){
            curentStage = new Stage(null);
            curentStage.stageName("FASE 1");
            this.addKeyListener(curentStage);
            
            gameChecker.start();
            
            this.remove(menu);
            this.add(curentStage);
            this.revalidate();
            this.repaint();
        }
        
        //Botão Fase 2
        if(command.equals("NewGame2")){
            curentStage = new Stage(null);
            curentStage.addObstacle(2, 10);
            curentStage.addObstacle(5, 15);
            curentStage.addObstacle(8, 13);
            curentStage.stageName("FASE 2");
            this.addKeyListener(curentStage);
            
            gameChecker.start();
            
            this.remove(menu);
            this.add(curentStage);
            this.revalidate();
            this.repaint();
        }
        
        //Botão Load
        if(command.equals("LoadGame")){
            curentStage = new Stage(null); 
            curentStage.load();
            
            this.addKeyListener(curentStage);
            
            gameChecker.start();
            
            this.remove(menu);
            this.add(curentStage);
            this.revalidate();
            this.repaint();
        } 
        
        //Ação retorno automático ao menu
        if(command.equals("GameChecker")){
            if(curentStage.getIsInGame() == false){
                gameChecker.stop();
                
                if(maxScore < curentStage.getScore()){
                    maxScore = curentStage.getScore();
                    maxPoints.setText("Max Score: " + maxScore);
                }
                
                this.removeKeyListener(curentStage);
                this.remove(curentStage);
                this.add(menu);
                this.revalidate();
                this.repaint();
            }
        }
            
            
    }
}
