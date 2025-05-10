package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortingTest {

    //Elementary Test
    @Test
    void elementaryTest() {
        System.out.println("Elementary Test");
        System.out.println("\n");

        int[] a = util.Utility.getIntegerArray(10000);
        int[] b = util.Utility.copyArray(a, a.length);

        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println(elementarySorting("bubbleSort", a, 50));
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println(elementarySorting("improvedBubbleSort", b, 100));
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println(elementarySorting("selectionSort", util.Utility.getIntegerArray(10000), 150));
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println(elementarySorting("countingSort", util.Utility.getIntegerArray(10000), 200));
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
    }
    private String elementarySorting(String algorithm, int[] array, int n) {

        String result = "";
        result += "Algorithm: " + algorithm+"\n";
        result += "Original Array: ";

        //En caso de que el array sea menor a n
        if (array.length < n) {
            for (int i = 0; i < array.length; i++)
                result += array[i] + ", ";

        } else {
            for (int i = 0; i <= n; i++)
                result += array[i] + ", ";

        }

        result += "\n" + "Sorted Array: ";
        util.Utility.sortElementaryArrays(algorithm, array);

        //Si el array es menor a n
        if (array.length < n) {
            for (int i = 0; i < array.length; i++)
                result += array[i] + ", ";

        } else {
            for (int i = 0; i <= n; i++)
                result += array[i] + ", ";

        }

        String resultCount = result;

        result += "\n" + "Total Iteractions: " + util.Utility.format(Elementary.getTotalIteractions());
        result += "\n" + "Total Changes: " + util.Utility.format(Elementary.getTotalChanges());


        //En caso de ser countingSort retorna también el contador
        if (util.Utility.compare(algorithm, "countingSort") == 0){
            resultCount += "\n" + "Counter array: ";

            if(array.length < n) {
                for (int i = 0; i < array.length; i++)
                    resultCount += Elementary.getCount()[i] + ", ";

            }else {
                for (int i = 0; i < n; i++)
                    resultCount += Elementary.getCount()[i] + ", ";

            }

            return resultCount;
        }
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void complexTest() {
        System.out.println("Complex Test");
        System.out.println("\n");

        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println(complexSorting("quickSort", util.Utility.getIntegerArray(20), 20));
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println(complexSorting("radixSort", util.Utility.getIntegerArray(20), 20));
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println(complexSorting("mergeSort", util.Utility.getIntegerArray(20), 20));
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println(complexSorting("shellSort", util.Utility.getIntegerArray(20), 20));
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
    }
    private String complexSorting(String algorithm, int[] array, int n) {
        Complex complex = new Complex();
        int[] copy = util.Utility.copyArray(array, array.length);
        StringBuilder result = new StringBuilder();

        // Imprimir el arreglo original
        result.append("Algorithm: ").append(algorithm).append("\n");
        result.append("Original Array: ");
        if (array.length <= n) {
            for (int i = 0; i < array.length; i++)
                result.append(array[i]).append(", ");
        } else {
            for (int i = 0; i < n; i++)
                result.append(array[i]).append(", ");
        }
        result.append("\n");

        // Ejecutar el algoritmo de ordenamiento correspondiente
        switch (algorithm) {
            case "quickSort":
                complex.quickSort(copy, 0, copy.length - 1);
                result.append("Low: ").append(complex.getLowsString()).append("\n");
                result.append("High: ").append(complex.getHighsString()).append("\n");
                result.append("Pivot: ").append(complex.getHighsString()).append("\n");
                result.append("Recursive Calls: ").append(complex.getRecursionCount()).append("\n");
                break;

            case "radixSort":
                complex.radixSort(copy, copy.length);
                // Mostrar el Counter Array para Radix Sort
                result.append("Counter Array: ");
                int[] counter = complex.getCounterRadix();
                for (int i = 0; i < counter.length; i++) {
                    result.append(counter[i]);
                    if (i < counter.length - 1) result.append(", ");
                }
                result.append("\n");
                break;

            case "mergeSort":
                int[] temp = new int[copy.length];
                complex.mergeSort(copy, temp, 0, copy.length - 1);
                // Mostrar Low y High para Merge Sort
                result.append("Low: ").append(complex.getLowsString()).append("\n");
                result.append("High: ").append(complex.getHighsString()).append("\n");
                result.append("High: ").append(complex.getPivot()).append("\n");
                result.append("Recursive Calls: ").append(complex.getRecursionCount()).append("\n");
                break;

            case "shellSort":
                complex.shellSort(copy);
                result.append("Gap: ").append(complex.getGapS().trim()).append("\n");
                result.append("Gap subArray1: ").append(complex.getGap1().trim()).append("\n");
                result.append("Gap subArray2: ").append(complex.getGap2().trim()).append("\n");
                result.append("Gap subArray3: ").append(complex.getGap3().trim()).append("\n");
                break;

            default:
                return "Unknown sorting algorithm!";
        }

        // Imprimir el arreglo ordenado
        result.append("Sorted Array: ");
        for (int i = 0; i < Math.min(n, copy.length); i++) {
            result.append(copy[i]);
            if (i < Math.min(n, copy.length) - 1) result.append(", ");
        }
        result.append("\n");
        result.append("_\n");
        return result.toString();
    }
}