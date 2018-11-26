package br.edu.fei.serverAuthLibrary;

public class AuthenticateSessionUseCase {

    private final Repository<String, AuthenticationSession> authenticationSessionRepository;
    private final Repository<String, IdentifiedUserRegistration> userRepository;

    public AuthenticateSessionUseCase(Repository<String, AuthenticationSession> authenticationSessionRepository,
                                           Repository<String, IdentifiedUserRegistration> userRepository) {
        this.authenticationSessionRepository = authenticationSessionRepository;
        this.userRepository = userRepository;
    }

    public String execute(AuthenticateSessionPayload payload) {
        if(authenticationSessionRepository.contains(payload.sessionId) == false)
            return "not_authenticated";

        if(userRepository.contains(payload.deviceId) == false)
            return "not_authenticated";

        AuthenticationSession session = authenticationSessionRepository.get(payload.sessionId);
        IdentifiedUserRegistration user = userRepository.get(payload.deviceId);

        String oneTimePassword = TOTPGenerator.generate(new TOTP(8, 300, session.getHashKey(), HashingAlgorithm.SHA_256));

        if(oneTimePassword.equals(session.getOneTimePassword()) && user.isVerified()) {
            authenticationSessionRepository.remove(payload.sessionId);
            return "authenticated as " + user.email;
        }

        return "not_authenticated";
    }
}
