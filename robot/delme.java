/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5216.robot;

import edu.wpi.cscore.AxisCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
//import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5216.robot.commands.Drive;
import org.usfirst.frc.team5216.robot.subsystems.EncoderToFeet;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Robot extends IterativeRobot {
	public CANSparkMax motor;
	

	//Initialize the subsystems
	public static EncoderToFeet encoderToFeet = new EncoderToFeet();
	
	//Initiates compressor
	public static Compressor c;
	
	//Initiates solenoids
	public static DoubleSolenoid S1, S2;
	
	
	Command autoChooser;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	
	
	//public static OI m_oi;

//	Command m_autonomousCommand;
//	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		motor = new CANSparkMax(60, MotorType.kBrushless);
		// S1 = new DoubleSolenoid(0, 1);
		// S2 = new DoubleSolenoid(4, 5);
		//Starts the compressor
		//c = new Compressor(0);
		//c.start();
		
		armUpL = new DigitalInput(5);
		armDownL = new DigitalInput(4);
		armUpR = new DigitalInput(6);
		armDownR = new DigitalInput(7);
		stick = new Xbox(0);
		/*frontLeft = new WPI_TalonSRX(55);
		frontRight = new WPI_TalonSRX(54);
		backLeft = new WPI_TalonSRX(56);
		backRight = new WPI_TalonSRX(53);
		//ArmMotor = new WPI_TalonSRX(50);
		right = new SpeedControllerGroup(frontRight, backRight);
		left = new SpeedControllerGroup(frontLeft, backLeft);
		drive = new DifferentialDrive(left, right);*/
		gyro = new ADXRS450_Gyro();
		
		//Reset sensor
		/*frontRight.setSelectedSensorPosition(0, 0, 0);
		frontLeft.setSelectedSensorPosition(0, 0, 0);*/
		//chooser = new SendableChooser();
		//chooser.addDefault("CenterAuton", new CenterAuton());
		//SmartDashboard.putData("Auton Select", chooser);
		
		
		//m_oi = new OI();
		
		/*chooser = new SendableChooser();
		
		chooser.addDefault("Drive", new Drive());
		chooser.addObject("weavingBaskets", new weavingBaskets());*/
		
		//chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Autos", chooser);
		SmartDashboard.putData(Scheduler.getInstance());
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		//Reset sensor
		/*frontRight.setSelectedSensorPosition(0, 0, 0);
		frontLeft.setSelectedSensorPosition(0, 0, 0);*/
		gyro.reset();
		// c.stop();
		autoChooser = (Command) chooser.getSelected();
		if (autoChooser != null) {
			autoChooser.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
		
		//c.start();
		//c.setClosedLoopControl(true); //Allows compressor to turn off with pressure switch
		//Reset sensor
		/*frontRight.setSelectedSensorPosition(0, 0, 0);
		frontLeft.setSelectedSensorPosition(0, 0, 0);*/
		gyro.reset();
		
		if (autoChooser != null) {
			autoChooser.cancel();
		}

	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		motor.set(stick.leftStick.getY());

		SmartDashboard.putNumber("Gyro", gyro.getAngle());
		/*SmartDashboard.putNumber("Front Left", frontLeft.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Front Right", frontRight.getSelectedSensorPosition(0));*/
		
		/*if(DriverStation.getInstance().getJoystickIsXbox(0)) {
			drive.arcadeDrive(m_oi.stick.leftStick.getY(), m_oi.stick.leftStick.getX());
		}else if(DriverStation.getInstance().getJoystickIsXbox(1)) {
			// Bad Drive
			//drive.tankDrive(m_oi.stick2.leftStick.getY(), m_oi.stick2.rightStick.getY());
		}*/
		
		// if(stick.rt.get()){ //close arms
		// 	S1.set(DoubleSolenoid.Value.kReverse);
		// 	//S2.set(DoubleSolenoid.Value.kReverse);
		// }
		// else if(stick.rb.get()){ //open arms
		// 	S1.set(DoubleSolenoid.Value.kForward);
		// 	//S2.set(DoubleSolenoid.Value.kForward);
		// }
		
		// if(stick.lt.get()){ //close arms
		// 	//S1.set(DoubleSolenoid.Value.kReverse);
		// 	S2.set(DoubleSolenoid.Value.kReverse);
		// }
		// else if(stick.lb.get()){ //open arms
		// 	//S1.set(DoubleSolenoid.Value.kForward);
		// 	S2.set(DoubleSolenoid.Value.kForward);
		// }

		// if(stick.a.get()){
		// 	S1.set(DoubleSolenoid.Value.kForward);
		// 	try {
		// 		Thread.sleep(200);
		// 	} catch (InterruptedException e1) {
		// 		// TODO Auto-generated catch block
		// 		e1.printStackTrace();
		// 	}
		// 	S2.set(DoubleSolenoid.Value.kForward);
		// 	try {
		// 		Thread.sleep(50);
		// 	} catch (InterruptedException e) {
		// 		// TODO Auto-generated catch block
		// 		e.printStackTrace();
		// 	}
		// 	S1.set(DoubleSolenoid.Value.kReverse);
		// 	try {
		// 		Thread.sleep(500);
		// 	} catch (InterruptedException e) {
		// 		// TODO Auto-generated catch block
		// 		e.printStackTrace();
		// 	}
		// 	S2.set(DoubleSolenoid.Value.kReverse);
		// }
		
		// if(stick.start.get() || stick.a.get()){ //Turns on the compressor
		// 	c.start();
		// }else if(stick.back.get() || stick.b.get()){ //Turns off the compressor
		// 	c.stop();
		// }
		
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
