package api;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import gameClient.util.Point3D;

public class DWGraph_DS implements directed_weighted_graph {

	private HashMap<Integer,node_data> nodes;
	private HashMap<Integer,HashMap<Integer,edge_data>> edges;
	private int mc;
	private int SizeEdge;
	
	/**
	 * constructor 
	 */
	public DWGraph_DS() {
		mc=0;
		SizeEdge=0;
		nodes=new HashMap<>();
		edges=new HashMap<Integer,HashMap<Integer,edge_data>>();
	}
	
	/**
	 * copy constructor 
	 */
	public DWGraph_DS(directed_weighted_graph g) {
		mc=g.getMC();
		SizeEdge=g.edgeSize();
		for(node_data i: g.getV()) {
			nodes.put(i.getKey(), i);
		}
		for(node_data i: g.getV()) {
			for(edge_data j: g.getE(i.getKey())){
				edges.put(i.getKey(), new HashMap<>());
				edges.get(i.getKey()).put(j.getDest(), j);
			}
		}
	}
	
	/**
	 * get key and return the node with this key
	 */
	public node_data getNode(int key) {
		return nodes.get(key);
	}

	/**
	 * this function gets src and dest and checks if they are found in the map
	 * if they are, returns the edge between them
	 */
	public edge_data getEdge(int src, int dest) {
		if(getNode(src)!=null&&getNode(dest)!=null&&edges.get(src)!=null&&edges.get(src).containsKey(dest)){
			return edges.get(src).get(dest);
		}
		return null;
	}

	/**
	 * this function gets node data and put her key in the map 
	 * and adds this node
	 */
	public void addNode(node_data n) {
		if(!nodes.containsKey(n.getKey())) {
			nodes.put(n.getKey(), n);
			mc++;
		}
	}


	/**
	 * this function gets src ,dest and weight checks if they are in the map 
	 * and check if there is edge to dest 
	 * adds edge to the hashmap of edges and connect between src and dest
	 * and adding the weight we get to this edge
	 */
	public void connect(int src, int dest, double w) {
		if(src!=dest && w>=0 && getNode(src)!=null && getNode(dest)!=null) {
			if(edges.get(src)==null) {
				edges.put(src, new HashMap<Integer,edge_data>());
				edges.get(src).put(dest, new EdgeData(src,dest,w));
			}else {
				edges.get(src).put(dest, new EdgeData(src,dest,w));
			}
			SizeEdge++;
			mc++;
		}
	}

	/**
	 * returns collection of the nodes and their values
	 */
	public Collection<node_data> getV() {
		return nodes.values();
	}


	/**
	 * gets node id and returns the values of edges with this id 
	 */
	public Collection<edge_data> getE(int node_id) {
		if(edges.get(node_id)!=null) {
			return edges.get(node_id).values();
		}
		return null;
	}

	/**
	 * this function gets key and checks if there is node with this key if there is
	 * remove this node and the edge that connect to this node with this key
	 * and returns the node data that we remove
	 */
	public node_data removeNode(int key) {
		if(nodes.containsKey(key)) {
			for(node_data i: getV()) {
				if(edges.get(i.getKey())!= null && edges.get(i.getKey()).containsKey(key)) {
					edges.get(i.getKey()).remove(key);
					mc++;
					SizeEdge--;
				}
			}
			if(edges.get(key)!=null)
				SizeEdge-=edges.get(key).size();
			edges.remove(key);
			mc++;
			return nodes.remove(key);
		}
		return null;
	}
	


	/**
	 * this function gets src ,dest checks if they exist,
	 * if they are we remove the edge between them
	 */
	public edge_data removeEdge(int src, int dest) {
		if(getNode(src)!=null&&getNode(dest)!=null&&edges.get(src)!=null&&edges.get(src).containsKey(dest)) {
			mc++;
			SizeEdge--;
			return edges.get(src).remove(dest);
		}
		return null;
	}


	/**
	 * returns how much nodes exist 
	 */
	public int nodeSize() {
		return nodes.size();
	}


	/**
	 * returns how much edges exist
	 */
	public int edgeSize() {
		return SizeEdge;
	}


	/**
	 * returns the mode counter
	 */
	public int getMC() {
		return mc;
	}

	

	/**
	 * this function compares objects
	 */
	public boolean equals(Object obj) {
		return this.SizeEdge== ((directed_weighted_graph)obj).edgeSize() &&
				this.nodeSize()== ((directed_weighted_graph)obj).nodeSize();
	}


	

	/**
	 * class of edge location
	 */
	public class EdgeLocation implements edge_location{
		private edge_data edge;
		

		/**
		 * constructor
		 */
		public EdgeLocation(int src,int dest,double weight) {
			edge=new EdgeData(src,dest,weight); 
		}


		/**
		 * returns edge of edge data
		 */
		public edge_data getEdge() {
			return edge;
		}


		/**
		 * returns the ratio
		 */
		public double getRatio() {
			return 0;
		}
		
	}

	/**
	 * class of edge data
	 */
	public class EdgeData implements edge_data{
		private int src;
		private int dest;
		private double weight;
		private String info;
		private int tag;


		/**
		 * constructor
		 */
		public EdgeData (int src,int dest,double weight) {
			this.src=src;
			this.dest=dest;
			this.weight=weight;
			this.info="";
			this.tag=0;
		}
		

		
		public EdgeData(edge_data edge) {
			this.src=edge.getSrc();
			this.dest=edge.getDest();
			this.info=edge.getInfo();
			this.tag=edge.getTag();
			this.weight=edge.getWeight();
		}


		/**
		 * returns src
		 */
		public int getSrc() {
			return src;
		}


		/**
		 * returns dest
		 */
		public int getDest() {
			return dest;
		}


		/**
		 * returns the weight of this edge
		 */
		public double getWeight() {
			return weight;
		}


		/**
		 * returns the info of this edge
		 */
		public String getInfo() {
			return info;
		}


		/**
		 * this function sets the info of this edge to s
		 */
		public void setInfo(String s) {
			info=s;
		}


		/**
		 * returns the tag of this edge
		 */
		public int getTag() {
			return tag;
		}


		/**
		 * sets the tag of this edge to t
		 */
		public void setTag(int t) {
			tag=t;	
		}
	}

}
