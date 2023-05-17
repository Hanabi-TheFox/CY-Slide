package com.cyslide.Model;
// ce main permet de tester nos fonction java
public class Main{
    public static void main(String[] args) throws PlayerPseudoException{
        Record record = new Record(new Player("Ymasuu"), 1, 0);
        Level level = new Level(3, record);
    }
}