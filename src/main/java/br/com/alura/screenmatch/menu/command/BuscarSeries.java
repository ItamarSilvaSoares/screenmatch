package br.com.alura.screenmatch.menu.command;

@Nome("Buscar Séries")
public class BuscarSeries extends Command {

  public BuscarSeries() {
    super(OperationId.SEARCH_SERIE.getOperationId(), BuscarSeries.class);
  }

  @Override
  public void executar() {

  }
}
