/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Elevator extends Command {
  public double m_speed = 0;
  public double m_feet;
  public double m_currentEncoderPosToMinus = 0;

  public Elevator(double speed, double feet) {
      requires(Robot.driveSubsystem);
      m_feet = feet;
      m_speed = speed;
      m_currentEncoderPosToMinus = Robot.driveSubsystem.elevatorMotor.getEncoder().getPosition();
      
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
      //0.50 works!
      Robot.driveSubsystem.driveElevator(0.50);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
      if(Robot.driveSubsystem.elevatorEncoderToFeet(m_currentEncoderPosToMinus) >= m_feet){
        return true;
      }
      return false;
		}
    

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
