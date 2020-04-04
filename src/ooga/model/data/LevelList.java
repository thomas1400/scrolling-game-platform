package ooga.model.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class LevelList implements Set<Level> {

  Map<String, Level> myLevels = new HashMap<>();

  @Override
  public int size() {
    return myLevels.size();
  }

  @Override
  public boolean isEmpty() {
    return myLevels.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return myLevels.containsValue(o);
  }

  @Override
  public Iterator iterator() {
    return null;
  }

  @Override
  public Object[] toArray() {
    return myLevels.values().toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return null;
  }

  @Override
  public boolean add(Level level) {
    myLevels.put(level.getName(), level);
    return !myLevels.containsValue(level);
  }

  @Override
  public boolean remove(Object o) {
    return false;
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean addAll(Collection<? extends Level> c) {
    return false;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return false;
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return false;
  }

  @Override
  public void clear() {
    myLevels = new HashMap<>();
  }

}