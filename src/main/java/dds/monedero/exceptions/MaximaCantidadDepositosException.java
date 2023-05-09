package dds.monedero.exceptions;

public class MaximaCantidadDepositosException extends RuntimeException {

  private MaximaCantidadDepositosException(String message) {
    super(message);
  }

  public static MaximaCantidadDepositosException superoElLimiteDeDepositosDiarios(int cantidadMaxDepositos) {
    return new MaximaCantidadDepositosException("Ya excedio los " + cantidadMaxDepositos + " depositos diarios");
  }

}