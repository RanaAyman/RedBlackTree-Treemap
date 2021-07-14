package eg.edu.alexu.csd.filestructure.redblacktree;

import java.security.Key;
import java.util.*;
import java.util.Map.Entry;

import javax.management.RuntimeErrorException;
//
public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T, V> {
    RedBlackTree  RBTree = new RedBlackTree();
    int size = 0;


    private Map.Entry<T,V> searchCeilingKeyEntry (INode root, T key, Map.Entry<T,V> sucessor){
        if (root.isNull()||root == null || root.getKey() == null)
            return sucessor;
        else if (key.compareTo((T)root.getKey())>0)
            return searchCeilingKeyEntry(root.getRightChild(),key,sucessor);
        else if(key.compareTo((T)root.getKey())<0)
            return searchCeilingKeyEntry(root.getLeftChild(),key,new AbstractMap.SimpleEntry(root.getKey(), root.getValue()));
        else return new AbstractMap.SimpleEntry(root.getKey(), root.getValue());
    }
    @Override
    public Map.Entry<T, V> ceilingEntry(T key) {
        if (key == null)
            throw new RuntimeErrorException(null,null);
        return searchCeilingKeyEntry(RBTree.getRoot(),key,null);
    }

    private T searchCeilingKey (INode root, T key, T sucessor){
        if (root == null || root.getKey() == null)
            return sucessor;
        else if (key.compareTo((T)root.getKey())>0)
            return searchCeilingKey(root.getRightChild(),key,sucessor);
        else if(key.compareTo((T)root.getKey())<0)
            return searchCeilingKey(root.getLeftChild(),key,(T)root.getKey());
        else return key;
    }
    @Override
    public T ceilingKey(T key) {
        if (key == null)
            throw new RuntimeErrorException(null,null);
        return searchCeilingKey(RBTree.getRoot(), key, null);
    }

    @Override
    public void clear() {
        RBTree.clear();
    }

    private boolean searchKey(INode root,T key){
        if (root == null || root.getKey() == null)
            return false;
        else if (key.compareTo((T) root.getKey())==0)
            return true;
        else if (key.compareTo((T) root.getKey()) > 0)
            return searchKey(root.getRightChild(), key);
        else
            return searchKey(root.getLeftChild(), key);
    }
    @Override
    public boolean containsKey(T key) {
        if (key == null)
            throw new RuntimeErrorException(null,null);
        return searchKey(RBTree.getRoot(), key);
    }

    private boolean searchValue(INode root , V value){
        if (root == null)
            return false;
        else if (root.getValue() != null && value.equals(root.getValue()))
            return true;
        return searchValue(root.getLeftChild(),value) || searchValue(root.getRightChild(),value);
    }
    @Override
    public boolean containsValue(V value) {
        INode root = RBTree.getRoot();
        if (value == null)
            throw new RuntimeErrorException(null,null);
        return searchValue(root,value);
    }


    private void getEntrySet(INode root, HashSet<Map.Entry<T,V>> Result){
        if (root == null || root.isNull())
            return;
        getEntrySet(root.getLeftChild(),Result);
        Result.add(new AbstractMap.SimpleEntry(root.getKey(), root.getValue()));
        getEntrySet(root.getRightChild(),Result);
    }
    @Override
    public Set<Map.Entry<T, V>> entrySet() {
        LinkedHashSet<Map.Entry<T,V>> Result = new LinkedHashSet<>();
        if (!RBTree.isEmpty())
            getEntrySet(RBTree.getRoot(),Result);
        return Result;
    }

    private Map.Entry<T,V> searchFirstEntry(INode root){
        if (root.getLeftChild().getLeftChild() != null)
            return searchFirstEntry(root.getLeftChild());
        else
            return new AbstractMap.SimpleEntry(root.getKey(), root.getValue());
    }
    @Override
    public Map.Entry<T, V> firstEntry() {
        if (RBTree.isEmpty())
            return null;
        else return searchFirstEntry(RBTree.getRoot());
    }


    private T searchFirstKey(INode root){
        if (root.getLeftChild().getLeftChild()!=null)
            return searchFirstKey(root.getLeftChild());
        else
            return (T)root.getKey();
    }
    @Override
    public T firstKey() {
        if (RBTree.isEmpty())
            return null;
        else return searchFirstKey(RBTree.getRoot());
    }

    /****************************************************************/
    @Override
    public Map.Entry<T, V> floorEntry(T key) {
        if (key != null) {
            return traverse(key);
        }
        throw new RuntimeErrorException(new Error());
    }

    @Override
    public T floorKey(T key) {
        if (key != null) {
            return (T) traverse(key).getKey();
        }
        throw new RuntimeErrorException(null,null);
    }

    @Override
    public V get(T key) {
        if (key == null)
            throw new RuntimeErrorException(null,null);
        if (!RBTree.contains(key))
            return null;
        INode curr = RBTree.getRoot();
        while (key != curr.getKey()) {
            if (key.compareTo((T) curr.getKey()) < 0)
                curr = curr.getLeftChild();
            if (key.compareTo((T) curr.getKey()) > 0)
                curr = curr.getRightChild();
            if (key.compareTo((T) curr.getKey()) == 0)
                break;
        }
        return (V) curr.getValue();
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey) {
        return headMap(toKey, false);
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey, boolean inclusive) {
        if (toKey == null)
            throw new RuntimeErrorException(new Error());
        if (RBTree.getRoot() == null)
            return null;
        ArrayList<Map.Entry<T, V>> headmap = new ArrayList<>();
        Iterator<Map.Entry<T, V>> iter = entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<T, V> head = iter.next();
            if (head.getKey().equals(toKey)) {
                if (inclusive)
                    headmap.add(head);
                break;
            }
            headmap.add(head);
        }
        return headmap;
    }

    @Override
    public Set<T> keySet() {
        if (RBTree.getRoot() == null)
            return null;
        Set<T> keys = new LinkedHashSet<>();
        Set<Map.Entry<T, V>> sets = entrySet();
        for (Map.Entry<T, V> set : sets) {
            keys.add(set.getKey());
        }
        return keys;
    }

    @Override
    public Map.Entry<T, V> lastEntry() {
        if (RBTree.getRoot().isNull())
            return null;
        INode<T, V> greatestKey = MaxKey(RBTree.getRoot());
        return new AbstractMap.SimpleEntry<T, V>(greatestKey.getKey(), greatestKey.getValue());

    }

    @Override
    public T lastKey() {
        if (RBTree.getRoot().isNull())
            return null;
        INode<T, V> greatestKey = MaxKey(RBTree.getRoot());
        return greatestKey.getKey();
    }

    @Override
    public Map.Entry<T, V> pollFirstEntry() {
        if (RBTree.getRoot().isNull())
            return null;
        INode<T, V> min = MinKey(RBTree.getRoot());
        remove(min.getKey());
        return new AbstractMap.SimpleEntry<T, V>(min.getKey(), min.getValue());
    }

    @Override
    public Map.Entry<T, V> pollLastEntry() {
        if (RBTree.getRoot().isNull())
            return null;
        INode<T, V> max = MaxKey(RBTree.getRoot());
        remove(max.getKey());
        return new AbstractMap.SimpleEntry<T, V>(max.getKey(), max.getValue());
    }

    @Override
    public void put(T key, V value) {
        if (value == null || key == null) {
            throw new RuntimeErrorException(new Error());
        }
        RBTree.insert(key, value);
    }

    @Override
    public void putAll(Map<T, V> map) {
        if (map != null) {
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                RBTree.insert((T) entry.getKey(), entry.getValue());
            }
        }
        else
            throw new RuntimeErrorException(new Error());
    }

    @Override
    public boolean remove(T key) {
        return RBTree.delete(key);
    }

    private int getSize(INode root){
        if (root==null || root.isNull())
            return 0;
        else
            return 1 + getSize(root.getLeftChild()) + getSize(root.getRightChild());
    }
    @Override
    public int size() {
        if(RBTree.isEmpty())
            return 0;
        else
            return getSize(RBTree.getRoot());
    }

    @Override
    public Collection<V> values() {
        List<V> list = new ArrayList<>();
        Set<Map.Entry<T, V>> set = entrySet();
        for (Map.Entry<T, V> entry : set) {
            list.add(entry.getValue());
        }
        return list;
    }

    private Map.Entry traverse(Comparable key) {
        INode curr = RBTree.getRoot();
        INode value = RBTree.getRoot();
        while (curr.getValue() != null) {
            if (key.compareTo(curr.getKey()) > 0) {
                value = curr;
                curr = curr.getRightChild();
            }
            if (key.compareTo(curr.getKey()) < 0) {
                curr = curr.getLeftChild();
            }
            if (key.compareTo(curr.getKey()) == 0) {
                value = curr;
                break;
            }
        }
        if (key.compareTo(value.getKey()) < 0)
            return null;
        else
            return new AbstractMap.SimpleEntry(value.getKey(), value.getValue());
    }

    private INode<T, V> MaxKey(INode<T, V> root) {
        if (root.isNull()||root.getRightChild().getRightChild() == null)
            return root;
        return MaxKey(root.getRightChild());
    }

    private INode<T, V> MinKey(INode<T, V> root) {
        if (root.isNull()||root.getLeftChild().getLeftChild() == null)
            return root;
        return MinKey(root.getLeftChild());

    }

}
