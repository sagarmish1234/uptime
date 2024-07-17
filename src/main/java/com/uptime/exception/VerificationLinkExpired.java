package com.uptime.exception;

public class VerificationLinkExpired extends RuntimeException{

    public VerificationLinkExpired(){
        super("Verification link has expired");
    }

}
