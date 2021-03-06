package assignment;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import assignment.KdTree.DPoint;


/**
 *  a k-d tree (short for k-dimensional tree) is a space-partitioning data structure for organizing points in a k-dimensional space.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/K-d_tree">K-D Tree (Wikipedia)</a>
 * <br>
 * @author Emrah Kivanc
 */
public class KdTree<T extends KdTree.DPoint> implements Iterable<T> {
    
    
    public KdNode getRoot() {
		return root;
	}

	public void setRoot(KdNode root) {
		this.root = root;
	}

	private KdNode root = null;

    
  private static final Comparator<DPoint> ALL_COMPARATOR = new Comparator<DPoint>() {
	  
  @Override
  public int compare(DPoint o1, DPoint o2) {
	  
	  for(int i =0; i< o1.getCoordinates().length ; i++ ) {
		  if(o1.getCoordinates()[i] <  o2.getCoordinates()[i])
			  return -1;
		  if(o1.getCoordinates()[i] >  o2.getCoordinates()[i])
			  return 1;
		  // else is zero which need to move to next item!
	  }
	  
      return 0;
  }
};
    

    /**
     * Default constructor.
     */
    public KdTree() { }

    /**
     * Constructor for creating a more balanced tree. It uses the "median of points" algorithm.
     *
     * @param list of DPoints.
     */
    public KdTree(List<DPoint> list) {
        super();
        root = createNode(list, list.get(0).getCoordinates().length  , 0);
    }

    /**
     * Constructor for creating a more balanced tree. It uses the "median of points" algorithm.
     *
     * @param list
     *            of DPoints.
     * @param k
     *            dimension of the tree.
     */
    public KdTree(List<DPoint> list, int k) {
        super();
        root = createNode(list, k, 0);
    }

    /**
     * Creates node from list of DPoints.
     *
     * @param list
     *            of DPoints.
     * @param k
     *           dimension of the tree.
     * @param depth
     *            depth of the node.
     * @return node created.
     */
    private static KdNode createNode(List<DPoint> list, int k, int depth) {
        if (list == null || list.size() == 0)
        	
            return null;
        
        int axis = depth % k;
        Collections.sort(list, new CoordinateComparator(axis));

        KdNode node = null;
        List<DPoint> less = new ArrayList<DPoint>(list.size());
        List<DPoint> more = new ArrayList<DPoint>(list.size());
        if (list.size() > 0) {
            int medianIndex = list.size() / 2;
            node = new KdNode(list.get(medianIndex), depth);
            // Process list to see where each non-median point lies
            for (int i = 0; i < list.size(); i++) {
                if (i == medianIndex)
                    continue;
                DPoint p = list.get(i);
                // Cannot assume points before the median are less since they could be equal
                if (KdNode.compareTo(depth, k, p, node.id) <= 0) {
                    less.add(p);
                } else {
                    more.add(p);
                }
            }

            if ((medianIndex-1 >= 0) && less.size() > 0) {
                node.lesser = createNode(less, k, depth + 1);
                node.lesser.parent = node;
            }

            if ((medianIndex <= list.size()-1) && more.size() > 0) {
                node.greater = createNode(more, k, depth + 1);
                node.greater.parent = node;
            }
        }

        return node;
    }

    /**
     * Adds value to the tree. Tree can contain multiple equal values.
     *
     * @param value
     *            T to add to the tree.
     * @return True if successfully added to tree.
     */
    public boolean add(T value) {
        if (value == null)
            return false;

        if (root == null) {
            root = new KdNode(value);
            return true;
        }

        KdNode node = root;
        while (true) {
            if (KdNode.compareTo(node.depth, node.k, value, node.id) <= 0) {
                // Lesser
                if (node.lesser == null) {
                    KdNode newNode = new KdNode(value, node.depth + 1);
                    newNode.parent = node;
                    node.lesser = newNode;
                    break;
                }
                node = node.lesser;
            } else {
                // Greater
                if (node.greater == null) {
                    KdNode newNode = new KdNode(value, node.depth + 1);
                    newNode.parent = node;
                    node.greater = newNode;
                    break;
                }
                node = node.greater;
            }
        }

        return true;
    }

    /**
     * Does the tree contain the value.
     *
     * @param value
     *            T to locate in the tree.
     * @return True if tree contains value.
     */
    public boolean contains(T value) {
        if (value == null || root == null)
            return false;

        KdNode node = getNode(this, value);
        return (node != null);
    }

    /**
     * Locates T in the tree.
     *
     * @param tree
     *            to search.
     * @param value
     *            to search for.
     * @return KdNode or NULL if not found
     */
    private static final <T extends KdTree.DPoint> KdNode getNode(KdTree<T> tree, T value) {
        if (tree == null || tree.root == null || value == null)
            return null;

        KdNode node = tree.root;
        while (true) {
            if (node.id.equals(value)) {
                return node;
            } else if (KdNode.compareTo(node.depth, node.k, value, node.id) <= 0) {
                // Lesser
                if (node.lesser == null) {
                    return null;
                }
                node = node.lesser;
            } else {
                // Greater
                if (node.greater == null) {
                    return null;
                }
                node = node.greater;
            }
        }
    }

    /**
     * Removes first occurrence of value in the tree.
     *
     * @param value
     *            T to remove from the tree.
     * @return True if value was removed from the tree.
     */
    public boolean remove(T value) {
        if (value == null || root == null)
            return false;

        KdNode node = getNode(this, value);
        if (node == null)
            return false;

        KdNode parent = node.parent;
        if (parent != null) {
            if (parent.lesser != null && node.equals(parent.lesser)) {
                List<DPoint> nodes = getTree(node);
                if (nodes.size() > 0) {
                    parent.lesser = createNode(nodes, node.k, node.depth);
                    if (parent.lesser != null) {
                        parent.lesser.parent = parent;
                    }
                } else {
                    parent.lesser = null;
                }
            } else {
                List<DPoint> nodes = getTree(node);
                if (nodes.size() > 0) {
                    parent.greater = createNode(nodes, node.k, node.depth);
                    if (parent.greater != null) {
                        parent.greater.parent = parent;
                    }
                } else {
                    parent.greater = null;
                }
            }
        } else {
            // root
            List<DPoint> nodes = getTree(node);
            if (nodes.size() > 0)
                root = createNode(nodes, node.k, node.depth);
            else
                root = null;
        }

        return true;
    }

    /**
     * Gets the (sub) tree rooted at root.
     *
     * @param root
     *            of tree to get nodes for.
     * @return points in (sub) tree, not including root.
     */
    private static final List<DPoint> getTree(KdNode root) {
        List<DPoint> list = new ArrayList<DPoint>();
        if (root == null)
            return list;

        if (root.lesser != null) {
            list.add(root.lesser.id);
            list.addAll(getTree(root.lesser));
        }
        if (root.greater != null) {
            list.add(root.greater.id);
            list.addAll(getTree(root.greater));
        }

        return list;
    }

    /** 
     * Searches the K nearest neighbor.
     *
     * @param K
     *            Number of neighbors to retrieve. Can return more than K, if
     *            last nodes are equal distances.
     * @param value
     *            to find neighbors of.
     * @return Collection of T neighbors.
     */
    @SuppressWarnings("unchecked")
    public Collection<T> nearestNeighbourSearch(int K, T value) {
        if (value == null || root == null)
            return Collections.EMPTY_LIST;

        
        EuclideanComparator eclComp = new EuclideanComparator(value);
        TreeSet<KdNode> results = new TreeSet<KdNode>(eclComp);
        
        // Map used for results
        // TreeSet<KdNode> results = new TreeSet<KdNode>(new EuclideanComparator(value));

        // Find the closest leaf node
        KdNode prev = null;
        KdNode node = root;
        while (node != null) {
            if (KdNode.compareTo(node.depth, node.k, value, node.id) <= 0) {
                // Lesser
                prev = node;
                node = node.lesser;
            } else {
                // Greater
                prev = node;
                node = node.greater;
            }
        }
        KdNode leaf = prev;

        if (leaf != null) {
            // Used to not re-examine nodes
            Set<KdNode> examined = new HashSet<KdNode>();

            // Go up the tree, looking for better solutions
            node = leaf;
            while (node != null) {
                // Search node
                searchNode(value, node, K, results, examined);
                node = node.parent;
            }
        }

        // Load up the collection of the results
        Collection<T> collection = new ArrayList<T>(K);
        for (KdNode kdNode : results)
            collection.add((T) kdNode.id);
        return collection;
    }

    private static final <T extends KdTree.DPoint> void searchNode(T value, KdNode node, int K, TreeSet<KdNode> results, Set<KdNode> examined) {
        examined.add(node);

        // Search node
        KdNode lastNode = null;
        Double lastDistance = Double.MAX_VALUE;
        if (results.size() > 0) {
            lastNode = results.last();
            lastDistance = lastNode.id.euclideanDistance(value);
        }
        Double nodeDistance = node.id.euclideanDistance(value);
        if (nodeDistance.compareTo(lastDistance) < 0) {
            if (results.size() == K && lastNode != null)
                results.remove(lastNode);
            node.id.setNearestPointEclDistance(nodeDistance); // emrah
            results.add(node);
        } else if (nodeDistance.equals(lastDistance)) {
        	node.id.setNearestPointEclDistance(nodeDistance); // emrah
            results.add(node);
        } else if (results.size() < K) {
        	node.id.setNearestPointEclDistance(nodeDistance); // emrah
            results.add(node);
        }
        lastNode = results.last();
        lastDistance = lastNode.id.euclideanDistance(value);

        int axis = node.depth % node.k;
        KdNode lesser = node.lesser;
        KdNode greater = node.greater;

        // Search children branches, if axis aligned distance is less than
        // current distance
        if (lesser != null && !examined.contains(lesser)) {
            examined.add(lesser);

            double nodePoint = Double.MIN_VALUE;
            double valuePlusDistance = Double.MIN_VALUE;
            
            nodePoint = node.id.getCoordinates()[axis];
            valuePlusDistance = value.getCoordinates()[axis] - lastDistance;
            
            boolean lineIntersectsCube = ((valuePlusDistance <= nodePoint) ? true : false);

            // Continue down lesser branch
            if (lineIntersectsCube)
                searchNode(value, lesser, K, results, examined);
        }
        if (greater != null && !examined.contains(greater)) {
            examined.add(greater);

            double nodePoint = Double.MIN_VALUE;
            double valuePlusDistance = Double.MIN_VALUE;
            
            nodePoint = node.id.getCoordinates()[axis];
            valuePlusDistance = value.getCoordinates()[axis] + lastDistance;
            
            boolean lineIntersectsCube = ((valuePlusDistance >= nodePoint) ? true : false);

            // Continue down greater branch
            if (lineIntersectsCube)
                searchNode(value, greater, K, results, examined);
        }
    }

    /** 
     * Adds, in a specified queue, a given node and its related nodes (lesser, greater).
     * 
     * @param node 
     *              Node to check. May be null.
     * 
     * @param results 
     *              Queue containing all found entries. Must not be null.
     */
    @SuppressWarnings("unchecked")
    private static <T extends DPoint> void search(final KdNode node, final Deque<T> results) {
        if (node != null) {
            results.add((T) node.id);
            search(node.greater, results);
            search(node.lesser, results);
        }
    }


    @Override
    public String toString() {
        return TreePrinter.getString(this);
    }

    protected static class EuclideanComparator implements Comparator<KdNode> {

        private DPoint point;
        
        public EuclideanComparator(DPoint point) {
            this.point = point;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(KdNode o1, KdNode o2) {
            Double d1 = point.euclideanDistance(o1.id);
            Double d2 = point.euclideanDistance(o2.id);
            
            if (d1.compareTo(d2) < 0)
                return -1;
            else if (d2.compareTo(d1) < 0)
                return 1;
            return o1.id.compareTo(o2.id);
        }
        
    }

    /** 
     * Searches all entries from the first to the last entry.
     * 
     * @return Iterator 
     *                  allowing to iterate through a collection containing all found entries.
     */
    public Iterator<T> iterator() {
        final Deque<T> results = new ArrayDeque<T>();
        search(root, results);
        return results.iterator();
    }

    /** 
     * Searches all entries from the last to the first entry.
     * 
     * @return Iterator 
     *                  allowing to iterate through a collection containing all found entries.
     */
    public Iterator<T> reverse_iterator() {
        final Deque<T> results = new ArrayDeque<T>();
        search(root, results);
        return results.descendingIterator();
    }

    public static class KdNode implements Comparable<KdNode> {

        private final DPoint id;
        private final int k;
        private final int depth;

		private KdNode parent = null;
        private KdNode lesser = null;
        private KdNode greater = null;

		public KdNode(DPoint id) {
            this.id = id;
            this.k = id.getCoordinates().length;
            this.depth = 0;
        }

        public KdNode(DPoint id, int depth) {
            this.id = id;
            this.k = id.getCoordinates().length;
            this.depth = depth;
        }

        public static int compareTo(int depth, int k, DPoint o1, DPoint o2) {
            int axis = depth % k;
            return new CoordinateComparator(axis).compare(o1, o2);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return 31 * (this.k + this.depth + this.id.hashCode());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (!(obj instanceof KdNode))
                return false;

            KdNode kdNode = (KdNode) obj;
            if (this.compareTo(kdNode) == 0)
                return true;
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(KdNode o) {
            return compareTo(depth, k, this.id, o.id);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("k=").append(k);
            builder.append(" depth=").append(depth);
            builder.append(" id=").append(id.toString());
            return builder.toString();
        }
        
        public KdNode getParent() {
			return parent;
		}

		public void setParent(KdNode parent) {
			this.parent = parent;
		}

		public KdNode getLesser() {
			return lesser;
		}

		public void setLesser(KdNode lesser) {
			this.lesser = lesser;
		}

		public KdNode getGreater() {
			return greater;
		}

		public void setGreater(KdNode greater) {
			this.greater = greater;
		}
		 public DPoint getId() {
				return id;
		}
    }

    public static class DPoint implements Comparable<DPoint> {
    	
    		protected final double[] coordinates;
    		
    		private double nearestPointEclDistance;
		


		/**
         * Default constructor
         *
         * @param p_coordinates coordinates identified in line
         */
        public DPoint(double[] p_coordinates) {
            this.coordinates = p_coordinates;
        }

		public void setNearestPointEclDistance(double nearestPointEclDistance) {
			this.nearestPointEclDistance = nearestPointEclDistance;
		}
		
		public double getNearestPointEclDistance() {
			return this.nearestPointEclDistance;
		}


		public double[] getCoordinates() {
			return coordinates;
		}

		/**
         * Computes the Euclidean distance from this point to the other.
         *
         * @param o1
         *            other point.
         * @return euclidean distance.
         */
        public double euclideanDistance(DPoint o1) {
            return euclideanDistance(o1, this);
        }

        /**
         * Computes the Euclidean distance from one point to the other.
         *
         * @param o1
         *            first point.
         * @param o2
         *            second point.
         * @return euclidean distance.
         */
        private static final double euclideanDistance(DPoint o1, DPoint o2) {
        		double retVal = 0;
        		
        		for (int i =0; i<o1.getCoordinates().length; i++) {
        			retVal +=  Math.pow((o1.getCoordinates()[i] - o2.getCoordinates()[i]), 2) ;
        		}
        		
        		double retval = Math.sqrt(retVal);
        		
        		return retval;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
        		int retValue = 31;
        		for(int i =0 ; i<  this.getCoordinates().length; i++) {
        			retValue *= this.getCoordinates()[i];
        		}
            return  retValue;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (!(obj instanceof DPoint))
                return false;

            DPoint otherPoint = (DPoint) obj;
            
            for(int i =0 ; i<  this.getCoordinates().length; i++) {
    				if(this.getCoordinates()[i] != otherPoint.getCoordinates()[i]) {
    					return false;
    				}
    			}
            
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(DPoint o) {
        		int comp = ALL_COMPARATOR.compare(this, o);
        		return comp;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for(int i=0; i< this.coordinates.length; i++) {
            	builder.append( formatNumber(this.coordinates[i])  ).append("\t");
            }
            // return builder.substring(0, builder.length() - 1);
            return builder.toString();
        }
    }
    
    // nicely format the doubles..
	public static String formatNumber(double d)
	{
	    if(d == (long) d)
	        return String.format("%d",(long)d);
	    else
	        return String.format("%s",d);
	}

    protected static class TreePrinter {

        public static <T extends DPoint> String getString(KdTree<T> tree) {
            if (tree.root == null)
                return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static String getString(KdNode node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            if (node.parent != null) {
                String side = "left";
                if (node.parent.greater != null && node.id.equals(node.parent.greater.id))
                    side = "right";
                builder.append(prefix + (isTail ? "└── " : "├── ") + "[" + side + "] " + "depth=" + node.depth + " id="
                        + node.id + "\n");
            } else {
                builder.append(prefix + (isTail ? "└── " : "├── ") + "depth=" + node.depth + " id=" + node.id + "\n");
            }
            List<KdNode> children = null;
            if (node.lesser != null || node.greater != null) {
                children = new ArrayList<KdNode>(2);
                if (node.lesser != null)
                    children.add(node.lesser);
                if (node.greater != null)
                    children.add(node.greater);
            }
            if (children != null) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString(children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString(children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "),
                            true));
                }
            }

            return builder.toString();
        }
    }
}


class CoordinateComparator  implements Comparator<DPoint> {
	
	
	int axisId;

	public CoordinateComparator(int axisId) {
		this.axisId = axisId;
	}
	

	@Override
	public int compare(DPoint o1, DPoint o2) {
		
			if(o1.getCoordinates()[axisId]  < o2.getCoordinates()[axisId]) {
				return -1; 
			}
			else if (o1.getCoordinates()[axisId]  > o2.getCoordinates()[axisId]) {
				return 1;	
			} 
	
		return 0;
	}

	
	public int getAxisId() {
		return axisId;
	}

	public void setAxisId(int axisId) {
		this.axisId = axisId;
	}
	

}

