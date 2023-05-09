package dds.monedero.exceptions;

public class SaldoMenorException extends RuntimeException {
  private SaldoMenorException(String message) {
    super(message);
  }

  public static SaldoMenorException saldoInsuficiente(double saldo) {
    return new SaldoMenorException("No puede sacar mas de " + saldo + " $");
  }

}