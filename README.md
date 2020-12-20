#pokemon game

![alt text](https://github.com/StavRada/GraphD/blob/master/data/Game.png)

 In this project we dealt with the development of data structures of directed weighted graph, after that we implemented a number of algorithms on the graph such as: connected, shortest path distance and more…
###About Graph:
 Graph is a collection of points and lines that connect pairs of points.
The points are called Vertices and the lines called edges.
In the first part in the project we implemented a weighted directed graph this is graph that when there is meaning in the direction of a directed edge - it exits from one node and enters another node. 
In the second part of the project we use the data structure and algorithms we have developed to realize the "Pokemon Challenge" game, which includes processes, a graphical interface, and an automated counterplay system server.

###The first class is DWGraph_Ds:
In this class there is edges size, mode counter (mc) and graph, I used hash map for the nodes and hash map for the edges.
and the functions are:
Get node – get the key of this node from linked hash map
Get edge – gets src and dest and checks if they are exist and returns the edge between them. 
Add node – check if this node exist in the graph, and adds the node with this key we get to the graph
Connect – The function gets src, dest and weight, checks first if there is edge between the two nodes and if not we connects between them and adds the weight we get
Remove node – remove the node that receive and the edge that connect to this node with this key and returns the node data that we remove.
Equals- The function compares the size of the graph to another size in the graph ,and also the size of the edges to the size of the edges of another graph.
And more function that receive node size, edge size

In this class we have subs classes edge location and edge data:
The function are:
Get edge- this function returns edge of edge data.
Get ratio- this function returns the ratio. 
Get src- this function returns the src
And more like: get dest, get src, get info, get tag

Another class is node data:
 and the functions are: key, tag ,info, edges, weight 
get Info- The function returns the information of the node
set Tag- we set the tag of the node for example: return tag infinity for algorithms Dijkstra
get Weight- we gets the weight of the edge or node
set location- this function sets the location by 3 points (x,y,z)
get location- this function returns the location of this node

###The second class is DWGraph Algo (algorithm):
In this class there are algorithms that we used to graph,
Is connected – I used algorithms to check if all nodes in graph are connected for that I used Tarjan(strongly connected components) algorithm and  Dfs (depth first search) algorithm. 
 
Tarjan strongly connected components is an algorithm in graph theory for finding the strongly connected components (SCCs) of a directed graph. 
Depth-first search (DFS) is an algorithm for traversing or searching tree or graph data structures in our case is graph.
 the dfs algorithm checks the node on which it was activated, and then runs itself recursively on each of the nodes linked to the node to which it was activated, if it has not yet visited them, In order to remember which nodes the algorithm has already visited I used stack to put all the visited nodes.
Shortest path – I used algorithm to check the shortest path between 2 nodes in graph -dijkstra is algorithm that we maintain two sets one set contains vertices included in shortest path tree, other set includes vertices not yet included in shortest path tree At every step of the algorithm, we find a vertex which is in the other set (set of not yet included) and has a minimum distance from the source. 
 my implementation requires Priority Queue to access in distance order.
This function returns how much we went in the path and the path.
We have more functions like:
Copy- the function copying the graph
Init- The function Initialize the graph
Save- The functions save the graph to fil
Load – The function load the graph to file

##The second part in  this project is "Pokemon Challenge" game ,the classes are: 
Arena: this class represents arena with agents moving on the graph and grabs pokemons that avoid from them. The functions in this class are: 
Set pokemons- the function gets list from type CL_pokemon
Set agents- the function gets list from type CL_agents
And more like: set graph, get graph, get pokemon, get info, set info, update edge, is on edge..

My Frame: This class represents a very simple GUI class to present a game on a graph. 
The functions in this class are: 
Update- his function gets arena and update the arena.
Update frame- this function update the frame (size of width an height).
And more :paint, draw info..

EX2: In this class we run the program i.e. run the game.
This class use Arena and MyFrame classes, and the functions are:
Run- this function run the game.
moveAgents- this function moves the agent with shortest path algorithm.
nextNode- this function checks what the shortest path to the pokemon.
Init- this function initialize the game gui, get pokemon from server and initialize agent.
setLevel_id- this function set level id.

