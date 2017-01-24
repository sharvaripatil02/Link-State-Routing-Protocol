import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*This main program is used to display menu to the user and user can select 5 different options 
for computing operations on Network Topology*/

public class LinkStateRouting
{
	
	static DijkstraAlgo djikstra = new DijkstraAlgo();					//object of class DijkstraAlgo
	public static void main(String[] args) throws Exception 
	{

		int matrix[][]=null; 											//2D matrix to store network topology input matrix
		
		System.out.println("------------Menu------------");
		System.out.println("1. Create a Network Topology");
		System.out.println("2. Build a Connection Table");
		System.out.println("3. Shortest path to destination router");
		System.out.println("4. Modify Topology");
	    System.out.println("5. Exit");

		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();

		while(choice!=5)
		{
			switch (choice)
			{
			case 1:
				   System.out.println("Enter input filename");
    			   String filename1 = sc.next();
    			   matrix = readInput(filename1);
    			   displayMatrix(matrix);
    			   break;
				
			case 2:
				   interfaceTable(matrix);
				   break;
				   
			case 3:
				   shortestPath(matrix);
				   break;
				   
			case 4:
				   deleteRouter(matrix);
				   break;
			
			default:System.out.println("Invalid. Please enter correct choice");
			}
			
			System.out.println("------------Menu------------");
			System.out.println("1. Create a Network Topology");
			System.out.println("2. Build a Connection Table");
			System.out.println("3. Shortest path to destination router");
			System.out.println("4. Modify Topology");
			System.out.println("5. Exit");
			
			choice=sc.nextInt();
			
		}
		
		System.out.println("Exited");
		sc.close();
		
	}
	
	
	/*This function is used to read the input matrix from the source location. User is prompted
	 * to insert the input file name when he selects option 1 from the selection menu.
	 * That input matrix is handled by this function which reads the data and store's it in 
	 * a 2D integer matrix*/
	
	public static int[][] readInput(String filename1) throws Exception {
		
		int rowCount = 0, colCount = 0, temp=0;
		String rowText, row[]; 
		
		BufferedReader a = new BufferedReader(new FileReader("src/"+filename1));    
																					 
		while((rowText = a.readLine())!=null)
		{
			rowCount++;
			row = rowText.split(" ");
			colCount = row.length;
			
		}
		a.close();
		
		int matrix[][] = new int[rowCount][colCount];
		
		BufferedReader b = new BufferedReader(new FileReader("src/"+filename1)); 
		while ((rowText = b.readLine()) != null)
		{
			row = rowText.split(" ");
			for(int i=0;i<colCount;i++)
			{
				matrix[temp][i] = Integer.parseInt(row[i]);
			}
			temp++;
		}
		
		b.close();
		
		System.out.println("Network Topology Matrix:");
		return matrix;
	}
		
	/*This function is used to display the stored 2D network topology matrix*/
	
    public static void displayMatrix(int matrix[][])
    {
    	for(int i=0;i<matrix.length;i++)
    	{
    		for(int j=0;j<matrix.length;j++)
    		{
    			System.out.print(matrix[i][j] + " ");
    		}
    		System.out.println();
    	}
    	System.out.println("Total Routers Nos. "+matrix.length);
    }
	
   
    /*This function is called when user selects option 2. This function computes connection table 
     * for respective router asked by the user and  present in the network topology*/
    
	public static void interfaceTable(int matrix[][])
	{	
		Scanner s=new Scanner(System.in);
		System.out.println("Enter the router");
		int router = s.nextInt();
		System.out.println("Dest\tInterface");
		for(int i=0;i<matrix.length;i++)
		{
			if(i!=router-1);
			{
				System.out.print("R"+(i+1));
				DijkstraAlgo.dijkstra(matrix, router-1, i, 1);
			}
		}	
		
	}

	
	/*This function is called when user opts for option 3 of the selection menu. It takes the user based 
	 * inputs from console. It prompts user to input source and destination routers in order to compute 
	 * shortest path between them. This function inturn calls Dijkstra's Algorithm to calculate shortest 
	 * path between source and destination in the network topology*/
	
	public static void shortestPath(int matrix[][]) throws IOException
	{
		System.out.println("Enter the source router");
		Scanner s =new Scanner(System.in);
		int src = Integer.parseInt(s.nextLine()) - 1;
		System.out.println("Enter the destination router");
		int dest = Integer.parseInt(s.nextLine()) - 1;
		DijkstraAlgo.dijkstra(matrix,src,dest,2);
		
	}

	
	/*This function is triggered when user selects option 4 to modify the network topology. It asks user 
	 * to input router that is meant to be deleted and updates the network topology matrix's subsequent 
	 * router entry to -1 {rows & columns}. Here -1 indicates that their is no dedicated path to tht respective 
	 * router. It sets routers value to -1 indicating router is removed or down. It then passes updated matrix to Dijkstra's
	 * algorithm to compute shortest path  */
	
	public static void deleteRouter(int[][] matrix) throws IOException{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter router to be deleted");
		int router = s.nextInt();
		
		for(int i=0;i<matrix.length; i++) //row
		{
			if(i==router-1)
			{
			for(int j=0;j<matrix.length;j++)
			{
				matrix[i][j] = -1;
				matrix[j][i] = -1;
			}
			}
		}
		
		displayMatrix(matrix);
		System.out.println("\nTo calculate shortest path\n");
		shortestPath(matrix);
		
	}
	
	
}
