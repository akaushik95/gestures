package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.ImportFlag;
import io.realm.ProxyUtils;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.internal.objectstore.OsObjectBuilder;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class com_example_gestures_BugDataModelRealmProxy extends com.example.gestures.BugDataModel
    implements RealmObjectProxy, com_example_gestures_BugDataModelRealmProxyInterface {

    static final class BugDataModelColumnInfo extends ColumnInfo {
        long maxColumnIndexValue;
        long countryIndex;
        long summaryIndex;
        long descriptionIndex;
        long selectTypeIndex;
        long fixingPriorityIndex;
        long platformIndex;
        long appVersionIndex;
        long customerRequestIdIndex;
        long providerIdIndex;
        long customerIdIndex;
        long filePathIndex;

        BugDataModelColumnInfo(OsSchemaInfo schemaInfo) {
            super(11);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("BugDataModel");
            this.countryIndex = addColumnDetails("country", "country", objectSchemaInfo);
            this.summaryIndex = addColumnDetails("summary", "summary", objectSchemaInfo);
            this.descriptionIndex = addColumnDetails("description", "description", objectSchemaInfo);
            this.selectTypeIndex = addColumnDetails("selectType", "selectType", objectSchemaInfo);
            this.fixingPriorityIndex = addColumnDetails("fixingPriority", "fixingPriority", objectSchemaInfo);
            this.platformIndex = addColumnDetails("platform", "platform", objectSchemaInfo);
            this.appVersionIndex = addColumnDetails("appVersion", "appVersion", objectSchemaInfo);
            this.customerRequestIdIndex = addColumnDetails("customerRequestId", "customerRequestId", objectSchemaInfo);
            this.providerIdIndex = addColumnDetails("providerId", "providerId", objectSchemaInfo);
            this.customerIdIndex = addColumnDetails("customerId", "customerId", objectSchemaInfo);
            this.filePathIndex = addColumnDetails("filePath", "filePath", objectSchemaInfo);
            this.maxColumnIndexValue = objectSchemaInfo.getMaxColumnIndex();
        }

        BugDataModelColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new BugDataModelColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final BugDataModelColumnInfo src = (BugDataModelColumnInfo) rawSrc;
            final BugDataModelColumnInfo dst = (BugDataModelColumnInfo) rawDst;
            dst.countryIndex = src.countryIndex;
            dst.summaryIndex = src.summaryIndex;
            dst.descriptionIndex = src.descriptionIndex;
            dst.selectTypeIndex = src.selectTypeIndex;
            dst.fixingPriorityIndex = src.fixingPriorityIndex;
            dst.platformIndex = src.platformIndex;
            dst.appVersionIndex = src.appVersionIndex;
            dst.customerRequestIdIndex = src.customerRequestIdIndex;
            dst.providerIdIndex = src.providerIdIndex;
            dst.customerIdIndex = src.customerIdIndex;
            dst.filePathIndex = src.filePathIndex;
            dst.maxColumnIndexValue = src.maxColumnIndexValue;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private BugDataModelColumnInfo columnInfo;
    private ProxyState<com.example.gestures.BugDataModel> proxyState;

    com_example_gestures_BugDataModelRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (BugDataModelColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.gestures.BugDataModel>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$country() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.countryIndex);
    }

    @Override
    public void realmSet$country(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.countryIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.countryIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.countryIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.countryIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$summary() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.summaryIndex);
    }

    @Override
    public void realmSet$summary(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.summaryIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.summaryIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.summaryIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.summaryIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$description() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.descriptionIndex);
    }

    @Override
    public void realmSet$description(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.descriptionIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.descriptionIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.descriptionIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.descriptionIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$selectType() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.selectTypeIndex);
    }

    @Override
    public void realmSet$selectType(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.selectTypeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.selectTypeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.selectTypeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.selectTypeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$fixingPriority() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.fixingPriorityIndex);
    }

    @Override
    public void realmSet$fixingPriority(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.fixingPriorityIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.fixingPriorityIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.fixingPriorityIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.fixingPriorityIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$platform() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.platformIndex);
    }

    @Override
    public void realmSet$platform(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.platformIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.platformIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.platformIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.platformIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$appVersion() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.appVersionIndex);
    }

    @Override
    public void realmSet$appVersion(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.appVersionIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.appVersionIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.appVersionIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.appVersionIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$customerRequestId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.customerRequestIdIndex);
    }

    @Override
    public void realmSet$customerRequestId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.customerRequestIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.customerRequestIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.customerRequestIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.customerRequestIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$providerId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.providerIdIndex);
    }

    @Override
    public void realmSet$providerId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.providerIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.providerIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.providerIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.providerIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$customerId() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.customerIdIndex);
    }

    @Override
    public void realmSet$customerId(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.customerIdIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.customerIdIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.customerIdIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.customerIdIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$filePath() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.filePathIndex);
    }

    @Override
    public void realmSet$filePath(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.filePathIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.filePathIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.filePathIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.filePathIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("BugDataModel", 11, 0);
        builder.addPersistedProperty("country", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("summary", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("description", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("selectType", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("fixingPriority", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("platform", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("appVersion", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("customerRequestId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("providerId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("customerId", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("filePath", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static BugDataModelColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new BugDataModelColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "BugDataModel";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "BugDataModel";
    }

    @SuppressWarnings("cast")
    public static com.example.gestures.BugDataModel createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.example.gestures.BugDataModel obj = realm.createObjectInternal(com.example.gestures.BugDataModel.class, true, excludeFields);

        final com_example_gestures_BugDataModelRealmProxyInterface objProxy = (com_example_gestures_BugDataModelRealmProxyInterface) obj;
        if (json.has("country")) {
            if (json.isNull("country")) {
                objProxy.realmSet$country(null);
            } else {
                objProxy.realmSet$country((String) json.getString("country"));
            }
        }
        if (json.has("summary")) {
            if (json.isNull("summary")) {
                objProxy.realmSet$summary(null);
            } else {
                objProxy.realmSet$summary((String) json.getString("summary"));
            }
        }
        if (json.has("description")) {
            if (json.isNull("description")) {
                objProxy.realmSet$description(null);
            } else {
                objProxy.realmSet$description((String) json.getString("description"));
            }
        }
        if (json.has("selectType")) {
            if (json.isNull("selectType")) {
                objProxy.realmSet$selectType(null);
            } else {
                objProxy.realmSet$selectType((String) json.getString("selectType"));
            }
        }
        if (json.has("fixingPriority")) {
            if (json.isNull("fixingPriority")) {
                objProxy.realmSet$fixingPriority(null);
            } else {
                objProxy.realmSet$fixingPriority((String) json.getString("fixingPriority"));
            }
        }
        if (json.has("platform")) {
            if (json.isNull("platform")) {
                objProxy.realmSet$platform(null);
            } else {
                objProxy.realmSet$platform((String) json.getString("platform"));
            }
        }
        if (json.has("appVersion")) {
            if (json.isNull("appVersion")) {
                objProxy.realmSet$appVersion(null);
            } else {
                objProxy.realmSet$appVersion((String) json.getString("appVersion"));
            }
        }
        if (json.has("customerRequestId")) {
            if (json.isNull("customerRequestId")) {
                objProxy.realmSet$customerRequestId(null);
            } else {
                objProxy.realmSet$customerRequestId((String) json.getString("customerRequestId"));
            }
        }
        if (json.has("providerId")) {
            if (json.isNull("providerId")) {
                objProxy.realmSet$providerId(null);
            } else {
                objProxy.realmSet$providerId((String) json.getString("providerId"));
            }
        }
        if (json.has("customerId")) {
            if (json.isNull("customerId")) {
                objProxy.realmSet$customerId(null);
            } else {
                objProxy.realmSet$customerId((String) json.getString("customerId"));
            }
        }
        if (json.has("filePath")) {
            if (json.isNull("filePath")) {
                objProxy.realmSet$filePath(null);
            } else {
                objProxy.realmSet$filePath((String) json.getString("filePath"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.gestures.BugDataModel createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.example.gestures.BugDataModel obj = new com.example.gestures.BugDataModel();
        final com_example_gestures_BugDataModelRealmProxyInterface objProxy = (com_example_gestures_BugDataModelRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("country")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$country((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$country(null);
                }
            } else if (name.equals("summary")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$summary((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$summary(null);
                }
            } else if (name.equals("description")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$description((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$description(null);
                }
            } else if (name.equals("selectType")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$selectType((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$selectType(null);
                }
            } else if (name.equals("fixingPriority")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$fixingPriority((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$fixingPriority(null);
                }
            } else if (name.equals("platform")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$platform((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$platform(null);
                }
            } else if (name.equals("appVersion")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$appVersion((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$appVersion(null);
                }
            } else if (name.equals("customerRequestId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$customerRequestId((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$customerRequestId(null);
                }
            } else if (name.equals("providerId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$providerId((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$providerId(null);
                }
            } else if (name.equals("customerId")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$customerId((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$customerId(null);
                }
            } else if (name.equals("filePath")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$filePath((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$filePath(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_example_gestures_BugDataModelRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating unexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.example.gestures.BugDataModel.class), false, Collections.<String>emptyList());
        io.realm.com_example_gestures_BugDataModelRealmProxy obj = new io.realm.com_example_gestures_BugDataModelRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.example.gestures.BugDataModel copyOrUpdate(Realm realm, BugDataModelColumnInfo columnInfo, com.example.gestures.BugDataModel object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.example.gestures.BugDataModel) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.example.gestures.BugDataModel copy(Realm realm, BugDataModelColumnInfo columnInfo, com.example.gestures.BugDataModel newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.gestures.BugDataModel) cachedRealmObject;
        }

        com_example_gestures_BugDataModelRealmProxyInterface realmObjectSource = (com_example_gestures_BugDataModelRealmProxyInterface) newObject;

        Table table = realm.getTable(com.example.gestures.BugDataModel.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, columnInfo.maxColumnIndexValue, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.countryIndex, realmObjectSource.realmGet$country());
        builder.addString(columnInfo.summaryIndex, realmObjectSource.realmGet$summary());
        builder.addString(columnInfo.descriptionIndex, realmObjectSource.realmGet$description());
        builder.addString(columnInfo.selectTypeIndex, realmObjectSource.realmGet$selectType());
        builder.addString(columnInfo.fixingPriorityIndex, realmObjectSource.realmGet$fixingPriority());
        builder.addString(columnInfo.platformIndex, realmObjectSource.realmGet$platform());
        builder.addString(columnInfo.appVersionIndex, realmObjectSource.realmGet$appVersion());
        builder.addString(columnInfo.customerRequestIdIndex, realmObjectSource.realmGet$customerRequestId());
        builder.addString(columnInfo.providerIdIndex, realmObjectSource.realmGet$providerId());
        builder.addString(columnInfo.customerIdIndex, realmObjectSource.realmGet$customerId());
        builder.addString(columnInfo.filePathIndex, realmObjectSource.realmGet$filePath());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_example_gestures_BugDataModelRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.example.gestures.BugDataModel object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.gestures.BugDataModel.class);
        long tableNativePtr = table.getNativePtr();
        BugDataModelColumnInfo columnInfo = (BugDataModelColumnInfo) realm.getSchema().getColumnInfo(com.example.gestures.BugDataModel.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        String realmGet$country = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$country();
        if (realmGet$country != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.countryIndex, rowIndex, realmGet$country, false);
        }
        String realmGet$summary = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$summary();
        if (realmGet$summary != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.summaryIndex, rowIndex, realmGet$summary, false);
        }
        String realmGet$description = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$description();
        if (realmGet$description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
        }
        String realmGet$selectType = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$selectType();
        if (realmGet$selectType != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.selectTypeIndex, rowIndex, realmGet$selectType, false);
        }
        String realmGet$fixingPriority = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$fixingPriority();
        if (realmGet$fixingPriority != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.fixingPriorityIndex, rowIndex, realmGet$fixingPriority, false);
        }
        String realmGet$platform = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$platform();
        if (realmGet$platform != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.platformIndex, rowIndex, realmGet$platform, false);
        }
        String realmGet$appVersion = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$appVersion();
        if (realmGet$appVersion != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.appVersionIndex, rowIndex, realmGet$appVersion, false);
        }
        String realmGet$customerRequestId = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$customerRequestId();
        if (realmGet$customerRequestId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.customerRequestIdIndex, rowIndex, realmGet$customerRequestId, false);
        }
        String realmGet$providerId = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$providerId();
        if (realmGet$providerId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.providerIdIndex, rowIndex, realmGet$providerId, false);
        }
        String realmGet$customerId = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$customerId();
        if (realmGet$customerId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.customerIdIndex, rowIndex, realmGet$customerId, false);
        }
        String realmGet$filePath = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$filePath();
        if (realmGet$filePath != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.filePathIndex, rowIndex, realmGet$filePath, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.gestures.BugDataModel.class);
        long tableNativePtr = table.getNativePtr();
        BugDataModelColumnInfo columnInfo = (BugDataModelColumnInfo) realm.getSchema().getColumnInfo(com.example.gestures.BugDataModel.class);
        com.example.gestures.BugDataModel object = null;
        while (objects.hasNext()) {
            object = (com.example.gestures.BugDataModel) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            String realmGet$country = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$country();
            if (realmGet$country != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.countryIndex, rowIndex, realmGet$country, false);
            }
            String realmGet$summary = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$summary();
            if (realmGet$summary != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.summaryIndex, rowIndex, realmGet$summary, false);
            }
            String realmGet$description = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$description();
            if (realmGet$description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
            }
            String realmGet$selectType = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$selectType();
            if (realmGet$selectType != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.selectTypeIndex, rowIndex, realmGet$selectType, false);
            }
            String realmGet$fixingPriority = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$fixingPriority();
            if (realmGet$fixingPriority != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.fixingPriorityIndex, rowIndex, realmGet$fixingPriority, false);
            }
            String realmGet$platform = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$platform();
            if (realmGet$platform != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.platformIndex, rowIndex, realmGet$platform, false);
            }
            String realmGet$appVersion = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$appVersion();
            if (realmGet$appVersion != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.appVersionIndex, rowIndex, realmGet$appVersion, false);
            }
            String realmGet$customerRequestId = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$customerRequestId();
            if (realmGet$customerRequestId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.customerRequestIdIndex, rowIndex, realmGet$customerRequestId, false);
            }
            String realmGet$providerId = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$providerId();
            if (realmGet$providerId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.providerIdIndex, rowIndex, realmGet$providerId, false);
            }
            String realmGet$customerId = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$customerId();
            if (realmGet$customerId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.customerIdIndex, rowIndex, realmGet$customerId, false);
            }
            String realmGet$filePath = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$filePath();
            if (realmGet$filePath != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.filePathIndex, rowIndex, realmGet$filePath, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.gestures.BugDataModel object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.gestures.BugDataModel.class);
        long tableNativePtr = table.getNativePtr();
        BugDataModelColumnInfo columnInfo = (BugDataModelColumnInfo) realm.getSchema().getColumnInfo(com.example.gestures.BugDataModel.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        String realmGet$country = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$country();
        if (realmGet$country != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.countryIndex, rowIndex, realmGet$country, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.countryIndex, rowIndex, false);
        }
        String realmGet$summary = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$summary();
        if (realmGet$summary != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.summaryIndex, rowIndex, realmGet$summary, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.summaryIndex, rowIndex, false);
        }
        String realmGet$description = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$description();
        if (realmGet$description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.descriptionIndex, rowIndex, false);
        }
        String realmGet$selectType = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$selectType();
        if (realmGet$selectType != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.selectTypeIndex, rowIndex, realmGet$selectType, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.selectTypeIndex, rowIndex, false);
        }
        String realmGet$fixingPriority = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$fixingPriority();
        if (realmGet$fixingPriority != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.fixingPriorityIndex, rowIndex, realmGet$fixingPriority, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.fixingPriorityIndex, rowIndex, false);
        }
        String realmGet$platform = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$platform();
        if (realmGet$platform != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.platformIndex, rowIndex, realmGet$platform, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.platformIndex, rowIndex, false);
        }
        String realmGet$appVersion = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$appVersion();
        if (realmGet$appVersion != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.appVersionIndex, rowIndex, realmGet$appVersion, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.appVersionIndex, rowIndex, false);
        }
        String realmGet$customerRequestId = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$customerRequestId();
        if (realmGet$customerRequestId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.customerRequestIdIndex, rowIndex, realmGet$customerRequestId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.customerRequestIdIndex, rowIndex, false);
        }
        String realmGet$providerId = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$providerId();
        if (realmGet$providerId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.providerIdIndex, rowIndex, realmGet$providerId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.providerIdIndex, rowIndex, false);
        }
        String realmGet$customerId = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$customerId();
        if (realmGet$customerId != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.customerIdIndex, rowIndex, realmGet$customerId, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.customerIdIndex, rowIndex, false);
        }
        String realmGet$filePath = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$filePath();
        if (realmGet$filePath != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.filePathIndex, rowIndex, realmGet$filePath, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.filePathIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.gestures.BugDataModel.class);
        long tableNativePtr = table.getNativePtr();
        BugDataModelColumnInfo columnInfo = (BugDataModelColumnInfo) realm.getSchema().getColumnInfo(com.example.gestures.BugDataModel.class);
        com.example.gestures.BugDataModel object = null;
        while (objects.hasNext()) {
            object = (com.example.gestures.BugDataModel) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            String realmGet$country = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$country();
            if (realmGet$country != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.countryIndex, rowIndex, realmGet$country, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.countryIndex, rowIndex, false);
            }
            String realmGet$summary = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$summary();
            if (realmGet$summary != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.summaryIndex, rowIndex, realmGet$summary, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.summaryIndex, rowIndex, false);
            }
            String realmGet$description = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$description();
            if (realmGet$description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.descriptionIndex, rowIndex, realmGet$description, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.descriptionIndex, rowIndex, false);
            }
            String realmGet$selectType = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$selectType();
            if (realmGet$selectType != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.selectTypeIndex, rowIndex, realmGet$selectType, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.selectTypeIndex, rowIndex, false);
            }
            String realmGet$fixingPriority = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$fixingPriority();
            if (realmGet$fixingPriority != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.fixingPriorityIndex, rowIndex, realmGet$fixingPriority, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.fixingPriorityIndex, rowIndex, false);
            }
            String realmGet$platform = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$platform();
            if (realmGet$platform != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.platformIndex, rowIndex, realmGet$platform, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.platformIndex, rowIndex, false);
            }
            String realmGet$appVersion = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$appVersion();
            if (realmGet$appVersion != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.appVersionIndex, rowIndex, realmGet$appVersion, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.appVersionIndex, rowIndex, false);
            }
            String realmGet$customerRequestId = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$customerRequestId();
            if (realmGet$customerRequestId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.customerRequestIdIndex, rowIndex, realmGet$customerRequestId, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.customerRequestIdIndex, rowIndex, false);
            }
            String realmGet$providerId = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$providerId();
            if (realmGet$providerId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.providerIdIndex, rowIndex, realmGet$providerId, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.providerIdIndex, rowIndex, false);
            }
            String realmGet$customerId = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$customerId();
            if (realmGet$customerId != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.customerIdIndex, rowIndex, realmGet$customerId, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.customerIdIndex, rowIndex, false);
            }
            String realmGet$filePath = ((com_example_gestures_BugDataModelRealmProxyInterface) object).realmGet$filePath();
            if (realmGet$filePath != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.filePathIndex, rowIndex, realmGet$filePath, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.filePathIndex, rowIndex, false);
            }
        }
    }

    public static com.example.gestures.BugDataModel createDetachedCopy(com.example.gestures.BugDataModel realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.gestures.BugDataModel unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.example.gestures.BugDataModel();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.gestures.BugDataModel) cachedObject.object;
            }
            unmanagedObject = (com.example.gestures.BugDataModel) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_example_gestures_BugDataModelRealmProxyInterface unmanagedCopy = (com_example_gestures_BugDataModelRealmProxyInterface) unmanagedObject;
        com_example_gestures_BugDataModelRealmProxyInterface realmSource = (com_example_gestures_BugDataModelRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$country(realmSource.realmGet$country());
        unmanagedCopy.realmSet$summary(realmSource.realmGet$summary());
        unmanagedCopy.realmSet$description(realmSource.realmGet$description());
        unmanagedCopy.realmSet$selectType(realmSource.realmGet$selectType());
        unmanagedCopy.realmSet$fixingPriority(realmSource.realmGet$fixingPriority());
        unmanagedCopy.realmSet$platform(realmSource.realmGet$platform());
        unmanagedCopy.realmSet$appVersion(realmSource.realmGet$appVersion());
        unmanagedCopy.realmSet$customerRequestId(realmSource.realmGet$customerRequestId());
        unmanagedCopy.realmSet$providerId(realmSource.realmGet$providerId());
        unmanagedCopy.realmSet$customerId(realmSource.realmGet$customerId());
        unmanagedCopy.realmSet$filePath(realmSource.realmGet$filePath());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("BugDataModel = proxy[");
        stringBuilder.append("{country:");
        stringBuilder.append(realmGet$country() != null ? realmGet$country() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{summary:");
        stringBuilder.append(realmGet$summary() != null ? realmGet$summary() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{description:");
        stringBuilder.append(realmGet$description() != null ? realmGet$description() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{selectType:");
        stringBuilder.append(realmGet$selectType() != null ? realmGet$selectType() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{fixingPriority:");
        stringBuilder.append(realmGet$fixingPriority() != null ? realmGet$fixingPriority() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{platform:");
        stringBuilder.append(realmGet$platform() != null ? realmGet$platform() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{appVersion:");
        stringBuilder.append(realmGet$appVersion() != null ? realmGet$appVersion() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{customerRequestId:");
        stringBuilder.append(realmGet$customerRequestId() != null ? realmGet$customerRequestId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{providerId:");
        stringBuilder.append(realmGet$providerId() != null ? realmGet$providerId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{customerId:");
        stringBuilder.append(realmGet$customerId() != null ? realmGet$customerId() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{filePath:");
        stringBuilder.append(realmGet$filePath() != null ? realmGet$filePath() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com_example_gestures_BugDataModelRealmProxy aBugDataModel = (com_example_gestures_BugDataModelRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aBugDataModel.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aBugDataModel.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aBugDataModel.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
