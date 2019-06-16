package javastudybuddies.leetcode;

import java.util.Arrays;

public class Leetcode455AssignCookies {
    public static void main(String[] args) {
            Solution solution = new Solution();
            System.out.println(solution.findContentChildren(new int[]{ 1, 2, 3}, new int[]{1, 91}));
            System.out.println(solution.findContentChildren(new int[]{ 1, 2}, new int[]{1, 2, 3}));
            System.out.println(solution.findContentChildren(new int[]{ 10, 9, 8, 7}, new int[]{5, 6, 7, 8}));
            System.out.println(solution.findContentChildren(new int[]{ 10, 9, 8, 7}, new int[]{ 10, 9, 8, 7}));
            System.out.println(solution.findContentChildren(new int[]{ 10, 9, 8, 7, 10, 9, 8, 7}, new int[]{ 10, 9, 8, 7}));

           // Solution2 solution2 = new Solution2();
          //  System.out.println(solution2.findContentChildren(new int[]{ 10, 9, 8, 7,10, 9, 8, 7}, new int[]{ 10, 9, 8, 7}));
    }

    static class Solution2 {
        public int findContentChildren(int[] g, int[] s) {
            int result = 0;

            if (s.length==0 || g.length==0)   {
                return 0;
            }

            Arrays.sort(g);
            Arrays.sort(s);

            for (int e: g)  {
                System.out.print(e + ";");
            }

            System.out.println();
            for (int e: s)  {
                System.out.print(e + ";");
            }

            System.out.println();
            int firstCookie = (s.length == 1 || s[0]<=s[1]) ? 0 : s.length-1;
            int cookieIncrement = firstCookie == 0 ? 1 : -1;

            int firstChild = (g.length == 1 || g[0]<=g[1]) ? 0 : g.length-1;
            int childIncrement = firstChild == 0 ? 1 : -1;

            outer: for (int child = firstChild; (child<g.length && child >= 0); child+=childIncrement)  {
                for (int cookie = firstCookie; (cookie<s.length && cookie >= 0); cookie+=cookieIncrement)  {
                    if (s[cookie]>=g[child])  {
                        System.out.println("cookie#" + s[cookie] + "; child#" + g[child]);
                        result++;
                        firstCookie = cookie+cookieIncrement;
                        continue outer;
                    }

                    // return result;
                }
            }

            return result;
        }
    }

    static class Solution {
        public int findContentChildren(int[] g, int[] s) {
            int result = 0;

            if (s.length==0 || g.length==0)   {
                return 0;
            }

            for (int child = 0; child<g.length; child++)  {
                int previousCookie = 0;
                int cookieIndex = -1;

                for (int cookie = 0; cookie < s.length; cookie++)  {
                    if (s[cookie] >= g[child] && (cookieIndex==-1 || s[cookie]<=previousCookie))  {
                            cookieIndex = cookie;
                            previousCookie = s[cookieIndex];
                    }
                }

                if (cookieIndex!=-1)  {
                    result++;
                    s[cookieIndex] = 0;
                }
            }

            return result;
        }
    }

}
