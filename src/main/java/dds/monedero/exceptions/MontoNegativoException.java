package dds.monedero.exceptions;

public class MontoNegativoException extends RuntimeException {
  private MontoNegativoException(String message) {
    super(message);
  }

  public static MontoNegativoException elMontoNoPuedeSerNegativoOMenorQueCero(double cuanto) {
    return new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
  }

}