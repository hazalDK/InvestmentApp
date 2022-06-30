package Project;

import java.awt.event.*;
class WindowCloser extends WindowAdapter{
    //when button is pressed the window closes
    public void windowClosing(WindowEvent evt) {
	System.exit(0);
    }
}
