package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.edge_data;
import api.game_service;
import api.node_data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Ex2 implements Runnable{
	
	private static MyFrame _win;
	private static Arena _ar;
	private int scenario_num=0;
	private int id=0;
//main to start the game
	public static void main(String[] a) {
	
		//start from gui
		if(a.length==0) {
		FrameStart start= new FrameStart();
		//start from cmd
		}else {
			Ex2 ex2=new Ex2();
			ex2.setLevel_Id(Integer.parseInt(a[0]),Integer.parseInt(a[1]));
			Thread client = new Thread(ex2);
			client.start(); 
		}
		
	}

	@Override
	public void run() {
		game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
		game.login(id);
		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		init(game);
		game.startGame();
		_win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
		long seconds = TimeUnit.MILLISECONDS.toSeconds(game.timeToEnd());
		int ind=(int)seconds*10;
		long dt=100;
		// 10 secenod 100 move
		while(game.isRunning()) {
			moveAgants(game, gg);
			_win.repaint();
			_win.paint_time(game.timeToEnd());
			try {
				if(ind%1==0) {_win.repaint();}
				Thread.sleep(dt);
				ind++;
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(game.toString());
		System.exit(0);
	}
	
	private static void moveAgants(game_service game, directed_weighted_graph g) {
		//get from server
		String move = game.move();
		String fs =  game.getPokemons();
		//init in arena and print in gui
		List<CL_Agent> agents = Arena.getAgents(move, g);
		_ar.setAgents(agents);

		List<CL_Pokemon> pok = Arena.json2Pokemons(fs);
		_ar.setPokemons(pok);

		//start algo short pokim
		// for all agents per agents excute next node 
		for(CL_Agent agent:agents) {
			CL_Agent ag = agent;
			int dest = ag.getNextNode();
			int src = ag.getSrcNode();
			if(dest==-1) {
				//get pok
				edge_data e = nextNode(g, src,pok);
				dw_graph_algorithms algo = new DWGraph_Algo();
				algo.init(g);
				List<node_data> path= algo.shortestPath(src, e.getSrc());
				if(path==null) {
					game.chooseNextEdge(ag.getID(), e.getDest());
				}else {
					//path to pok short 
					for(node_data i: path) {
						game.chooseNextEdge(ag.getID(), i.getKey());
					}
					game.chooseNextEdge(ag.getID(), e.getDest());
				}
				
			}
		}
	}

// algorithm next node for all pokimone chose shortest pokrmon 
	private static edge_data nextNode(directed_weighted_graph g, int src,List<CL_Pokemon> pok) {
		double min = Double.MAX_VALUE;
		dw_graph_algorithms algo = new DWGraph_Algo();
		algo.init(g);
		edge_data e=null;
		double S=-1;
		for(CL_Pokemon poki:pok) {
			CL_Pokemon po=poki;
			S = algo.shortestPathDist(src, po.get_edge().getSrc());
			if(S<min  ) {
				min=S;
				e= po.get_edge();
			}

		}
		return e;
	}
	
// init the game gui , get pok from server and init agent 

	private void init(game_service game) {
		String fs = game.getPokemons();
		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
		
		//init arena 
		_ar = new Arena();
		_ar.setGraph(gg);
		_ar.setPokemons(Arena.json2Pokemons(fs));
		//init gui 
		_win = new MyFrame("Ex2 Game");
		_win.setSize(1000, 700);
		_win.update(_ar);
		_win.show();
		
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("agents");

			ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
			for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
			
			for(int a = 0;a<rs;a++) {
				int ind = a%cl_fs.size();
				CL_Pokemon c = cl_fs.get(ind);
				int nn = c.get_edge().getDest();
				if(c.getType()<0 ) {nn = c.get_edge().getSrc();}

				game.addAgent(nn);
				}
		}
		catch (JSONException e) {e.printStackTrace();}
	}
	
	// set level id 
	public void setLevel_Id(int id,int senrio) {
		this.scenario_num=senrio;
		this.id=id;
	}

}
