package dds.monedero.exceptions;

public class MaximaCantidadMovimientosDiariosException extends RuntimeException {

  private MaximaCantidadMovimientosDiariosException(String message) {
    super(message);
  }

  public static MaximaCantidadMovimientosDiariosException superoElLimiteDeDepositosDiarios(int cantidadMaxDepositos) {
    return new MaximaCantidadMovimientosDiariosException("Ya excedio los " + cantidadMaxDepositos + " depositos diarios");
  }

}