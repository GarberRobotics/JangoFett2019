/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.commands.driveSubsystemDefaultCommand;
/**
 * Add your docs here.
 */
public class DriveSubsystem extends Subsystem {
  public CANSparkMax motor;
  public CANSparkMax secondmotor;


  public DriveSubsystem(){
    motor = new CANSparkMax(60, MotorType.kBrushless);
    secondmotor = new CANSparkMax(61, MotorType.kBrushless);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new driveSubsystemDefaultCommand());
  }

  public void drive(double leftspeed, double rightspeed){
    SmartDashboard.putNumber("61 Encoder", secondmotor.getEncoder().getPosition());
    SmartDashboard.putNumber("61 Encoder Velocity", secondmotor.getEncoder().getVelocity());
    SmartDashboard.putNumber("61 Encoder get", secondmotor.get());
    //motor.set(leftspeed);
    secondmotor.set(leftspeed);
  }

}
