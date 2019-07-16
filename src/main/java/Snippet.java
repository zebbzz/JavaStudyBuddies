public class Snippet
{
    public static void main(String[] args)
    {
        Project p = getById(Project.class, 0);
        System.out.println(p);
        System.out.println(getById(Project.class, 1));
    }

    private static <T extends Insertable> T getById(Class<T> type, int id)
    {
        return (T) new Project();
    }

    private static class Project implements Insertable
    {
        @Override
        public String toString()
        {
            StringBuilder result = new StringBuilder();
            result.append("Hello");
            return result.toString();
        }
    }

    interface Insertable
    {
    }
}