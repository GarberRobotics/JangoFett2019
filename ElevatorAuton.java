/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.Elevator;

public class ElevatorAuton extends CommandGroup {
  /**
   * Add your docs here.
   */
  public ElevatorAuton() {
    // Add Commands here:
    addSequential(new Elevator(0.50, 1));
  }
}
