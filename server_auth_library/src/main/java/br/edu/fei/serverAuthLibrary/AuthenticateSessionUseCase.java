package br.edu.fei.serverAuthLibrary;

public class AuthenticateSessionUseCase {

    private final Repository<String, AuthenticationSession> authenticationSessionRepository;
    private final Repository<String, IdentifiedDeviceRegistrationPayload> userRepository;

    public AuthenticateSessionUseCase(Repository<String, AuthenticationSession> authenticationSessionRepository,
                                           Repository<String, IdentifiedDeviceRegistrationPayload> userRepository) {
        this.authenticationSessionRepository = authenticationSessionRepository;
        this.userRepository = userRepository;
    }

    public String execute(AuthenticateSessionPayload payload) {
        if(authenticationSessionRepository.contains(payload.sessionId) == false) {
            System.out.println("AUTHENTICATION FAILED");
            return "not_authenticated";
        }

        if(userRepository.contains(payload.deviceId) == false) {
            System.out.println("AUTHENTICATION FAILED");
            return "not_authenticated";
        }

        AuthenticationSession session = authenticationSessionRepository.get(payload.sessionId);
        IdentifiedDeviceRegistrationPayload user = userRepository.get(payload.deviceId);

        String oneTimePassword = TOTPGenerator.generate(new TOTP(8, 300, session.getHashKey(), HashingAlgorithm.SHA_256));

        if(oneTimePassword.equals(session.getOneTimePassword()) && user.isVerified()) {
            authenticationSessionRepository.remove(payload.sessionId);
            System.out.println("AUTHENTICATED AS " + user.email);
            return "authenticated as " + user.email;
        }

        System.out.println("AUTHENTICATION FAILED");
        return "not_authenticated";
    }
}
