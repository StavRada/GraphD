package gameClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import gameClient.util.Range;
import gameClient.util.Range2D;

public class Gui extends JPanel{

	private Arena _ar;
	private gameClient.util.Range2Range _w2f;
	private Image pokemon;
	private Image agent ;
	private Image bakground ;
	private Graphics2D g2D;
	private long  milis=0;
	
	public void setmils(long mil) {
		this.milis=mil;
	}
	/**
	 * the function gets arena and updates the pokemon,agent and background
	 */
	public void update(Arena ar) {
		this._ar = ar;
		pokemon = new ImageIcon("data\\pok.jpg").getImage();
		agent = new ImageIcon("data\\agent.png").getImage();
		bakground = new ImageIcon("data\\bg.jpg").getImage();
		updateFrame();
	}
	/**
	 * the function updates the frame- size of width and height
	 */
	private void updateFrame() {
		Range rx = new Range(20,this.getWidth()-20);
		Range ry = new Range(this.getHeight()-10,150);
		Range2D frame = new Range2D(rx,ry);
		directed_weighted_graph g = _ar.getGraph();
		_w2f = Arena.w2f(g,frame);
		
	}
	/**
	 * the function prints the pokemons ,agants and the graph
	 * prints the time of the game in seconds
	 */
	public void paint(Graphics g) {
		int w = this.getWidth();
		int h = this.getHeight();
		g.clearRect(0, 0, w, h);
		g.drawImage(this.bakground, 0, 0, w, h, null);
		drawGraph(g);
		drawPokemons(g);
		drawAgants(g);
		
		long seconds = TimeUnit.MILLISECONDS.toSeconds(milis);
		Font font = new Font("Verdana", Font.PLAIN, 24); 
		g.setFont(font);
		g.drawString("Time: "+seconds,50, 50);
	}
	
	
	/**
	 * the function draw the graph on the arena
	 * make the nodes, edges and the colors of them
	*/
	private void drawGraph(Graphics g) {
		directed_weighted_graph gg = _ar.getGraph();
		Iterator<node_data> iter = gg.getV().iterator();
		while(iter.hasNext()) {
			node_data n = iter.next();
			g.setColor(Color.blue);
			drawNode(n,5,g);
			Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
			while(itr.hasNext()) {
				edge_data e = itr.next();
				g.setColor(Color.GREEN);
				drawEdge(e, g);
			}
		}
	}
	/**
	 * the function draws the pokemons on the graph
	 * and sets their icon image
	 */
	private void drawPokemons(Graphics g) {
		List<CL_Pokemon> fs = _ar.getPokemons();
		if(fs!=null) {
		Iterator<CL_Pokemon> itr = fs.iterator();
		
		while(itr.hasNext()) {
			
			CL_Pokemon f = itr.next();
			Point3D c = f.getLocation();
			int r=10;
			if(f.getType()<0) {g.setColor(Color.yellow);}
			if(c!=null) {

				geo_location fp = this._w2f.world2frame(c);
				g.drawImage(this.pokemon,(int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r, null);
				
			}
		}
		}
	}
	/**
	 * the function draw the agants on the graph
	 * and sets their icon image
	 */
	private void drawAgants(Graphics g) {
		g2D =  (Graphics2D) g;
		List<CL_Agent> rs = _ar.getAgents();
		int i=0;
		while(rs!=null && i<rs.size()) {
			geo_location c = rs.get(i).getLocation();
			int r=8;
			String s=""+rs.get(i).getValue();
			i++;
			if(c!=null) {

				geo_location fp = this._w2f.world2frame(c);
				g.drawImage(this.agent,(int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r, null);
				g2D.setColor(Color.RED);
				Font font = new Font("Verdana", Font.PLAIN, 16); 
				g.setFont(font);
				g2D.drawString(s,(int)fp.x(), (int)fp.y()-4*r);
			}
		}
	}
	/**
	 * the function draw the nodes on the graph
	 * and sets their color and values
	 */
	private void drawNode(node_data n, int r, Graphics g) {
		geo_location pos = n.getLocation();
		geo_location fp = this._w2f.world2frame(pos);
		g.setColor(Color.PINK);
		g.fillOval((int)fp.x()-r, (int)fp.y()-r, 2*r, 2*r);
		g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-4*r);
	}
	/**
	 * the function draw the edges on the graph
	 * and sets their color
	 */
	private void drawEdge(edge_data e, Graphics g) {
		directed_weighted_graph gg = _ar.getGraph();
		geo_location s = gg.getNode(e.getSrc()).getLocation();
		geo_location d = gg.getNode(e.getDest()).getLocation();
		geo_location s0 = this._w2f.world2frame(s);
		geo_location d0 = this._w2f.world2frame(d);
		g.setColor(Color.BLACK);
		g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
	}
}
