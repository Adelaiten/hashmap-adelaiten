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
    }

    public void add(T key, V value) {
        int hash = key.hashCode();
        int index = hash % mapCapacity;
        LinkedList<KeyValue<T,V>> list = map[index];
        resizeMap();

        throwExceptionWhenKeyExists(key, list);

        KeyValue<T, V> keyValue = new KeyValue<>(key, value);
        if(list == null) {
            list = new LinkedList<>();
            list.add(keyValue);
            map[index] = list;
        }else{
            list.add(keyValue);
        }

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


    private void throwExceptionWhenKeyExists(T key, LinkedList<KeyValue<T, V>> list) {
        if(list == null) {
            return;
        }
        for(KeyValue keyValue : list) {

            if(keyValue.getKey().equals(key)) {
                throw new KeyAlreadyExistsException("Key already exists in the map!");
            }
        }
    }

    private void resizeMap() {
        if(mapSize == mapCapacity) {
            mapCapacity = mapCapacity * 2;
            LinkedList<KeyValue<T,V>>[] temp = map.clone();
            map = Arrays.copyOf(temp, mapCapacity);
        }
    }


}
