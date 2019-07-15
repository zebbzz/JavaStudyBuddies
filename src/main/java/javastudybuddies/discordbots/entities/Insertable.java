package javastudybuddies.discordbots.entities;

public interface Insertable {
         String getIdentificator();
         String getTable();
         Object get(String key);
         void set(Column column, Object value);
}
