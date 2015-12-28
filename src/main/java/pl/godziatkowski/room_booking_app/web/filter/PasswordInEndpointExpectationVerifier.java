package pl.godziatkowski.room_booking_app.web.filter;

import java.util.HashSet;
import java.util.Set;

/**
Useful util to verify that called endpoint URL matches one of the listyed endpoints which contains password.
*/
class PasswordInEndpointExpectationVerifier {

   private final Set<String> endpointsWithPassword = new HashSet<>();

   PasswordInEndpointExpectationVerifier(String endpoints) {
      if (endpoints != null) {
         String[] splitted = endpoints.split("\n");
         for (String string : splitted) {
            string = string.trim();
            if (!string.isEmpty()) {
               endpointsWithPassword.add(string);
            }
         }
      }
   }

   boolean isEndpointWithPasswordInContent(BufferedRequestWrapper request) {
      String path = request.getRequestURI().substring(request.getContextPath().length());
      return endpointsWithPassword.contains(path);
   }
}
