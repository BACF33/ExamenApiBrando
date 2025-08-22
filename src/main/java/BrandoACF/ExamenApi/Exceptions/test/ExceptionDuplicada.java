package BrandoACF.ExamenApi.Exceptions.test;

//Excepcion destinada a ser llamada para mostrar un mensaje en caso de haber un campo duplicado, utiliza un getter para mostrar el dato duplicado en si en el mensaje

import lombok.Getter;
@Getter
public class ExceptionDuplicada extends RuntimeException {

  private String columnDuplicate;
  public ExceptionDuplicada(String message) {

    super(message);
  }

  public ExceptionDuplicada(String message, String columnDuplicate) {

    super(message);
    this.columnDuplicate = columnDuplicate;
  }
}