package br.com.alura.screenmatch.menu.command;

@Nome("Buscar Episódios")
public class BuscarEpisodios extends Command {

  public BuscarEpisodios() {
    super(OperationId.SEARCH_EPISODE.getOperationId(), BuscarEpisodios.class);
  }

  @Override
  public void executar() {

  }
}
