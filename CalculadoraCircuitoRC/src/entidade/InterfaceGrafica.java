package entidade;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
  -> Interface gráfica da Calculadora de Circuito RC.

 */
public class InterfaceGrafica extends JFrame {

   
    private static final Color BG_JANELA   = new Color(13,  17,  23);   // fundo geral
    private static final Color BG_CARD     = new Color(22,  27,  34);   // fundo dos cards
    private static final Color BG_CAMPO    = new Color(10,  13,  18);   // fundo dos inputs
    private static final Color CL_BORDA    = new Color(48,  54,  61);   // borda dos cards
    private static final Color CL_ACENTO   = new Color(88,  166, 255);  // azul elétrico
    private static final Color CL_SUCESSO  = new Color(63,  185, 80);   // verde (resultados)
    private static final Color CL_ERRO     = new Color(248, 81,  73);   // vermelho
    private static final Color CL_AVISO    = new Color(210, 153, 34);   // âmbar
    private static final Color TXT_FORTE   = new Color(201, 209, 217);  // texto principal
    private static final Color TXT_FRACO   = new Color(139, 148, 158);  // texto secundário

    
    //  CAMPOS DE ENTRADA
    
    private JTextField campoR;
    private JTextField campoC;
    private JTextField campoEpsilon;
    private JTextField campoTempo;

    
    //  LABELS DE RESULTADO — principais
    
    private JLabel valTau;
    private JLabel valTeq;
    private JLabel valVeq;
    private JLabel valImax;

     
    //  LABELS DE RESULTADO — instante específico
     
    private JLabel valVCt;
    private JLabel valVRt;
    private JLabel valIt;
    private JLabel valQt;
    private JLabel valPct;

     
    //  OUTROS COMPONENTES
     
    private JLabel    lblMsgErro;
    private JPanel    painelResultadosTempo;

     
    //  CONSTRUTOR
     

    /**
     * Constrói e exibe a janela da calculadora.
     */
    public InterfaceGrafica() {
        super("⚡  Calculadora — Circuito RC  |  Carregamento de Capacitor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(BG_JANELA);
        raiz.add(montarCabecalho(), BorderLayout.NORTH);
        raiz.add(montarCorpo(),     BorderLayout.CENTER);
        raiz.add(montarRodape(),    BorderLayout.SOUTH);

        setContentPane(raiz);
        pack();
        setMinimumSize(new Dimension(700, 540));
        setLocationRelativeTo(null);
        setVisible(true);
    }

     
    //  ESTRUTURA PRINCIPAL
     

    /**
     * Monta o cabeçalho com título, subtítulo e separador.
     */
    private JPanel montarCabecalho() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_JANELA);
        p.setBorder(new EmptyBorder(20, 24, 8, 24));

        JLabel titulo = label("⚡  CIRCUITO RC — CARREGAMENTO DE CAPACITOR",
                CL_ACENTO, new Font("SansSerif", Font.BOLD, 16));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = label(
                "Chave fechada em t = 0  ·  Capacitor inicialmente descarregado",
                TXT_FRACO, new Font("SansSerif", Font.ITALIC, 11));
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(CL_BORDA);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        p.add(titulo);
        p.add(Box.createVerticalStrut(4));
        p.add(sub);
        p.add(Box.createVerticalStrut(14));
        p.add(sep);
        return p;
    }

    /**
     * Monta o corpo central com todos os cards e botões.
     */
    private JPanel montarCorpo() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_JANELA);
        p.setBorder(new EmptyBorder(14, 24, 14, 24));

        p.add(cardEntradas());
        p.add(Box.createVerticalStrut(10));

        p.add(cardTempoOpcional());
        p.add(Box.createVerticalStrut(10));

        lblMsgErro = label("", CL_ERRO, new Font("SansSerif", Font.BOLD, 11));
        lblMsgErro.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(lblMsgErro);
        p.add(Box.createVerticalStrut(8));

        p.add(cardBotoes());
        p.add(Box.createVerticalStrut(14));

        p.add(cardResultadosPrincipais());
        p.add(Box.createVerticalStrut(10));

        painelResultadosTempo = cardResultadosTempo();
        painelResultadosTempo.setVisible(false);
        p.add(painelResultadosTempo);

        return p;
    }

    /**
     * Monta o rodapé com as equações usadas e a versão.
     */
    private JPanel montarRodape() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG_JANELA);
        p.setBorder(new CompoundBorder(
                new MatteBorder(1, 0, 0, 0, CL_BORDA),
                new EmptyBorder(7, 24, 10, 24)));

        JLabel eq = label(
                "V_C(t) = ε(1 − e^(−t/τ))   |   V_R(t) = ε·e^(−t/τ)   |   i(t) = (ε/R)·e^(−t/τ)   |   τ = RC",
                TXT_FRACO, new Font("Monospaced", Font.PLAIN, 10));

        JLabel ver = label("v1.0", TXT_FRACO, new Font("SansSerif", Font.PLAIN, 10));

        p.add(eq,  BorderLayout.WEST);
        p.add(ver, BorderLayout.EAST);
        return p;
    }

     
    //  CARDS DE ENTRADA
     

    /**
     * Card com os três campos de entrada do circuito (R, C e ε).
     */
    private JPanel cardEntradas() {
        JPanel card = criarCard("PARÂMETROS DO CIRCUITO");

        JPanel grid = new JPanel(new GridLayout(1, 3, 14, 0));
        grid.setBackground(BG_CARD);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoR       = new JTextField("20.0");
        campoC       = new JTextField("15.0");
        campoEpsilon = new JTextField();

        grid.add(grupoInput("Resistência   R", "Ω",  "valor > 0",   campoR));
        grid.add(grupoInput("Capacitância  C", "μF", "valor > 0",   campoC));
        grid.add(grupoInput("Tensão fonte  ε", "V",  "valor > 0",   campoEpsilon));

        card.add(grid);
        return card;
    }

    /**
     * Card com o campo de tempo personalizado (opcional).
     */
    private JPanel cardTempoOpcional() {
        JPanel card = criarCard("ANÁLISE EM INSTANTE ESPECÍFICO  ·  opcional");

        JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        linha.setBackground(BG_CARD);
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoTempo = new JTextField(10);
        estilizar(campoTempo);

        linha.add(label("Instante  t  = ", TXT_FORTE, new Font("SansSerif", Font.PLAIN, 12)));
        linha.add(campoTempo);
        linha.add(label("  ms", CL_ACENTO,  new Font("SansSerif", Font.BOLD, 12)));
        linha.add(label("     ← deixe vazio para ignorar",
                TXT_FRACO, new Font("SansSerif", Font.ITALIC, 11)));

        card.add(linha);
        return card;
    }

    
    //  BOTÕES
     

    /**
     * Painel com os botões CALCULAR e RESET lado a lado.
     */
    private JPanel cardBotoes() {
        JPanel p = new JPanel(new GridLayout(1, 2, 14, 0));
        p.setBackground(BG_JANELA);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        JButton btnCalc  = botao("▶   CALCULAR", CL_ACENTO, Color.WHITE);
        JButton btnReset = botao("↺   RESET",    CL_ERRO,   Color.WHITE);

        btnCalc.addActionListener(e  -> calcular());
        btnReset.addActionListener(e -> resetar());

        p.add(btnCalc);
        p.add(btnReset);
        return p;
    }

     
    //  CARDS DE RESULTADO
     

    /**
     * Card com os quatro resultados principais: τ, t_eq, V_eq e I₀.
     */
    private JPanel cardResultadosPrincipais() {
        JPanel card = criarCard("RESULTADOS PRINCIPAIS");

        JPanel grid = new JPanel(new GridLayout(2, 2, 10, 10));
        grid.setBackground(BG_CARD);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        valTau  = labelResultado("—", CL_SUCESSO);
        valTeq  = labelResultado("—", CL_SUCESSO);
        valVeq  = labelResultado("—", CL_SUCESSO);
        valImax = labelResultado("—", CL_SUCESSO);

        grid.add(miniCard("τ   Constante de Tempo  (τ = R·C)",              valTau));
        grid.add(miniCard("t   Instante  V_C = V_R  (t = τ·ln 2)",         valTeq));
        grid.add(miniCard("V   Tensão de Equilíbrio  (V = ε / 2)",          valVeq));
        grid.add(miniCard("I₀  Corrente Inicial em t = 0  (I₀ = ε / R)",   valImax));

        card.add(grid);
        return card;
    }

    /**
     * Card com os resultados para o instante t informado pelo usuário.
     * Exibe V_C(t), V_R(t), i(t), q(t) e porcentagem de carga.
     */
    private JPanel cardResultadosTempo() {
        JPanel card = criarCard("RESULTADOS NO INSTANTE  t  (análise personalizada)");

        JPanel grid = new JPanel(new GridLayout(1, 5, 8, 0));
        grid.setBackground(BG_CARD);
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        valVCt  = labelResultado("—", CL_ACENTO);
        valVRt  = labelResultado("—", CL_ACENTO);
        valIt   = labelResultado("—", CL_ACENTO);
        valQt   = labelResultado("—", CL_ACENTO);
        valPct  = labelResultado("—", CL_AVISO);

        grid.add(miniCard("V_C(t)  Tensão no Capacitor",  valVCt));
        grid.add(miniCard("V_R(t)  Tensão no Resistor",   valVRt));
        grid.add(miniCard("i(t)    Corrente",              valIt));
        grid.add(miniCard("q(t)    Carga no Capacitor",   valQt));
        grid.add(miniCard("%       Carga Percentual",      valPct));

        card.add(grid);
        return card;
    }

     
    //  LÓGICA DE CÁLCULO E RESET
    

    /**
     * Valida todos os campos, instancia CircuitoRC e exibe os resultados.
     * Mostra mensagem de erro descritiva se algum campo for inválido.
     */
    private void calcular() {
        lblMsgErro.setText("");

        //  Validação 
        String eR = Validador.validarResistencia(campoR.getText());
        String eC = Validador.validarCapacitancia(campoC.getText());
        String eE = Validador.validarTensao(campoEpsilon.getText());
        String eT = Validador.validarTempo(campoTempo.getText());

        if (eR != null) { lblMsgErro.setText("⚠  " + eR); return; }
        if (eC != null) { lblMsgErro.setText("⚠  " + eC); return; }
        if (eE != null) { lblMsgErro.setText("⚠  " + eE); return; }
        if (eT != null) { lblMsgErro.setText("⚠  " + eT); return; }

        //  Parsing 
        double R       = Validador.parseDouble(campoR.getText());
        double C       = Validador.parseDouble(campoC.getText());
        double epsilon = Validador.parseDouble(campoEpsilon.getText());

        CircuitoRC circ = new CircuitoRC(R, C, epsilon);

        //  Resultados principais 
        double tau  = circ.calcularTau();
        double tEq  = circ.calcularTempoEquilibrio();
        double vEq  = epsilon / 2.0;
        double iMax = circ.calcularCorrenteMaxima();

        valTau.setText(formatarTempo(tau));
        valTeq.setText(formatarTempo(tEq));
        valVeq.setText(String.format("%.4f V  (ε / 2)", vEq));
        valImax.setText(String.format("%.4f A", iMax));

        //  Resultados do instante opcional 
        String textoT = campoTempo.getText().trim();
        if (!textoT.isEmpty()) {
            double tMs = Validador.parseDouble(textoT);
            double t   = tMs * 1e-3;               // ms → s

            valVCt.setText(String.format("%.4f V",   circ.calcularTensaoCapacitor(t)));
            valVRt.setText(String.format("%.4f V",   circ.calcularTensaoResistor(t)));
            valIt.setText(String.format("%.6f A",    circ.calcularCorrente(t)));
            valQt.setText(String.format("%.4e C",    circ.calcularCarga(t)));
            valPct.setText(String.format("%.2f %%",  circ.calcularPorcentagemCarga(t)));

            painelResultadosTempo.setVisible(true);
        } else {
            painelResultadosTempo.setVisible(false);
        }

        pack();
    }

    /**
     * Limpa todos os campos de entrada e reseta os resultados para "—".
     */
    private void resetar() {
        campoR.setText("20.0");
        campoC.setText("15.0");
        campoEpsilon.setText("");
        campoTempo.setText("");
        lblMsgErro.setText("");

        valTau.setText("—");  valTeq.setText("—");
        valVeq.setText("—");  valImax.setText("—");
        valVCt.setText("—");  valVRt.setText("—");
        valIt.setText("—");   valQt.setText("—");
        valPct.setText("—");

        painelResultadosTempo.setVisible(false);
        pack();
    }

     
    //  HELPERS DE CONSTRUÇÃO DE COMPONENTES
     

    /**
     * Cria um painel card com título em azul, separador e BoxLayout vertical.
     *
     * @param titulo texto do título do card
     * @return painel pronto para receber conteúdo
     */
    private JPanel criarCard(String titulo) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setBorder(new CompoundBorder(
                new LineBorder(CL_BORDA, 1),
                new EmptyBorder(12, 14, 12, 14)));

        JLabel lTitulo = label(titulo, CL_ACENTO, new Font("SansSerif", Font.BOLD, 10));
        lTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(CL_BORDA);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        card.add(lTitulo);
        card.add(Box.createVerticalStrut(8));
        card.add(sep);
        card.add(Box.createVerticalStrut(10));
        return card;
    }

    /**
     * Cria um mini-card de resultado com nome e label de valor.
     *
     * @param nome     nome/descrição do resultado
     * @param lblValor label que exibirá o valor calculado
     * @return painel mini-card configurado
     */
    private JPanel miniCard(String nome, JLabel lblValor) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_JANELA);
        p.setBorder(new CompoundBorder(
                new LineBorder(CL_BORDA, 1),
                new EmptyBorder(8, 10, 8, 10)));

        JLabel lNome = label(nome, TXT_FRACO, new Font("SansSerif", Font.PLAIN, 10));
        lNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblValor.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(lNome);
        p.add(Box.createVerticalStrut(6));
        p.add(lblValor);
        return p;
    }

    /**
     * Cria um grupo de input com nome do parâmetro, unidade, restrição e campo.
     *
     * @param nome      nome do parâmetro
     * @param unidade   unidade física exibida em azul
     * @param restricao restrição exibida em cinza
     * @param campo     JTextField a ser estilizado
     * @return painel do grupo de input configurado
     */
    private JPanel grupoInput(String nome, String unidade, String restricao, JTextField campo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(BG_CARD);

        JLabel lNome = label(nome, TXT_FORTE, new Font("SansSerif", Font.BOLD, 11));
        lNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel subLinha = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        subLinha.setBackground(BG_CARD);
        subLinha.setAlignmentX(Component.LEFT_ALIGNMENT);
        subLinha.add(label(unidade, CL_ACENTO, new Font("SansSerif", Font.BOLD, 13)));
        subLinha.add(label("  (" + restricao + ")", TXT_FRACO,
                new Font("SansSerif", Font.PLAIN, 10)));

        estilizar(campo);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(lNome);
        p.add(Box.createVerticalStrut(2));
        p.add(subLinha);
        p.add(Box.createVerticalStrut(4));
        p.add(campo);
        return p;
    }

    /**
     * Aplica o estilo de tema escuro a um JTextField.
     *
     * @param campo campo a ser estilizado
     */
    private void estilizar(JTextField campo) {
        campo.setBackground(BG_CAMPO);
        campo.setForeground(TXT_FORTE);
        campo.setCaretColor(CL_ACENTO);
        campo.setFont(new Font("Monospaced", Font.PLAIN, 13));
        campo.setBorder(new CompoundBorder(
                new LineBorder(CL_BORDA, 1),
                new EmptyBorder(4, 8, 4, 8)));
    }

    /**
     * Cria um JLabel com cor e fonte específicas.
     *
     * @param texto  conteúdo do label
     * @param cor    cor do texto
     * @param fonte  fonte a ser aplicada
     * @return JLabel configurado
     */
    private JLabel label(String texto, Color cor, Font fonte) {
        JLabel l = new JLabel(texto);
        l.setForeground(cor);
        l.setFont(fonte);
        return l;
    }

    /**
     * Cria um JLabel de resultado com fonte monoespaçada em negrito.
     *
     * @param texto valor inicial (geralmente "—")
     * @param cor   cor do valor
     * @return JLabel estilizado para resultado
     */
    private JLabel labelResultado(String texto, Color cor) {
        JLabel l = new JLabel(texto);
        l.setForeground(cor);
        l.setFont(new Font("Monospaced", Font.BOLD, 14));
        return l;
    }

    /**
     * Cria um JButton com fundo colorido e texto em negrito.
     *
     * @param texto      texto do botão
     * @param corFundo   cor de fundo
     * @param corTexto   cor do texto
     * @return JButton configurado
     */
    private JButton botao(String texto, Color corFundo, Color corTexto) {
        JButton btn = new JButton(texto);
        btn.setBackground(corFundo);
        btn.setForeground(corTexto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new CompoundBorder(
                new LineBorder(corFundo.darker(), 1),
                new EmptyBorder(6, 14, 6, 14)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

     
    //  UTILITÁRIOS
     

    /**
     * Formata um tempo em segundos para a unidade mais legível.
     * Usa µs para valores abaixo de 1 ms, ms abaixo de 1 s, ou s.
     *
     * @param segundos tempo em segundos
     * @return string formatada com unidade
     */
    private String formatarTempo(double segundos) {
        if (segundos < 1e-3) {
            return String.format("%.4f µs", segundos * 1e6);
        } else if (segundos < 1.0) {
            return String.format("%.4f ms", segundos * 1e3);
        } else {
            return String.format("%.4f s", segundos);
        }
    }
}
