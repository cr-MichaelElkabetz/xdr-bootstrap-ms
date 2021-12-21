package com.cybereason.xdr.bootstrap.config.Bigtable;

import com.google.api.gax.rpc.NotFoundException;
import com.google.cloud.bigtable.admin.v2.BigtableTableAdminClient;
import com.google.cloud.bigtable.admin.v2.BigtableTableAdminSettings;
import com.google.cloud.bigtable.admin.v2.models.CreateTableRequest;
import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.BigtableDataSettings;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowCell;
import com.google.cloud.bigtable.data.v2.models.RowMutation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

public class BigtableConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(BigtableConfig.class);
    private static final String COLUMN_FAMILY = "user";
    private static final String COLUMN_QUALIFIER_IDENTITY = "identity";

    private String server = "localhost";

    private String projectID = "test";

    private String instanceID = "test";

    private String tableID = "identities";

    private BigtableTableAdminClient adminClient = BigtableAdmin();
    private BigtableDataClient dataClient = BigtableClient();

    @Bean
    public BigtableTableAdminClient BigtableAdmin() {
        BigtableTableAdminSettings adminSettings;
        try {
            adminSettings = BigtableTableAdminSettings.newBuilderForEmulator(server, 9035)
                    .setProjectId(projectID)
                    .setInstanceId(instanceID)
                    .build();
            return BigtableTableAdminClient.create(adminSettings);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public BigtableDataClient BigtableClient() {
        BigtableDataSettings settings =
                BigtableDataSettings.newBuilderForEmulator(server, 9035).setProjectId(projectID).setInstanceId(instanceID).build();
        try {
            return BigtableDataClient.create(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isIdentityTableExists() {
        if (!adminClient.exists(tableID)) {
            LOGGER.info("Creating table: " + tableID);
            CreateTableRequest createTableRequest =
                    CreateTableRequest.of(tableID).addFamily(COLUMN_FAMILY);
            adminClient.createTable(createTableRequest);
            LOGGER.info("Table %s created successfully%n", tableID);
        } else {
            LOGGER.info("The table %s is already exist%n", tableID);
        }
        return true;
    }

    public void setData(String key, String value) {
        try {
            LOGGER.info("\nSetting a new data to table");
            if (isIdentityTableExists()) {
                RowMutation rowMutation = RowMutation.create(tableID, key).setCell(COLUMN_FAMILY, COLUMN_QUALIFIER_IDENTITY, value);
                dataClient.mutateRow(rowMutation);
            }
        } catch (NotFoundException e) {
            LOGGER.error("Failed to write to non-existent table: " + e.getMessage());
        }
    }

    public String getData(String key) {
        try {
            LOGGER.info("\nGetting data for key: " + key);
            Row row = dataClient.readRow(tableID, key);
            for (RowCell cell : row.getCells()) {
                if (COLUMN_FAMILY.equalsIgnoreCase(cell.getFamily()) && COLUMN_QUALIFIER_IDENTITY.equalsIgnoreCase(cell.getQualifier().toStringUtf8())) {
                    return cell.getValue().toStringUtf8();
                }
            }
            LOGGER.error("Unable to find dat for key: " + key);
            return null;
        } catch (NotFoundException e) {
            LOGGER.error("Failed to read from a non-existent table: " + e.getMessage());
            return null;
        }
    }

    public void deleteTable(String tableID) {
        System.out.println("\nDeleting table: " + tableID);
        try {
            adminClient.deleteTable(tableID);
            System.out.printf("Table %s deleted successfully%n", tableID);
        } catch (NotFoundException e) {
            System.err.println("Failed to delete a non-existent table: " + e.getMessage());
        }
    }
}
