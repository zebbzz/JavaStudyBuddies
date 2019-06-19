package javastudybuddies.discordbots;

import java.util.*;

public class UltimateMap<K extends Stringable, V> implements Map<K, V> {
        Map<K, V> values = new HashMap<>();
        Map<String, K> keys = new HashMap<>();

        public V put(K key, V value)  {
            values.put(key, value);
            keys.put(key.getStringId(), key);

            return value;
        }

        @Override
        public V get(Object key)  {
            return values.get(key);
        }

        public V get(String keyId)  {
            return values.get(keys.get(keyId));
        }

        @Override
        public void clear()  {
            values = new HashMap<>();
            keys = new HashMap<>();
        }

        @Override
        public Set<K> keySet()  {
            return new HashSet<>(keys.values());
        }

        @Override
        public Collection<V> values()  {
            return values.values();
        }

        @Override
        public boolean isEmpty()  {
            return values.isEmpty();
        }

        @Override
        public boolean containsValue(Object value)  {
            return values.containsValue(value);
        }

        @Override
        public boolean containsKey(Object key)  {
            return values.containsKey(key);
        }

        @Override
        public Set<Entry<K, V>> entrySet()  {
            return values.entrySet();
        }

        @Override
        public int hashCode()  {
            return values.hashCode();
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> m)  {
             values.putAll(m);
        }

        @Override
        public int size()  {
            return values.size();
        }

        @Override
        public V remove(Object key)  {
            return values.remove(key);
        }

    public static void main(String[] args) {
            UltimateMap<User, String> userPets = new UltimateMap<>();
            userPets.put(new User("Sam"), "cat");
            userPets.put(new User("Charlie"), "dog");
            userPets.put(new User("John"), "parrot");

            System.out.println(userPets.get("John"));
    }
}

interface Stringable  {
    String getStringId();
}

class User implements Stringable {
    private String name;

    public User(String name)  {this.name = name;}

    public String getName()  {return name;}

    @Override
    public String getStringId()  {return name;}
}