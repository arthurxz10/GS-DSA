# Mission Control AI — Cápsula ORION
### GS 2026.1 | Data Structures & Algorithms | FIAP 1CCPY | Grupo 05

---

## Integrantes

| Nome | RM |
|---|---|
| Arthur dos Santos Bezerra | 569721 |
| Carlos Henrique Fratezi | 571792 |
| Felipe Gouveia Braga | 568956 |

---

## Sobre o projeto

O sistema simula o painel de controle da Cápsula ORION, uma missão espacial experimental criada como conceito base da GS 2026.1. O programa monitora os três sensores principais da nave — temperatura, energia e comunicação — registrando cada leitura como um ciclo de missão e emitindo alertas automáticos quando algum parâmetro sai do normal.

Cada ciclo inserido fica salvo em vetores paralelos, o que permite consultar o histórico completo, analisar tendências e calcular um índice de risco operacional geral da missão.

---

## Como compilar e executar

Você vai precisar do Java instalado (JDK 8 ou superior). No terminal:

```bash
javac MissionControl.java
java MissionControl
```

No Windows, os comandos são os mesmos — basta abrir o terminal na pasta onde o arquivo está salvo.

> O nome do arquivo `.java` precisa ser exatamente `MissionControl.java` porque Java exige que o nome da classe e do arquivo sejam iguais.

---

## Funcionalidades do menu

**1. Inserir dados**
Solicita temperatura (°C), nível de energia (%) e status da comunicação. Após o cadastro, os alertas do ciclo são exibidos na hora com o status resultante.

**2. Visualizar status**
Mostra os dados do último ciclo registrado com seu status atual (NOMINAL, ATENCAO ou CRITICO).

**3. Executar análise**
Calcula médias de temperatura e energia, conta falhas de comunicação, distribui os ciclos por classificação e exibe o índice de risco operacional da missão.

**4. Histórico de leituras**
Lista todos os ciclos em formato de tabela com temperatura, energia, status de comunicação e classificação de cada um.

**0. Encerrar sistema**
Finaliza o programa.

---

## Regras de alerta

| Condição | Alerta gerado |
|---|---|
| Temperatura > 80°C | Superaquecimento |
| Energia < 20% | Modo econômico ativado |
| Comunicação = 0 | Falha de comunicação |

Cada alerta incrementa um contador interno. Com 0 alertas o status é NOMINAL, com 1 é ATENCAO, com 2 ou mais é CRITICO.

---

## Estruturas de dados utilizadas

O programa usa vetores paralelos — todos com o mesmo tamanho máximo — onde o índice `i` sempre corresponde ao mesmo ciclo em todos os vetores:

```java
static int[]     numeroCiclo  = new int[MAX_CICLOS];
static double[]  temperatura  = new double[MAX_CICLOS];
static double[]  energia      = new double[MAX_CICLOS];
static int[]     comunicacao  = new int[MAX_CICLOS];
static String[]  status       = new String[MAX_CICLOS];

static int totalCiclos = 0; // conta quantos ciclos foram inseridos
```

---

## Fluxograma

```
                  ┌─────────────────────┐
                  │  Iniciar o Sistema  │
                  └────────┬────────────┘
                           │
                  ┌────────▼────────────┐
                  │    Exibir Menu      │◄──────────────────┐
                  └────────┬────────────┘                   │
                           │                                │
          ┌────────────────┼────────────────┐               │
          │                │                │               │
   ┌──────▼──────┐  ┌──────▼──────┐  ┌─────▼──────┐        │
   │  Inserir    │  │ Visualizar  │  │  Executar  │        │
   │   Dados     │  │   Status    │  │  Analise   │        │
   └──────┬──────┘  └──────┬──────┘  └─────┬──────┘        │
          │                │               │                │
   ┌──────▼──────┐         │               │       ┌────────▼──────┐
   │ Verificar   │         │               │       │  Historico de │
   │  Alertas    │         │               │       │   Leituras    │
   └──────┬──────┘         │               │       └───────────────┘
          │                │               │
   ┌──────▼──────┐         │               │
   │ Temp > 80?  │         │               │
   │ Energia<20? │         │               │
   │ Comm = 0?   │         │               │
   └──────┬──────┘         │               │
          │                │               │
   ┌──────▼──────┐         └───────────────┘
   │  Atribuir   │                         │
   │   Status    │─────────────────────────┘
   │ (NOMINAL /  │
   │  ATENCAO /  │
   │   CRITICO)  │
   └─────────────┘
          │
   ┌──────▼──────┐
   │  Opcao = 0? │
   │  Encerrar   │
   └─────────────┘
```

---

## Explicação da lógica

O programa declara vetores estáticos com capacidade para 50 ciclos e uma variável `totalCiclos` que funciona como índice e contador ao mesmo tempo — ela aponta sempre para a próxima posição livre.

Quando o usuário insere um ciclo, os valores são gravados na posição `totalCiclos` de cada vetor e o contador é incrementado só depois que tudo foi preenchido. Isso garante que o índice nunca fica inconsistente entre os vetores.

A verificação de alertas usa `if` separados e não encadeados com `else if`, porque um ciclo pode ter mais de um problema ao mesmo tempo. Cada condição verdadeira soma 1 ao contador de alertas, e o status é definido no final com base nesse total.

A análise geral percorre todos os vetores com um laço `for`, acumulando valores para calcular médias e contagens por categoria. O índice de risco operacional pondera ciclos em atenção com peso 1 e ciclos críticos com peso 2, dividindo pelo máximo possível para normalizar entre 0% e 100%.

---

## Evidência de execução

Teste com 3 ciclos (nominal, crítico, nominal):

```
Ciclo 01 — Temp: 75.5°C, Energia: 85%, Comm: ATIVA  → NOMINAL
Ciclo 02 — Temp: 90.2°C, Energia: 15.3%, Comm: FALHA → CRITICO (3 alertas)
Ciclo 03 — Temp: 68.0°C, Energia: 42%, Comm: ATIVA   → NOMINAL

Analise geral:
  Temperatura media: 77.9 graus C
  Energia media: 47.4%
  Falhas de comunicacao: 1
  Ciclos nominais: 2 | Criticos: 1
  Indice de risco operacional: 33.3% → Atencao recomendada
```

---

## Critérios atendidos

| Critério | Como foi atendido |
|---|---|
| Funcionamento do sistema | Menu funcional com 5 opções, alertas automáticos, análise geral |
| Uso correto de estruturas de dados | Vetores paralelos, laços `for` e `while`, condicionais `if/else if` |
| Organização do código | Métodos separados por responsabilidade, constantes nomeadas, comentários explicativos |
| Lógica implementada | Limites definidos por constante, índice de risco ponderado, status por contagem de alertas |

