package entidade;

/**
 * Classe responsável pela validação dos dados de entrada do usuário.
 * Todos os métodos retornam null para entradas válidas,
 * ou uma mensagem de erro descritiva para entradas inválidas.
 */
public class Validador {

    /**
     * Verifica se uma string pode ser interpretada como um número double válido.
     * Aceita vírgula como separador decimal.
     *
     * @param texto string a ser verificada
     * @return true se for numérico, false caso contrário
     */
    public static boolean isNumerico(String texto) {
        if (texto == null || texto.trim().isEmpty()) return false;
        try {
            Double.parseDouble(normalizar(texto));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Converte uma string numérica para double,
     * normalizando vírgula para ponto antes da conversão.
     *
     * @param texto string numérica (pode conter vírgula)
     * @return valor double correspondente
     */
    public static double parseDouble(String texto) {
        return Double.parseDouble(normalizar(texto));
    }

    /**
     * Remove espaços extras e substitui vírgula por ponto.
     *
     * @param texto string a ser normalizada
     * @return string normalizada
     */
    private static String normalizar(String texto) {
        return texto.trim().replace(",", ".");
    }

    /**
     * Valida o campo de resistência R.
     * Restrições: deve ser numérico e maior que zero (R > 0).
     *
     * @param texto valor inserido pelo usuário
     * @return mensagem de erro, ou null se válido
     */
    public static String validarResistencia(String texto) {
        if (!isNumerico(texto))       return "Resistência (R) deve ser um número válido.";
        if (parseDouble(texto) <= 0)  return "Resistência (R) deve ser maior que zero.";
        return null;
    }

    /**
     * Valida o campo de capacitância C.
     * Restrições: deve ser numérico e maior que zero (C > 0).
     *
     * @param texto valor inserido pelo usuário
     * @return mensagem de erro, ou null se válido
     */
    public static String validarCapacitancia(String texto) {
        if (!isNumerico(texto))       return "Capacitância (C) deve ser um número válido.";
        if (parseDouble(texto) <= 0)  return "Capacitância (C) deve ser maior que zero.";
        return null;
    }

    /**
     * Valida o campo de tensão da fonte ε.
     * Restrições: deve ser numérico e maior que zero (ε > 0).
     *
     * @param texto valor inserido pelo usuário
     * @return mensagem de erro, ou null se válido
     */
    public static String validarTensao(String texto) {
        if (!isNumerico(texto))       return "Tensão da fonte (ε) deve ser um número válido.";
        if (parseDouble(texto) <= 0)  return "Tensão da fonte (ε) deve ser maior que zero.";
        return null;
    }

    /**
     * Valida o campo de tempo personalizado t (campo opcional).
     * Restrições: se preenchido, deve ser numérico e maior ou igual a zero (t ≥ 0).
     *
     * @param texto valor inserido pelo usuário (pode estar vazio)
     * @return mensagem de erro, ou null se válido ou vazio
     */
    public static String validarTempo(String texto) {
        if (texto == null || texto.trim().isEmpty()) return null; // campo opcional
        if (!isNumerico(texto))       return "Tempo (t) deve ser um número válido.";
        if (parseDouble(texto) < 0)   return "Tempo (t) deve ser maior ou igual a zero.";
        return null;
    }
}
