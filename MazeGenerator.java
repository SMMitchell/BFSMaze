// Programmer: Stephen Mitchell
// Student ID: c3349182
// Last modified: 25 Sept 2022
// Course: COMP2230
// Assignment 1

// This program prompts a user for dimensions of  
// a maze, and uses the input to randomly generate a maze.

import java.util.*;
import java.io.*;

class MazeGenerator
{
	public static void main(String[] args) 	
	{
							
		int height = Integer.valueOf(args[0]);
		int width = Integer.valueOf(args[1]);
		
		// Create a 2d Array to Make a grid outline of the specified dimensions

		Node[][] grid = new Node[height][width];
		
		int nodeCount = 0;		

		// Add nodes to each cell of the maze

		for(int i = 0; i < height; i++)		// Row by Row 
		{
			for(int j = 0; j < width; j++)	// Column by Column
			{
				nodeCount++;									// Increment Node count
				grid[i][j] = new Node(nodeCount, i, j, 0);		// Add new Node with location coordinates				
			}
		}
		
		
		// Locate and set Neighbouring Nodes

		for(int i = 0; i < height; i++)		// Row by Row
		{
			for(int j = 0; j < width; j++)	// Column by Column
			{	

				// For nodes not in the first row

				if(i != 0)
				{
					grid[i][j].setAbove(grid[i-1][j]);
				}

				// For nodes not in the first column 
				
				if (j != 0)
				{
					grid[i][j].setLeft(grid[i][j-1]);
				}
				
				// For nodes not in the last row

				if (i != height-1)
				{
					grid[i][j].setBelow(grid[i+1][j]);
				}

				// For nodes not in the first column 
				
				if (j != width-1)
				{
					grid[i][j].setRight(grid[i][j+1]);
				}				
			}
		}


		// Set random start point 

		Random rand = new Random();		// Create random variable for Random Walk
		
		int start = rand.nextInt(height*width);		// Make sure it is within the dimensions of the Maze

		// If random generates the Number 0, it will not link to a Node number
		// because Node numbers start at 1

		if (start == 0)				
		{
			while(start == 0)		// Loop until not 0
			{
				start = rand.nextInt(height*width);		// Attempt new random number
			}
		}

		// Directions

		int moveUp = 0;
		int moveDown = 1;
		int moveLeft = 2;
		int moveRight = 3;

		// Create a list of possible directions for traversal

		ArrayList<Integer> possiblePaths = new ArrayList<Integer>();

		// Create a temp node to represent the next move in the maze

		Node next = new Node();

		int unvisited = (height*width);		// Create a counter to track the amount of unvisted Nodes
		int path = 0;						// Create a varible to represent the chosen path direction

		
		// Start Random Walk


		int bothClosed = 0;
		int rightOnlyOpen = 1;
		int downOnlyOpen = 2;
		int bothOpen = 3;

		// Set the randomised start point, set to visited and move to next node

		for(int i = 0; i < height; i++)			// Row by Row
		{
			for(int j = 0; j < width; j++)		// Column by Column
			{
				// If the node is the start node

				if (grid[i][j].getNodeNumber() == start)	
				{
					grid[i][j].setStartPoint();		// Set as start point
					grid[i][j].setVisited();		// Mark as visited
					
					unvisited--;					// Decrement list of unvisited Nodes

					// Check for possible paths

					if (grid[i][j].getAbove() != null)			// If not in top Row
					{
						if (grid[i][j].getAbove().getVisited() == false)	// And above cell has not been visited
						{
							possiblePaths.add(moveUp);			// Add 'Up' to list of possible directions
						}				
					}
					
					// Check Below

					if (grid[i][j].getBelow() != null)			// If not in bottom Row
					{
						if (grid[i][j].getBelow().getVisited() == false)	// And cell below has not been visited
						{
							possiblePaths.add(moveDown);		// Add 'Down' to list of possible directions
						}
					}

					// Check Left

					if (grid[i][j].getLeft() != null)			// If not in first Column
					{
						if(grid[i][j].getLeft().getVisited() == false)		// And cell to the left has not been visited
						{
							possiblePaths.add(moveLeft);		// Add 'Left' to list of possible directions
						}
					}

					// Check Right

					if (grid[i][j].getRight() != null)			// If not in the Last Column 
					{
						if (grid[i][j].getRight().getVisited() == false)	// And cell to the right has not been visited
						{
							possiblePaths.add(moveRight);		// Add 'Right' to list of possible directions
						}
					}

					// Choose random path from available options

					for (int q = 0; q < possiblePaths.size(); q++)
					{
						path = rand.nextInt(possiblePaths.size());
					}

					// Use chosen path option to set the openness of start node

					if (possiblePaths.get(path) == moveUp)
					{
						grid[i - 1][j].setOpenness(downOnlyOpen);		// Knock down wall above
						grid[i - 1][j].setPrevious(grid[i][j]);			// Set start node as previous node
						next = grid[i - 1][j];							// Set next node to above node
					}

					else if (possiblePaths.get(path) == moveDown)		
					{
						grid[i][j].setOpenness(downOnlyOpen);			// Knock down wall below
						grid[i+1][j].setPrevious(grid[i][j]);			// Set start node as previous node
						next = grid[i+1][j];							// Set next node to node below
					}
					
					else if (possiblePaths.get(path) == moveLeft)
					{
						grid[i][j-1].setOpenness(rightOnlyOpen);		// Knock down wall to the left
						grid[i][j-1].setPrevious(grid[i][j]);			// Set start node as previous node
						next = grid[i][j-1];							// Set next node to left node
					}
					else if (possiblePaths.get(path) == moveRight)		
					{
						grid[i][j].setOpenness(rightOnlyOpen);			// Knock down wall to the left
						grid[i][j+1].setPrevious(grid[i][j]);			// Set start node as previous node
						next = grid[i][j+1];							// Set next node to right node
					}
					
					possiblePaths.clear();		// Clear list to use for next Node
				}
			}
		}
		
		// Loop until each cell has been visited

		while (unvisited > 0)
		{
			
			// If the cell has not been visited set to visited

			if (grid[next.getRowNumber()][next.getColumnNumber()].getVisited() == false) 
			{	
				grid[next.getRowNumber()][next.getColumnNumber()].setVisited();
				unvisited--;
			}
			
			// Check for possible paths

			// Check Above, if not in the top Row and has not already been visited
			// add to possible paths

			if (grid[next.getRowNumber()][next.getColumnNumber()].getAbove() != null)
			{
				if (grid[next.getRowNumber()][next.getColumnNumber()].getAbove().getVisited() == false)
				{
					possiblePaths.add(moveUp);
				}				
			}
			
			// Check Below, if not in the bottom Row and has not already been visited
			// add to possible paths

			if (grid[next.getRowNumber()][next.getColumnNumber()].getBelow() != null)
			{
				if (grid[next.getRowNumber()][next.getColumnNumber()].getBelow().getVisited() == false)
				{
					possiblePaths.add(moveDown);
				}
			}

			// Check Left, if not in the first Column and has not already been visited
			// add to possible paths

			if (grid[next.getRowNumber()][next.getColumnNumber()].getLeft() != null)
			{
				if(grid[next.getRowNumber()][next.getColumnNumber()].getLeft().getVisited() == false)
				{
					possiblePaths.add(moveLeft);
				}
			}

			// Check Right, if not in the last Column and has not already been visited
			// add to possible paths

			if (grid[next.getRowNumber()][next.getColumnNumber()].getRight() != null)
			{
				if (grid[next.getRowNumber()][next.getColumnNumber()].getRight().getVisited() == false)
				{
					possiblePaths.add(moveRight);
				}
			}


			// Set Openness of the rest of the Nodes, as done previously with the start node

			if (possiblePaths.size() > 0)
			{
				
				for (int q = 0; q < possiblePaths.size(); q++)
				{
					path = rand.nextInt(possiblePaths.size());
				}
				

				if (possiblePaths.get(path) == moveUp)
				{
					grid[next.getRowNumber() - 1][next.getColumnNumber()].setOpenness(downOnlyOpen);	
					
					grid[next.getRowNumber() - 1][next.getColumnNumber()].setPrevious(grid[next.getRowNumber()][next.getColumnNumber()]);
					
					next = grid[next.getRowNumber() - 1][next.getColumnNumber()];
				}

				else if (possiblePaths.get(path) == moveDown)
				{
					if (grid[next.getRowNumber()][next.getColumnNumber()].getOpenness() == rightOnlyOpen)
					{
						grid[next.getRowNumber()][next.getColumnNumber()].setOpenness(bothOpen);
					}
					
					else
					{
						grid[next.getRowNumber()][next.getColumnNumber()].setOpenness(downOnlyOpen);
					}
					grid[next.getRowNumber() + 1][next.getColumnNumber()].setPrevious(grid[next.getRowNumber()][next.getColumnNumber()]);
		
					next = grid[next.getRowNumber() + 1][next.getColumnNumber()];

				}
				
				else if (possiblePaths.get(path) == moveLeft)
				{
					grid[next.getRowNumber()][next.getColumnNumber() - 1].setOpenness(rightOnlyOpen);
					
					grid[next.getRowNumber()][next.getColumnNumber() - 1].setPrevious(grid[next.getRowNumber()][next.getColumnNumber()]);
					
					next = grid[next.getRowNumber()][next.getColumnNumber() - 1];
				}
				
				else if (possiblePaths.get(path) == moveRight)
				{
					if (grid[next.getRowNumber()][next.getColumnNumber()].getOpenness() == downOnlyOpen)
					{
						grid[next.getRowNumber()][next.getColumnNumber()].setOpenness(bothOpen);
					}
					
					else
					{
						grid[next.getRowNumber()][next.getColumnNumber()].setOpenness(rightOnlyOpen);
					}
					
					grid[next.getRowNumber()][next.getColumnNumber() + 1].setPrevious(grid[next.getRowNumber()][next.getColumnNumber()]);
					
					next = grid[next.getRowNumber()][next.getColumnNumber() + 1];
				}
			}	

			// If no possible paths, find new random, unvisited coordinate

			else if (possiblePaths.size() == 0 && unvisited == 0)
			{
				grid[next.getRowNumber()][next.getColumnNumber()].setFinishPoint();
				break;
			}

			else if (possiblePaths.size() == 0)
			{	

				next = grid[next.getRowNumber()][next.getColumnNumber()].getPrevious();

				
			}
			
			possiblePaths.clear();

		}

		// Create and populate cell_openness_list

		ArrayList<Integer> cell_openness_list = new ArrayList<Integer>();

		for(int i = 0; i < height; i++)		// Row by Row
		{
			for(int j = 0; j < width; j++)		// Column by Column
			{
				cell_openness_list.add(grid[i][j].getOpenness());		// Populate list
			}
		}

		// Label variables as specified in assignment spec

		int n = height;					// Change height to n
		int m = width;					// Change width to m
		int start_node = start;			// Change start to start_node
		int finish_node = 0;			// Change finish to finish_node

		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				if (grid[i][j].getFinishPoint() == true)
				{
					finish_node = grid[i][j].getNodeNumber();	
				}				
			}
		}

		// Write newly generated maze data to a new file

		String fileName = args[2];			// Get file name provided from the command line
        
        PrintWriter outputStream;			// Initialise variable to write output file

    	int openNumber = 0;					// Create and initialise variable track cell number for cell openness list

        try
        {
       		outputStream = new PrintWriter(fileName);
           
           	// Write Rows, Columns, Start Point and Finish Point to the file in the rrequired format

           	outputStream.print(n + "," + m + ":" + start_node + ":" + finish_node + ":");		
            
            // Write cell openness list in required format

            for(int i = 0; i < height; i++)		// Row by Row
			{
				for(int j = 0; j < width; j++)		// Column by Column
				{
					outputStream.print(cell_openness_list.get(openNumber));		// Write openness of the cell
					openNumber++;												// Increment cell nember
				}
			}

        }
        catch(FileNotFoundException e)
        {    
            System.out.println("Error opening the file " + fileName);
            return;
        }
        outputStream.close();



		//IF DIMENSIONS LESS THAN OR EQUAL TO 10!!! 
		
		// Print Maze
        if (height <= 10 || width <= 10)
		{
			// Print the top boundary of the Maze 

			for(int i = 0; i < width*3 + 1; i++)
			{
				System.out.print("-");	
			}
			
			// Drop to First Row

			System.out.print("\n");
			
			for(int i = 0; i < height; i++) // Row by Row 
			{
				System.out.print("|");		// Print Left Boundary of the Maze
				
				for(int j = 0; j < width; j++)		// Column By Column
				{
		
					// If cell needs a wall to the right

					if (grid[i][j].getOpenness() == bothClosed || grid[i][j].getOpenness() == downOnlyOpen) 
					{	
						if (grid[i][j].getStartPoint() == true)		// If cell is the start Point for the Maze
						{
							System.out.print("S |");				// Add an S to the cell
						}
						
						else if (grid[i][j].getFinishPoint() == true)	// If cell is the finish Point for the Maze
						{
							System.out.print("F |");					//Add an F to the cell
						}				
						
						else
						{
							System.out.print("  |");	// Close right side of cell
						}
					}

					// If cell has no wall to the right

					if (grid[i][j].getOpenness() == bothOpen || grid[i][j].getOpenness() == rightOnlyOpen) 
					{	
						if (grid[i][j].getStartPoint() == true)		// If cell is the start Point for the Maze
						{
							System.out.print("S  ");				// Add an S to the cell
						}
						
						else if (grid[i][j].getFinishPoint() == true)	// If cell is the finish Point for the Maze
						{
							System.out.print("F  ");					// Add an F to the cell
						}
						
						else 
						{
							System.out.print("   ");	// Open right side of cell
						}
					}		

				}
				
				System.out.print("\n");		// Move to next line
				
				// Print bottom half of the cell
				
				if (i == height - 1)		// For the last Row of the Maze
				{
					for(int g = 0; g < width*3 + 1; g++)
					{
						System.out.print("-");		// Print boundary for the bottome of the Maze
					}
				}
				
				else
				{
					System.out.print("|");
					
					for(int k = 0; k < width; k++)
					{				
						// Check if bottom of the cell is open

						if (grid[i][k].getOpenness() == bothClosed || grid[i][k].getOpenness() == rightOnlyOpen) 
						{
							System.out.print("--|");	// Close bottom of cell
						}
						
						else System.out.print("  |");	// Open Bottom of cell
					}
				    
				    System.out.print("\n");		// Move to next line
				}
			}
		}
		System.out.println("\n\nSuccess! New Maze has been generated and saved to file: " + fileName);
	}


}

