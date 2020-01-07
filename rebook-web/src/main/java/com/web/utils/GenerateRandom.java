package com.web.utils;

public class GenerateRandom {

  public static int randomNewsId() {
    int min=1;
    int max=20;
    System.out.println("Random value in int from "+min+" to "+max+ ":");
    int random_int = (int )(Math.random() * max + min);
    System.out.println(random_int);
    return random_int;
  }

}
