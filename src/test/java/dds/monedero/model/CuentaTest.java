package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadMovimientosDiariosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoOCeroException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

  private Cuenta cuenta;

  @BeforeEach
  public void setUp() {
    cuenta = new Cuenta();
    cuenta.agregarSaldo(900);
  }

  @Test
  public void sacarSaldoDebeFallarSiLePidoMasQueMiSaldo() {
    assertThrows(SaldoMenorException.class, () -> cuenta.sacarSaldo(1000));
  }

  @Test
  public void sacarSaldoDebePermitirmeSiLePidoUnMontoDentroDeMiSaldo() {
    cuenta.sacarSaldo(800);
    assertEquals(100, cuenta.miSaldo());
  }

  @Test
  public void sacarSaldoDebeFallarSiLePidoSacarCero() {
    assertThrows(MontoNegativoOCeroException.class, () -> cuenta.sacarSaldo(0));
  }

  @Test
  public void sacarSaldoDebeFallarSiLePidoSacarUnMontoNegativo() {
    assertThrows(MontoNegativoOCeroException.class, () -> cuenta.sacarSaldo(-1));
  }

  @Test
  public void sacarSaldoDebeFallarSiLePidoMasQueLaExtraccionPosibleDiaria() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> cuenta.sacarSaldo(1001));
  }

  @Test
  public void sacarSaldoDebeFallarSiExcedoElLimiteDeMovimientosDiarios() {
    cuenta.sacarSaldo(700);
    cuenta.sacarSaldo(100);
    assertThrows(MaximaCantidadMovimientosDiariosException.class, () -> cuenta.sacarSaldo(100));
  }

  @Test
  public void sacarSaldoDebeDejarmeSumarUnMovimientoAlPasarLasValidaciones() {
    cuenta.sacarSaldo(800);
    cuenta.sacarSaldo(100);
    assertEquals(3, cuenta.getMovimientos().size());
  }

  @Test
  public void agregarSaldoDebePermitirmeagregarSiNoSupero() {
    cuenta.agregarSaldo(800);
    assertEquals(1700, cuenta.miSaldo());
  }

  @Test
  public void agregarSaldoDebeFallarSiLePidoAgregarCero() {
    assertThrows(MontoNegativoOCeroException.class, () -> cuenta.agregarSaldo(0));
  }

  @Test
  public void agregarSaldoDebeFallarSiLePidoAgregarUnMontoNegativo() {
    assertThrows(MontoNegativoOCeroException.class, () -> cuenta.agregarSaldo(-1));
  }

  @Test
  public void agregarSaldoDebeDejarmeSumarUnMovimientoAlPasarLasValidaciones() {
    cuenta.agregarSaldo(800);
    cuenta.agregarSaldo(100);
    assertEquals(3, cuenta.getMovimientos().size());
  }

}