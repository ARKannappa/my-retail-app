create keyspace my_retail_keyspace with replication = {'class': 'org.apache.cassandra.locator.SimpleStrategy', 'replication_factor': '1'};

create table if not exists my_retail_keyspace.price
(
    sku      text primary key,
    currency text,
    price    double
)
    with caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
     and compaction = {'max_threshold': '32', 'min_threshold': '4', 'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy'}
     and compression = {'class': 'org.apache.cassandra.io.compress.LZ4Compressor', 'chunk_length_in_kb': '64'}
     and dclocal_read_repair_chance = 0.1;

INSERT INTO my_retail_keyspace.price (sku, currency, price) VALUES ('13860425', 'USD', 10);
INSERT INTO my_retail_keyspace.price (sku, currency, price) VALUES ('13860429', 'USD', 25);
INSERT INTO my_retail_keyspace.price (sku, currency, price) VALUES ('13860428', 'USD', 20);
INSERT INTO my_retail_keyspace.price (sku, currency, price) VALUES ('13860427', 'USD', 20);