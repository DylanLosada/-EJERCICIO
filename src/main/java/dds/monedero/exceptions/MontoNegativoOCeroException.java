package dds.monedero.exceptions;

public class MontoNegativoOCeroException extends RuntimeException {
  private MontoNegativoOCeroException(String message) {
    super(message);
  }

  public static MontoNegativoOCeroException elMontoNoPuedeSerNegativoOMenorQueCero(double cuanto) {
    return new MontoNegativoOCeroException(cuanto + ": el monto a ingresar debe ser un valor positivo");
  }

}