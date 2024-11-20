package ch5;

import java.util.Arrays;

public class example1 {
    public static void main (String[] args) {
        int[] arr = {1,2,3,4,5,5,6,7,8,9};
        int[] arr2 = new int[10];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = (int)(Math.random() *10) + 1;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        System.out.println(Arrays.toString(arr));
        System.out.println(arr2);
    }

}
