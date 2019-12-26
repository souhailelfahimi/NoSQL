package com.projectHbase.javaHbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;


/*
 * #Answer

disable 'answer'
drop 'answer'
create 'answer', 'info', 'data', 'rate'

put 'answer', '1', 'info:id', '1'
put 'answer', '1', 'info:userId', '1'
put 'answer', '1', 'info:questionId', '1'
#date : doit etre un timestamp valide
put 'answer', '1', 'info:date', '123991990018'
put 'answer', '1', 'data:text', 'le ciel est bleu parce que ...'
put 'answer', '1', 'data:description', 'Opinion personnelle'
put 'answer', '1', 'data:image', '[\'http://img.com/dzidhzi.jpg\']'
put 'answer', '1', 'data:link', '[\' http://www.meteo.org/phenomen/cielbleu.htm\']'
put 'answer', '1', 'rate:votes', '16'
*/
// create 'reponse','info','data'
public class ReponseRepository {
    Configuration conf = HBaseConfiguration.create();

    String tblName = "reponse";
    String info = "info";
    String data = "data";
    
    public void save(Reponse reponse) throws ZooKeeperConnectionException, IOException {
        System.out.println("" + reponse.toString());

        HBaseAdmin admin = new HBaseAdmin(conf);

        HConnection connection = HConnectionManager.createConnection(conf);


        HTableInterface tblIface = connection.getTable(tblName);


        String rowKey = reponse.getId() + "RK"; //System.currentTimeMillis();

        Put put = new Put(Bytes.toBytes(rowKey));
        put.add(Bytes.toBytes(info), Bytes.toBytes("id"), Bytes.toBytes(reponse.getId()));
        put.add(Bytes.toBytes(info), Bytes.toBytes("user_id"), Bytes.toBytes(reponse.getUser_id()));
        put.add(Bytes.toBytes(info), Bytes.toBytes("reponse_id"), Bytes.toBytes(reponse.getQuestion_id()));
        put.add(Bytes.toBytes(data), Bytes.toBytes("text"), Bytes.toBytes(reponse.getText()));
        put.add(Bytes.toBytes(data), Bytes.toBytes("votes"), Bytes.toBytes(reponse.getVotes()));
        tblIface.put(put);

        tblIface.close();
    }
}
