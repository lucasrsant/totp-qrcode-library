package br.edu.fei.serverAuthLibrary;

public class ConfirmValidationCodeUseCase {

    private Repository<String, IdentifiedDeviceRegistrationPayload> repository;

    public ConfirmValidationCodeUseCase(Repository<String, IdentifiedDeviceRegistrationPayload> repository) {
        this.repository = repository;
    }

    public String execute(ConfirmValidationCodeRequest request) {
        if(repository.contains(request.deviceId)) {
            IdentifiedDeviceRegistrationPayload registrationRequest = repository.get(request.deviceId);
            if(isValidConfirmation(request, registrationRequest)) {
                registrationRequest.setIsVerified();
                return "valid";
            }
        }

        return "invalid";
    }

    private boolean isValidConfirmation(ConfirmValidationCodeRequest request, IdentifiedDeviceRegistrationPayload identifiedRequest) {
        return request.emailAddress.equals(identifiedRequest.email)
                && request.verificationCode.equals(identifiedRequest.verificationCode)
                && request.deviceId.equals(identifiedRequest.deviceId);
    }
}
