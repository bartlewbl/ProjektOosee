public class Verein {


    public String _nameTeam;
    public int _anzahlSpiele;
    public int _teamPunkte=0;
    public int _anzahlTore = 0;
    public int _anzahlGegentore = 0;
    public int _torDifferenz;
    public int i = 0;

    private int _sturm;
	private int _mi;
	private int _vert;
	private int _torw;


    public Verein (String nameTeam){
        _nameTeam = nameTeam;

        _sturm = 4 + Wuerfel.W6.wuerfle();
		_mi =   4 +  Wuerfel.W6.wuerfle();
		_vert = 14 + Wuerfel.W6.wuerfle();
		if (_vert>=20) _vert=19;
		_torw = 14 + Wuerfel.W6.wuerfle();
		if (_torw>=20) _torw=19;
    }


    public boolean angriff(){
		
		int wurf1 = Wuerfel.W20.wuerfle();    //2mal würfeln
		int wurf2 =  Wuerfel.W20.wuerfle();
				
		if (wurf1<=_mi && wurf2<= _sturm){      
			return true;
		}else{
			return false;
		}
	}
		

	public boolean verteidigt(){    //das gleiche machen wie davor+-
		//Wuerfel wVert = new Wuerfel(20);
		int wurf1 = Wuerfel.W20.wuerfle();
		int wurf2 = Wuerfel.W20.wuerfle();
		
		if(wurf1<=_mi && wurf2<=_vert){
			return true;
		}else{
			return false;
		}
	}
		

	public int schiesseTor(){
		return Wuerfel.W6.wuerfle();
	}
	

	public boolean kassiereTor(int tsStaerke){
		if(tsStaerke>=5){
			return true;
		}else if(tsStaerke>=1 && tsStaerke<=4){
			if(Wuerfel.W20.wuerfle() <= _torw){
				return false;
			}else{
				return true;
			}
		}
		return false;
	}
	
	
	//4 methoden um bei spielereignis 2 Punkte abzuziehen
	public void minusTorw(int x){
		_torw = _torw - x;
	}
	
	public void minusSturm(int x){
		_sturm = _sturm - x;
	}
	
	public void minusMi(int x){
		_mi = _mi - x;
	}	
	
	public void minusVert(int x){
		_vert = _vert - x;
	}



    public int getAnzahlSpiele (){
        return _anzahlSpiele;
    }
    public int getTeamPunkte (){
        return _teamPunkte;
    }
    public int getAnzahlTore(){
        return _anzahlTore;
    }
    public int getTorDifferenz(){
        return _torDifferenz;
    }
    public int getGegenTore(){
        return _anzahlGegentore;
    }
    public String getNameTeam(){
        return _nameTeam;
    }
    public int getSturm(){
        return _sturm;
    }
    public int getMi(){
        return _mi;
    }
    public int getVert(){
        return _vert;
    }
    public int getTorw(){
        return _torw;
    }

    
    public void setAnzahlSpiele (int anzahlSpiele){
        _anzahlSpiele = anzahlSpiele;
    }
    public void setTeamPunkte (int teamPunkte){
        _teamPunkte = teamPunkte;
    }
    public void setAnzahlTore(int anzahlTore){
        _anzahlTore = anzahlTore;
    }
    public void setTorDifferenz(int torDifferenz){
        _torDifferenz = torDifferenz;
    }
    public void setGegenTore(int gegenTore){
        _anzahlGegentore = gegenTore;
    }
    public void setNameTeam(String nameTeam){
        _nameTeam = nameTeam;
    }
    public void setAllesNull(){
        _anzahlSpiele = 0;
        _teamPunkte = 0;
        _anzahlTore = 0;
        _torDifferenz = 0;
        _anzahlGegentore = 0;
    }
    public void setSturm(int sturm){
        _sturm=sturm;
    }
    public void setMi (int mi){
        _mi = mi;
    }
    public void setVert (int vert){
        _vert = vert;
    }
    public void setTorw(int torw){
        _torw = torw;
    }

    
    public int compareTo(Verein ver){
        if (this.getTeamPunkte() > ver.getTeamPunkte()){                 // 1 = this  -1 = ver  0 = gleich
            return 1;
        }
        else if (this.getTeamPunkte() == ver.getTeamPunkte()){
            i = 1;
        }
        else if (this.getTeamPunkte() < ver.getTeamPunkte()){
            return -1;
        }

        if (i == 1){
            if (this.getTorDifferenz() > ver.getTorDifferenz()){
                return 1;
            }
            else if (this.getTorDifferenz() < ver.getTorDifferenz()) {
                return -1;
            }
            else {
                i = 2;
            }
        }
        if (i == 2){
            if (this.getAnzahlTore() > ver.getAnzahlTore()){
                return 1;
            }
            else if (this.getAnzahlTore() < ver.getAnzahlTore()){
                return -1;
            }
            else {
                return 0;            
            }

        }
        if(this.getNameTeam().compareTo(ver.getNameTeam())== 0){
            return -2;
        }
        else {
            return -3;
        }
        
        
    }
}
