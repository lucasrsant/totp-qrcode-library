package br.edu.fei.auth_library;

public class AuthenticationLibraryConfiguration {
    private String authServerHost;
    private String serverPublicKeyEndpoint;
    private String serverRegisterDeviceEndpoint;
    private String serverConfirmVerificationCodeEndpoint;
    private String serverAuthenticateSessionEndpoint;

    private AuthenticationLibraryConfiguration() {

    }

    public String getServerPublicKeyUrl() {
        return String.format("%s/%s", authServerHost, serverPublicKeyEndpoint);
    }

    public String getRegisterDeviceUrl() {
        return String.format("%s/%s", authServerHost, serverRegisterDeviceEndpoint);
    }

    public String getConfirmVerificationCodeEndpoint() {
        return String.format("%s/%s", authServerHost, serverConfirmVerificationCodeEndpoint);
    }

    public String getAuthenticateSessionEndpoint() {
        return String.format("%s/%s", authServerHost, serverAuthenticateSessionEndpoint);
    }

    public static class Builder
    {
        private AuthenticationLibraryConfiguration configuration;
        private Builder() {
            configuration = new AuthenticationLibraryConfiguration();
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withServerHost(String serverHost) {
            configuration.authServerHost = serverHost;
            return this;
        }

        public Builder withPublicKeyEndpoint(String publicKeyEndpoint) {
            configuration.serverPublicKeyEndpoint = publicKeyEndpoint;
            return this;
        }

        public Builder withRegisterDeviceEndpoint(String registerDeviceEndpoint) {
            configuration.serverRegisterDeviceEndpoint = registerDeviceEndpoint;
            return this;
        }

        public Builder withConfirmVerificationCodeEndpoint(String confirmVerificationCodeEndpoint) {
            configuration.serverConfirmVerificationCodeEndpoint = confirmVerificationCodeEndpoint;
            return this;
        }

        public Builder withAuthenticateSessionEndpoint(String authenticateSessionEndpoint) {
            configuration.serverAuthenticateSessionEndpoint = authenticateSessionEndpoint;
            return this;
        }

        public AuthenticationLibraryConfiguration build() {
            return configuration;
        }
    }
}
