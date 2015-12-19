package pro.beam.interactive;

import com.google.common.util.concurrent.ListenableFuture;
import pro.beam.api.BeamAPI;
import pro.beam.interactive.event.EventListener;
import pro.beam.interactive.net.packet.Protocol;
import pro.beam.interactive.robot.Robot;
import pro.beam.interactive.robot.RobotBuilder;

import java.net.URI;

public class EuroTruckRobot {
    //These details are used to get past lab HTTP auth.
    private static final String httpUser = "matt";
    private static final String httpPass = "welovenetworkz";

    //These should be filled in with your Beam details
    private static final String username = "";
    private static final String password = "";
    private static final int channelId   = -1;

    private static final double THRESHOLD = 0.5;

    public static void main(String[] args) throws Exception {
        final KeyboardController keyboardController = new KeyboardController();
        BeamAPI beam;

        if (httpUser != null && httpPass != null) {
            beam = new BeamAPI(URI.create("https://lab.beam.pro/api/v1/"), "matt", "welovenetworkz");
        } else {
            beam = new BeamAPI();
        }


        Robot robot = new RobotBuilder().username(username).password(password).channel(channelId).build(beam).get();

        //Listen for report events on the robot.
        robot.on(Protocol.Report.class, new EventListener<Protocol.Report>() {
            public void handle(Protocol.Report report) {
                //Iterate over all the tactile actions performed in the report
                for (Protocol.Report.TactileInfo tactile : report.getTactileList()) {

                    //Check if more than 50% of people were holding it down.
                    boolean passedThreshold = tactile.getDown().getMean() > THRESHOLD;

                    switch (tactile.getCode()) {
                        case 87:
                            keyboardController.setMovement(Direction.FORWARD, passedThreshold);
                            break;
                        case 65:
                            keyboardController.setMovement(Direction.LEFT, passedThreshold);
                            break;
                        case 83:
                            keyboardController.setMovement(Direction.BACKWARD, passedThreshold);
                            break;
                        case 68:
                            keyboardController.setMovement(Direction.RIGHT, passedThreshold);
                            break;
                    }
                }
            }
        });
    }
}
