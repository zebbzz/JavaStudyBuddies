package javastudybuddies.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Leetcode728SelfDividingNumbers {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.selfDividingNumbers(5, 22));
    }

    static class Solution {
        public List<Integer> selfDividingNumbers(int left, int right) {
            right = Math.min(right, 10000);
            left = Math.max(left, 1);

            ArrayList<Integer> result = new ArrayList<>();
            outer: for (int i = left; i <= right; i++) {
                int divided = i;
                while (divided != 0) {
                    if (divided%10 == 0 || i % (divided % 10) != 0) {
                        continue outer;
                    }

                    divided-=(divided%10);
                    divided/=10;
                }

                result.add(i);
            }

            return result;
        }
    }
}