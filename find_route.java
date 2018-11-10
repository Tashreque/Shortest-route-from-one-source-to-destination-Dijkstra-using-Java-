import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class find_route {
	
	public static int n, m; // n = #vertices, m = #edges
    public static LinkedList[] graph; //
    public static int start, end; // start and end points for shortest path
    static HashMap<Integer, String> hmap = new HashMap<Integer, String>();
	static HashMap<String, Integer> hmap1 = new HashMap<String, Integer>();
	
    public static void main(String[] args) 
    {
    	BufferedReader br;
    	int edgeCount = -1;
    	ArrayList<String> unique = new ArrayList<String>();
    	try{
    	    br = new BufferedReader(new FileReader(args[0]));		
    	    String line = "";
    	    
    	    while (!line.equals("END OF INPUT")) {
    	       line = br.readLine();
    	       String[] seperate = line.split(" ");
    	       edgeCount = edgeCount + 1;
    	       for(int i=0; i<seperate.length-1; i++) {
    	    	   if(!unique.contains(seperate[i]) && !line.equals("END OF INPUT")) {
    	    		   unique.add(seperate[i]);
    	    	   }
    	       }
    	    }
    	    br.close();
    	    //System.out.println(edgeCount);
    	}catch(IOException e) {
    		   e.printStackTrace();
    	}
 
    	String[] uniqueArray = new String[unique.size()];
    	uniqueArray = unique.toArray(uniqueArray);
    	
    	for(int i=0; i<uniqueArray.length; i++) {
    		hmap.put(i, uniqueArray[i]);
    		hmap1.put(uniqueArray[i], i);
    	}
 
        n = unique.size();
        m = edgeCount;
 
        // Initialize adjacency list structure to empty lists:
        graph = new LinkedList[n];
        for (int i = 0; i < n; i++)
            graph[i] = new LinkedList();
 
        // Add each edge twice
        
        try{
    	    br = new BufferedReader(new FileReader(args[0]));		
    	    String line = "";
    	    
    	    while (!line.equals("END OF INPUT")) {
    	       line = br.readLine();
    	       String[] seperate = line.split(" ");
    	       if(!line.equals("END OF INPUT")) {
    	    	   int v1 = hmap1.get(seperate[0]);
                   int v2 = hmap1.get(seperate[1]);
                   int w = Integer.parseInt(seperate[2]);
                   graph[v1].add(new Node(v2, w));
                   graph[v2].add(new Node(v1, w));
    	       }
    	       
    	    }
    	    br.close();
    	}catch(IOException e) {
    		   e.printStackTrace();
    	}
 
        // starting and ending vertices:
        start = hmap1.get(args[1]);
        end = hmap1.get(args[2]);
 
        // FOR DEBUGGING ONLY:
        //displayGraph();
 
        // Print shortest path from start to end:
        shortest();
    }
 
    public static void shortest() 
    {
        boolean[] done = new boolean[n];
        Node[] table = new Node[n];
        for (int i = 0; i < n; i++)
            table[i] = new Node(-1, Integer.MAX_VALUE); 
 
        table[start].weight = 0; 
        for (int count = 0; count < n; count++) 
        {
            int min = Integer.MAX_VALUE;
            int minNode = -1;
            for (int i = 0; i < n; i++)
                if (!done[i] && table[i].weight < min) 
                {
                    min = table[i].weight;
                    minNode = i;
                }
            //System.out.println(minNode);
            if(minNode == -1) {
            	break;
            }
            else{
            	done[minNode] = true;
            }
 
            ListIterator iter = graph[minNode].listIterator();
            int i;
            
            while (iter.hasNext()) 
            {
                Node nd = (Node) iter.next();
                int v = nd.label;
                int w = nd.weight;
 
                if (!done[v] && table[minNode].weight + w < table[v].weight) 
                {
                    table[v].weight = table[minNode].weight + w;
                    table[v].label = minNode;
                }
            }
            
        }
        
        if (table[end].weight < Integer.MAX_VALUE) 
        {
        	
        	System.out.println("Distance: "+table[end].weight+"km");
        	int next = table[end].label;
        	ArrayList<String> output = new ArrayList<String>();
        	System.out.println("Route:");
            while (next >= 0) 
            {
            	int a = next;
            	String display[] = (hmap.get(end) +" to "+hmap.get(a)).split(" ");
            	String temp = display[2] +" "+ display[1] +" "+ display[0];
            	output.add(temp);
            	end = a;
                next = table[next].label;
                
            }
            for(int i=output.size()-1; i>=0; i--) {
            	System.out.println(output.get(i));
            }
            System.out.println();
        } else
            System.out.println("Distance: Infinity\nRoute:\nnone");
    }
 
    public static void displayGraph() 
    {
        for (int i = 0; i < n; i++) 
        {
            System.out.print(i + ": ");
            ListIterator nbrs = graph[i].listIterator(0);
            while (nbrs.hasNext()) 
            {
                Node nd = (Node) nbrs.next();
                System.out.print(nd.label + "(" + nd.weight + ") ");
            }
            System.out.println();
        }
    }

}
