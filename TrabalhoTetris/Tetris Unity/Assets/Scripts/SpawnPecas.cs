using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SpawnPecas : MonoBehaviour
{
    public GameObject[] Pecas;
    // Start is called before the first frame update
    void Start()
    {
        NewPecas();
    }

    public void NewPecas()
    {
        Instantiate(Pecas[Random.Range(0,Pecas.Length)], transform.position, Quaternion.identity);
    }
}
