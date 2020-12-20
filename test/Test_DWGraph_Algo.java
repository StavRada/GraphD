


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import api.*;

class Test_DWGraph_Algo {
	directed_weighted_graph graph;
	dw_graph_algorithms algo;

	@BeforeEach
	void setUp() throws Exception {
		graph=new DWGraph_DS();
		algo=new DWGraph_Algo();
	}


	@Test
	void test11() {


		graph.addNode(new NodeData(0));
		graph.addNode(new NodeData(1));
		graph.addNode(new NodeData(2));
		graph.addNode(new NodeData(3));
		graph.addNode(new NodeData(4));
		graph.connect(0, 1,15);
		graph.connect(1, 2,16);
		graph.connect(2, 0,17);
		graph.connect(2, 3,5);
		graph.connect(3, 4,6);
		graph.connect(4, 3,2);
		algo.init(graph);
		assertEquals(algo.isConnected() , false);

	}
	@Test
	void test12() {


		graph.addNode(new NodeData(0));
		graph.addNode(new NodeData(1));
		graph.addNode(new NodeData(2));
		graph.addNode(new NodeData(3));
		graph.addNode(new NodeData(4));
		graph.connect(0, 1,15);
		graph.connect(1, 2,16);
		graph.connect(2, 0,17);
		graph.connect(2, 3,5);
		graph.connect(3, 2,5);
		graph.connect(3, 4,6);
		graph.connect(4, 3,2);
		algo.init(graph);
		assertEquals(algo.isConnected() , true);

	}
	@Test
	void test2() {
		graph.addNode(new NodeData(0));
		graph.addNode(new NodeData(1));
		graph.addNode(new NodeData(2));
		graph.addNode(new NodeData(3));
		graph.addNode(new NodeData(4));
		graph.connect(0, 1,15);
		graph.connect(1, 2,16);
		graph.connect(2, 0,17);
		graph.connect(2, 3,5);
		graph.connect(3, 4,6);
		graph.connect(4, 4,2);
		algo.init(graph);
		assertEquals(algo.isConnected() , false);
	}
	@Test
	void test3() {
		graph.addNode(new NodeData(0));
		graph.addNode(new NodeData(1));
		graph.connect(0, 1,15);
		graph.connect(1, 2,16);
		algo.init(graph);
		assertEquals(algo.isConnected() , false);
	}
	@Test
	void test4() {
		graph.addNode(new NodeData(0));
		graph.addNode(new NodeData(1));
		graph.addNode(new NodeData(2));
		graph.addNode(new NodeData(3));
		graph.connect(0, 1 ,10);
		graph.connect(1, 2 ,21);
		graph.connect(1, 3 ,3);
		graph.connect(2, 0 ,7);
		graph.connect(2, 1 ,12);
		graph.connect(3, 2 ,2);
		algo.init(graph);
		assertEquals(algo.shortestPathDist(0,2) , 15);
		List<node_data> list = algo.shortestPath(0,2);
		assertEquals( list.size(), 4);
	}
	@Test
	void test_load_save() {
		graph.addNode(new NodeData(0));
		graph.addNode(new NodeData(1));
		graph.addNode(new NodeData(2));
		graph.addNode(new NodeData(3));
		graph.addNode(new NodeData(4));
		
		graph.connect(0, 1,15);
		graph.connect(1, 2,16);
		graph.connect(2, 0,17);
		graph.connect(2, 3,5);
		graph.connect(3, 4,6);
		graph.connect(4, 3,2);

		
		algo.init(graph);
		algo.save("data\\test1");
		algo.load("data\\test1");

		directed_weighted_graph graph1=algo.getGraph();
		assertEquals( graph, graph1);
		
	}
}
