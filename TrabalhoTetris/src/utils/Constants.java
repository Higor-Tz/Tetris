package utils;

import java.io.File;

/**
 * Classe Constants
 * 
 * Utilizada para armazenar as constantes do jogo
 *
 */
public class Constants {
    //SIZE CONSTANTS

    /**
     *
     */
    public static final int CELL_SIZE = 30;

    /**
     *
     */
    public static final int NUM_CELLS = 20;

    /**
     *
     */
    public static final int HEIGHT = 18;

    /**
     *
     */
    public static final int WIDTH = 10;
    
    //PATH CONSTANTS

    /**
     *
     */
    public static final String IMG_PATH = File.separator+"images"+File.separator;
    
    //TIME CONSTANTS

    /**
     *
     */
    public static final int DELAY_SCREEN_UPDATE = 20;

    /**
     *
     */
    public static final double ACELERATION_RATE = 15;
    
    /**
     *
     */
    public static final int BASE_FALL_TICKS = 25;

    /**
     *
     */
    public static final double FALL_TICKS_REDUCTION_RATE = 0.7;
    
    //SHAPES
    //Matriz de rotação e tipo das peças. Parâmetros: [shape][rotation][i][j]

    /**
     *
     */
    public static final int [][][][] BLOCK_SHAPES = {
        {{{1,1},{1,1}}, {{1,1},{1,1}},  {{1,1},{1,1}},  {{1,1},{1,1}}},     //O
        {{{0,0,0,0},{0,0,0,0},{1,1,1,1},{0,0,0,0}}, {{0,0,1,0},{0,0,1,0},{0,0,1,0},{0,0,1,0}}, {{0,0,0,0},{0,0,0,0},{1,1,1,1},{0,0,0,0}}, {{0,1,0,0},{0,1,0,0},{0,1,0,0},{0,1,0,0}}},      //I
        {{{0,1,0},{1,1,1},{0,0,0}},    {{0,1,0},{0,1,1},{0,1,0}}, {{0,0,0},{1,1,1},{0,1,0}}, {{0,1,0},{1,1,0},{0,1,0}}},       //T
        {{{0,1,1},{1,1,0},{0,0,0}},    {{0,1,0},{0,1,1},{0,0,1}}, {{0,0,0},{0,1,1},{1,1,0}}, {{1,0,0},{1,1,0},{0,1,0}}},     //S
        {{{1,1,0},{0,1,1},{0,0,0}},    {{0,0,1},{0,1,1},{0,1,0}}, {{0,0,0},{1,1,0},{0,1,1}}, {{0,1,0},{1,1,0},{1,0,0}}},    //Z
        {{{1,0,0},{1,1,1},{0,0,0}},    {{0,1,1},{0,1,0},{0,1,0}}, {{0,0,0},{1,1,1},{0,0,1}}, {{0,1,0},{0,1,0},{1,1,0}}},    //J
        {{{0,0,1},{1,1,1},{0,0,0}},    {{0,1,0},{0,1,0},{0,1,1}}, {{0,0,0},{1,1,1},{1,0,0}}, {{1,1,0},{0,1,0},{0,1,0}}}     //L
    };
    
    //NÚMERO DE TEXTURAS DE PEÇAS

    /**
     *
     */
    public static final int NUM_OF_PIECES = 8;
    
}
