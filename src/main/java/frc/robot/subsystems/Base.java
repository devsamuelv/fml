package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import frc.robot.Constants;

public class Base extends SubsystemBase {
  /** Creates a new Motors. */
  WPI_TalonFX talonOne = new WPI_TalonFX(Constants.talonOneCANID);
  WPI_TalonFX talonTwo = new WPI_TalonFX(Constants.talonTwoCANID);
  WPI_TalonFX talonThree = new WPI_TalonFX(Constants.talonThreeCANID);
  WPI_TalonFX talonFour = new WPI_TalonFX(Constants.talonFourCANID);

  // speed
  // 5.1 sec for 50 feet
  // 5.63
  // 5.86

  BufferedWriter wr;
  FileWriter write;
  File f;

  public Base() {
    try {
      f = new File("/home/lvuser/speeds.csv");

      f.createNewFile();

      write = new FileWriter(f);
      wr = new BufferedWriter(write);
    } catch (IOException err) {
      System.err.println(err.getMessage());
    }

    talonOne.setNeutralMode(NeutralMode.Brake);
    talonTwo.setNeutralMode(NeutralMode.Brake);
    talonThree.setNeutralMode(NeutralMode.Brake);
    talonFour.setNeutralMode(NeutralMode.Brake);

    talonThree.setInverted(true);
    talonFour.setInverted(true);
  }

  public void tankDriveByJoystick(double speedL, double speedR) {
    talonOne.set(speedL);
    talonTwo.set(speedL);
    talonThree.set(speedR);
    talonFour.set(speedR);
  }

  public void LogSpeed(double l, double r) {
    try {
      wr.write(java.time.LocalTime.now().toString() + "," + l + "," + r + "\n");

    } catch (IOException err) {
      System.err.println(err.getMessage());
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    double l = talonOne.getSelectedSensorVelocity() * 1 / -13.72 * Math.PI * .146 / 2048 * 10;
    double r = talonThree.getSelectedSensorVelocity() * 1 / -13.72 * Math.PI * .146 / 2048 * 10;

    LogSpeed(l, r);

    SmartDashboard.putNumber("rate l", l);
    SmartDashboard.putNumber("rate r", r);
  }
}