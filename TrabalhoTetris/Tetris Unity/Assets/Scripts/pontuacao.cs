using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Pontuacao : MonoBehaviour
{
    public static int score;
    public Text text;
    // Start is called before the first frame update
    void Start()
    {
        score = 0;// pontuação inicial
    }

    void Update()
    {
        text.text = "Pontuação: " + score;
    }
}
