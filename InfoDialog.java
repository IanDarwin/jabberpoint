// This example is from the book _Java in a Nutshell_ by David Flanagan.
// Written by David Flanagan.  Copyright (c) 1996 O'Reilly & Associates.
// You may study, use, modify, and distribute this example for any purpose.
// This example is provided WITHOUT WARRANTY either expressed or implied.

// Warmed over by Ian Darwin for use in JabaDex
// In Java 2, replace this whole mess with JOptionPane.showMessageDialog(...);

import java.awt.*;
import java.awt.event.*;

public class InfoDialog extends Dialog implements ActionListener {
    protected Button button;
    protected MultiLineLabel label;

	/** Construct the InfoDialog */
    public InfoDialog(Frame parent, String title, String message)
    {
        // Create a dialog with the specified title
        super(parent, title, true);
        
        // Create and use a BorderLayout manager with specified margins
        this.setLayout(new BorderLayout(15, 15));
        
        // Create the message component and add it to the window
        label = new MultiLineLabel(message, 20, 20);
        this.add("Center", label);
        
        // Create an Okay button in a Panel; add the Panel to the window
        // Use a FlowLayout to center the button and give it margins.
        button = new Button("OK");
        Panel p = new Panel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        p.add(button);
        this.add("South", p);

        // Resize the window to the preferred size of its components
        this.pack();
    }
    
	/** Pop the window up when something important happens in the caller 
	 */
	public void actionPerformed(ActionEvent e) {
		setVisible(true);
	}
    // Pop down the window when the button is clicked.
    public boolean action(Event e, Object arg)
    {
        if (e.target == button) {
            this.setVisible(false);
            this.dispose();
            return true;
        }
        else return false;
    }

    // When the window gets the keyboard focus, give it to the button.
    // This allows keyboard shortcuts to pop down the dialog.
    public boolean gotFocus(Event e, Object arg) {
        button.requestFocus();
        return true;
    }
    
    public static void main(String[] args) {
        Frame f = new Frame("InfoDialog Test");
        f.setSize(100, 100);
        f.setVisible(true);
        InfoDialog d = new InfoDialog(f, "Help", 
                          "The host you are trying to contact\n" +
                          "is not currently responding.\n" +
                          "Please try again later.");
        d.setVisible(true);
    }
}
