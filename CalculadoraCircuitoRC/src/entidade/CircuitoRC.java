package entidade;

/**
  Classe responsável pelos cálculos físicos do circuito RC em carregamento.
 
 * Contexto: chave fechada em t = 0, capacitor inicialmente descarregado,
 * conectado em série com um resistor e uma fonte de tensão.
 *
 * Equações base:
 *   V_C(t) = ε · (1 − e^(−t/τ))
 *   V_R(t) = ε · e^(−t/τ)
 *   i(t)   = (ε / R) · e^(−t/τ)
 *   τ      = R · C
 */
public class CircuitoRC {

    private final double resistencia;    // R em Ohms
    private final double capacitancia;   // C em Farads (convertido internamente de µF)
    private final double tensaoBateria;  // ε em Volts

    /**
     * Construtor da classe CircuitoRC.
     *
     * @param R       Resistência em Ohms (R > 0)
     * @param C_uF    Capacitância em microfarads (C > 0); convertida para Farads internamente
     * @param epsilon Tensão da fonte de alimentação em Volts (ε > 0)
     */
    public CircuitoRC(double R, double C_uF, double epsilon) {
        this.resistencia   = R;
        this.capacitancia  = C_uF * 1e-6; // converte µF → F
        this.tensaoBateria = epsilon;
    }

    /**
     * Calcula a constante de tempo do circuito.
     * τ = R × C
     *
     * @return τ em segundos
     */
    public double calcularTau() {
        return resistencia * capacitancia;
    }

    /**
     * Calcula o instante em que a tensão no capacitor iguala a do resistor.
     *
     * Derivação:
     *   ε(1 − e^(−t/τ)) = ε · e^(−t/τ)
     *   1 = 2 · e^(−t/τ)
     *   e^(−t/τ) = 1/2
     *   t = τ · ln(2)
     *
     * @return tempo t em segundos
     */
    public double calcularTempoEquilibrio() {
        return calcularTau() * Math.log(2);
    }

    /**
     * Calcula a tensão no capacitor no instante t.
     * V_C(t) = ε · (1 − e^(−t/τ))
     *
     * @param t tempo em segundos (t ≥ 0)
     * @return V_C em Volts
     */
    public double calcularTensaoCapacitor(double t) {
        return tensaoBateria * (1.0 - Math.exp(-t / calcularTau()));
    }

    /**
     * Calcula a tensão no resistor no instante t.
     * V_R(t) = ε · e^(−t/τ)
     *
     * @param t tempo em segundos (t ≥ 0)
     * @return V_R em Volts
     */
    public double calcularTensaoResistor(double t) {
        return tensaoBateria * Math.exp(-t / calcularTau());
    }

    /**
     * Calcula a corrente no circuito no instante t.
     * i(t) = (ε / R) · e^(−t/τ)
     *
     * @param t tempo em segundos (t ≥ 0)
     * @return corrente em Amperes
     */
    public double calcularCorrente(double t) {
        return (tensaoBateria / resistencia) * Math.exp(-t / calcularTau());
    }

    /**
     * Calcula a corrente máxima no instante inicial (t = 0).
     * i₀ = ε / R
     *
     * @return corrente máxima em Amperes
     */
    public double calcularCorrenteMaxima() {
        return tensaoBateria / resistencia;
    }

    /**
     * Calcula a carga armazenada no capacitor no instante t.
     * q(t) = C · V_C(t)
     *
     * @param t tempo em segundos (t ≥ 0)
     * @return carga em Coulombs
     */
    public double calcularCarga(double t) {
        return capacitancia * calcularTensaoCapacitor(t);
    }

    /**
     * Calcula a porcentagem de carga do capacitor no instante t.
     * % = (V_C(t) / ε) × 100
     *
     * @param t tempo em segundos (t ≥ 0)
     * @return porcentagem de 0 a 100
     */
    public double calcularPorcentagemCarga(double t) {
        return (calcularTensaoCapacitor(t) / tensaoBateria) * 100.0;
    }
}
