package br.edu.fei.server;

import br.edu.fei.server.requests.ConfirmValidationCodeRequest;

public class ConfirmValidationCodeUseCase {

    private Repository<String, IdentifiedUserRegistrationRequest> repository;

    public ConfirmValidationCodeUseCase(Repository<String, IdentifiedUserRegistrationRequest> repository) {
        this.repository = repository;
    }

    public String execute(ConfirmValidationCodeRequest request) {
        if(repository.contains(request.email)) {
            IdentifiedUserRegistrationRequest registrationRequest = repository.get(request.email);

            return isValidConfirmation(request, registrationRequest) ? "valid" : "invalid";
        }

        return "invalid";
    }

    private boolean isValidConfirmation(ConfirmValidationCodeRequest request, IdentifiedUserRegistrationRequest identifiedRequest) {
        return request.email.equals(identifiedRequest.email)
                && request.verificationCode.equals(identifiedRequest.verificationCode)
                && request.deviceId.equals(identifiedRequest.deviceId);
    }
}
