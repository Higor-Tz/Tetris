﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpawnPecas : MonoBehaviour
{
    public GameObject[] pecas;
    [SerializeField] private Indiv1 player;
    public static SpawnPecas instance;
    private Random rand;
    private int seed = 42;

    void Awake()
    {
		if(instance!=null)
        {
			Debug.LogError("Mais de um SpawnPecas");
			return;
		}

		instance = this;
        Random.seed = seed;
	}

    public void Reset()
    {
        Random.seed = seed;
        Random.Range(0,pecas.Length);
    }

    public void NewPecas()
    {
        int rand = Random.Range(0,pecas.Length);
        
        Instantiate(pecas[rand], transform.position, Quaternion.identity);
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
