package frc.robot.commands;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class Shoot extends InstantCommand {
    ShooterSubsystem shooterSubsystem;
    private double power;
    public Shoot(ShooterSubsystem shooterSubsystem, double power) {
        this.shooterSubsystem = shooterSubsystem;
        this.power = power;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void execute() {
        shooterSubsystem.rampUpShooter(power);
    }


}
