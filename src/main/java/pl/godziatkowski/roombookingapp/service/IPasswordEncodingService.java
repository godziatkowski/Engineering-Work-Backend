package pl.godziatkowski.roombookingapp.service;

public interface IPasswordEncodingService {

   String encode(String rawPassword);

   boolean isMatch(String passwordToCheck, String encodedPassword);

}
