package ru.itone.beeline.database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.itone.beeline.database.entity.UserWebVisit;

public class InitDataGenerator {

    private static Logger log = LoggerFactory.getLogger(InitDataGenerator.class);

    public static void main(String[] args) {

        long count = args.length >= 1 ? Long.parseLong(args[0]) : 100;
        String output = args.length >= 2 ? args[1] : "data/sql/init.sql";

        final String tableDDL = """
        drop table if exists `user_web_visit`;
        create table `user_web_visit` (
            `login` varchar(255),
            `location` varchar(255),
            `date_time_ts` timestamp default current_timestamp
        );
        """;
        
        final String insertPrefix = """
        insert into `user_web_visit` (`login`, `location`, `date_time_ts`) values
        """;
        
        final String nl = "\r\n";
        
        File file = new File(output);
        if(file.exists()) {
            file.delete();
        }

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(tableDDL);
            bw.write(nl);
            bw.write(insertPrefix);
            String line;
            for(int i=0; i<count; i++) {
                UserWebVisit row = UserWebVisit.random();
                line = "\t('"+row.getLogin()+"', '"+row.getLocation()+"', '"+row.getDateTime().minusMinutes(Long.valueOf(count/60).intValue())+"')";
                bw.write(line);
                bw.write(i==count-1 ? ";" : nl);
            }
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
