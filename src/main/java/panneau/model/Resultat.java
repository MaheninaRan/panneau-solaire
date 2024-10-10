package panneau.model;

import java.sql.Time;
import java.util.List;

public class Resultat {

    private double conso;
    private SourceEnergie sourceEnergie;
    private List<DetailConsommation> detailConsommations;
    private Time heure_coupure;

    public Resultat(double conso, SourceEnergie sourceEnergie, List<DetailConsommation> detailConsommations, Time heure_coupure) {
        this.setConso(conso);
        this.setSourceEnergie(sourceEnergie);
        this.setDetailConsommations(detailConsommations);
        this.setHeure_coupure(heure_coupure);
    }

    public Resultat() {}

    public double getConso() {
        return conso;
    }

    public void setConso(double conso) {
        this.conso = conso;
    }

    public SourceEnergie getSourceEnergie() {
        return sourceEnergie;
    }

    public void setSourceEnergie(SourceEnergie sourceEnergie) {
        this.sourceEnergie = sourceEnergie;
    }

    public List<DetailConsommation> getDetailConsommations() {
        return detailConsommations;
    }

    public void setDetailConsommations(List<DetailConsommation> detailConsommations) {
        this.detailConsommations = detailConsommations;
    }

    public Time getHeure_coupure() {
        return heure_coupure;
    }

    public void setHeure_coupure(Time heure_coupure) {
        this.heure_coupure = heure_coupure;
    }
}
