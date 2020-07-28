using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TetrisBlock : MonoBehaviour
{
    public Vector3 rotationPoint; // rotação da peça
    private float previousTime; // tempo de queda
    public float fallTime = 0.8f; // tempo de queda automática
    public static int height = 20; // linhas
    public static int width = 10; // colunas
    public static Transform[,] grid = new Transform [width,height];

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update() // Atualização a cada frame de jogo
    {
        if(Input.GetKeyDown(KeyCode.LeftArrow)) // checa se a esquerda foi precionado
        {
            MoveLeft();
        }
        else if (Input.GetKeyDown(KeyCode.RightArrow)) // checa se a direita foi precionado
        {
            MoveRight();
        }
        else if (Input.GetKeyDown(KeyCode.UpArrow))
        {   
            Rotate();
        }

        if(Time.time - previousTime > (Input.GetKeyDown(KeyCode.DownArrow) ? (fallTime / 10) : fallTime)) // quando precionado para baixo diminui o tempo de queda
        {
            MoveDown();
            previousTime = Time.time;
        }
    }

    public void MoveLeft()
    {
        transform.position += new Vector3(-1, 0, 0); // posiciona a peça a esquerda
        
        if(!ValidMove())
            transform.position -= new Vector3(-1, 0, 0);
    }
    
    public void MoveRight()
    {
        transform.position += new Vector3(1, 0, 0); // posiciona a peça a direita

        if(!ValidMove())
            transform.position -= new Vector3(1, 0, 0);
    }

    public void Rotate()
    {
        transform.RotateAround(transform.TransformPoint(rotationPoint), new Vector3(0,0,1), 90); // Rotação da peça

        if(!ValidMove())
            transform.RotateAround(transform.TransformPoint(rotationPoint), new Vector3(0,0,1), -90);
    }

    public void MoveDown()
    {
        transform.position += new Vector3(0, -1, 0); // posiciona a peça para baixo

        if(!ValidMove())
        {
            transform.position -= new Vector3(0, -1, 0);// pode estar faltando essa verificação na função
            AddToGrid();
            CheckForLines();
            
            this.enabled = false;
            FindObjectOfType<SpawnPecas>().NewPecas();
        }
    }

    public void CheckForLines()
    {
        for (int i = height-1; i >= 0; i--)
        {
            if(Hasline(i))
            {
                DeleteLine(i);
                RowDown(i);
            }
        }
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
}
