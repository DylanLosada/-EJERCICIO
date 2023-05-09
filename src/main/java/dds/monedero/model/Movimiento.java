package dds.monedero.model;

import dds.monedero.enums.TipoMovimineto;
import java.time.LocalDate;

public class Movimiento {
  // Todos los atributos deberian ser final.
  private final LocalDate fecha;
  // Nota: En ningún lenguaje de programación usen jamás doubles (es decir, números con punto flotante) para modelar dinero en el mundo real.
  // En su lugar siempre usen numeros de precision arbitraria o punto fijo, como BigDecimal en Java y similares
  // De todas formas, NO es necesario modificar ésto como parte de este ejercicio. 
  private final double monto;
  private final TipoMovimineto tipoMovimineto;

  public Movimiento(LocalDate fecha, double monto, TipoMovimineto tipoMovimineto) {
    this.fecha = fecha;
    this.monto = monto;
    this.tipoMovimineto = tipoMovimineto;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public TipoMovimineto getTipoMovimineto() {
    return tipoMovimineto;
  }

  public boolean esDeposito() {
    return TipoMovimineto.DEPOSITO.equals(getTipoMovimineto());
  }

}
