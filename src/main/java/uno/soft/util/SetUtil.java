package uno.soft.util;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Iterator;

public class SetUtil {
    public static <E> Set<E> getSubset(LinkedHashSet<E> set, int start, int end) {
        if (start < 0 || end > set.size() || start > end) {
            throw new IndexOutOfBoundsException("Invalid start or end index");
        }

        Set<E> subset = new LinkedHashSet<>();
        Iterator<E> iterator = set.iterator();
        int currentIndex = 0;

        while (iterator.hasNext()) {
            E element = iterator.next();
            if (currentIndex >= start && currentIndex < end) {
                subset.add(element);
            }
            currentIndex++;
            if (currentIndex >= end) {
                break;
            }
        }

        return subset;
    }
}