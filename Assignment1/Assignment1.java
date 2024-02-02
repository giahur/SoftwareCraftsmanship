// Gia Hur (GXH221): code uses recursion, not loops
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Assignment1<T extends Comparable<? super T>> {
    List<T> list = new ArrayList<>();
    public List<T> longestHigherSuffix(List<T> a, List<T> b, Comparator<T> cmp) {
        if(a.isEmpty() || b.isEmpty()) {
            return list;
        }

        int i = a.size()-1;
        int j = b.size()-1;

        if(cmp.compare(a.get(i), b.get(j)) < 0) {
            return list;
        }

        list.add(0, a.get(i));
        return longestHigherSuffix(a.subList(0, i), b.subList(0, j), cmp);
    }
}