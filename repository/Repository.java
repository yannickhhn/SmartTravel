package repository;

//----------------------------------------------
//Assignment 3
//Package repository
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------

import exceptions.EntityNotFoundException;
import interfaces.Identifiable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Repository<T extends Identifiable & Comparable<? super T>> {

    private final List<T> items = new ArrayList<>();

    // 1. Add items (mirrors main List)
    public void add(T item) {
        items.add(item);
    }

    // 2. ID-based lookup (throws EntityNotFoundException)
    public T findById(String id) throws EntityNotFoundException {
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            if (item != null && item.getID().equalsIgnoreCase(id)) {
                return item;
            }
        }
        throw new EntityNotFoundException("Entity with ID '" + id + "' not found.");
    }

    // 3. FILTERING: Accepts any Predicate<T> "yes/no question"
    public List<T> filter(Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            T item = items.get(i);
            if (item != null && predicate.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    // 4. Smart sort using business natural order (defined by compareTo)
    public List<T> getSorted() {
        List<T> sorted = new ArrayList<>(items);
        Collections.sort(sorted);
        return sorted;
    }
}
