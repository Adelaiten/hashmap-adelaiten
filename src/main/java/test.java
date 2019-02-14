import java.util.LinkedList;

public class test {
    public static void main(String[] args) {
        LinkedList<KeyValue<String,String>>[] map = new LinkedList[16];
        map[0] = new LinkedList<>();
        System.out.println(map[0].get(0));
    }
}
