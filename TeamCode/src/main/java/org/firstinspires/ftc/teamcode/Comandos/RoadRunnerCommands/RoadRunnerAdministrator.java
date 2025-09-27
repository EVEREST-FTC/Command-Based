package org.firstinspires.ftc.teamcode.Comandos.RoadRunnerCommands;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.SubSistemas.MecanumDrive;

import java.util.Optional;

public abstract class RoadRunnerAdministrator extends org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command {
    /**
     * Essa classe é um tipo de wrapper de action. Ela permite que as trajetórias sejam
     * integradas na função run(), pertencente ao CommandSCheduler, uma vez que
     * a administração da execução da trajetória é responsabilidade de um comando (envolvemos
     * a action em um comando),
     * Dentro dessa classe, temos um atributo instance, que é justamente o elemento do wrapper
     * a ser decorado.
     * Além do elemento, temos uma instância imagética do chassi para ler a posição do robõ,
     * uma variável booleana para inferir sobre o término da trajetória e
     * um atributo telemetryPacket, exigido pelo executor de trajaetórias;
     */
    //prefiro usar o optional do que arriscar o
    //NullPointerException (anulamos a instância no final do comando),
    protected Optional<TrajectoryActionBuilder> instance = Optional.empty();
    private final TelemetryPacket telemetryPacket = new TelemetryPacket();
    private final MecanumDrive mecanumDrive;
    private boolean inTrajectory = false;

    /**
     * O construtor simplesmente inicializa o chassi com um argumento
     * repassado na criação do comando.
     */
    public RoadRunnerAdministrator(MecanumDrive mecanumDrive) {

        this.mecanumDrive = mecanumDrive;
        addRequirements(mecanumDrive);
    }

    /**
     * O métod0 initiaize irá sobrepor o valor da variável de análise como verdadeiro,
     * permitindo a execução do comando.
     * Posteriormente, alocamos a posição atual das rodas mecanum à variáveal de construção
     * dos caminhos (agora, nossa posição inicial é a posição recuperada pelo localizador
     * do chassi mecanum).
     */
    @Override
    protected void initialize() {
        inTrajectory = true;
        instance = Optional.of(getBase());
    }

    /**
     * Aqui, executamos de fato a action com a métod0 run. O métod0 run é abstrato, e possui
     * implementações diferentes para cada modelo de chassi (uma vez que cada classe
     * geral de chassi tem uma cinemática inversa específica e, portanto, um comportamento
     * específico).
     * O métod0 run, para o caso dos robôs da nossa escola, está implementado no link abaixo:
     * {@link MecanumDrive.FollowTrajectoryAction#run(TelemetryPacket)}
     * <P>
     * Além da execução do tratador holonômico, da atualização do tempo da trajetória e de
     * * tantos outros procedimentos internos a esse métod0, atualizamos também a variável inTrajectory
     * * com o valor de retorno do mesmo métod0.
     * </P>
     * O métod0 run retorna falso caso interrompido, ou finalizado.
     * Verdadeiro caso contrário.
     * Portanto, ele representa a não finalização do comando,
     * A justificativa para utilizar essa estrutura reside no métod0 {@link com.acmerobotics.roadrunner.ftc.Actions#runBlocking(Action)},
     * da interface Action. Ele usa exatamente essa linha em um loop muito semelhante ao
     * recriado em CommandBased:
     * <p>
     * Essa é a linha responsável pelo início do loop da trajetória da RoadRunner. Por isso,
     * eles demandam o uso dessas actions. Como o loop ocupa a thread daemon, é impossível fazer
     * ações simultâneas gerenciadas autônomamente pelo código (uma máquina de estados, por
     * exemplo).
     * </p>
     * <p>
     * Para resolver isso, utiliza-se essa estrutura das actions. Se tudo der certo, o mesmo
     * resultado deve ser obtido com CommandBased.
     * </p>
     */

    @Override
    public void execute() {
        //TelemetryPacket telemetryPacket = new TelemetryPacket();
        inTrajectory = instance.get().build().run(telemetryPacket);
    }

    /**
     * O métod0 end zera a potência dos motores (restrição da FTC) e anula a instãncia no final do comando.
     */
    @Override
    public void end(boolean interrupted) {
        instance = Optional.empty();
        mecanumDrive.setDrivePowers(new PoseVelocity2d(new Vector2d(0.0, 0.0), 0.0));
    }

    /**
     * O métod0 isFinished retorna justamente o !valor da action.
     * Se o valor da aciton for verdadeiro, devemos continuar ela. Na análise,
     * invertemos o valor, retornando falso para o isFinished e requerindo o métod0
     * periodic() mais uma vez para o CommandScheduler.
     * Veja abaixo a atuação dessa condição no cancelamento dos comandos:
     */
    @Override
    protected boolean isFinished() {
        return !inTrajectory;
    }

    /**
     * @return a posição atual do localizador
     */
    protected TrajectoryActionBuilder getBase() {
        return mecanumDrive.actionBuilder(mecanumDrive.localizer.getPose());

    }

    public static class RoadRunnerFactory{
        RoadRunnerAdministrator lineToX(MecanumDrive mecanumDrive, double x) {
            return new RoadRunnerAdministrator(mecanumDrive) {
                @Override
                protected void initialize() {
                    super.initialize();
                    instance = Optional.of(instance.get().lineToX(x));
                }
            };
        }
        public static RoadRunnerAdministrator lineToY(MecanumDrive mecanumDrive, double y){
            return new RoadRunnerAdministrator(mecanumDrive){
                @Override
                protected void initialize() {
                    super.initialize();
                    instance = Optional.of(instance.get().lineToY(y));
                }
            };
        }
        public static RoadRunnerAdministrator splineToLinearHeading(MecanumDrive mecanumDrive, Pose2d pose, Rotation2d tangent){
            return new RoadRunnerAdministrator(mecanumDrive){
                @Override
                protected void initialize() {
                    super.initialize();
                    instance = Optional.of(instance.get().splineToLinearHeading(pose, tangent));
                }
            };
        }
    }


}

