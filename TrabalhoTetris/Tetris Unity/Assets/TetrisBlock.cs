using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TetrisBlock : MonoBehaviour
{
    public Vector3 rotationPoint; // rotação da peça
    private float previusTime; // tempo de queda
    public float fallTime = 0.8f; // tempo de queda automática
    public static int height = 20; // linhas
    public static int width = 10; // colunas


    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update() // Atualização a cada frame de jogo
    {
        if(Input.GetkeyDown(KeyCode.LeftArrow)) // checa se a esquerda foi precionado
        {
            transform.position += new Vector3(-1, 0, 0); // posiciona a peça a esquerda
            if(!ValidMove())
                transform.position -= new Vector3(-1, 0, 0);
        }
        else if (Input.GetkeyDown(KeyCode.RightArrow)) // checa se a direita foi precionado
        {
            transform.position += new Vector3(1, 0, 0); // posiciona a peça a direita
            if(!ValidMove())
                transform.position -= new Vector3(1, 0, 0);
        }
        else if (Input.GetKeyDown(KeyCode.UpArrow))
        {   //Rotação da peça
            transform.RotateAround(transform.TransformPoint(rotationPoint), new Vector3(0,0,1), 90);
            if(!ValidMove())
                transform.position -= new Vector3(transform.TransformPoint(rotationPoint), new Vector3(0,0,1), -90);
        }


        if(Time.time - previousTime > (Imput.Getkey(/keyCode.DownArrow) ? fallTime / 10) : fallTime)) // quando precionado para baixo diminui o tempo de queda
        {
            transform.position += new Vector3(0, -1, 0); // posiciona a peça para baixo
            if(!ValidMove())
                transform.position -= new Vector3(0, -1, 0);// pode estar faltando essa verificação na função
            previousTime = Time.time;
        }

        bool ValidMove() // para validar um movimento
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
            }

            return true;
        }

    }
}
