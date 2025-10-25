package frc.robot;
import com.pathplanner.lib.events.EventTrigger;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.swerve.SwerveTeleopCMD;
import frc.robot.subsystems.swerve.SwerveDriveTrain;
import frc.robot.subsystems.targeting.Vision;

public class RobotContainer {

  // ---------------------- START OF CONFIG SECTION --------------------------

  // Defines starting pose of robot
  // TODO - Please remove this in future if developing for AprilTags
  Pose2d startpose = new Pose2d(new Translation2d(8.7, 4.0), new Rotation2d());
  // add start pose if needed
  // ---------------------- END OF CONFIG SECTION --------------------------

  // Xbox + an additional one for PC use
  private final CommandXboxController drivingXbox = new CommandXboxController(0);
  private final CommandJoystick mechJoystick = new CommandJoystick(1);

  private SwerveDriveTrain swerveDriveTrain;
  private SwerveTeleopCMD swerveTeleopCMD;

  private Vision vision;

  public RobotContainer() {

    // Starts recording to data log
    DataLogManager.start();

    // Record both DS control and joystick data
    DriverStation.startDataLog(DataLogManager.getLog());



    //Call this last since this creates the parallel command groups
    //and requires elevator and coral manipulator
    BALLASDHAKHSDHASDKJAS();
  }

  private void createSwerve() {
    //Swerve needs the vision make sure to create this first
    //Create swerveDriveTrain
    swerveDriveTrain = new SwerveDriveTrain(startpose,
    Constants.SwerveModuleIOConfig.moduleFL,
    Constants.SwerveModuleIOConfig.moduleFR,
    Constants.SwerveModuleIOConfig.moduleBL,
    Constants.SwerveModuleIOConfig.moduleBR,
     () -> {return drivingXbox.getLeftTriggerAxis();});
    
    //Create swerve commands here
    swerveTeleopCMD = new SwerveTeleopCMD(this.swerveDriveTrain, this.drivingXbox);


    //Set default swerve command to the basic drive command, not field orientated
    this.swerveDriveTrain.setDefaultCommand(swerveTeleopCMD);

    //This requires the swerve subsystem make sure to create that first before creating this
    //7drivingXbox.x().onTrue(this.swerveDriveTrain.toggleFieldCentric());
    drivingXbox.y().onTrue(this.swerveDriveTrain.resetHeadingCommand());

    drivingXbox.leftTrigger(0.02).whileTrue(swerveDriveTrain.driveForward());

    // longAlignment = new LongitudinalAlignment(swerveDriveTrain, vision);
  }



  private void BALLASDHAKHSDHASDKJAS() {

  }


  public Command getAutonomousCommand() {
    return swerveDriveTrain.getAutonomousCommand();
  }

  public void initCommandInTeleop() {
    //swerveDriveTrain.setDefaultCommand(swerveTeleopCMD);
  }
}