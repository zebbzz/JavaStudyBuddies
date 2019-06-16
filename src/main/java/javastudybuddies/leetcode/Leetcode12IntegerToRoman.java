package javastudybuddies.leetcode;

import java.util.HashMap;
import java.util.Map;

public class Leetcode12IntegerToRoman {
        public static void main(String[] args)  {

        }

        static class Solution {
            private Map<Integer, String> map = new HashMap<>();

            {
                map.put(1, "I");
                map.put(5, "V");
                map.put(10, "X");
                map.put(50, "L");
                map.put(100, "C");
                map.put(500, "D");
                map.put(1000, "M");

                /*map.put(4, "IV");
                map.put(9, "IX");
                map.put(40, "XL");
                map.put(90, "XC");
                map.put(400, "CD");
                map.put(900, "CM");*/
            }

            public String intToRoman(int num) {
                int number = num%10;

                return "";
            }
        }
}

