package com.cyslide.Model;
// ce main permet de tester nos fonction java
public class Main{
    public static void main(String[] args) throws PlayerPseudoException{
        Level level = new Level(8,-1);
        for (int i=0; i < level.getTable().length; i++){
            for (int j=0; j < level.getTable().length; j++){
                level.getTable()[i][j].showTile();
            }
        }
    }
}