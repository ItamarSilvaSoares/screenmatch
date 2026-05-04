package br.com.alura.screenmatch.menu.command;

public class Sair extends Command {

  public Sair() {
    super(OperationId.EXIT.getOperationId(), Sair.class);
  }

  @Override
  public void executar() {
    System.out.println("Saindo....");

  }
}
