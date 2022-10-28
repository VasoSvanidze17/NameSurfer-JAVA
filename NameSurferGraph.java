/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		//	 You fill in the rest //
		arrayOfEntry = new ArrayList<NameSurferEntry>();
		arrayOfColor = new ArrayList<Color>();
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		//	 You fill this in //
			arrayOfEntry.clear();
	}
	
	public void deleteGraph(String name) {
		for(NameSurferEntry entry : arrayOfEntry) {
			if(entry.getName().equals(name)) {
				arrayOfColor.remove(arrayOfEntry.indexOf(entry));
				arrayOfEntry.remove(entry);
				break;
			}
		}
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		// You fill this in //
		//add entry object in array list
		Color[] color = new Color[]{Color.BLACK, Color.RED, Color.BLUE, Color.MAGENTA};
		boolean cond = true;
		
		for(NameSurferEntry ent : arrayOfEntry) {
			if(ent.getName().equals(entry.getName())) {
				cond = false;
				break;
			}
		}
		
		if(cond) {
			//each index of arrayOfEntry is also index of arrayOfColor, which is for relevant colour 
			arrayOfEntry.add(entry);
			arrayOfColor.add(color[(arrayOfEntry.size() - 1) % color.length]); //for colour
		}
	}
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		//	 You fill this in //
		removeAll();
		drawBackground();
		drawGraphs();
	}
	
	public void deleteLastGraph() {
		arrayOfColor.remove(arrayOfEntry.size() - 1);
		arrayOfEntry.remove(arrayOfEntry.size() - 1);
	}
	
	private void drawBackground() {
		GLine line; //for vertical line
		GLabel label; //for drawing years
		
		for(int i = 0; i < NDECADES; i++) { 
			//draw background vertical line
			line = new GLine(((double)i/NDECADES) * getWidth(), 0, ((double)i/NDECADES) * getWidth(), getHeight());
			add(line);
			
			//for years
			if(i < NDECADES) { 
				label = new GLabel(Integer.toString(START_DECADE + i * 10));
				add(label, ((double)i/NDECADES) * getWidth(), getHeight());
			}
		}
		
		//for two top and bottom horizontal line
		line = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		add(line);
		
		line = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(line);
	}
	
	private void drawGraphs() {
		GLine line = null; //Connecting line between points
		GLabel label = null; //for draw name on canvas
		
		double diagramHeight = getHeight() - 2 * GRAPH_MARGIN_SIZE;
		
		double x1;
		double x2;
		double y1;
		double y2;
		
		//draw graphs. arrayOfEntry.size() determines how many graphs must draw so NDECADES determines how many Connecting line between points must draw
		for(int i = 0; i < arrayOfEntry.size(); i++) {
			for(int j = 1; j < NDECADES; j++) {
				// X coordinate of points
				x1 = ((double)(j - 1) / NDECADES) * getWidth();
				x2 = ((double)(j) / NDECADES) * getWidth();
				
				//for lastY
				if(arrayOfEntry.get(i).getRank(j) != 0) {
					y1 = GRAPH_MARGIN_SIZE + (diagramHeight / (double) MAX_RANK) * arrayOfEntry.get(i).getRank(j);
					
					//draw Name on canvas
					label = new GLabel(arrayOfEntry.get(i).getName() + " " + arrayOfEntry.get(i).getRank(j));
					label.setColor(arrayOfColor.get(i));
					
				}else {
					y1 = GRAPH_MARGIN_SIZE + diagramHeight;
					
					//draw Name on canvas
					label = new GLabel(arrayOfEntry.get(i).getName() + "*");
					label.setColor(arrayOfColor.get(i));
				}
				
				//for newY
				if(arrayOfEntry.get(i).getRank(j + 1) != 0) {
					y2 = GRAPH_MARGIN_SIZE + (diagramHeight / (double) MAX_RANK) * arrayOfEntry.get(i).getRank(j + 1);
					
					//for label font
					if(getHeight() < getWidth()) {
						label.setFont("SenSerif-Bold-" + getHeight()/40);
					}else {
						label.setFont("SenSerif-Bold-" + getWidth()/65);
					}
					
					
					if(isLabelDown(y1, y2) && y1 == 0) {
						add(label, x1, y1 + label.getHeight());
					}else {
						add(label, x1, y1);
					}
//					
				}else {
					y2 = GRAPH_MARGIN_SIZE + diagramHeight;
					
					if(isLabelDown(y1, y2) && y1 == 0) {
						add(label, x1, y1 + label.getHeight());
					}else {
						add(label, x1, y1);
					}
					
				}
				
				//lastX and lastY are coordinates of first point and newX and newY are coordinates of second point
				line = new GLine(x1, y1, x2, y2); //Connecting line between two points
				line.setColor(arrayOfColor.get(i));
				add(line);
			}
		}
	}
	
	//for more Optimization of drawing Lable
	private boolean isLabelDown(double y1, double y2) {
		if(y2 >= y1) {
			return false;
		}else {
			return true;
		}
	}
	
	private ArrayList<NameSurferEntry> arrayOfEntry;
	private ArrayList<Color> arrayOfColor;
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
