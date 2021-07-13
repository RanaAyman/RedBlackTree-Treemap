package eg.edu.alexu.csd.filestructure.redblacktree;

public class Node  <T extends Comparable<T>, V>implements INode<T,V>{
    T key;
    V value;
    INode parent;
    INode left , right ;
    boolean color;

    public Node(T key, V value,boolean color) {
        this.key = key;
        this.value = value;
        this.parent = null;
        this.left = null;
        this.right = null;
        this.color = color ;
    }
    @Override
    public void setParent(INode parent) {
        this.parent=parent;
    }

    @Override
    public INode getParent() {
        return  this.parent;
    }

    @Override
    public void setLeftChild(INode leftChild) {
        this.left=leftChild;
        leftChild.setParent(this);
    }

    @Override
    public INode getLeftChild() {
        return this.left;
    }

    @Override
    public void setRightChild(INode rightChild) {
        this.right=rightChild;
        rightChild.setParent(this);
    }

    @Override
    public INode getRightChild() {
        return this.right;
    }

    @Override
    public T getKey() {
        return this.key;
    }

    @Override
    public void setKey(Comparable key) {
        this.key=(T)key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public void setValue(Object value) {
        this.value= (V) value;
    }

    @Override
    public boolean getColor() {
        return this.color;
    }

    @Override
    public void setColor(boolean color) {
        this.color=color;
    }

    @Override
    public boolean isNull() {
        return this.key==null;
    }
}