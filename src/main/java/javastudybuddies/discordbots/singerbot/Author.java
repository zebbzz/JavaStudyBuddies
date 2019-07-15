package javastudybuddies.discordbots.singerbot;

public class Author {
        private String name;

        public Author()  {}
        public Author(String name)  {this.name = name;}

        //getters
        public String getName()  {return name;}

        //setters
        public void setName(String name)  {this.name = name;}

        public static void main(String[] args) {
                int[] array = {1, 4, 8};
                int[] result = findPairs(array, 5);
                System.out.println(result[0] + ", " + result[1]);
        }

        public static int[] findPairs(int[] array, int number)  {
                int[] result = new int[2];
                for (int i=0; i<array.length; i++)  {
                        for (int j=0; j<array.length; j++)  {
                                System.out.println("i: " + i + ", j: " + j + "; i+j: " + (i+j));

                                if (array[i]+array[j]==number)  {
                                        result[0] = array[i];
                                        result[1] = array[j];
                                        return result;
                                }
                        }
                }

                result[0] = -1;
                result[1] = -1;
                return result;

        }
}
