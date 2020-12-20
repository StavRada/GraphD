package api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.JsonParser;

import gameClient.util.Point3D;

public class DWGraph_Algo implements dw_graph_algorithms {

	private directed_weighted_graph d_graph;

	/**
	 * constructor
	 */
	public DWGraph_Algo() {
		d_graph=new DWGraph_DS();
	}


	/**
	 * Initialize the graph 
	 */
	public void init(directed_weighted_graph g) {
		d_graph=g;
	}

	/**
	 * returns directed weighted graph 
	 */
	public directed_weighted_graph getGraph() {
		return d_graph;
	}

	/**
	 * copying the graph
	 */
	public directed_weighted_graph copy() {
		directed_weighted_graph g_copy= new DWGraph_DS(d_graph);
		return  g_copy;
	}
	/**
	 * stack and hash map for "isConnected" algorithm
	 */
	private Stack<node_data> s= new Stack<node_data>();
	private HashMap<Integer,Boolean> onStack= new HashMap<Integer,Boolean>();
	private int Scc=0;
	private int id=0;

	/**
	 * we used the tarjan algorithm to find the strongly connected components in the graph
	 * if we have 1 components graph is connected else is not connected
	 * 
	 */
	public boolean isConnected() {
		toZeroPoint();
		for(node_data i:this.d_graph.getV()) onStack.put(i.getKey(), false);


		for(node_data i:this.d_graph.getV()) {
			if(i.getTag()==0) {
				dfs(i);
			}
		}
		//ids -> tag
		//low -> weight
		//System.out.println(Scc);
		if(Scc==1)return true;
		else return false;

	}
	/**
	 * dfs (depth first search) algorithm
	 */
	private void dfs(node_data at) {
		//init
		s.add(at);
		onStack.replace(at.getKey(), true);
		id++;
		at.setTag(id);
		at.setWeight(id);
		if(this.d_graph.getE(at.getKey())!=null) {
			for(edge_data e: this.d_graph.getE(at.getKey())) {
				node_data to = this.d_graph.getNode(e.getDest());
				if(to.getTag()==0) dfs(to);
				if(onStack.get(to.getKey())) at.setWeight(Math.min(at.getWeight(),to.getWeight()));	
			}
		}
		if(at.getTag()==at.getWeight()) {
			while(!s.isEmpty()) {
				node_data node=s.pop();
				onStack.replace(node.getKey(), false);
				node.setWeight(at.getTag());
				if(node.getKey()==at.getKey()) break;
			}
			Scc++;
		}



	}

	/**
	 * Algorithm to check the shortest path between 2 nodes in graph, src and dest.
	 *  Using dijkstra algorithm.
	 *  my implementation requires PriorityQueue to access in distance order.
	 *  This function returns how much we went in the path
	 */
	public double shortestPathDist(int src, int dest) {
		if(this.d_graph.getNode(src) == null||this.d_graph.getNode(dest)== null) return -1;
		toZeroPoint();

		PriorityQueue<node_data> minHeap = new PriorityQueue<node_data>(new Comparator<node_data>() {
			@Override
			public int compare(node_data o1, node_data o2) {
				return - Double.compare(o2.getWeight(),o1.getWeight());
			}
		});

		node_data src_n=d_graph.getNode(src);
		src_n.setWeight(0);;
		minHeap.add(src_n);

		while(!minHeap.isEmpty()) {
			node_data u=minHeap.poll();
			if( d_graph.getE(u.getKey())!=null) {
				for(edge_data i : d_graph.getE(u.getKey())) {
					node_data v=d_graph.getNode(i.getDest());
					double dist=u.getWeight() +  i.getWeight();
					if(dist<v.getWeight()) {
						v.setWeight(dist);
						minHeap.remove(v);
						minHeap.add(v);
					}

				}
			}
		}
		return this.d_graph.getNode(dest).getWeight();
	}

	/**
	 * This function set the tag to infinity 
	 */
	private void toZeroPoint() {
		Iterator<node_data> it=d_graph.getV().iterator();
		while(it.hasNext()) {
			node_data temp=it.next();
			temp.setWeight(Double.POSITIVE_INFINITY);
			temp.setTag(0);


		}
	}
	/**
	 * This function returns list of nodes we went
	 * and returns the path
	 */
	public List<node_data> shortestPath(int src, int dest) {
		toZeroPoint();
		LinkedHashMap<node_data,node_data> p=new LinkedHashMap<>();
		PriorityQueue<node_data> minHeap = new PriorityQueue<node_data>(new Comparator<node_data>() {
			@Override
			public int compare(node_data o1, node_data o2) {
				return - Double.compare(o2.getWeight(),o1.getWeight());
			}
		});

		node_data src_n=d_graph.getNode(src);
		src_n.setWeight(0);
		minHeap.add(src_n);

		while(!minHeap.isEmpty()) {
			node_data u=minHeap.poll();
			if(d_graph.getE(u.getKey())!=null) {
				for(edge_data i : d_graph.getE(u.getKey())) {
					node_data v=d_graph.getNode(i.getDest());
					double dist=u.getWeight() + i.getWeight();
					if(dist<v.getWeight()) {
						v.setWeight(dist);
						p.put(v, u);
						minHeap.remove(v);
						minHeap.add(v);
					}
				}
			}
		}
		return getpath(p,dest);
	}

	private List<node_data> getpath(LinkedHashMap<node_data,node_data> p,int dest){
		List<node_data> list =new LinkedList<>();

		node_data d=d_graph.getNode(dest);
		while(d!=null){
			list.add(d);
			d = p.get(d);
		}
		Collections.reverse(list);
		return list;
	}

	/**
	 * This function load graph to file
	 */
	public boolean save(String file) {
		Gson gsonbuild = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
		FileOutputStream out;
		try {
			out = new FileOutputStream(file);
			JsonWriter Jwrite = new JsonWriter(new OutputStreamWriter(out));           
			JsonObject g = new JsonObject();
			List<JsonObject> Nodes = new ArrayList<>();
			List<JsonObject> Edges = new ArrayList<>();
			for(node_data i:getGraph().getV()) {
				//{"pos":"35.18910131880549,32.103618700840336,0.0","id":10}
				JsonObject jsonnode = new JsonObject();
				jsonnode.addProperty("pos",i.getLocation().toString());
				jsonnode.addProperty("id",i.getKey());
				Nodes.add(jsonnode);

				//{"src":0,"w":1.4004465106761335,"dest":1}
				if(this.d_graph.getE(i.getKey())!=null) {
					for(edge_data j:this.d_graph.getE(i.getKey())) {

						JsonObject jsonedge = new JsonObject();
						jsonedge.addProperty("src",j.getSrc());
						jsonedge.addProperty("w",j.getWeight());
						jsonedge.addProperty("dest",j.getDest());
						Edges.add(jsonedge);
					}
				}
			}
			g.add("Edges",gsonbuild.toJsonTree(Edges.toArray()));
			g.add("Nodes", gsonbuild.toJsonTree(Nodes.toArray()));

			gsonbuild.toJson(g,Jwrite); 
			Jwrite.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * This function load graph to file
	 */
	public boolean load(String file) {
		try{
			FileInputStream input = new FileInputStream(file);
			JsonReader Jread = new JsonReader(new InputStreamReader(input));
			JsonObject jsonObject = JsonParser.parseReader(Jread).getAsJsonObject(); 
			directed_weighted_graph graph_read = new DWGraph_DS();
			for (JsonElement element : jsonObject.getAsJsonArray("Nodes")) {
				NodeData n = new NodeData(element.getAsJsonObject().get("id").getAsInt());
				String [] geoPos = element.getAsJsonObject().get("pos").getAsString().split(",");
				n.setLocation(new Point3D(Double.parseDouble(geoPos[0]),Double.parseDouble(geoPos[1]),Double.parseDouble(geoPos[2])));
				graph_read.addNode(n);
			}
			for (JsonElement element : jsonObject.getAsJsonArray("Edges")) {
				graph_read.connect(element.getAsJsonObject().get("src").getAsInt(),element.getAsJsonObject().get("dest").getAsInt(),element.getAsJsonObject().get("w").getAsDouble());
			}

			this.init(graph_read);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
