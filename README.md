# RedBlackTree-Treemap

## Red Black Tree
* A red black tree is a kind of self-balancing binary search tree in computer science.  Each node ofthe binary tree has an extra bit, and that bit is often interpreted as the color (red or black) ofthe node.  These color bits are used to ensure the tree remains approximately balanced duringinsertions and deletions.  Balance is preserved by painting each node of the tree with one of twocolors in a way that satisfies certain properties, which collectively constrain how unbalancedthe tree can become in the worst case.  When the tree is modified, the new tree is subsequentlyrearranged  and  repainted  to  restore  the  coloring  properties.   The  properties  are  designed  insuch a way that this rearranging and recoloring can be performed efficiently.

### Methods
1.  getRoot:  return the root of the given Red black tree.
2.  isEmpty:  return whether the given tree isEmpty or not.
3.  clear:  Clear all keys in the given tree.
4.  search:  return the value associated with the given key or null if no value is found.
5.  contains:  return true if the tree contains the given key and false otherwise.
6.  insert:  Insert the given key in the tree while maintaining the red black tree properties.  Ifthe key is already present in the tree, update its value.
7.  delete:  Delete the node associated with the given key.  Return true in case of success andfalse otherwise.

## Tree Map
* A Red-Black tree based NavigableMap implementation.  The map is sorted according to thenatural ordering of its keys, or by a Comparator provided at map creation time, depending onwhich constructor is used.  This implementation provides guaranteed log(n) time cost for thecontainsKey, get, put and remove operations.  Algorithms are adaptations of those in Cormen,Leiserson, and Rivest’s Introduction to Algorithms.

### Methods
1.  ceilingEntry:  Returns a key-value mapping associated with the least key greater than orequal to the given key, or null if there is no such key.
2.  ceilingKey:  Returns the least key greater than or equal to the given key, or null if thereis no such key.
3.  clear:  Removes all of the mappings from this map.
4.  containsKey:  Returns true if this map contains a mapping for the specified key.
5.  containsValue:  Returns true if this map maps one or more keys to the specified value.
6.  ntrySet:  Returns a Set view of the mappings contained in this map in ascending keyorder.
7.  firstEntry:  Returns a key-value mapping associated with the least key in this map,  ornull if the map is empty.
8.  firstKey:  Returns the first (lowest) key currently in this map, or null if the map is empty.
9.  floorEntry:  Returns a key-value mapping associated with the greatest key less than orequal to the given key, or null if there is no such key.
10. floorKey:  Returns the greatest key less than or equal to the given key, or null if there isno such key.
11. get:  Returns the value to which the specified key is mapped, or null if this map containsno mapping for the key.
12. headMap:  Returns a view of the portion of this map whose keys are strictly less thantoKey in ascending order.
13. headMap:  Returns a view of the portion of this map whose keys are less than (or equalto, if inclusive is true) toKey in ascending order.
14. keySet:  Returns a Set view of the keys contained in this map.
15. lastEntry:  Returns a key-value mapping associated with the greatest key in this map, ornull if the map is empty.
16. lastKey:  Returns the last (highest) key currently in this map.
17. pollFirstElement:  Removes  and  returns  a  key-value  mapping  associated  with  the  leastkey in this map, or null if the map is empty.
18. pollLastEntry:  Removes and returns a key-value mapping associated with the greatestkey in this map, or null if the map is empty.
19. put:  Associates the specified value with the specified key in this map.
20. putAll:  Copies all of the mappings from the specified map to this map.
21. remove:  Removes the mapping for this key from this TreeMap if present.
22. size:  Returns the number of key-value mappings in this map.23.  values:  Returns a Collection view of the values contained in this map.
