package br.edu.fei.server;

import br.edu.fei.server.payloads.ConfirmValidationCodeRequest;

public class ConfirmValidationCodeUseCase {

    private Repository<String, IdentifiedUserRegistration> repository;

    public ConfirmValidationCodeUseCase(Repository<String, IdentifiedUserRegistration> repository) {
        this.repository = repository;
    }

    public String execute(ConfirmValidationCodeRequest request) {
        if(repository.contains(request.deviceId)) {
            IdentifiedUserRegistration registrationRequest = repository.get(request.deviceId);
            return isValidConfirmation(request, registrationRequest) ? "valid" : "invalid";
        }

        return "invalid";
    }

    private boolean isValidConfirmation(ConfirmValidationCodeRequest request, IdentifiedUserRegistration identifiedRequest) {
        return request.emailAddress.equals(identifiedRequest.email)
                && request.verificationCode.equals(identifiedRequest.verificationCode)
                && request.deviceId.equals(identifiedRequest.deviceId);
    }
}
