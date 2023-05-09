package dds.monedero.model;

import dds.monedero.enums.TipoMovimineto;
import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  // TODO Duplicate Code, en verdad es innecesario dado que lo estas haciendo en el constructor.
  // por otro lado, deberia ser final, no cambio en ningun momento.
  private double saldo = 0;
  // Ya que estamos realizando la configuracion del objeto en el costructor, deberiamos realizarlo con este atributo tambien.
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }

  private final int CANTIDAD_MOVIMIENTOS_PERMITIDOS = 3;

  public void poner(double cuanto) {
    validarQueNoSeaMenorOIgualAZero(cuanto);
    validarQueNOExcedioLosMovimientosPermitidos();

    Movimiento nuevoMovimiento = new Movimiento(LocalDate.now(), cuanto, TipoMovimineto.DEPOSITO);
    agregarMovimiento(nuevoMovimiento);
  }

  // TODO Long Method
  public void sacar(double cuanto) {
    validarQueNoSeaMenorOIgualAZero(cuanto);
    validarQueNoExtraigaMasDeMiSaldoDisponible(cuanto);
    validarQueNoExtraigaMasDelMontoPermitidoPorDia(cuanto);

    Movimiento nuevoMovimiento = new Movimiento(LocalDate.now(), cuanto, TipoMovimineto.EXTRACCION);
    agregarMovimiento(nuevoMovimiento);
  }

  private void validarQueNoExtraigaMasDelMontoPermitidoPorDia(double cuanto) {
    final int MONTO_MAXIMO_EXTRAIBLE_DIARIO = 1000;
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = MONTO_MAXIMO_EXTRAIBLE_DIARIO - montoExtraidoHoy;

    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
  }

  // TODO Data Clumps (Esto deberia ser un movimineto) y Divergent Change (no es responsabilidad de Cuenta tener que instanciar un Movimiento)
  public void agregarMovimiento(Movimiento movientoAAgregar) {
    movimientos.add(movientoAAgregar);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> !movimiento.esDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  private void validarQueNOExcedioLosMovimientosPermitidos() {
    if (superoLosMovientosPermitidos()) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + CANTIDAD_MOVIMIENTOS_PERMITIDOS + " depositos diarios");
    }
  }

  private boolean superoLosMovientosPermitidos() {
    return getMovimientos().stream().filter(Movimiento::esDeposito).count() >= CANTIDAD_MOVIMIENTOS_PERMITIDOS;
  }

  private void validarQueNoSeaMenorOIgualAZero(double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  private void validarQueNoExtraigaMasDeMiSaldoDisponible(double cuanto) {
    if (getSaldo() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
  }

}
