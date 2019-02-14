import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.Arrays;
import java.util.LinkedList;

public class CustomHashMap<T, V> {
    private LinkedList<KeyValue<T,V>>[] map;
    private int mapCapacity;
    private int mapSize;

    public CustomHashMap() {
        this.mapCapacity = 16;
        this.map = new LinkedList[mapCapacity];
        initMap();
    }

    public void add(T key, V value) {
        int hash = key.hashCode();
        int index = hash % mapCapacity;
        LinkedList<KeyValue<T,V>> list = map[index];
        resizeMap();

        throwExceptionWhenKeyExists(key, list);

        KeyValue<T, V> keyValue = new KeyValue<>(key, value);

        list.add(keyValue);

        mapSize++;
    }

    public V getValue(T key) throws KeyNotFoundException{
        int hash = key.hashCode();
        int index = hash % mapCapacity;
        LinkedList<KeyValue<T,V>> list = map[index];
        for(KeyValue<T, V> keyValue : list) {
            if(keyValue.getKey().equals(key)) {
                return keyValue.getValue();
            }
        }
        throw new KeyNotFoundException("Key not found!");
    }


    public void remove(T key) throws KeyNotFoundException {
        int hash = key.hashCode();
        int index = hash % mapCapacity;
        LinkedList<KeyValue<T,V>> list = map[index];
        for(int i = list.size() -1; i >= 0; i--) {
            if(list.get(i).getKey().equals(key)) {
                list.remove(i);
            }
        }
        throw new KeyNotFoundException("Key not found!");
    }

    public void clearAll() {
        initMap();
    }


    private void throwExceptionWhenKeyExists(T key, LinkedList<KeyValue<T, V>> list) {
        for(KeyValue keyValue : list) {
            if(keyValue.getKey().equals(key)) {
                throw new KeyAlreadyExistsException("Key already exists in the map!");
            }
        }
    }

    private void initMap() {
        for(int i = 0; i < mapCapacity; i++) {
            map[i] = new LinkedList<>();
        }
    }


    private void resizeMap() {
        if(mapSize == mapCapacity) {
            mapCapacity = mapCapacity * 2;
            LinkedList<KeyValue<T,V>>[] temp = map.clone();
            map = Arrays.copyOf(temp, mapCapacity);
            for(int i = mapSize; mapSize < mapCapacity; i++) {
                map[i] = new LinkedList<>();
            }
        }
    }


}
