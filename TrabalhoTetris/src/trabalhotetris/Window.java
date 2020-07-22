
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
 * 
 */
public class Window extends JFrame implements ActionListener{
    
    private final JPanel menu = new JPanel(null);
    // Botão de iniciar
    private final JButton newGame1 = new JButton("Play");
    // Botão de carregar um jogo anterior (mais utilizado para jogar manualmente)
    private final JButton loadGame = new JButton("Load Game");
    // Painel de Pontuação
    private final JLabel maxPoints = new JLabel("Max Score: 0");
    // Label que mostra o titulo do jogo
    private JLabel tetris;
    // Label para carregar o plano de fundo
    private JLabel background;
    // inicia um timer
    private final Timer gameChecker = new Timer(Constants.DELAY_SCREEN_UPDATE, this);
    // Carrega a tela da fase
    private Stage curentStage;
    // Contagem da pontuação máxima
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
                // Título da Página
                Window window = new Window("Tetris");
                try {
                    //Tenta carregar uma imagem para o plano de fundo do menu
                    window.background = new JLabel(new ImageIcon(new File(".").getCanonicalPath() + File.separator+"Textures"+File.separator+"Background4.png"));
                } catch (IOException ex) {
                    // Realiza um tratamento de exceção
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                // Define as proporções da página do jogo
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setLayout(null);
                window.setSize(Constants.CELL_SIZE*(Constants.WIDTH + 8),Constants.CELL_SIZE*(Constants.HEIGHT + 2));
                window.setResizable(false); // impede o jogador de alterar o tamanho do menu
                window.setLocationRelativeTo(null);
                window.setVisible(true);
                
                // Centraliza a imagem do menu
                window.background.setLayout(null);
                window.background.setBounds(0, 0, window.getWidth(), window.getHeight());
        
                //Elementos de Texto
                window.maxScore = 0; // inicializa a pontuação máxima
                // Coloca o texto da pontuação máxima
                window.maxPoints.setLayout(null);
                window.maxPoints.setVerticalAlignment(JLabel.CENTER);
                window.maxPoints.setHorizontalAlignment(JLabel.LEFT);
                window.maxPoints.setFont(new java.awt.Font("Verdana", 1, 18)); // Fonte, estilo e tamanho da pontuação máxima
                window.maxPoints.setForeground(new java.awt.Color(255, 255, 255));// Cor das letras
                window.maxPoints.setPreferredSize(new Dimension(Constants.CELL_SIZE*5, Constants.CELL_SIZE));
                window.maxPoints.setBounds(5,Constants.CELL_SIZE*17, Constants.CELL_SIZE*7, Constants.CELL_SIZE); // Posiciona a pontuação máxima
                window.add(window.maxPoints);
                // Texto do nome do jogo no menu principal
                window.tetris = new JLabel("TETRIS");
                window.tetris.setLayout(null);
                window.tetris.setVerticalAlignment(JLabel.CENTER);
                window.tetris.setHorizontalAlignment(JLabel.CENTER);
                window.tetris.setFont(new java.awt.Font("Broadway", 1, 60));// Fonte, estilo e tamanho do título do jogo
                window.tetris.setForeground(new java.awt.Color(255, 255, 255));// Cor do título
                window.tetris.setPreferredSize(new Dimension(Constants.CELL_SIZE*6, Constants.CELL_SIZE*2));
                window.tetris.setBounds((window.getWidth()-Constants.CELL_SIZE*6)/4, Constants.CELL_SIZE, Constants.CELL_SIZE*12, Constants.CELL_SIZE*2); // Posicionamento do título no menu
                window.add(window.tetris);
        
                //Botões
                // Posiciona o botão "Play" no menu
                window.newGame1.setBounds(window.getWidth()/2 - 50, 200, 100, 50);
                window.newGame1.addActionListener(window);
                window.newGame1.setActionCommand("NewGame1");
                // Posiciona o botão "Load Game no jogo"
                window.loadGame.setBounds(window.getWidth()/2 - 50, 300, 100, 50);
                window.loadGame.addActionListener(window);
                window.loadGame.setActionCommand("LoadGame");
        
                //Timer verifica fim de jogo
                window.gameChecker.setActionCommand("GameChecker");
                window.gameChecker.addActionListener(window);
                window.gameChecker.setInitialDelay(0);
        
                window.menu.setBounds(0,0, 1000, 900);
                window.menu.setBackground(Color.BLACK);
                window.menu.add(window.newGame1);
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
        //Botão Play é devinido
        if(command.equals("NewGame1")){
            curentStage = new Stage(null);
            curentStage.stageName("Tetris"); // Mantem o nome do jogo no canto superior direito
            this.addKeyListener(curentStage);
            //inicia o jogo com click
            gameChecker.start();
            
            this.remove(menu);
            this.add(curentStage);
            this.revalidate();
            this.repaint();
        }
        
        //Botão de carregar jogo anterior
        if(command.equals("LoadGame")){
            curentStage = new Stage(null); 
            curentStage.load();
            
            this.addKeyListener(curentStage);
            // Inicia com um click o jogo salvo anteriormente
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
                // Caso ele retorne ao menu inicial ele verifica a pontuação atingida anteriormente
                if(maxScore < curentStage.getScore()){ // se a pontuação feita nesse jogo for maior que a anterior salva
                    maxScore = curentStage.getScore(); // atualizamos a pontuação máxima
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
