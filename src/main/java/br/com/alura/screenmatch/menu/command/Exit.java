package br.com.alura.screenmatch.menu.command;

import org.springframework.stereotype.Component;

@Component
@Nome("Sair")
public class Exit extends Command {

  public Exit() {
    super(OperationId.EXIT.getOperationId(), Exit.class, null);
  }

  @Override
  public void executar() {
    System.out.println("Saindo....");
  }
}
