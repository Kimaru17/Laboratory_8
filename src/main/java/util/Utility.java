package util;

import domain.Complex;
import domain.Elementary;

import java.text.DecimalFormat;
import java.util.Random;

public class Utility {

    //static init
    static {
    }

    public static String format(double value){
        return new DecimalFormat("###,###,###.##").format(value);
    }
    public static String $format(double value){
        return new DecimalFormat("$###,###,###.##").format(value);
    }

    public static void fill(int[] a, int bound) {
        for (int i = 0; i < a.length; i++) {
            a[i] = new Random().nextInt(bound);
        }
    }

    public static int getRandom(int bound) {
        return new Random().nextInt(bound)+1;
    }

    public static int compare(Object a, Object b) {
        switch (instanceOf(a, b)){
            case "Integer":
                Integer int1 = (Integer)a; Integer int2 = (Integer)b;
                return int1 < int2 ? -1 : int1 > int2 ? 1 : 0; //0 == equal
            case "String":
                String st1 = (String)a; String st2 = (String)b;
                return st1.compareTo(st2)<0 ? -1 : st1.compareTo(st2) > 0 ? 1 : 0;
            case "Character":
                Character c1 = (Character)a; Character c2 = (Character)b;
                return c1.compareTo(c2)<0 ? -1 : c1.compareTo(c2)>0 ? 1 : 0;

        }
        return 2; //Unknown
    }

    private static String instanceOf(Object a, Object b) {
        if(a instanceof Integer && b instanceof Integer) return "Integer";
        if(a instanceof String && b instanceof String) return "String";
        if(a instanceof Character && b instanceof Character) return "Character";
        return "Unknown";
    }

    public static int maxArray(int[] a) {
        int max = a[0]; //first element
        for (int i = 1; i < a.length; i++) {
            if(a[i]>max){
                max=a[i];
            }
        }
        return max;
    }

    public static void sortElementaryArrays(String algorithm, int[] array) {

        Complex complex = new Complex();

        switch (algorithm) {
            case "bubbleSort":
                Elementary.bubbleSort(array);
                break;

            case "improvedBubbleSort":
                Elementary.improvedBubbleSort(array);
                break;

            case "selectionSort":
                Elementary.selectionSort(array);
                break;

            case "countingSort":
                Elementary.countingSort(array);
                break;

            case "quickSort":
                // complex.quickSort(array);
        }
    }

    public static int[] getIntegerArray(int n){
        int arrayResult[] = new int[n];

        for (int i = 0; i < n; i++)
            arrayResult[i] = getRandom(9999);

        return arrayResult;
    }
    public static int[] copyArray(int[] a, int length) {
        int[] result = new int[length];

        // Solo copiar hasta el tamaño del arreglo original
        for (int i = 0; i < Math.min(a.length, length); i++)
            result[i] = a[i];

        return result;
    }
}
