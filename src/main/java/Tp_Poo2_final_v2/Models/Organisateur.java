package Tp_Poo2_final_v2.Models;

import java.util.ArrayList;
import java.util.List;

public class Organisateur extends Participant {

        private List<Evenement> evenementsOrganises;

        public Organisateur(String id, String nom, String email) {
            super(id, nom, email);
            this.evenementsOrganises = new ArrayList<>();
        }

        public void ajouterEvenementOrganise(Evenement evenement) {
            this.evenementsOrganises.add(evenement);
        }

        public List<Evenement> getEvenementsOrganises() {
            return evenementsOrganises;
        }
    }


