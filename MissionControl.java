import java.util.Scanner;

public class MissionControl {

    // limite de ciclos que o sistema aguenta guardar
    static int MAX_CICLOS = 50;

    // vetores separados pra cada dado da missao
    static int[]     numeroCiclo  = new int[MAX_CICLOS];
    static double[]  temperatura  = new double[MAX_CICLOS];
    static double[]  energia      = new double[MAX_CICLOS];
    static int[]     comunicacao  = new int[MAX_CICLOS];   // 1 = ok, 0 = falha
    static String[]  status       = new String[MAX_CICLOS];

    static int totalCiclos = 0; // quantos ciclos foram inseridos ate agora

    static Scanner sc = new Scanner(System.in);

    // -------------------------------------------------------

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("   MISSION CONTROL AI  --  CAPSULA ORION     ");
        System.out.println("   FIAP GS 2026.1  --  GRUPO 05  --  1CCPY   ");
        System.out.println("==============================================");
        System.out.println("Sistema inicializado. Aguardando dados...\n");

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("--------- MENU ---------");
            System.out.println("1. Inserir dados");
            System.out.println("2. Visualizar status");
            System.out.println("3. Executar analise");
            System.out.println("4. Historico de leituras");
            System.out.println("0. Encerrar sistema");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();

            if (opcao == 1) {
                inserirDados();
            } else if (opcao == 2) {
                visualizarStatus();
            } else if (opcao == 3) {
                executarAnalise();
            } else if (opcao == 4) {
                historicoLeituras();
            } else if (opcao == 0) {
                System.out.println("\nEncerrando Mission Control. Ate a proxima missao!");
            } else {
                System.out.println("Opcao invalida. Tente novamente.\n");
            }
        }
    }

    // -------------------------------------------------------

    static void inserirDados() {

        if (totalCiclos >= MAX_CICLOS) {
            System.out.println("Limite de ciclos atingido!\n");
            return;
        }

        int i = totalCiclos; // posicao atual no vetor

        numeroCiclo[i] = i + 1;

        System.out.println("\n-- Inserir dados do Ciclo " + numeroCiclo[i] + " --");

        System.out.print("Temperatura da nave (graus C): ");
        temperatura[i] = sc.nextDouble();

        System.out.print("Nivel de energia (%): ");
        energia[i] = sc.nextDouble();

        System.out.print("Comunicacao ativa? (1=Sim / 0=Nao): ");
        comunicacao[i] = sc.nextInt();

        // define o status com base nos alertas
        int alertas = 0;

        if (temperatura[i] > 80) {
            System.out.println("  ALERTA: Superaquecimento detectado!");
            alertas++;
        }

        if (energia[i] < 20) {
            System.out.println("  ALERTA: Energia critica -- modo economico ativado!");
            alertas++;
        }

        if (comunicacao[i] == 0) {
            System.out.println("  ALERTA: Falha de comunicacao com a base!");
            alertas++;
        }

        if (alertas == 0) {
            status[i] = "NOMINAL";
        } else if (alertas == 1) {
            status[i] = "ATENCAO";
        } else {
            status[i] = "CRITICO";
        }

        System.out.println("  Status do ciclo: " + status[i]);

        totalCiclos++; // so incrementa depois de tudo preenchido
        System.out.println();
    }

    // -------------------------------------------------------

    static void visualizarStatus() {

        if (totalCiclos == 0) {
            System.out.println("Nenhum ciclo registrado ainda.\n");
            return;
        }

        int ultimo = totalCiclos - 1; // indice do ultimo ciclo

        System.out.println("\n-- Status Atual da Missao ORION --");
        System.out.println("  Ultimo ciclo  : " + numeroCiclo[ultimo]);
        System.out.println("  Temperatura   : " + temperatura[ultimo] + " graus C");
        System.out.println("  Energia       : " + energia[ultimo] + "%");

        if (comunicacao[ultimo] == 1) {
            System.out.println("  Comunicacao   : ATIVA");
        } else {
            System.out.println("  Comunicacao   : FALHA");
        }

        System.out.println("  Status        : " + status[ultimo]);
        System.out.println();
    }

    // -------------------------------------------------------

    static void executarAnalise() {

        if (totalCiclos == 0) {
            System.out.println("Sem dados para analisar.\n");
            return;
        }

        double somaTemp    = 0;
        double somaEnergia = 0;
        int falhasComm     = 0;
        int nominais       = 0;
        int atencao        = 0;
        int criticos       = 0;

        // percorre todos os ciclos somando os valores
        for (int i = 0; i < totalCiclos; i++) {
            somaTemp    += temperatura[i];
            somaEnergia += energia[i];

            if (comunicacao[i] == 0) {
                falhasComm++;
            }

            if (status[i].equals("NOMINAL")) {
                nominais++;
            } else if (status[i].equals("ATENCAO")) {
                atencao++;
            } else {
                criticos++;
            }
        }

        double mediaTemp    = somaTemp / totalCiclos;
        double mediaEnergia = somaEnergia / totalCiclos;

        // risco: ciclos em atencao valem 1, criticos valem 2
        double risco = ((atencao + criticos * 2.0) / (totalCiclos * 2.0)) * 100.0;

        System.out.println("\n-- Analise Geral da Missao --");
        System.out.println("  Ciclos registrados       : " + totalCiclos);
        System.out.printf ("  Temperatura media        : %.1f graus C%n", mediaTemp);
        System.out.printf ("  Energia media            : %.1f%%%n", mediaEnergia);
        System.out.println("  Falhas de comunicacao    : " + falhasComm);
        System.out.println("  Ciclos nominais          : " + nominais);
        System.out.println("  Ciclos em atencao        : " + atencao);
        System.out.println("  Ciclos criticos          : " + criticos);
        System.out.printf ("  Indice de risco          : %.1f%%%n", risco);

        if (risco < 20) {
            System.out.println("  Resultado: Missao sob controle.");
        } else if (risco < 50) {
            System.out.println("  Resultado: Atencao recomendada ao status da missao.");
        } else {
            System.out.println("  Resultado: Missao em estado critico! Intervencao necessaria.");
        }

        System.out.println();
    }

    // -------------------------------------------------------

    static void historicoLeituras() {

        if (totalCiclos == 0) {
            System.out.println("Nenhuma leitura registrada.\n");
            return;
        }

        System.out.println("\n-- Historico de Ciclos --");
        System.out.println("Ciclo  Temp(C)   Energia(%)  Comunicacao  Status");
        System.out.println("-----  -------   ----------  -----------  -------");

        for (int i = 0; i < totalCiclos; i++) {
            String comm = (comunicacao[i] == 1) ? "ATIVA" : "FALHA";
            System.out.printf("  %02d   %6.1f   %6.1f      %-11s  %s%n",
                numeroCiclo[i], temperatura[i], energia[i], comm, status[i]);
        }

        System.out.println();
    }
}
