package br.edu.fei.server;

import br.edu.fei.auth.qrcode.QRCodeGenerator;

/*package*/ class CreateAuthenticationSessionUseCase {
    private final Repository<String, AuthenticationSession> authenticationSessionRepository;

    /*package*/ CreateAuthenticationSessionUseCase(Repository<String, AuthenticationSession> repository) {
        this.authenticationSessionRepository = repository;
    }

    /*package*/ byte[] execute() {
        AuthenticationSession authenticationSession = new AuthenticationSession();
        authenticationSessionRepository.insertOrUpdate(authenticationSession.getId(), authenticationSession);
        return QRCodeGenerator.generate(authenticationSession.getId());
    }
}
