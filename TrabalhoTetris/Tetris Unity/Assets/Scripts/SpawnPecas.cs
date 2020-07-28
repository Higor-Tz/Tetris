using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpawnPecas : MonoBehaviour
{
    public GameObject[] Pecas;
    private TetrisBlock currentPiece;
    [SerializeField] private Indiv1 player;
    public static SpawnPecas instance;

    void Awake()
    {
		if(instance!=null)
        {
			Debug.LogError("Mais de um SpawnPecas");
			return;
		}

		instance = this;
	}

    void Start()
    {
        //surface = new int[10];
        NewPecas();
    }

    public void NewPecas()
    {
        int rand = Random.Range(0,Pecas.Length);
        
        GameObject gO = Instantiate(Pecas[rand], transform.position, Quaternion.identity);
        currentPiece = gO.GetComponent<TetrisBlock>();

        //AI();
    }

    public double AI(int holes, int bumpiness, int height)
    {
        return player.Decision(holes, bumpiness, height);
    }

    // public void NextPlayer(Indiv player)
    // {
    //     this.player = player;
    // }
}
