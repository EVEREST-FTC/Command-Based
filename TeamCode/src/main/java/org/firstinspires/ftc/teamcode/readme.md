# Command Based para FTC

O projeto tem, como objetivo, representar um sistema baseado em comandos e subsistemas
para controlar mum robô de FTC (em qualquer natureza de opmode).
O projeto conta com algumas pastas voltadas para a organização dos sistemas e outras utilitárias,
sendo elas:
1. ufPackages
2. subsystems
3. commands
4. rc

## 1 Explicação de cada pacote:
### 1.1 - ufpackages
O pacote ufpackages é um pacote utilitário para command-based. Aqui existem algumas classes
úteis para a organização do código em um sistema de comandos, sendo os mais importantes:
- Subsystem
- Command
- CommandScheduler
- Trigger
- SequentialCommandGroup
- ParallelCommandGroup
- ParallelRaceGroup
Caso você queira utulizar essas classses em seu projeto, copie e cole o diretório ufpackages
dentro da sua pasta teamcode.
### 1.2- subsystems
Autoexplicativo, esse pacote se destina ao armazenamento dos susbsitemas criados pela equipe.
### 1.3 - commands
O pacote commands é destinado para a organização dos seus comandos. Sinta-se à vontade para
subdividir o pacote de comandos em quantos subpacotes quiser.
### 1.4 - rc
No pacote rc, o usuário deve armazenar as classes RobotContainer de cada OpMode.

## 2 - Explicação dos Principais Conceitos do Command-Based
Abaixo, segue um resumo dos conceitos mais importantes para esse padrão de projeto, bem como alguns
exemplos (que não aconselho de serem copiados). Caso você queira copiá-los, atente-se ao nome
dos dispositivos na driverstation, por favor.
### 2.1 - Subsistemas

Aqui nos familiarizamos com o primeiro conceito desse padrão de projetos.
Em suma, um subsistema é um conjunto de dispositivos que se propõe a uma função específica (quanto
mais específica a definição das funções, mais independente será o conjunto de subsistemas).
Por exemplo, imagine o subsistema Garra.
A garra possui alguns motores e servos que são atuados para coletar um elemento ou largá-lo em algum
lugar. Para isso, criamos métodos para esse subsistema, que se responsabilizam por atuar esses
conjuntos de motores e servos e realizar uma função que nós compreendemos como o funcionamento dessa
garra (ou a atuação do subsistema).
Então, em resumo, um subsistema é um conjunto de mecanismos que se propõe a realizar uma ação.
Para criar um subsistema, você precisa criar uma classe que represente o seu subsistema e, então,
herdar a classe SubsystemBase (para permitir que essa classe seja compreendida, de fato, como um
subsistema).
Portanto, seguiremos a estrutura abaixo:

```java
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.SubsystemBase;

public class MeuSubsistema extends SubsystemBase {
    //declaramos os dispositivos desse subsistema

    public MeuSubsistema(HardwareMap hardwareMap) {
        //inicializamos os dispositivos
    }
    
    //criamos os métodos necessários para que o subsistema funcione
}
```
Abaixo, temos um exemplo de subsistema que representa um chassi diferencial:

```java
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.SubsystemBase;

//esse subsistema é ilustrativo, para fins unicamente educativos (não copie isso no seu projeto)
public class ChassiDiferencial extends SubsystemBase {
    //Declaração dos nossos atuadores
    private final DcMotor trasEsquerda;
    private final DcMotor trasDireita;
    private final DcMotor frenteEsquerda;
    private final DcMotor frenteDireita;

    public ChassiDiferencial(HardwareMap hardwareMap) {
        //inicializamos os motores pelo atributo hardwaremap
        trasEsquerda = hardwareMap.get(DcMotor.class, "te");
        trasDireita = hardwareMap.get(DcMotor.class, "td");
        frenteDireita = hardwareMap.get(DcMotor.class, "fd");
        frenteEsquerda = hardwareMap.get(DcMotor.class, "fe");

        //configuramos os motores para facilitar o tratamendo da lógica
        trasDireita.setDirection(DcMotorSimple.Direction.REVERSE);
        frenteDireita.setDirection(DcMotorSimple.Direction.REVERSE);
        trasEsquerda.setDirection(DcMotorSimple.Direction.FORWARD);
        frenteEsquerda.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void periodic() {
        //coloque aqui o que você quiser que seja periodicamente executado pelo subsistema
    }

    public void controleDoChassi(double frente, double giro) {
        //criamos um método principal, sendo o maior responsável pela autação do subsistema        
        idleMode(DcMotor.ZeroPowerBehavior.FLOAT);
        double pLeft = frente + giro;
        double pRight = frente - giro;

        setRight(pRight);
        setLeft(pLeft);
    }
    //criamos alguns métodos auxiliares, para facilitar o tratamento dentro da classe ou em
    //alguma atuação futura

    //paramos o chassi
    public void stopChassis() {
        setRight(0.0);
        setLeft(0.0);
        idleMode(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void idleMode(DcMotor.ZeroPowerBehavior mode) {
        trasEsquerda.setZeroPowerBehavior(mode);
        trasDireita.setZeroPowerBehavior(mode);
        frenteEsquerda.setZeroPowerBehavior(mode);
        frenteDireita.setZeroPowerBehavior(mode);
    }

    //mudamos a potencia do lado direito do chassi
    public void setRight(double power) {
        trasDireita.setPower(power);
        frenteDireita.setPower(power);
    }

    //mudamos a potencia do lado esquerdo do chassi
    public void setLeft(double power) {
        trasEsquerda.setPower(power);
        frenteEsquerda.setPower(power);
    }
}
```
### 2.2 - Comandos
Um comando é uma execução de um subsistema, ou seja:
Um comando deve ter, como atributo, um subsistema (para que seja possível a sua manipulação).
O comando precisa receber todos os atributos necessários par a atuação desse subsistema (
se estiver lidando com valores variáveis, como um joystick, repasse os valores do joystick
para a classe do comando - geralmente por uma interface funcional Supplier<T>.
Se estiver lidando com valores estáticos, repasse-os pelo construtor da classe normalmente)
Um comando deve requerir o subsistema, para impedir colisões entre comandos).
Em resumo, um comando deve ter um subsistema e gerenciar as suas atuações.
As atuações do subsistema são realizadas em três períodos bem definidos, acionados por estados
finitos de máquina (só que astronomicamente simplificado pelo número reduzido de estados).
Esses estados são initialize(), periodic() e end().
O estado initialize() sempre é chamado uma vez no início do comando. Nele, você deverá estruturar
todas as instruções que devem ser realizadas antes da execução do comando.
O estado periodic() é executado periodicamente, enquanto o comando não tiver terminado.
O estado end() é executado uma vez, quando a condição de finalização do comando se verifica
verdadeira (quando o comando acaba).
Além desses estados, você precisa definir uma condição de finalização para seu comando (caso deseje
que ele não seja infinito). Essa condição é um valor booleano, represnetado pelo método isFinished()
Para criarmos um comando, vamos seguir a estrutura abaixo:
```java
public class MeuComando extends Command{
    private final MeuSubsistema meuSubsistema;
    //atributos necessários para o controle desse subsistema
    //(números, classes personalizadas, interfaces funcionais etc...)
    public MeuComando(MeuSubsistema meuSubsistema
            /*receba os outros atributos pelo construtor também*/){
        //recebemos o subsistema no construtor da classe
        this.meuSubsistema = meuSubsistema;
        //recebemos os demais atributos (basta seguir a mesma forma)
        
        //no final, requerimos o nosso subsistema e tornamos ele visível no agendador d ecomandos
        addRequirements(meuSubsistema);
    }

    @Override protected void initialize(){
        //fazemos todas as ações necessárias no início
    }
    @Override public void execute(){
        //atuamos o subsistema com as ações que precisam ser periódicas
    }
    @OVerride public void end(boolean interrupted){
        //realizamos as ações que interrompem o comando (paramos o chassi, por exemplo)
    }
    @OVerride protected boolean isFinished(){ 
        //representamos a condição de parada do comando
        //aqui, como deixamos como falso sempre, o comando será infinito.
        return false;
    }
}
```
Abaixo, um exemplo de comando envolvendo o subsistema ChassiDiferencial, que recbe um chassi e dois
suppliers de double:

```java
import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.Command;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

//esse comando é ilustrativo
public class ComandoChassiDiferencial extends Command {
    private final ChassiDiferencial chassiDiferencial;
    private final Supplier<Double> eixoFrontal;
    private final Supplier<Double> eixoRotacional;

    public ComandoChassiDiferencial(ChassiDiferencial chassiDiferencial,
                                    Supplier<Double> eixoFrontal,
                                    Supplier<Double> eixoRotacional) {
        //inicializamos os atributos
        this.chassiDiferencial = chassiDiferencial;
        this.eixoFrontal = eixoFrontal;
        this.eixoRotacional = eixoRotacional;
        //requreimos os subsistemas necessários
        addRequirements(chassiDiferencial);
    }

    @Override
    protected void initialize() {
        //liberamos os freios dos motores
        chassiDiferencial.idleMode(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @Override
    public void execute() {
        //lemos os últimos valores dos fornecedores
        double frontal = eixoFrontal.get();
        double rotacional = eixoRotacional.get();
        //acionamos o chassi de acordo com esses valores
        chassiDiferencial.controleDoChassi(frontal, rotacional);
    }

    @Override
    public void end(boolean interrupted) {
        //no final, paramos o chassi
        chassiDiferencial.stopChassis();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
```
### 2.3 - RobotContainer
Essa classe
RobotContainer representa quais relações seráo feitas entre os comandos e os botões do joystick.
Para fazer essa relação, precisamos instanciar nossos subsistemas e comandos, além de criar um
objeto da classe Trigger uma unica vez, que irá dinamicamente agendar os comandos de acordo com as
condições neles estruturadas.
Por exemplo, imagine que queremos atuar o MeuComando enquanto encostamos o dedo no botão
a:

```java
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotContainer {
    //declaramos nossos subsistemas
    private final MeuSubsistema meuSubsistema;
    //delcarmaos um gamepad, necessário para relacioanr os triggers
    private final Gamepad gamepad;

    //no construtor da classe, recebemos um hardwaremap (usado para inicalizar o subsistema):
    public RobotContainer(HardwareMap hardwareMap, Gamepad gamepad){
        meuSubsistema = new MeuSubsistema(hardwareMap);
        this.gamepad = gamepad;
        //chamamos o método na criação do objeto da classe RobotContainer
        configureBindings();
    }
    //para fins de organização, estruturamos nossas relações nesse método
    private void configureBindings(){
        //criamos um novo trigger
        //o primeiro argumento do trigger é a condição de sua execução
        //esse argumento será o botão a do gamepad
        new Trigger(()->gamepad.a)//usamos a notação lambda ver o valor o tempo todo
                .whileTrue(//escolhemos a análise sobre a condição acima
                        new MeuComando(meuSubsistema)//recebemos o comando associado à ação
                );
    }
}
```
O mesmo processo aconteceria se quiséssemos controalr o chassi diferencial hipotético.
Mas, para isso, como vamos controlar pelos joysticks, definiremos o comando como o padrão do
subsistema:
```java
public class RobotContainer {
    private final ChassiDiferencial meuChassi;
    private final Gamepad gamepad;
    public RobotContainer(HardwareMap hardwareMap, Gamepad gamepad){
        meuChassi = new ChassiDiferencial(hardwareMap);
        this.gamepad = gamepad;
        configureBindings();
    }
    private void configureBindings(){
        meuChassi.setDefaultCommand(//usamos o método setDefaultCommand para definir esse comando
                //como o padrão desse subsistema (sempre será chamado quando nnehum outro comando
                //o requerir).
                new ComandoChassiDiferencial(
                        meuChassi,//recebemos o subsistema
                        ()->-gamepad.left_stick_y,//repassamos os valores do gamepad
                        ()->-gamepad.right_stick_x//como suppliers
                )
        );
    }
}
```
### 2.4 - A classe principal
Com todas as definições de subsistemas e comandos feitas, bem como suas interrelações no
robotcontainer, basta acionar essas relações numa classe.
Sempre seguiremos a estrutura seguinte:

```java
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ufpackages.CommandBased.CommandScheduler;

@TeleOp(name = "Main")
public class MainTeleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        //vamos criar nossa instância do robotContainer, fazendo o agendamento de todos os comandos
        //e a definição de todos os triggers para aquela classe:
        new RobotContainer(hardwareMap, gamepad1);
        waitForStart();
        while (opModeIsActive()) {
            //chamamos o agendador de comados periodicamenteo, enquanto o código está rodando.
            CommandScheduler.getInstance().run(this);
        }
    }
}
```
### 2.5 - O Agendador de Comandos (CommandScheduler)
Aqui, nos deparamos com o último conceito da introdução aos comandos na FTC:
```java
@TeleOp(name = "Main")
public class MainTeleop extends LinearOpMode {
    //...
    {//...
        while (opModeIsActive()) {
            CommandScheduler.getInstance().run(this);
        }
    }
}
```
A classe CommandScheduler é a classe responsável por permitir que varios comandos sejam executados
ao mesmo tempo, e que exista toda uma lógica refinada de exclusão de subssitemas concomitantes.
Através de uma lista de comandos agendados (requeridos pelos triggers, geralmente), realizamos, para
cada comando agendado, uma série de ações periódicas, que nos dá a ilusão de várias ações simultâneas.
É como se, para cada comando na lista dos comandos agendados, se executasse a função periódica (essa
corrida é feita justamente por essa função run, que consome os comandos existentes na agenda ou
os retira, caso os triggers assim decidam).
O propósito do agendador é justamente esse: permitir que os comandos sejam executados paralelamente.

## 3 - Considerações Finais
Publicaremos alguns repositórios com exemplos de sistemas que empregem o padrão de comandos a fim de
revisar possíveis dúvidas na interpretação deste documento.
