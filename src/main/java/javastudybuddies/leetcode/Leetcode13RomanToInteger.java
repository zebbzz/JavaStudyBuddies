package javastudybuddies.leetcode;

//Symbol       Value
//I             1
//V             5
//X             10
//L             50
//C             100
//D             500
//M             1000

//I can be placed before V (5) and X (10) to make 4 and 9.
//X can be placed before L (50) and C (100) to make 40 and 90.
//C can be placed before D (500) and M (1000) to make 400 and 900.

import java.util.HashMap;
import java.util.Map;

public class Leetcode13RomanToInteger {
        public static void main(String[] args)  {
            Solution solution = new Solution();
            System.out.println(solution.romanToInt("III"));
            System.out.println(solution.romanToInt("IV"));
            System.out.println(solution.romanToInt("IX"));
            System.out.println(solution.romanToInt("IX"));
            System.out.println(solution.romanToInt("LVIII"));
            System.out.println(solution.romanToInt("MCMXCIV"));
        }
}

class Solution  {
    private  Map<Character, Integer> map = new HashMap<>();

     {
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
    }

    public int romanToInt(String s) {
            int result = 0;
            boolean add = true;

            Character nextLetter = 'I';
            for (int i=s.length()-1; i>=0; i--)  {
                char letter = s.charAt(i);

                if (map.get(letter) < map.get(nextLetter))  {
                    result-=map.get(letter);
                }
                else  {
                    result+=map.get(letter);
                }

                nextLetter = s.charAt(i);
            }

            return result;
    }
}





