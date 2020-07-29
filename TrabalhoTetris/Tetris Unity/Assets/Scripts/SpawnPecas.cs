using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpawnPecas : MonoBehaviour
{
    public GameObject[] Pecas;
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

    public void NewPecas()
    {
        int rand = Random.Range(0,Pecas.Length);
        
        Instantiate(Pecas[rand], transform.position, Quaternion.identity);
    }

    public double AI(int holes, int bumpiness, int height)
    {
        return player.Decision(holes, bumpiness, height);
    }

    public void NextPlayer(Indiv1 player)
    {
        this.player = player;
    }
}
