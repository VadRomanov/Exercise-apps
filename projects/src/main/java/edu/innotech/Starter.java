package edu.innotech;

import java.util.List;

public class Starter {
    public static void main(String[] args) {
        List<Integer> names = List.of(1,2,3,4,5);
        for (Object i : names.stream().sorted((o1, o2) -> o2-o1).toArray()) {
            System.out.println(i);
        }
    }
}
