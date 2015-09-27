package org.jrivets.connector.saltedge.v2;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public enum Error {
    
    //An account with the sent account_id could not be found
    AccountNotFound,

    //APP_ID was not provided in the request headers
    AppIdNotProvided,

    //The client has been disabled. You can find out more about the disabled status on Disabled guides page
    ClientDisabled,

    //The Client-id was not provided in request headers
    ClientIdNotProvided,

    //The Client-id and Client-secret fields do not coincide
    ClientNotFound,

    //The client is pending approval. You can find out more about the pending status on Pending guides page
    ClientPending,
    
    // customer with the internal id was not found
    CustomerNotFound,

    //The client is in the Restricted state. You can find out more about the restricted status on Restricted guides page
    ClientRestricted,

    //Some network errors appear while fetching data
    ConnectionFailed,

    //Sending a country_code that is not present in our system
    CountryNotFound,

    //We have received an invalid Date format
    DateFormatInvalid,

    //Sending a date value that does not fit in admissible range
    DateOutOfRange,

    //The customer you are trying to create already exists
    DuplicatedCustomer,

    //The whole fetching process took too long to execute
    ExecutionTimeout,

    //The Expires-at header is invalid
    ExpiresAtInvalid,

    //One of the steps of the fetching process took too long to execute
    FetchingTimeout,

    //There were errors while uploading and processing files
    FileError,

    //Provider with the file mode has been chosen, but no file was uploaded before the creating or reconnecting a login
    FileNotProvided,

    //File was not saved because of an error
    FileNotSaved,

    //The interactive step of the fetching process took too long to execute
    InteractiveAdapterTimeout,

    //It took too long to respond to the interactive question
    InteractiveTimeout,

    //The customer tried to connect/reconnect a login with invalid credentials
    InvalidCredentials,

    //We have received some other request format instead of JSON, or the body could not be parsed
    JsonParseError,

    //The customer tried to connect, reconnect or fetch a login, but it appears to be disabled
    LoginDisabled,

    //The client tried to create a login that already exists
    LoginDuplicated,

    //LoginLimitReachedThe client tried to create more logins than possible for a client which is in Test or Pending status
    LoginLimitReached,

    //We could not find a login with the requested login_id
    LoginNotFound,

    //The Expires-at field is missing in the headers
    MissingExpiresAt,

    //The Signature field is missing in the headers
    MissingSignature,

    //The customer denied access to his data from the provider’s page
    ProviderAccessNotGranted,

    //The accessed provider is disabled
    ProviderDisabled,

    //There’s an error on the provider’s side which obstructs us from obtaining the data for the Login
    ProviderError,

    //The accessed provider is inactive at the moment
    ProviderInactive,

    //Sending a provider_code that is not present in our system
    ProviderNotFound,

    //The provider is temporary unavailable
    ProviderUnavailable,

    //The client did not provide the public key in his account information
    PublicKeyNotProvided,

    //Too many logins are being processed at the same time from one application
    RateLimitExceeded,

    //The request has been expired, took longer than mentioned in the Expires-at header
    RequestExpired,

    //The return_to URL is invalid
    ReturnURLInvalid,

    //The return_to URL exceeds 512 characters
    ReturnURLTooLong,

    //The App-secret or Service-secret was not provided in request headers
    SecretNotProvided,

    //The Signature header does not math with the correct one
    SignatureNotMatch,

    //Too many requests have occured for connecting/reconnecting a login from one IP address in a small period of time
    TooManyRequests,

    //A transaction with the sent transaction_id could not be found
    TransactionNotFound,

    //Sending a value (id for example) which exceeds integer limit
    ValueOutOfRange,

    //We have received a wrong combination of customer_id, client_id and login_id
    WrongClientToken,

    //The JSON request is incorrectly formed
    WrongRequestFormat,
    
    UNDEFINED;
    
    private final static Logger logger = Logger.getLogger(Error.class);
    
    private static Map<String, Error> strToError;

    static {
        strToError = new HashMap<>();
        for (Error err: Error.values()) {
            strToError.put(err.name().toLowerCase(), err);
        }
    }
    
    public static Error ToError(String strErr) {
        if (StringUtils.isBlank(strErr)) {
            return null;
        }
        Error err = strToError.get(strErr.toLowerCase().trim());
        if (err == null) {
            logger.warn("Unknown error string value strErr=" + strErr);
            err = UNDEFINED; 
        }
        return err;
    }
 
}
