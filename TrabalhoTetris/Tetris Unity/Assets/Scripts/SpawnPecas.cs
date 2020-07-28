using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpawnPecas : MonoBehaviour
{
    public GameObject[] Pecas;
    private TetrisBlock currentPiece;
    [SerializeField] private Indiv player;
    [SerializeField] private int currentPieceIndex;
    private int[] surface;

    void Start()
    {
        surface = new int[10];
        NewPecas();
    }

    void Update()
    {
        currentPiece.MoveDown();
    }

    public void NewPecas()
    {
        int rand = Random.Range(0,Pecas.Length);
        
        GameObject gO = Instantiate(Pecas[rand], transform.position, Quaternion.identity);
        currentPiece = gO.GetComponent<TetrisBlock>();

        AI();
    }

    public void AI()
    {
        for(int i = 0; i < TetrisBlock.width; i++)
        {
            for(int j = 0; j < TetrisBlock.height; j++)
            {
                if(TetrisBlock.grid[i,j] != null){
                    surface[i] = j;
                    j = TetrisBlock.height;
                }
            }
        }

        double decision = player.ComandDecision(currentPieceIndex, surface);

        while(decision >= 10)
        {
            currentPiece.Rotate();
            decision -= 10;
        }

        if(decision >= 6)
        {
            while(decision >= 6)
            {
                currentPiece.MoveRight();
                decision -= 1;
            }
        }
        else
        {
            while(decision > 0)
            {
                currentPiece.MoveLeft();
                decision -= 1;
            }
        }
    }
}
