package br.com.alura.screenmatch.menu.command;

public abstract class Command {
  protected String operationId;
  protected String description;

  protected Command(String operationId, Class<?> clazz) {
    this.operationId = operationId;
    this.description = getDescription(clazz);
  }

  private String getDescription(Class<?> clazz) {
    Nome nome = clazz.getAnnotation(Nome.class);

    return nome != null ? nome.value() : formatDescription(clazz.getSimpleName());
  }

  private String formatDescription(String description) {
    return description.replaceAll("(?<!^)([A-Z])", " $1");
  }

  public abstract void executar();

  @Override
  public String toString() {
    return this.operationId + " - " + this.description;
  }

  public String getOperationId() {
    return operationId;
  }
}
