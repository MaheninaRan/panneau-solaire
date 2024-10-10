package panneau.model;

import java.sql.Time;

public class DetailConsommation {
    private Time debut;
    private Time fin;
    private double batterie;
    private double puissance_panneau;
    private double solaire;
    private double consommation;
    private int nbr_eleve;

    public DetailConsommation() {}

    public DetailConsommation(Time debut, Time fin, double batterie, double puissance_panneau, double solaire, double consommation, int nbr_eleve) {
        this.setDebut(debut);
        this.setFin(fin);
        this.setBatterie(batterie);
        this.setPuissance_panneau(puissance_panneau);
        this.setSolaire(solaire);
        this.setConsommation(consommation);
        this.setNbr_eleve(nbr_eleve);
    }

    public Time getDebut() {
        return debut;
    }

    public void setDebut(Time debut) {
        this.debut = debut;
    }

    public Time getFin() {
        return fin;
    }

    public void setFin(Time fin) {
        this.fin = fin;
    }

    public double getBatterie() {
        return batterie;
    }

    public void setBatterie(double batterie) {
        this.batterie = batterie;
    }

    public double getPuissance_panneau() {
        return puissance_panneau;
    }

    public void setPuissance_panneau(double puissance_panneau) {
        this.puissance_panneau = puissance_panneau;
    }

    public double getSolaire() {
        return solaire;
    }

    public void setSolaire(double solaire) {
        this.solaire = solaire;
    }

    public double getConsommation() {
        return consommation;
    }

    public void setConsommation(double consommation) {
        this.consommation = consommation;
    }

    public int getNbr_eleve() {
        return nbr_eleve;
    }

    public void setNbr_eleve(int nbr_eleve) {
        this.nbr_eleve = nbr_eleve;
    }
}
