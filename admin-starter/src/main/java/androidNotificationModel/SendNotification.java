package androidNotificationModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class SendNotification {

	public void sendNotification(String titre,String msg) throws IOException {
	/*	// TODO Auto-generated method stub
		FcmClient client = new FcmClient();
		// You can get from firebase console.
		// "select your project>project settings>cloud messaging"
		client.setAPIKey("##############"); 

		// Data model for sending messages to specific entity(mobile devices,browser
		// front-end apps)s
		EntityMessage mssg = new EntityMessage();

		// Set registration token that can be retrieved
		// from Android entity(mobile devices,browser front-end apps) when calling
		// FirebaseInstanceId.getInstance().getToken();
		mssg.addRegistrationToken(
				"efpBIcl0fIo:APA91bG9dQIYkEwpG3mXs8QTdywROb121rDGqhdAbjEVHrXG8rERySZPf5RiF9gspZJC9jaiiNqF-2knjW79QXBJBtt7kcZJkQdO-IvDoph7QKm4PlovsZ6t39QKTV_nCxCHTsNPDC25");

		// Add key value pair into payload
		mssg.putStringData("myKey1", "myValue1");
		mssg.putStringData("myKey2", "myValue2");

		// push
		FcmResponse res = client.pushToEntities(mssg);

		System.out.println(res);*/
		
		

		String AUTH_KEY_FCM = "AIzaSyDGCEaBHCrGC58mhnJX1ftI-lEcA1oEUUw";
		String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
		String deviceToken = "cklZAHm__CM:APA91bE3uwfrXXdxdtOEJd1a_qnLMRWtsTheLhxqQLdKmlZ21Oq1sGF1ZISHFWRGn3zg25xMjpp0WjlD0Fgj-cKQOMEwGnw2TTCc0WIC0cyYSUhlNAETBj8wTK6J-dfzSjC5ICLa-C9I";

        URL url = new URL(API_URL_FCM);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
 
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
 
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
        conn.setRequestProperty("Content-Type", "application/json");
 
        JSONObject json = new JSONObject();
 
        json.put("to", deviceToken.trim());
        JSONObject info = new JSONObject();
        info.put("title", titre); // Notification title
        info.put("body", msg); // Notification body
        json.put("notification", info);
        try {
            OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
 
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
           // result = CommonConstants.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
          //  result = CommonConstants.FAILURE;
        }
        System.out.println("GCM Notification is sent successfully");
 
      
	}

}
