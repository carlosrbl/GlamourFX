/**
 * @author Adrían
 * This interface contains the methods to change the cursor of the buttons
 */

package finalproject.glamourfx.controllers;

import javafx.scene.input.MouseEvent;

public interface ButtonCursor {
    /**
     * Changes the cursor to a hand cursor when hovering over a button.
     * @param event The MouseEvent triggered by hovering over a button.
     */

    void changeCursorToHand(MouseEvent event);

    /**
     * Changes the cursor back to the default cursor when not hovering over a button.
     * @param event The MouseEvent triggered by not hovering over a button.
     */
    void changeCursorToDefault(MouseEvent event);
}
