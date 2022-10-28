
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.graphics.GLine;
import acm.program.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import com.sun.xml.internal.bind.v2.runtime.NameList;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public NameSurfer() {
		graph = new NameSurferGraph();
		add(graph);
	}

	public void init() {
		// You fill this in, along with any helper methods //
		JLabel name = new JLabel("Name");
		add(name, SOUTH);

		// add text field
		inputName = new JTextField(20);
		add(inputName, SOUTH);
		inputName.addActionListener(this);

		// add graph button
		graphButton = new JButton("Graph");
		add(graphButton, SOUTH);

		// add clear button
		clear = new JButton("Clear");
		add(clear, SOUTH);
		
		JLabel deletename = new JLabel("Name");
		add(deletename, NORTH);
		
		//for delete text field
		inputDeleteName = new JTextField(15);
		add(inputDeleteName, NORTH);
		inputDeleteName.addActionListener(this);
		
		//for delete button
		deleteButton = new JButton("Delete Graph");
		add(deleteButton, NORTH);
		
		//for JComboBox
		JLabel chooseLabel = new JLabel("Choose Name");
		add(chooseLabel, SOUTH);
		
		//for JComboBox
		chooseName = new JComboBox();
		chooseName.setEditable(true);
		add(chooseName, SOUTH);
		
		//for JComboBox ok
		ok = new JButton("OK");
		add(ok, SOUTH);

		addActionListeners();

		data = new NameSurferDataBase(NAMES_DATA_FILE);
		
		//this list is Required for JComboBox
		ArrayList<String> nameList = new ArrayList<String>();
		nameList = data.getNameList();
		
		for(int i = 0; i < nameList.size(); i++) {
				chooseName.addItem(nameList.get(i));
		}
		
		deleteLast = new JButton("Delete Last Graph");
		add(deleteLast, NORTH);

	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		// You fill this in //
		if (e.getActionCommand().equals("Graph")) { // if user click Graph button
			NameSurferEntry entry = data.findEntry(inputName.getText());

			if (entry != null) {
				graph.addEntry(entry);
				graph.update();
			} else {

			}

			inputName.setText("");

		} else if (e.getActionCommand().equals("Clear")) { // if user click clear button
			graph.clear();
			graph.update();

		} else if (e.getSource() == inputName) { // if user press enter
			NameSurferEntry entry = data.findEntry(inputName.getText());

			if (entry != null) {
				graph.addEntry(entry);
				graph.update();
			} else {

			}

			inputName.setText("");

		} else if (e.getSource() == inputDeleteName) { // if user press enter
			graph.deleteGraph(inputDeleteName.getText());
			graph.update();
			inputDeleteName.setText("");

		} else if (e.getActionCommand().equals("Delete Graph")) {// if user click delete graph button
			graph.deleteGraph(inputDeleteName.getText());
			graph.update();
			inputDeleteName.setText("");

		}else if(e.getActionCommand().equals("OK")) { //for combo box
			NameSurferEntry entry = data.findEntry((String)chooseName.getSelectedItem());
			
			if (entry != null) {
				graph.addEntry(entry);
				graph.update();
			} else {
				
			}
			
		}else if(e.getActionCommand().equals("Delete Last Graph")){ //for delete last graph. bbuugggggggggggg. dont work. idk why :)
			graph.deleteLastGraph(); //this method works good but program don't enter this else if
			graph.update();
			inputDeleteName.setText("sadasd");
			
		}
	}
	
	//instance variable
	private JTextField inputName;
	private JButton graphButton;
	private JButton clear;
	private JButton ok;
	private JButton deleteLast;
	private JTextField inputDeleteName;
	private JButton deleteButton;
	private JComboBox chooseName; 
	private NameSurferGraph graph;
	private NameSurferDataBase data;
}
