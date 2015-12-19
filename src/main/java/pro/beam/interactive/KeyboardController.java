package pro.beam.interactive;

import java.awt.*;
import java.awt.event.KeyEvent;

/*
    This class basically just wraps Java's Robot interface to allow for key control.
 */
public class KeyboardController {
    private Robot robot;

    public KeyboardController() throws AWTException {
        this.robot = new Robot();
    }

    public void setMovement(Direction direction, boolean activate) {
        switch (direction) {
            case LEFT:
                if (activate) {
                    robot.keyPress(KeyEvent.VK_A);
                } else {
                    robot.keyRelease(KeyEvent.VK_A);
                }

                break;
            case RIGHT:
                if (activate) {
                    robot.keyPress(KeyEvent.VK_D);
                } else {
                    robot.keyRelease(KeyEvent.VK_D);
                }

                break;
            case FORWARD:
                if (activate) {
                    robot.keyPress(KeyEvent.VK_W);
                } else {
                    robot.keyRelease(KeyEvent.VK_W);
                }

                break;
            case BACKWARD:
                if (activate) {
                    robot.keyPress(KeyEvent.VK_S);
                } else {
                    robot.keyRelease(KeyEvent.VK_S);
                }

                break;
        }

    }
}
