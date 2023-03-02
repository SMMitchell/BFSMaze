// Programmer: Stephen Mitchell
// Student ID: c3349182
// Last modified: 25 Sept 2022
// Course: COMP2230
// Assignment 1

// This file implements the Node class

import java.util.*;
import java.io.*;

public class Node
{
	// Integer variables

	private int nodeNumber;			// Track the ID of the Node
	private int rowNumber;			// For finding coordinates of Node
	private int columnNumber;		// For finding coordinates of Node
	private int openness;			// Stores the openness of the Node
	
	// Node variables

	private Node above;				// Stores the Neighbour above
	private Node below;				// Stores the Neighbour below
	private Node left;				// Stores the Neighbour to the left
	private Node right;				// Stores the Neighbour to the right
	private Node previous;			// Stores the previously visited node
	private Node next;				// Stores the next node

	// Booleans

	private boolean startPoint;		// Stores whether or not the node is the Start point to the Maze
	private boolean finishPoint;	// Stores whether or not the node is the Finish point to the Maze
	private boolean visited; 		// Stores whether or not Node has been visited in Random Walk and BFS
	private boolean solution;		// Stores whether or not the Node is part of the Solution

	// Empty Constructor

	public Node()
	{
		nodeNumber = 0;
		rowNumber = 0;
		columnNumber = 0;
		openness = 0;
		startPoint = false;
		finishPoint = false;
		visited = false;
		above = null;
		below = null;
		left = null;
		right = null;
	}

	public Node(int n, int r, int c, int o)
	{
		nodeNumber = n;
		rowNumber = r;
		columnNumber = c;
		openness = o;
		startPoint = false;
		finishPoint = false;
		visited = false;
		above = null;
		below = null;
		left = null;
		right = null;
		solution = false;
	}

	// Getters and Setters 

	public void setNodeNumber(int nodeNumber)
	{
		this.nodeNumber = nodeNumber;
	}
	public int getNodeNumber()
	{
		return nodeNumber;
	}
	
	public void setRowNumber(int rowNumber)
	{
		this.rowNumber = rowNumber;
	}
	public int getRowNumber()
	{
		return rowNumber;
	}
	
	public void setColumnNumber(int columnNumber)
	{
		this.columnNumber = columnNumber;
	}
	public int getColumnNumber()
	{
		return columnNumber;
	}
	
	public void setOpenness(int openness)
	{
		this.openness = openness;
	}
	public int getOpenness()
	{
		return openness;
	}
	
	public void setStartPoint()
	{
		this.startPoint = true;
	}
	
	public boolean getStartPoint()
	{
		return startPoint;
	}
	
	public void setFinishPoint()
	{
		this.finishPoint = true;
	}
	public boolean getFinishPoint()
	{
		return finishPoint;
	}
	
	public void setAbove(Node above)
	{
		this.above = above;
	}
	public Node getAbove()
	{
		return above;
	}
	
	public void setBelow(Node below)
	{
		this.below = below;
	}
	public Node getBelow()
	{
		return below;
	}
	
	public void setLeft(Node left)
	{
		this.left = left;
	}
	public Node getLeft()
	{
		return left;
	}
	
	public void setRight(Node right)
	{
		this.right = right;
	}
	public Node getRight()
	{
		return right;
	}
	
	public void setVisited()
	{
		this.visited = true;
	}
	public boolean getVisited()
	{
		return visited;
	}
	
	public void setPrevious(Node previous)
	{
		this.previous = previous;
	}
	public Node getPrevious()
	{
		return previous;
	}
	
	public void setSolution()
	{
		this.solution = true;
	}
	public boolean getSolution()
	{
		return solution;
	}
}