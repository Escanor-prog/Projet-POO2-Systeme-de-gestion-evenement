package Tp_Poo2_final_v2.Observers;

public interface EvenementObservable {
    void  AjouterObserver(ParticipantObserver participantObserver);
    void  supprimerObserver(ParticipantObserver participantObserver);
    void  notifierObserver(String message);
    void annuler();
}
