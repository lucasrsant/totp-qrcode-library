package br.edu.fei.serverAuthLibrary;

public class CreateAuthenticationSessionUseCase {
    private final Repository<String, AuthenticationSession> authenticationSessionRepository;

    public CreateAuthenticationSessionUseCase(Repository<String, AuthenticationSession> repository) {
        this.authenticationSessionRepository = repository;
    }

    public byte[] execute(int duration) {
        AuthenticationSession authenticationSession = new AuthenticationSession(duration);
        authenticationSessionRepository.insertOrUpdate(authenticationSession.getId(), authenticationSession);
        return QRCodeGenerator.generate(authenticationSession.getId());
    }
}
