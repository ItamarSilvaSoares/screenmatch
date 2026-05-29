package br.com.alura.screenmatch.menu.command;

import org.springframework.stereotype.Component;

@Component
public class Exit extends Command {

  public Exit() {
    super(null, OperationId.EXIT.getOperationId(), OperationId.EXIT.getDescription());
  }

  @Override
  public void executar() {
    System.out.println("Saindo....");
  }
}
