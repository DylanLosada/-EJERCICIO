package dds.monedero.exceptions;

public class MaximoExtraccionDiarioException extends RuntimeException {
  private MaximoExtraccionDiarioException(String message) {
    super(message);
  }

  public static MaximoExtraccionDiarioException superoElLimiteDeDineroExtraidoDiario(double cantidadMaxExtraccion, double limite) {
    return new MaximoExtraccionDiarioException("No puede extraer mas de $ " + cantidadMaxExtraccion
        + " diarios, límite: " + limite);
  }

}