package exception;


public class FichierExisteDejaException extends Exception{
	
	public FichierExisteDejaException() {
		super();
	}
	
	public FichierExisteDejaException(String message) {
		super(message);
	}

}
