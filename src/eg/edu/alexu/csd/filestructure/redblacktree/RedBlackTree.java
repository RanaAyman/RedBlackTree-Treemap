package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

public class RedBlackTree  <T extends Comparable<T>, V>implements IRedBlackTree<T,V> {
    INode<T,V> root;
    public RedBlackTree() {
        this.root = new Node(null,null,INode.BLACK);
    }
    @Override
    public INode getRoot() {
        return root;
    }

    @Override
    public boolean isEmpty() {
        return ((root == null) || (this.root.isNull()));
    }
    @Override
    public void clear() {
        this.root=new Node(null,null,INode.BLACK);
    }

    @Override
    public Object search(Comparable key) {
        if (key==null){throw new RuntimeErrorException(null);}
        return search_helper(root,key).getValue();
    }
    @Override
    public boolean contains(Comparable key) {
        if(key==null){throw new RuntimeErrorException(null);}
        return search(key)!=null;
    }

    @Override
    public void insert(Comparable key,Object value) {
        if((key==null)||(value==null)){throw new RuntimeErrorException(null);}
        if(this.contains(key)){
            Node n= (Node) this.search_helper(this.root,key);
            n.setValue(value);
            return;
        }
        Node<T,V> n = new Node(key, value,INode.RED);

        //basic case if the tree is empty then make it the root and color it black
        if (this.isEmpty()) {
            this.root = n;
            n.setColor(INode.BLACK);
            n.setRightChild(new Node(null,null,INode.BLACK));
            n.setLeftChild(new Node(null,null,INode.BLACK));
            return;
        }


        //first find the appropriate position for this node as if it was a BST
        INode<T,V> x = this.root;
        while (!x.isNull()){
            if (x.getKey().compareTo(n.getKey()) > 0)
                x = (Node) x.getLeftChild();
            else
                x = (Node) x.getRightChild();
        }

        //now x should be the parent of our new node
        x = (Node) x.getParent();
        n.setParent(x);

        if (x.getKey().compareTo(n.getKey()) > 0)
            x.setLeftChild(n);
        else
            x.setRightChild(n);

        //adding black null nodes to the end of it
        n.setRightChild(new Node(null,null,INode.BLACK));
        n.setLeftChild(new Node(null,null,INode.BLACK));
        //now the new node in it's place and we need to fix the tree
        fixInsert(n);
    }

    public void fixInsert(Node n){
        //base case if parent is black then tree is already fixed
        if(n.getParent().getColor() == INode.BLACK)
            return;

        while (n.getParent() != null && n.getParent().getColor() == INode.RED){
            //G : grand parent , U : uncle
            Node G = (Node) n.getParent().getParent() , U;

            //if P is the right child of G
            if (n.getParent().equals(G.getRightChild())){
                U = (Node) G.getLeftChild();

                if (U.getColor() == INode.RED){
                    //in this case P and U = RED then change P U
                    n.getParent().setColor(INode.BLACK);
                    U.setColor(INode.BLACK);
                    if (!G.equals(root)){
                        G.setColor(INode.RED);
                        //move the problem to the grandparent
                        n = G ;
                    }
                }
                else{
                    if (n.equals(n.getParent().getLeftChild())){
                        rightRotate((Node) n.getParent());
                        //make n point on the leaf node again to make leftRotate in the next step
                        n = (Node) n.getRightChild();
                    }
                    n.getParent().setColor(INode.BLACK);
                    n.getParent().getParent().setColor(INode.RED);
                    leftRotate((Node) n.getParent().getParent());
                }
            }

            //if P is the left child of G
            else{
                U = (Node) G.getRightChild();

                if (U != null && U.getColor() == INode.RED){
                    //in this case P and U = RED then change P U
                    n.getParent().setColor(INode.BLACK);
                    U.setColor(INode.BLACK);
                    if (!G.equals(root)) {
                        G.setColor(INode.RED);
                        //move the problem to the grandparent
                        n = G;
                    }
                }

                else{
                    if (n.equals(n.getParent().getRightChild())) {
                        leftRotate((Node) n.getParent());
                        //make n point on the leaf node again to make rightRotate in the next step
                        n = (Node) n.getLeftChild();
                    }
                    n.getParent().setColor(INode.BLACK);
                    G.setColor(INode.RED);
                    rightRotate((Node) n.getParent().getParent());
                }
            }
        }
        root.setColor(INode.BLACK);
    }

    public INode search_helper(INode root,Comparable key){
        if(root.isNull()||(root.getKey().compareTo(key)==0)){
            return root;
        }
        if(root.getKey().compareTo(key)>0){
            return search_helper(root.getLeftChild(),key);
        }
        if(root.getKey().compareTo(key)<0){
            return search_helper(root.getRightChild(),key);
        }
        return null;
    }

    public INode minValue(INode root) {
        //int min = root.key;
        while (!root.getLeftChild().isNull())
        {
            root = root.getLeftChild();
        }
        return root;
    }

    private INode<T, V> getSibling(INode<T, V> node){
        if(node.getParent() != null) {
            //if this node is the left child then return the right child
            if(node.equals(node.getParent().getLeftChild()))
                return node.getParent().getRightChild();

                //else if it is the right child then return the left one
            else
                return node.getParent().getLeftChild();

        }
        return null;
    }

    public void leftRotate (Node x){
        //node x is the parent of node y and y is the right child of x
        Node y = (Node) x.getRightChild();

        x.setRightChild(y.getLeftChild());
        if(!y.getLeftChild().isNull())
            y.getLeftChild().setParent(x);

        //make the old parent of x new parent of y
        y.setParent(x.getParent());

        //if x is the root then make y the new root
        if (x.getParent()==null){
            this.root = y ;
        }
        else if (x.equals(x.getParent().getLeftChild()))
            x.getParent().setLeftChild(y);
        else
            x.getParent().setRightChild(y);

        y.setLeftChild(x);
        x.setParent(y);
    }

    public void rightRotate(Node x){
        //node x is the parent of node y and y is the left child of x
        Node y = (Node) x.getLeftChild();

        x.setLeftChild(y.getRightChild());
        if(!y.getRightChild().isNull())
            y.getRightChild().setParent(x);

        //make the old parent of x new parent of y
        y.setParent(x.getParent());

        //if x is the root then make y the new root
        if (x.getParent()==null){
            this.root = y ;
        }
        else if (x.equals(x.getParent().getLeftChild()))
            x.getParent().setLeftChild(y);
        else
            x.getParent().setRightChild(y);

        y.setRightChild(x);
        x.setParent(y);
    }

    public boolean delete(T key) {
        //base cases
        if(key == null)
            throw new RuntimeErrorException(null);
        else if (!this.contains(key))
            return false;

        //else if key is found in the tree then delete this node
        INode<T, V> x = this.search_helper(this.root, key);
        deleteNode(x);
        return true;
    }


    private void deleteNode(INode<T, V> x) {
        INode<T, V> replacementNode = getReplacement(x);

        boolean doubleBlack = ((replacementNode.getColor() == INode.BLACK) && (x.getColor() == INode.BLACK));
        if(replacementNode.isNull()) {
            //case of deleting tree with one node "root"
            if(x.equals(root)) {
                root = replacementNode;
                root.setParent(null);
            }else {
                if(doubleBlack) {
                    fix_delete(x);
                }else {
                    INode<T, V> sibling = getSibling(x);
                    if(!(sibling == null || sibling.isNull())) sibling.setColor(INode.RED);
                }

                //set parent of x as a new parent of the replacementNode
                if(x.equals(x.getParent().getLeftChild()))
                    x.getParent().setLeftChild(replacementNode);
                else
                    x.getParent().setRightChild(replacementNode);

                replacementNode.setParent(x.getParent());
            }
            return;
        }

        if(!x.getLeftChild().isNull() && !x.getRightChild().isNull()) {
            //if x has left and right child so we swap there values and keys
            T tempkey = x.getKey();
            V valueValue = x.getValue();
            x.setKey(replacementNode.getKey());
            x.setValue(replacementNode.getValue());
            replacementNode.setKey(tempkey);
            replacementNode.setValue(valueValue);
            deleteNode(replacementNode);
        }else {
            //if if it has at least one child swap parents wth the replacementNode
            swapNodesParents(x, replacementNode);
            if(doubleBlack) {
                fix_delete(replacementNode);
            }else {
                replacementNode.setColor(INode.BLACK);
            }
        }
    }

    private INode<T, V> getReplacement(INode<T, V> x) {
        INode<T, V> replacementNode;

        //if node x has left and right child
        //then replacementNode will be the successor of this node x
        if(!x.getLeftChild().isNull() && !x.getRightChild().isNull())
            replacementNode = this.minValue(x.getRightChild());

            //if x node is a leaf that has no children
            //then replacementNode will be nill node
        else if(x.getLeftChild().isNull() && x.getRightChild().isNull())
            replacementNode = x.getLeftChild();

            //if x has one its children null
            //then replacementNode will be the other nonnill child
        else {
            if(x.getLeftChild().isNull()) {
                replacementNode = x.getRightChild();
            }else {
                replacementNode = x.getLeftChild();
            }
        }
        return replacementNode;
    }

    private void fix_delete(INode<T, V> node) {
        //if the node that has a doubleBlack porobelm is the root node then it is already fixed
        if(root.equals(node)) return;

        INode<T, V> parent = node.getParent();
        INode<T, V> sibling = this.getSibling(node);
        if(sibling == null) return;
        if(sibling.isNull()) {
            //if the sibling is nill then move the porblem to the parent
            fix_delete(parent);
            return;
        }

        //case 2.1 : when s is red
        if(sibling.getColor() == INode.RED) {
            //switches colors of s and its parent
            sibling.setColor(INode.BLACK);
            parent.setColor(INode.RED);

            //if s is the left child
            if(sibling.equals(parent.getLeftChild())) {
                rightRotate((Node) parent); //rotate s up
            }else {
                leftRotate((Node) parent);//rotate s up
            }
            fix_delete(node);
        }else {

            //case2.2 : sibling s is balck and both of s's childern are black
            if((sibling.getLeftChild().getColor() == INode.BLACK) && (sibling.getRightChild().getColor() == INode.BLACK)) {
                sibling.setColor(INode.RED);
                //if parent of s is red , recolor it black else move the problem to the parent
                if(parent.getColor() == INode.RED)
                    parent.setColor(INode.BLACK);
                else
                    fix_delete(parent);

            }else {
                //case : s is black and its left child is red
                if(sibling.getLeftChild().getColor() == INode.RED) {
                    //if s is the left child
                    if(sibling.equals(parent.getLeftChild())) {
                        //make left child same as s color - change s color to color of its parent - rotate s up
                        sibling.getLeftChild().setColor(sibling.getColor());
                        sibling.setColor(parent.getColor());
                        rightRotate((Node) parent);
                    }else {
                        sibling.getLeftChild().setColor(parent.getColor());
                        rightRotate((Node) sibling);
                        leftRotate((Node) parent);
                    }
                }else {
                    //else case : s is black and its right child is red
                    if(sibling.equals(parent.getLeftChild())) {
                        sibling.getRightChild().setColor(parent.getColor());
                        leftRotate((Node) sibling);
                        rightRotate((Node) parent);
                    }else {
                        //if s is the right child
                        //make right child same as s color - change s color to color of its parent - rotate s up
                        sibling.getRightChild().setColor(sibling.getColor());
                        sibling.setColor(parent.getColor());
                        leftRotate((Node) parent);
                    }
                }
                if(parent != null) parent.setColor(INode.BLACK);
            }
        }
    }

    private void swapNodesParents(INode<T, V> node1, INode<T, V> node2) {
        if(root.equals(node1)) {
            root = node2;
        }else if(node1.getParent().getRightChild().equals(node1)) {
            node1.getParent().setRightChild(node2);
        }else {
            node1.getParent().setLeftChild(node2);
        }
        node2.setParent(node1.getParent());
    }

}