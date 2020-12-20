package api;

import gameClient.util.Point3D;

public class NodeData implements node_data{
	private static int count=0;
	private int key;
	private geo_location location;
	private double weight;
	private String info;
	private int tag;

	public NodeData() {
		key=count++;
		location = new Point3D(0,0,0);
		weight=0;
		info = "";
		tag=0;
	}
	/**
	 * constructor
	 */
	public NodeData(int _key) {
		key=_key;
		location = new Point3D(0,0,0);
		weight=0;
		info = "";
		tag=0;
	}

   /**
    * copying constructor
    */
	public NodeData(node_data node) {
		key=count++;
		location = new  Point3D(node.getLocation().x(),node.getLocation().y(),node.getLocation().z());
		this.weight=node.getWeight();
		this.info=node.getInfo();
		this.tag=node.getTag();

	}
	
	/**
	 * return the key of this node
	 */
	public int getKey() {
		return key;
	}

	/**
	 * returns the location of this node
	 */
	public geo_location getLocation() {
		return location;
	}

	/**
	 * this function sets the location by 3 points
	 */
	public void setLocation(geo_location p) {
		location=new Point3D(p.x(),p.y(),p.z());
	}

	/**
	 * returns the weight of this node
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * the function sets the weight to w
	 */
	public void setWeight(double w) {
		weight=w;
	}

	/**
	 * returns the information of this node
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * the function sets the information to s
	 */
	public void setInfo(String s) {
		info=s;

	}

	/**
	 * returns the tag of this nodes
	 */
	public int getTag() {
		return tag;
	}

	/**
	 * this function sets the tag to t
	 */
	public void setTag(int t) {
		tag=t;
	}

}
