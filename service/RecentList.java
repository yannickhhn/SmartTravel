//----------------------------------------------
//Assignment 3
//Package service
//Written by Hantaniaina Yannick H.N 40306516
//----------------------------------------------
package service;

import java.util.LinkedList;

public class RecentList<T> {

    private LinkedList<T> list = new LinkedList<>();
    private final int MAX_SIZE = 10;

    // Add item to front; remove oldest from end if over capacity
    public void addRecent(T item) {
        list.addFirst(item);
        if (list.size() > MAX_SIZE) {
            list.removeLast();
        }
    }

    // Display up to maxToShow most recent items (most recent first)
    public void printRecent(int maxToShow) {
        int count = Math.min(maxToShow, list.size());
        for (int i = 0; i < count; i++) {
            System.out.println(list.get(i));
        }
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    // Get item at index (0 = most recent)
    public T getRecent(int index) {
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }

}
