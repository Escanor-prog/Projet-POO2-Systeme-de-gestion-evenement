package Tp_Poo2_final_v2.Services;

public class NotificationServiceImpl implements NotificationService {
    @Override
    public void envoyerNotification(String message) {
        System.out.println("📣 Notification : " + message);
    }

}
