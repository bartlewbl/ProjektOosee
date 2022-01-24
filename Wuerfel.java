import java.lang.Math;

public class Wuerfel{
	/**
	 * Mögliche Anwendung für Static: 
	 * Man könnnte hier zwei konstante Variablen machen, um einen W6 und W20 zu definieren:
	 */
	public static final Wuerfel W6 = new Wuerfel(6);
	/**
	 * Mögliche Anwendung für Static: 
	 * Man könnnte hier zwei konstante Variablen machen, um einen W6 und W20 zu definieren:
	 */
	public static final Wuerfel W20 = new Wuerfel(20);
	
	
	private int _seiten;

	public Wuerfel(int seiten){
		if(seiten< 2){
			throw new RuntimeException("Ein Würfel muss mind 2 Seiten (=Münze) haben");
		}
		/*Moeglich, wenn wir nur W6 und W20 zulassen möchten. Jedoch gibt es dazu wenig Grund 
		if(seiten!=6 && seiten!=20){
			throw new RuntimeException("Bitte einen gueltigen Wuerfel mit 6 oder 20 Seiten eingeben");
		}
		*/
		
		_seiten=seiten;
	}
		
	public int wuerfle(){
		int rnd=(int)(Math.random()*_seiten)+1;
		return rnd;
	} 
	
	private static void testWuerfel(int seiten){
		//IDEE: Wir würfeln 10*seiten und hoffen dann dass alle Zahlen mind. ein Mal gefallen sind!
		
		Wuerfel w=new Wuerfel(seiten);
		boolean [] zahlGefallen=new boolean [seiten];
		for(int i=0; i<zahlGefallen.length; i++){
			zahlGefallen[i]=false;
		}
		
		for (int i=0; i<=10*seiten; i++){
			int erg=w.wuerfle();
			zahlGefallen[erg-1]=true;
		}
		
		System.out.print("Ergebnisse Test W"+seiten+":");
		String s="";
		for(int i=0; i<zahlGefallen.length; i++){
			if(zahlGefallen[i]==false){
				if(s.length()>0){
					s+=", ";
				}
				s+=i;
			}
		}
		if(s.length()==0){
			System.out.println(" OK");
		}
		else {
			System.out.println(s+" sind nicht gekommen");
		}
		
	}
	
	
	public static void main (String[]args){
		Wuerfel w6 = new Wuerfel(6);
		Wuerfel w20 = new Wuerfel(20);
		//Wuerfel W22 = new Wuerfel(22);
		
		for(int i =0;i<=4;i++){   //wuerfeln wir 4mal mit beiden Wuerfeln
			System.out.println("Wurf mit W6: "+ w6.wuerfle());
			System.out.println("Wurf mit W20: "+ w20.wuerfle());
			System.out.println();
		}
		
		//Oder besser:
		testWuerfel(6);
		testWuerfel(20);
		
	}
}