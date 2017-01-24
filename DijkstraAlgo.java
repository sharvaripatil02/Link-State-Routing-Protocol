/*This class will compute Dijkstra's algorithm to determine the shortest path between two routers*/

public class DijkstraAlgo
{

	public static Object dijkstra(int[][] matrix, int src , int dest ,int choice) 
	{
		
		
		int[] distance = new int[matrix.length];						// distance[] array will maintain shortest
																    	//distance between 2 routers
		int[] visited = new int[matrix.length];							//visited[] array will keep track of visited 
																		//routers to avoid same router to be considered for shortest path calculation 	
				
		int nextRouter = src;											// An index to predecessor array 
		
		
			for(int j=0;j<matrix[0].length;j++)							//Copy router subsequent weights into an distance[] array
			{
				distance[j] = matrix[src][j];
				
			}
			
		
		int[] predecessor = new int[matrix[0].length];					//initialize predecessor that will help further to traverse path
			
		for (int i = 0; i < predecessor.length; i++) 					
		{
			predecessor[i] = src;
		}
		
		visited[nextRouter] = 1;							     		// initially visited of source make it as true
		
		for(int x=0;x<matrix[0].length; x++)							//sort distance array based on minimum path weight, to determine shortest path between routers
		{
			int min = Integer.MAX_VALUE;

			for (int j = 0; j < distance.length; j++) {
				
				if (visited[j]!=1 && j != src && distance[j] != -1) {
					
					if (distance[j] < min) {
						min = distance[j];
						nextRouter = j;
					}
				}
			}
			
			if (nextRouter == dest) 										
			{
				break;
			}
			
			visited[nextRouter] = 1;						
			
			for (int i = 0; i < distance.length; i++) {

				
					if(visited[i] != 1 && distance[i] == -1 && matrix[nextRouter][i] != -1)					//handle routers with -1 weight means routers having no link/connectivity
					{
						distance[i] =  matrix[nextRouter][i] + distance[nextRouter];
						predecessor[i] = nextRouter;
					}
					
					else if(matrix[nextRouter][i] != -1 && distance[nextRouter] > min+matrix[nextRouter][i])
					{
						distance[i] =  matrix[nextRouter][i] + distance[nextRouter];
						predecessor[i] = nextRouter;	
					}
				
			}
		}
		
		
		/*Hops in to this when user opts for creating shortest path option 3. User provided source and 
		 * destination values are passed further to output minimum path*/
		
		if(choice ==2 ) 
		{
			minWeight(predecessor, src, dest, distance.length,1);
			System.out.println();
			int total = distance[dest] - distance[src];
			if(total == -1)																	// when user request for the router that is down or removed from the topology
			{
				System.out.println("Router is down. Please chose different router");
			}
			else
			{
			System.out.println("Total cost =  "+ total);
			}
	    }
		
		/*To determine connection table for the router user has requested*/
		
		if(choice==1)
		{
			minWeight(predecessor, src, dest, distance.length, 2);
		}
		else return null;
		
		return null;
    }
	
	
	/*This function output's shortest path between two routers and it also populates connection router table */
	
	public static void minWeight(int[] predecessor, int src, int dest, int length, int choice)
	{
		
		int current = 1;
		int[] edgePath = new int[length];
		int i = dest;
		edgePath[0] = i;
		
		
		boolean flag=false;
		
		while (predecessor[i] != src) 			// aligns predecessor routers to edgePath[] array in order to output the path
		{
			i = predecessor[i];
			edgePath[current] = i;
			current++;
		}
		edgePath[current] = src;

		if(choice==1) 																// triggered when user wants to output the shortest path between source and destination
		{
			System.out.print("Shortest path from " + (src + 1) + " to " + (dest + 1));
			System.out.println();
			for (int k = current; k > 0; k--)
			{
				System.out.print("R" + (edgePath[k] + 1) + " to ");
				
			}
			System.out.print("R" + (edgePath[0] + 1)) ;
		}
		
		
		if(choice==2) 															// triggered when user wants to get connection table for specified router
		{	
			
			if(current>0)
			{
				for (int x = current-1; x > 0; x--)
				{
					System.out.print("\tR" + (edgePath[x] + 1) + "\n");
					flag=true;
				}
			}

			if(flag==false)
			{
				System.out.print("\tR" + (edgePath[0] + 1)+"\n") ;
			}

		}
	}
}
