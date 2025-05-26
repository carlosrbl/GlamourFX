/**
 * @author Adrían
 * This interface contains the methods to change the cursor of the buttons
 */

package finalproject.glamourfx.controllers;

import javafx.scene.input.MouseEvent;

public interface ButtonCursor {
    public void changeCursorToHand(MouseEvent event);

    public void changeCursorToDefault(MouseEvent event);
}
