package dds.monedero.model;

import dds.monedero.enums.TipoMovimineto;
import dds.monedero.exceptions.MaximaCantidadMovimientosDiariosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoOCeroException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
  private final int CANTIDAD_MOVIMIENTOS_PERMITIDOS = 3;
  private double saldo;
  // Ya que estamos realizando la configuracion del objeto en el costructor, deberiamos realizarlo con este atributo tambien.
  private final List<Movimiento> movimientos;

  public Cuenta() {
    movimientos = new ArrayList<>();
    saldo = 0;
  }

  public void agregarSaldo(double saldoASumar) {
    validarQueNOExcedioLosMovimientosPermitidos();
    validarQueNoSeaMenorOIgualACero(saldoASumar);

    Movimiento nuevoMovimiento = new Movimiento(LocalDate.now(), saldoASumar, TipoMovimineto.DEPOSITO);
    agregarMovimiento(nuevoMovimiento);
    sumarSaldo(saldoASumar);
  }

  public void sacarSaldo(double saldoASacar) {
    validarQueNOExcedioLosMovimientosPermitidos();
    validarQueNoSeaMenorOIgualACero(saldoASacar);
    validarQueNoExtraigaMasDelMontoPermitidoPorDia(saldoASacar);
    validarQueNoExtraigaMasDeMiSaldoDisponible(saldoASacar);

    Movimiento nuevoMovimiento = new Movimiento(LocalDate.now(), saldoASacar, TipoMovimineto.EXTRACCION);
    agregarMovimiento(nuevoMovimiento);
    restarSaldo(saldoASacar);
  }

  private void validarQueNoExtraigaMasDelMontoPermitidoPorDia(double cuanto) {
    final int MONTO_MAXIMO_EXTRAIBLE_DIARIO = 1000;
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = MONTO_MAXIMO_EXTRAIBLE_DIARIO - montoExtraidoHoy;

    if (cuanto > limite) {
      throw MaximoExtraccionDiarioException.superoElLimiteDeDineroExtraidoDiario(CANTIDAD_MOVIMIENTOS_PERMITIDOS, limite);
    }
  }

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

  public double miSaldo() {
    return saldo;
  }

  private void validarQueNOExcedioLosMovimientosPermitidos() {
    if (superoLosMovientosPermitidos()) {
      throw MaximaCantidadMovimientosDiariosException.superoElLimiteDeDepositosDiarios(CANTIDAD_MOVIMIENTOS_PERMITIDOS);
    }
  }

  private boolean superoLosMovientosPermitidos() {
    return getMovimientos().size() == CANTIDAD_MOVIMIENTOS_PERMITIDOS;
  }

  private void validarQueNoSeaMenorOIgualACero(double cuanto) {
    if (cuanto <= 0) {
      throw MontoNegativoOCeroException.elMontoNoPuedeSerNegativoOMenorQueCero(cuanto);
    }
  }

  private void validarQueNoExtraigaMasDeMiSaldoDisponible(double cuanto) {
    if (miSaldo() - cuanto < 0) {
      throw SaldoMenorException.saldoInsuficiente(miSaldo());
    }
  }

  private void sumarSaldo(Double saldoASumar) {
    saldo += saldoASumar;
  }

  private void restarSaldo(Double saldoARestar) {
    saldo -= saldoARestar;
  }

}
