# Tetris Unity

Tetris Game (Game Remaster Version) with AI

## Introduction:
This repository is a remastering of the original 1984 Tetris game and application of the evolutionary self-organization learning of the pieces, in order to obtain an optimal score.

## Description
Tetris is a game that consists of a 10x20 board where pieces of the tetramino type will be stacked (four identical squares, arranged according to the rule of poliminós), they will fall into the board, when a line of blocks is completed this line disappears, with all blocks above this line going down one level.

Each completed line is scored 50 points, if two are completed at the same time the point value obtained for each completed line is doubled, if there are three lines, the previous score is quadrupled and if four lines are filled at once with the piece “ I ”, the total the score 3200 points is added, in other words, four times the previous score.

The game ends when one of the pieces crosses the top of the board.

## Genetic Algorithm and Crossover
We selected 3 pieces of information that the AI should take into account:

The number of holes on the board;

The irregularity of the surface;

The maximum height of the pieces.

All of these parameters are multiplied by weights so that the AI makes a decision, to choose these weights a genetic algorithm was used.

In the genetic algorithm, after putting each individual to play we select the best and cross the others with him, a crossover in which the child will be the average between the parents, we apply a mutation, this has a 100% chance of occurring (we start with 1 % mutation, but it is possible to increase during execution if you see that the population is trapped in a maximum location), and then we repeat the process for several generations to find the best AI that plays the tetris.

## Dependencies:
 - *Unity*
#### How to install:
To compile and run this program, you need to install the Unity development platform, in version 2019.4.4 (or higher), we use Visual Studio Code to write the scripts in C #.
Both Programs can be installed through the links:

https://unity3d.com/pt/unity/whats-new/2019.4.4

https://code.visualstudio.com/

#### How to run:
After installing of the dependencies, go to the directory inside the folder "TabalhoTetris" and download "Tetris Unity".

Begin Unity with any self account or existing Google account, open the project in "Tetris Unity", check whether the GAME and SCENE  tab's is visible and run normally or program by clicking on the "▶" BUTTON.

The evolutionary process of the game will be visible in the execution.

## Results:
https://youtu.be/rdDWwyJX9VU (video in Portuguese-Br)
