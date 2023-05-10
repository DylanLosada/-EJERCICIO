package dds.monedero.model;

import dds.monedero.enums.TipoMovimineto;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovimientoTest {

  @Test
  void esDepositoDevuelveVerdaderoSiEsUnDeposito() {
    Movimiento movimiento = new Movimiento(LocalDate.now(), 1000, TipoMovimineto.DEPOSITO);
    assertTrue(movimiento.esDeposito());
  }

  @Test
  void esDepositoDevuelveFalsoSiEsUnaExtraccion() {
    Movimiento movimiento = new Movimiento(LocalDate.now(), 1000, TipoMovimineto.EXTRACCION);
    assertFalse(movimiento.esDeposito());
  }

}