package com.se1858.G5.LandAuction.Service;

public interface EmailService {
    void sendSimpleMail(String to, String subject, String text);
}