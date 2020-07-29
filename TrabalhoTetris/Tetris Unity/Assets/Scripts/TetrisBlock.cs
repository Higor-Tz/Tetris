using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TetrisBlock : MonoBehaviour
{
    public Vector3 rotationPoint; // rotação da peça
    private float previousTime = 0; // tempo de queda
    public float fallTime = 0.8f; // tempo de queda automática
    public static int height = 20; // linhas
    public static int width = 10; // colunas
    public int scoreValue = 50; // pontuação
    public int qlinhas = 0;//quantidades de linhas a serem deletadas
    public static Transform[,] grid = new Transform [width,height];
    public int nRotates;
    public Indiv1 player;
    public double bestScore;
    public Vector3 bestPosition;
    public Quaternion bestRotation;
    public static int nPiece = 0;

    void Start()
    {
        nPiece++;

        if(!ValidMove())
            EndGame();
        else
        {
            bestScore = double.MaxValue;
            // StartCoroutine(TestPositions());
            TestPositions();
        }
    }

    // void Update() // Atualização a cada frame de jogo
    // {
    //     if(Input.GetKeyDown(KeyCode.LeftArrow)) // checa se a esquerda foi precionado
    //     {
    //         MoveLeft();
    //     }
    //     else if (Input.GetKeyDown(KeyCode.RightArrow)) // checa se a direita foi precionado
    //     {
    //         MoveRight();
    //     }
    //     else if (Input.GetKeyDown(KeyCode.UpArrow))
    //     {   
    //         Rotate();
    //     }

    //     if(Time.time - previousTime > (Input.GetKeyDown(KeyCode.DownArrow) ? (fallTime / 10) : fallTime)) // quando precionado para baixo diminui o tempo de queda
    //     {
    //         MoveDown();
    //         previousTime = Time.time;
    //     }
    // }

    public bool MoveLeft()
    {
        transform.position += new Vector3(-1, 0, 0); // posiciona a peça a esquerda
        
        if(!ValidMove())
        {
            transform.position -= new Vector3(-1, 0, 0);
            return false;
        }
        return true;
    }
    
    public bool MoveRight()
    {
        transform.position += new Vector3(1, 0, 0); // posiciona a peça a direita

        if(!ValidMove())
        {
            transform.position -= new Vector3(1, 0, 0);
            return false;
        }
        return true;
    }

    public bool Rotate()
    {
        transform.RotateAround(transform.TransformPoint(rotationPoint), new Vector3(0,0,1), 90); // Rotação da peça

        if(!ValidMove())
        {
            transform.RotateAround(transform.TransformPoint(rotationPoint), new Vector3(0,0,1), -90);
            return false;
        }
        return true;
    }

    // public bool MoveDown()
    // {
    //     transform.position += new Vector3(0, -1, 0); // posiciona a peça para baixo

    //     if(!ValidMove())
    //     {
    //         transform.position -= new Vector3(0, -1, 0);// pode estar faltando essa verificação na função
    //         AddToGrid();
    //         CheckForLines();
            
    //         this.enabled = false;
    //         FindObjectOfType<SpawnPecas>().NewPecas();
    //         return false;
    //     }
    //     return true;
    // }

    public bool MoveDown()
    {
        transform.position += new Vector3(0, -1, 0); // posiciona a peça para baixo

        if(!ValidMove())
        {
            transform.position -= new Vector3(0, -1, 0);// pode estar faltando essa verificação na função
            AddToGrid();
            Evaluate();
            RemoveToGrid();
            
            return false;
        }
        return true;
    }
    
    public bool MoveUp()
    {
        transform.position += new Vector3(0, 1, 0); // posiciona a peça para baixo

        if(!ValidMove())
        {
            transform.position -= new Vector3(0, 1, 0);// pode estar faltando essa verificação na função
            
            return false;
        }
        return true;
    }

    public void CheckForLines()
    {
        /*for(int a = 0; a < width; a++)// fecha a aplicação caso o bloco esteja na ultima linha
        {
            if(grid[a,height-1] == null)
                Application.Quit();
        }*/

        for (int i = height-1; i >= 0; i--)
        {
            if(Hasline(i))
            {
                DeleteLine(i);
                RowDown(i);
                qlinhas++;
            }
        }
        if(qlinhas==1) //eliminou 1 linha
            Pontuacao.score += scoreValue; // score soma 50
        else if(qlinhas==2) //eliminou 2 linha concecultivas
            Pontuacao.score += scoreValue*4; // score soma 200
        else if(qlinhas==3) //eliminou 3 linha concecultivas
            Pontuacao.score += scoreValue*16; // score soma 800
        else if(qlinhas==4) //eliminou 4 linha concecultivas
            Pontuacao.score += scoreValue*64; // score soma 3200
        qlinhas=0;
    }

    public bool Hasline(int i)
    {
        for(int j = 0; j < width; j++)
        {
            if (grid[j,i] == null)
                return false;
        }
        return true;
    }

    public void DeleteLine(int i)
    {
        for(int j = 0; j < width; j++)
        {
            Destroy(grid[j,i].gameObject);
            grid[j,i] = null;
        }
        
    }

    public void RowDown(int i)
    {
        for(int y = i; y < height; y++)
        {
            for (int j = 0; j < width; j++)
            {
                if(grid[j,y] != null)
                {
                    grid[j, y - 1] = grid[j, y];
                    grid[j, y] = null;
                    grid[j, y - 1].transform.position -= new Vector3(0, 1, 0);
                }
            }
        }

    }

    public void AddToGrid()
    {
        foreach (Transform children in transform)
        {
            int roundedX = Mathf.RoundToInt(children.transform.position.x);
            int roundedY = Mathf.RoundToInt(children.transform.position.y);
            
            grid[roundedX,roundedY] = children;
        }
    }
    
    public void RemoveToGrid()
    {
        foreach (Transform children in transform)
        {
            int roundedX = Mathf.RoundToInt(children.transform.position.x);
            int roundedY = Mathf.RoundToInt(children.transform.position.y);
            
            grid[roundedX,roundedY] = null;
        }
    }

    public bool ValidMove() // para validar um movimento
    {
        foreach (Transform children in transform)
        {
            int roundedX = Mathf.RoundToInt(children.transform.position.x);
            int roundedY = Mathf.RoundToInt(children.transform.position.y);
            //se a peça estiver nas bordas o movimento não deve ser execultado
            if(roundedX < 0 || roundedX >= width || roundedY < 0 || roundedY >= height)
            {
                return false;
            }

            if(grid[roundedX, roundedY] != null)
                return false;
        }

        return true;
    }

    public void ClearGrid()
    {
        for(int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if(grid[j,i] != null)
                {
                    Destroy(grid[j,i].gameObject);
                    grid[j,i] = null;
                }
            }
        }
    }

    public void EndGame()
    {
        ClearGrid();
        Population.instance.Evaluate(Pontuacao.score);
        Pontuacao.score = 0;
        Population.instance.NextIndividual();
        Destroy(this.gameObject);
    }

    // IEnumerator TestPositions()
    public void TestPositions()
    {
        Vector3 initPosition;
        int mostRight = 0;
        bool tp = false;

        initPosition = transform.position;

        for(int i = 0; i < nRotates; i++)
        {
            // yield return new WaitForSeconds(0.01f);
            transform.position = initPosition;
            Rotate();
            // Debug.Log(nPiece + ":R");

            mostRight = 0;
            foreach (Transform children in transform)
            {
                if(Mathf.RoundToInt(children.transform.position.x) > mostRight)
                    mostRight = Mathf.RoundToInt(children.transform.position.x);
            }

            while(MoveLeft())
            {
                mostRight--;
                // Debug.Log(nPiece + ":E1");
                // yield return new WaitForSeconds(0.01f);
            }
            
            while(MoveDown())
            {
                // Debug.Log(nPiece + ":B1");

                if(MoveLeft())
                {
                    // Debug.Log(nPiece + ":E2");
                    mostRight--;
                }
                // yield return new WaitForSeconds(0.01f);
            }

            tp = false;
            while(mostRight < width-1)
            {
                while(!MoveRight())
                {
                    // yield return new WaitForSeconds(0.01f);
                    // Debug.Log(nPiece + ":!D");
                    if(!MoveUp())
                    {
                        // Debug.Log(nPiece + ":!C");
                        if(!tp)
                        {
                            tp = true;

                            float x = transform.position.x;
                            transform.position = initPosition;
                            while(transform.position.x < x)
                                MoveRight();
                            while(transform.position.x > x)
                                MoveLeft();
                            
                            while(MoveDown())
                            {
                                // Debug.Log(nPiece + ":B2");
                                // yield return new WaitForSeconds(0.01f);
                                
                            }
                        }
                        else
                        {
                            mostRight = 10;
                            break; 
                        }
                    }
                    // else
                        // Debug.Log(nPiece + ":C");

                }
                // yield return new WaitForSeconds(0.01f);
                // Debug.Log(nPiece + ":D");
                mostRight++;
                
                while(MoveDown())
                {
                    if(MoveLeft())
                    {
                        mostRight--;
                        // Debug.Log(nPiece + ":E3");
                    }
                    // Debug.Log(nPiece + ":B3");
                    // yield return new WaitForSeconds(0.01f);
                    
                }
            }
        }

        transform.position = bestPosition;
        transform.rotation = bestRotation;
        AddToGrid();
        CheckForLines();
        this.enabled = false;
        FindObjectOfType<SpawnPecas>().NewPecas();
    }

    public void Evaluate()
    {
        int holes = 0, bumpiness = 0, heightPieces = 0;
        int colHeight = 0;
        int colHeightPrev = 0;

        for(int i = 0; i < width; i++)
        {
            colHeight = 0;
            for(int j = height-1; j >= 0; j--)
            {
                if(colHeight == 0)
                {
                    if(TetrisBlock.grid[i,j] != null)
                    {
                        colHeight = j+1;
                    }
                }
                else
                {
                    if(TetrisBlock.grid[i,j] == null)
                    {
                        holes++;
                    }
                }
            }
            // Debug.Log("line" + i + "col:" + colHeight);

            if(heightPieces < colHeight)
                heightPieces = colHeight;

            if(i != 0)
            {
                if(colHeight - colHeightPrev < 0)
                    bumpiness += colHeightPrev - colHeight;
                else    
                    bumpiness += colHeight - colHeightPrev;
            }
            colHeightPrev = colHeight;
        }

        // Debug.Log("void:" + holes + ", bumpiness:" + bumpiness + ", height:" + heightPieces);
        var score = SpawnPecas.instance.AI(holes, bumpiness, heightPieces);
        // Debug.Log(score);

        if(score < bestScore)
        {
            bestScore = score;
            bestPosition = transform.position;
            bestRotation = transform.rotation;
        }
    }
}
