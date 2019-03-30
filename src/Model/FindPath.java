package Model;

import java.util.ArrayList;
import java.util.List;
/**
 * <h1>FindPath</h1> 
 * This class for defining how to find a path between two countries, which belong to same player.
 *
 * @author chenwei_song
 * @version 3.0
 * @since 2019-03-01
 */
public class FindPath { 
		private int v; 
		private ArrayList<Integer>[] adjList; 	
		public String allpath ="";
		
		/**
		 * This is a constructor of FindPath.
		 * 
		 * @param vertices The size of vertices.
		 */ 
		public FindPath(int vertices){ 	
			this.v = vertices; 
			initAdjList(); 
		}
		
		@SuppressWarnings("unchecked") 
		/**
		 * This method initialize adjacency list.
		 */
		private void initAdjList() 
		{ 
			adjList = new ArrayList[v]; 
			
			for(int i = 0; i < v; i++) 
			{ 
				adjList[i] = new ArrayList<>(); 
			} 
		} 
		
		/**
		 * This method is to add edge.
		 * 
		 * @param u A node.
		 * @param v A node.
		 */
		public void addEdge(int u, int v) 
		{ 
			adjList[u].add(v); 
		} 
	
		/**
		 * This method Prints all paths
		 * 
		 * @param s start node
		 * @param d destination node
		 */
		public void printAllPaths(int s, int d) 
		{ 
			boolean[] isVisited = new boolean[v]; 
			ArrayList<Integer> pathList = new ArrayList<Integer>(); 
			pathList.add(s); 
			printAllPathsUtil(s, d, isVisited, pathList); 
		} 
		
		/**
		 * This method is a recursive function to print all paths from 'u' to 'd'. 
		 * 
		 * @param u             Start node.
		 * @param d             Destination node.
		 * @param isVisited     IsVisited[] keeps track of vertices in current path. 
		 * @param localPathList Path from node u to d ,stores actual vertices in the current path .
		 */
		private void printAllPathsUtil(Integer u, Integer d, 
										boolean[] isVisited, 
										ArrayList<Integer> localPathList) { 
			isVisited[u] = true; 
			
			if (u.equals(d)&&localPathList.size()!=1)
			{ 
				System.out.println(localPathList); 
			
				for (int i = 0; i < localPathList.size(); i++) {
					
					if (allpath == "") {
						allpath = String.valueOf(localPathList.get(i))+" ";
					}
					else {
						allpath = allpath+String.valueOf(localPathList.get(i))+" ";
					}
				}
				allpath = allpath +"#";
				
				// if match found then no need to traverse more till depth 
				isVisited[u]= false; 
				return ; 
			} 
			
			for (Integer i : adjList[u]) 
			{ 
				if (!isVisited[i]) 
				{
					localPathList.add(i); 
					printAllPathsUtil(i, d, isVisited, localPathList); 
					localPathList.remove(i); 
				} 
			} 
			
			// Mark the current node 
			isVisited[u] = false; 
		} 


}
