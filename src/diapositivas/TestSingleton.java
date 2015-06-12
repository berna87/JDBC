package diapositivas;

public class TestSingleton {

	public static void main(String[] args) {
		// creamos objetos Singleton
		Singleton s = Singleton.getObjetoUnico();
		Singleton s2 = Singleton.getObjetoUnico();
		
		System.out.println(s.equals(s2)); //ambos apuntan a la misma posicion de memoria, solo hay un objeto en realidad
	}

}


class Singleton{
	private static Singleton OBJETOUNICO = new Singleton();
	
	private Singleton (){
	}
	
	public static Singleton getObjetoUnico(){
		return OBJETOUNICO;
	}
	
}