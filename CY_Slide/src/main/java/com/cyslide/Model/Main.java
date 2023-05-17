package com.cyslide.Model;
// ce main permet de tester nos fonction java
public class Main{
    public static void main(String[] args){
        Level level = new Level(9);
        for (int i=0; i < level.getTable().length; i++){
            for (int j=0; j < level.getTable().length; j++){
                System.out.println("case [" + i + "][" + j +"]");
                level.getTable()[i][j].showTile();
            }
        }
        System.out.println("record du niveau " + level.getNumber() + " est : " + level.getRecord());
    }
}