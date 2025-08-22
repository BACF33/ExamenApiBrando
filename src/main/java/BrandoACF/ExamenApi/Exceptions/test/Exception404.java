package BrandoACF.ExamenApi.Exceptions.test;

//Excepcion destinada a ser llamada para mostrar un mensaje en caso de no haber x (pero el resto funciona)
public class Exception404 extends RuntimeException {
  public Exception404(String message) {

    super(message);
  }
}