package br.com.alura.screenmatch.menu.command;

@Nome("Listar Séries Buscadas")
public class ListarSeriesBuscadas extends Command{

  public ListarSeriesBuscadas() {
    super(OperationId.LIST_SERIE.getOperationId(), ListarSeriesBuscadas.class);
  }

  @Override
  public void executar() {

  }
}
