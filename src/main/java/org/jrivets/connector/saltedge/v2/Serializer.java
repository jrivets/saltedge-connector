package org.jrivets.connector.saltedge.v2;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.util.ISO8601Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

final class Serializer {

    final Gson gson;

    Serializer() {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        
        JsonSerializer<Date> dateSerializer = (Date date, Type type, JsonSerializationContext jsc) -> {
            return new JsonPrimitive(ISO8601Utils.format(date, true));
        };
        JsonDeserializer<Date> dateDeserializer = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
            return ISO8601Utils.parse(json.getAsString());
        };
        
        JsonSerializer<DateOnly> dateOnlySerializer = (DateOnly date, Type type, JsonSerializationContext jsc) -> {
            return new JsonPrimitive(df.format(date));
        };
        JsonDeserializer<DateOnly> dateOnlyDeserializer = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
            try {
                return new DateOnly(df.parse(json.getAsString()));
            } catch (ParseException e) {
                throw new InternalErrorException(e);
            }
        };
        
        JsonSerializer<ProviderMode> prvModeSerializer = (ProviderMode pm, Type type, JsonSerializationContext jsc) -> {
            return new JsonPrimitive(pm.name().toLowerCase());
        };
        JsonDeserializer<ProviderMode> prvModeDeserializer = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
            return ProviderMode.valueOf(json.getAsString().toUpperCase());
        };
        
        JsonSerializer<ProviderStatus> prvStatusSerializer = (ProviderStatus ps, Type type, JsonSerializationContext jsc) -> {
            return new JsonPrimitive(ps.name().toLowerCase());
        };
        JsonDeserializer<ProviderStatus> prvStatusDeserializer = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
            return ProviderStatus.valueOf(json.getAsString().toUpperCase());
        };

        JsonSerializer<Error> errorSerializer = (Error error, Type type, JsonSerializationContext jsc) -> {
            return new JsonPrimitive(error.name());
        };
        JsonDeserializer<Error> errorDeserializer = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
            return Error.ToError(json.getAsString());
        };
        
        JsonSerializer<LoginStatus> lgnStatusSerializer = (LoginStatus ls, Type type, JsonSerializationContext jsc) -> {
            return new JsonPrimitive(ls.name().toLowerCase());
        };
        JsonDeserializer<LoginStatus> lgnStatusDeserializer = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
            return LoginStatus.valueOf(json.getAsString().toUpperCase());
        };
        
        JsonSerializer<FieldNature> fldNatureSerializer = (FieldNature fn, Type type, JsonSerializationContext jsc) -> {
            return new JsonPrimitive(fn.name().toLowerCase());
        };
        JsonDeserializer<FieldNature> fldNatureDeserializer = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
            return FieldNature.valueOf(json.getAsString().toUpperCase());
        };
        
        JsonSerializer<ClientStatus> clientStatusSerializer = (ClientStatus cs, Type type, JsonSerializationContext jsc) -> {
            return new JsonPrimitive(cs.name().toLowerCase());
        };
        JsonDeserializer<ClientStatus> clientStatusDeserializer = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
            return ClientStatus.valueOf(json.getAsString().toUpperCase());
        };
        
        JsonSerializer<LoginFetchType> loginFTSerializer = (LoginFetchType ft, Type type, JsonSerializationContext jsc) -> {
            return new JsonPrimitive(ft.name().toLowerCase());
        };
        JsonDeserializer<LoginFetchType> loginFTDeserializer = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
            return LoginFetchType.valueOf(json.getAsString().toUpperCase());
        };
        
        JsonSerializer<AccountStatus> accountStatusSerializer = (AccountStatus as, Type type, JsonSerializationContext jsc) -> {
            return new JsonPrimitive(as.name().toLowerCase());
        };
        JsonDeserializer<AccountStatus> accountStatusDeserializer = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
            return AccountStatus.valueOf(json.getAsString().toUpperCase());
        };
        
        JsonSerializer<TransactionStatus> txStatusSerializer = (TransactionStatus ts, Type type, JsonSerializationContext jsc) -> {
            return new JsonPrimitive(ts.name().toLowerCase());
        };
        JsonDeserializer<TransactionStatus> txStatusDeserializer = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
            return TransactionStatus.valueOf(json.getAsString().toUpperCase());
        };
        
        JsonSerializer<TransactionMode> txModeSerializer = (TransactionMode tm, Type type, JsonSerializationContext jsc) -> {
            return new JsonPrimitive(tm.name().toLowerCase());
        };
        JsonDeserializer<TransactionMode> txModeDeserializer = (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> {
            return TransactionMode.valueOf(json.getAsString().toUpperCase());
        };

        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, dateSerializer)
                .registerTypeAdapter(Date.class, dateDeserializer)
                .registerTypeAdapter(DateOnly.class, dateOnlySerializer)
                .registerTypeAdapter(DateOnly.class, dateOnlyDeserializer)
                .registerTypeAdapter(ProviderMode.class, prvModeSerializer)
                .registerTypeAdapter(ProviderMode.class, prvModeDeserializer)
                .registerTypeAdapter(ProviderStatus.class, prvStatusSerializer)
                .registerTypeAdapter(ProviderStatus.class, prvStatusDeserializer)
                .registerTypeAdapter(Error.class, errorSerializer)
                .registerTypeAdapter(Error.class, errorDeserializer)
                .registerTypeAdapter(LoginStatus.class, lgnStatusSerializer)
                .registerTypeAdapter(LoginStatus.class, lgnStatusDeserializer)
                .registerTypeAdapter(FieldNature.class, fldNatureSerializer)
                .registerTypeAdapter(FieldNature.class, fldNatureDeserializer)
                .registerTypeAdapter(ClientStatus.class, clientStatusSerializer)
                .registerTypeAdapter(ClientStatus.class, clientStatusDeserializer)
                .registerTypeAdapter(LoginFetchType.class, loginFTSerializer)
                .registerTypeAdapter(LoginFetchType.class, loginFTDeserializer)
                .registerTypeAdapter(AccountStatus.class, accountStatusSerializer)
                .registerTypeAdapter(AccountStatus.class, accountStatusDeserializer)
                .registerTypeAdapter(TransactionMode.class, txModeSerializer)
                .registerTypeAdapter(TransactionMode.class, txModeDeserializer)
                .registerTypeAdapter(TransactionStatus.class, txStatusSerializer)
                .registerTypeAdapter(TransactionStatus.class, txStatusDeserializer)
                .create();
    }
    
    String toJson(Object o) {
        return gson.toJson(o);
    }
    
    <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
    
    <T> T fromJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }
    

}
