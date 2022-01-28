import java.nio.file.*;
import java.io.*;
import java.nio.file.Paths;


public class Tabelle {
    
    Verein [] _teamArray = new Verein[0];
    private int i = 0;
    private int _tore1=0;
	private int _tore2=0;
    private final String CSTR_TRENN_ZEICHEN= ";";

    IReporter _reporter;
    public Tabelle (IReporter rep){
        _reporter = rep;
    }

    public void addTeam(Verein ver){
        Verein[] _teamArrayCopy = new Verein [_teamArray.length + 1]; 

		for (int i = 0; i < _teamArray.length; i++) { 
			_teamArrayCopy[i] = _teamArray[i];
		}
				
		_teamArrayCopy[_teamArray.length] =  ver; 

		_teamArray = _teamArrayCopy;
    }
    public int findeTeam (Verein ver){
        for (int j=0; j <= _teamArray.length; j++){
            if (_teamArray[j].compareTo(ver) == 0){
                return j;
            }
        }
        return -1;
    }
    public void sortiereVereine (){
        boolean swapped;
        int length = _teamArray.length;
        
        do {
            swapped = false;
            
            for (int i = 1 ; i < length ; i++) {
                if (_teamArray[i-1].compareTo(_teamArray[i]) < 0) {
                    
                    Verein tmp = _teamArray[i-1];
                    _teamArray[i-1] = _teamArray[i];
                    _teamArray[i] = tmp;
                    swapped = true;
                }
            }
            
            length--;
        } while (swapped);
    }
    public boolean removeTeam (Verein ver){
        int index = findeTeam(ver); 
		
		if (index == -1) {
			return false; 
		}
		
		Verein[] _teamArrayCopy2 = new Verein[_teamArray.length - 1]; 
		int j = 0; 
				
		for (int i = 0; i < _teamArray.length; i++) {
			if (index == i) { 
				continue; 
			}
			
			_teamArrayCopy2[j] = _teamArray[i];  
			j++; 
		}
		
		_teamArray = _teamArrayCopy2; 
		return true; 
    }
    public void resetAlleTeams(){
        for (int i = 0; i < _teamArray.length; i++){
            _teamArray[i].setAllesNull();
        }
    }

    public Verein getVerein(int zahl){
        if (zahl < _teamArray.length){
            return _teamArray[zahl];
        }
        System.out.println("kein Verein in Array [zahl], deswegen übergebe ich einfach _teamArray[0]");
        return _teamArray[0];
    }

    public void starteSpiel(Tabelle a, Verein ver1, Verein ver2){
				
		for(int i=0; i<=90; i++){
			_reporter.giveNewMessage("\nSpielminute: "+i);
			starteSpielMin(i,ver1,ver2);

			try{
				Thread.sleep(50); // Erweiterung aus Aufgabe 5a -> wir verzögern damit das Spiel so dass eine Minute in 0,5 Sekunden vergeht.
			}catch(Exception e){
			}
			
		}
        if(_tore1==_tore2){
			_reporter.giveNewMessage("\nDas Spiel zwischen " +ver1.getNameTeam()+ " und " +ver2.getNameTeam()+" ist unentschieden ausgefallen mit: " + _tore1+":"+_tore2);
            ver1.setTeamPunkte(ver1.getTeamPunkte()+1);
            ver2.setTeamPunkte(ver2.getTeamPunkte()+1);
		}
		
		if(_tore1>_tore2){
			_reporter.giveNewMessage("\nSieg fuer: "+ ver1.getNameTeam()+" "+_tore1+":"+_tore2);
            ver1.setTeamPunkte(ver1.getTeamPunkte()+3);
		}
		if(_tore1<_tore2){
			_reporter.giveNewMessage("\nSieg fuer: "+ ver2.getNameTeam()+" "+_tore2+":"+_tore1);
            ver2.setTeamPunkte(ver2.getTeamPunkte()+3);
		}
        ver1.setAnzahlTore(ver1.getAnzahlTore()+_tore1);
        ver2.setAnzahlTore(ver2.getAnzahlTore()+_tore2);

        ver1.setGegenTore(ver1.getGegenTore()+_tore2);
        ver2.setGegenTore(ver2.getGegenTore()+_tore1);
        
        ver1.setAnzahlSpiele(ver1.getAnzahlSpiele()+1);
        ver2.setAnzahlSpiele(ver2.getAnzahlSpiele()+1);

        _tore1 = 0;
        _tore2 = 0;

        System.out.println(ver1.getAnzahlTore());
        System.out.println(ver2.getAnzahlTore());
    }
    public void starteSpielMin(int spielMin,Verein ver1,Verein ver2){
		if(ver1.angriff()){
			_reporter.giveNewMessage("AV               ");
			if(ver2.verteidigt()){
				_reporter.giveNewMessage("VT");
			}else{
				_reporter.giveNewMessage("!!!");
				int schuss=ver1.schiesseTor();
				
				if(ver2.kassiereTor(schuss)){
					_tore1+=1;
					_reporter.giveNewMessage("TOOOR fuer Mannschaft "+ ver1.getNameTeam()+ " in der "+ spielMin+"ten Spielminute!");	
					_reporter.giveNewMessage("Neuer Spielstand " + ver1.getNameTeam()+" " +_tore1 + ":" + _tore2 + " "+ ver2.getNameTeam());
				}
				else{
					_reporter.giveNewMessage("Gute Parade durch den Keeper von Mannschaft " + ver2.getNameTeam());
				}
			}
		}
		
		if(ver2.angriff()){
			//System.out.println("AV");
						
			if(ver1.verteidigt()){
				_reporter.giveNewMessage("VT               AV");
			}else{
				_reporter.giveNewMessage("!!!              AV");
				int schuss=ver2.schiesseTor();
				if(ver1.kassiereTor(schuss)){
					_tore2+=1;
					_reporter.giveNewMessage("TOOOR fuer Mannschaft "+ ver2.getNameTeam()+ " in der "+ spielMin+"ten Spielminute!");
					_reporter.giveNewMessage("Neuer Spielstand " + ver1.getNameTeam()+" "+ _tore1 + ":" + _tore2 + " "+ ver2.getNameTeam());
				}else{
					_reporter.giveNewMessage("Gute Parade durch den Keeper von Mannschaft " + ver1.getNameTeam());
				}
			}
		}
	}


    public File getFilePath() {
		Path path=Paths.get("C:","Temp","SaveFile.txt");
		return path.toFile();
	}

    public void speichernDatei (File file){
        if (file.exists()==false){
            System.out.println("Datei:"+file+"exisitiert nicht");
        }

        BufferedWriter bw = null;
        try {

            FileWriter fw = new FileWriter(file); 
		    bw = new BufferedWriter(fw); 
		    for(int i = 0; i < _teamArray.length; i++){
			    Verein ver = _teamArray[i];
			    bw.write(ver.getNameTeam() + CSTR_TRENN_ZEICHEN + ver.getAnzahlSpiele() + CSTR_TRENN_ZEICHEN + ver.getTeamPunkte() + CSTR_TRENN_ZEICHEN + ver.getAnzahlTore()
                 + CSTR_TRENN_ZEICHEN + ver.getGegenTore() + CSTR_TRENN_ZEICHEN + ver.getTorDifferenz() + CSTR_TRENN_ZEICHEN + ver.getSturm() + CSTR_TRENN_ZEICHEN + ver.getMi() 
                 + CSTR_TRENN_ZEICHEN + ver.getVert() + CSTR_TRENN_ZEICHEN + ver.getTorw());
			    bw.newLine(); // Neue Zeile einfügen
	        }
			bw.flush();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
        finally{
			try{
				if(bw != null){
					bw.close();
				} 
			} 
			catch(IOException ioex){
				ioex.printStackTrace(); //Bei diesem Fehler kann man wohl nicht viel machen, aber zumindest den Stacktrace und eine Fehlermeldung sollte man immer ausgeben -> bitte keine Fehler verschlucken!
			} 
		}
        

    }
    
    public void verarbeiteZeile(String strZeile){
        String [] strs = strZeile.split(CSTR_TRENN_ZEICHEN);

        int spAnzahlSpiele,spTeamPunkte,spAnzahlTore,spGegenTore,spTorDifferenz,spSturm,spMi,spVert,spTorw;
        try {
            spAnzahlSpiele = Integer.parseInt(strs[1].trim());
            spTeamPunkte = Integer.parseInt(strs[2].trim());
            spAnzahlTore = Integer.parseInt(strs[3].trim());
            spGegenTore = Integer.parseInt(strs[4].trim());
            spTorDifferenz = Integer.parseInt(strs[5].trim());
            spSturm = Integer.parseInt(strs[6].trim());
            spMi = Integer.parseInt(strs[7].trim());
            spVert = Integer.parseInt(strs[8].trim());
            spTorw = Integer.parseInt(strs[9].trim());
        }
        catch (Exception ex){
            throw new RuntimeException("Folgene Zeile kann nicht geparst werden");
        }
        Verein ver = new Verein(strs[0]);
        ver.setAnzahlSpiele(spAnzahlSpiele);
        ver.setTeamPunkte(spTeamPunkte);
        ver.setAnzahlTore(spAnzahlTore);
        ver.setGegenTore(spGegenTore);
        ver.setTorDifferenz(spTorDifferenz);
        ver.setSturm(spSturm);
        ver.setMi(spMi);
        ver.setVert(spVert);
        ver.setTorw(spTorw);
        addTeam(ver);
    }

    public void ladenDatei(File file){
        if(file.exists() == false) {
			//throw new IOException("Datei '" + file + "' existiert nicht!");
            System.out.println("nein");
		}
		
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			String strZeile = br.readLine();
			while(strZeile != null){
				 verarbeiteZeile(strZeile);
				 strZeile = br.readLine();
			}
		}
		catch (Exception ex) { 
			ex.printStackTrace(); 
		} 
		finally{
			try{  
				if(br != null){ 
					br.close(); 
				} 
			} 
			catch(IOException ioex){
                ioex.printStackTrace();
            }
        }
    }
    public Verein getTeam (int i){
        return _teamArray[i];
    }

    public void getTeamsToTabelle(){
        _reporter.resetTabelle();


        for(int n = 0; n<_teamArray.length; n++){
            String leerZeichen = " ";
            String leerZeichenZusammen = "";
            String zwischenSchritt;
            String c = _teamArray[n].getNameTeam();
            int lengthOfString = c.length();
            for (int i =lengthOfString; i < 42-lengthOfString; i++){
                zwischenSchritt = leerZeichenZusammen;
                leerZeichenZusammen = zwischenSchritt + leerZeichen;
            }


            _reporter.giveNewMessage(_teamArray[n].getNameTeam() + leerZeichenZusammen + _teamArray[n].getTeamPunkte() + "             " + _teamArray[n].getAnzahlTore() + "              " + _teamArray[n].getGegenTore() + "                         " + _teamArray[n].getAnzahlSpiele());
        }
    }

    public int getLengthArray(){
        return _teamArray.length;
    }
    public Verein[] getTeamArray (){
        return _teamArray;
    }




    public static void main (String [] args){
        
    }

}
