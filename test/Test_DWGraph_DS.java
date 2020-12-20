

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import api.*;
import gameClient.util.Point3D;
class Test_DWGraph_DS {
	directed_weighted_graph g;
	@BeforeEach
	void setUp() throws Exception {
		g=new DWGraph_DS();
	}

	
	@Test
	void NodeTest() {
		node_data a=new NodeData();
		node_data b=new NodeData();
		node_data c=new NodeData();
		assertEquals( a.getKey() , 0 );
		assertEquals( b.getKey() , 1 );
		assertEquals( c.getKey() , 2 );
		assertEquals( c.getWeight() , 0 );
		assertEquals( c.getTag(), 0 );
		assertEquals( c.getInfo() , "" );
		assertNotEquals( c.getLocation(),null);
		c.setWeight(100);
		c.setInfo("ariel");
		c.setLocation(new Point3D(2.6,7,1));
		node_data d= new NodeData(c);
		assertEquals( d.getKey() , 3 );
		assertEquals( d.getWeight() , 100 );
		assertEquals( d.getInfo() , "ariel" );
		assertEquals( d.getLocation().x() , 2.6 );
		assertEquals( d.getLocation().y() , 7 );
		assertEquals( d.getLocation().z() , 1 );
	}
	
	
	@Test
	void DWGraph_DS() {
		//*********add node************
		node_data a=new NodeData();
		node_data b=new NodeData(a);
		g.addNode(a);
		assertEquals(g.nodeSize() , 1 );
		g.addNode(a);
		assertEquals(g.nodeSize() , 1 );
		assertEquals(g.getMC() , 1 );
		g.addNode(b);
		assertEquals(g.nodeSize() , 2 );
		assertEquals(g.getMC() , 2 );
		assertEquals(g.edgeSize() , 0 );
		//*********get node************
		assertEquals(g.getNode(2) ,null);
		g.getNode(5).setTag(500);
		assertEquals(b.getTag() ,500);
		
		//*********get edge************
		assertEquals(g.getEdge(4, 5) ,null);
		assertEquals(g.getEdge(10, 11) ,null);
		
		//*********connect************
		g.connect(4, 5,-10);
		assertEquals( g.edgeSize(),0);
		g.connect(5, 5,10);
		assertEquals( g.edgeSize(),0);
		g.connect(50, 5,10);
		assertEquals( g.edgeSize(),0);
		g.connect(4, 5,10);
		assertEquals( g.edgeSize(),1);
		
		//*********get edge************
		assertNotEquals(g.getEdge(4, 5) ,null);
		assertEquals(g.getEdge(4, 5).getWeight() ,10);
		assertEquals(g.getEdge(4, 5).getSrc() ,4);
		assertEquals(g.getEdge(4, 5).getDest() ,5);
		assertEquals(g.getEdge(4, 5).getInfo() ,"");
		assertEquals(g.getEdge(4, 5).getTag(),0);
		g.getEdge(4, 5).setInfo("edge1");
		g.getEdge(4, 5).setTag(5);
		assertEquals(g.getEdge(4, 5).getInfo() ,"edge1");
		assertEquals(g.getEdge(4, 5).getTag(),5);
		assertEquals( g.getMC(),3);
		
		//*********remove edge************
		g.removeEdge(7, 5);
		assertEquals( g.edgeSize(),1);
		g.removeEdge(4, 8);
		assertEquals(g.edgeSize(),1);
		node_data c=new NodeData();
		g.addNode(c);
		g.removeEdge(4, 6);
		assertEquals( g.edgeSize(),1);
		edge_data edge=g.removeEdge(4, 5);
		assertEquals( edge.getWeight(),10);
		assertEquals( edge.getInfo(),"edge1");
		assertEquals( g.removeEdge(4, 5),null);
		
		//*********remove node************
		g.connect(4, 5,10);
		assertEquals( g.removeNode(7),null);
		assertEquals( g.removeNode(6).getKey(),6);
		assertEquals( g.getMC(),7);
		assertEquals(g.getV().iterator().next().getKey(),4);
		g.removeNode(4);
		assertEquals( g.getMC(),8);
		assertEquals( g.edgeSize(),0);
		assertEquals( g.nodeSize(),1);
	}
}
