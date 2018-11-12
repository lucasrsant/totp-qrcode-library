package br.edu.fei.auth_library;

import android.content.Context;
import android.content.SharedPreferences;

import br.edu.fei.auth_library.exceptions.LibraryNotInitializedException;

public class AuthenticationLibrary {

    private SharedPreferences sharedPreferences;
    private AuthenticationLibraryConfiguration libraryConfiguration;
    private static AuthenticationLibrary instance;

    private AuthenticationLibrary(Context context, AuthenticationLibraryConfiguration configuration) {
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        this.libraryConfiguration = configuration;

        KeyManager.retrieveServerPublicKey(context, configuration.getServerPublicKeyUrl());
    }

    public static void initialize(Context context, AuthenticationLibraryConfiguration configuration) {
        if (instance == null)
            instance = new AuthenticationLibrary(context, configuration);
    }

    public static AuthenticationLibrary getInstance() throws LibraryNotInitializedException {
        if (instance == null)
            throw new LibraryNotInitializedException();

        return instance;
    }
}
