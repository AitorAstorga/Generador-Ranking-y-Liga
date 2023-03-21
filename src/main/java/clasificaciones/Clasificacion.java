package clasificaciones;

public enum Clasificacion {
	TODAS("Ranking ABS M", true, new String[]{"Supercopa", "1LA", "2LA", "3LA", "Cto. �lava", "Cto. �lava JUN"}),
	R_ABS_M("Ranking ABS M", true, new String[]{"Supercopa", "1LA", "2LA", "3LA", "Cto. �lava"}),
	R_ABS_F("Ranking ABS F", false, new String[]{"Supercopa", "1LA", "2LA", "3LA", "Cto. �lava"}),
	R_JUN_M("Ranking JUN M", true, new String[]{"1LA", "2LA", "3LA", "Cto. �lava JUN"}),
	R_JUN_F("Ranking JUN F", false, new String[]{"1LA", "2LA", "3LA", "Cto. �lava JUN"}),
	LIGA_M("Liga M", true, new String[]{"1LA", "2LA", "3LA", "Cto. �lava"}),
	LIGA_F("Liga F", false, new String[]{"1LA", "2LA", "3LA", "Cto. �lava"});
	
    private final String denominacion;
    private final boolean masculino;
    private final String competiciones[];

	Clasificacion(final String denominacion, final boolean masculino, final String competiciones[]) {
		this.denominacion = denominacion;
		this.masculino = masculino;
		this.competiciones = competiciones;
	}
	
	public String[] getCompeticiones() {
		return this.competiciones;
	}
	
	public boolean isMasculino() {
		return this.masculino;
	}
	
	public boolean contains(String competicion) {
		for(String com :this.competiciones) {
			if(com.equals(competicion)) return true;
		}
		return false;
	}
	
	@Override
    public String toString() {
        return denominacion;
    }
	
}
