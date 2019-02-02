/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.commands.driveSubsystemDefaultCommand;
/**
 * Add your docs here.
 */
public class DriveSubsystem extends Subsystem {
  //SparkMax Motor
  // public CANSparkMax motor;
  // public CANSparkMax secondmotor;
   public CANSparkMax elevatorMotor;
  
  //Limit switch
  public static DigitalInput topElevator, bottomElevator;

  public DriveSubsystem(){
    // motor = new CANSparkMax(60, MotorType.kBrushless);
    elevatorMotor = new CANSparkMax(61, MotorType.kBrushless);
    bottomElevator = new DigitalInput(0);
    topElevator = new DigitalInput(4);
  }

  public double elevatorEncoderToFeet(double minus){
    //About 1 ft
    //65.4
    double elevatorEncoder = ((elevatorMotor.getEncoder().getPosition() - minus) / 65.4);
    //double feetL = (leftEncoder / -2497.0);
    return elevatorEncoder;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new driveSubsystemDefaultCommand());
  }

  public void drive(double leftspeed, double rightspeed){
    SmartDashboard.putNumber("61 Encoder", elevatorMotor.getEncoder().getPosition());
    SmartDashboard.putNumber("61 Encoder Velocity", elevatorMotor.getEncoder().getVelocity());
    SmartDashboard.putNumber("61 Encoder get", elevatorMotor.get());
    //motor.set(leftspeed);
    // secondmotor.set(leftspeed);
  }
  
  public void driveElevator(double speed){
    SmartDashboard.putBoolean("Bottom Elevator", bottomElevator.get());
    SmartDashboard.putBoolean("Top Elevator", topElevator.get());
        //minus 0 because we want to print the real feet.
    SmartDashboard.putNumber("Encoder to feet", elevatorEncoderToFeet(0));
    SmartDashboard.putNumber("61 Encoder", elevatorMotor.getEncoder().getPosition());
    SmartDashboard.putNumber("61 Encoder Velocity", elevatorMotor.getEncoder().getVelocity());
    SmartDashboard.putNumber("61 Encoder get", elevatorMotor.get());
    
    double holdSpeed = 0.05;

    //Look over this again
    //If bottom elevator is triggered and if speed is about 0, do nothing; 
    //If bottom elevator is triggered and speed is negitive, do nothing;
    if(!(bottomElevator.get()) && speed > -0.1 && speed < 0.1 || !(bottomElevator.get()) && speed < 0) {
      //Do nothing
      speed = 0;
    }else if(!(topElevator.get()) && speed > 0){
      //Hold speed
      speed = holdSpeed;
    }else if(speed > -0.1 && speed < 0.1){
      //hold speed
      speed = holdSpeed;
    }
    SmartDashboard.putNumber("Speed", speed);
    elevatorMotor.set(speed);
    
  }

}