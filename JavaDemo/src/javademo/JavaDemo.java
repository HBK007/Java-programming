/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javademo;

import java.util.ArrayList;

/**
 *
 * @author Nguyen Duc Hai
 */
public class JavaDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<String> arr = new ArrayList<>();
        arr.add("1,2f,3d,10:00:00");
        arr.add("2,2f,3d,10:00:01");
        arr.add("3,3r,4d,23:00:00");
        System.out.println("Arr1: " + arr);
        String temp = "1,2f,3d,10:00:03";
        if (!arr.contains(temp)) {
            arr.add(temp);
        }
        System.out.println("Arr2: " + arr);
        updateGpsDevice(arr);
        System.out.println("Arr3: " + arr);
        System.out.print("Arr4: ");
        for(int i = 0; i < arr.size(); i++)
            System.out.print(arr.get(i));
        System.out.println("");

    }

    private static void updateGpsDevice(ArrayList<String> arr) {
        int count = arr.size();
        // slipt Gps signal to array string
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                String[] strPartsOne = arr.get(i).split(",");
                for (int j = i + 1; j < count; j++) {
                    String[] strPartsSecond = arr.get(j).split(",");
                    if (strPartsOne[0].equals(strPartsSecond[0])) {
                        String temp = strPartsSecond[0] + "," + strPartsSecond[1]
                                + "," + strPartsSecond[2] + "," + strPartsSecond[3];
                        arr.set(i, temp);
                        arr.remove(j);
                        count--;
                    }
                }
            }
        }
    }

}
