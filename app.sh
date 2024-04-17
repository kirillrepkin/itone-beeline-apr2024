#!/bin/bash

case $1 in
    build)
        echo executing \"$1\"
        docker build -t bee_app -f app.Dockerfile .
        echo done
    ;;

    init)
        echo executing \"$1\"
        for num in $(seq 1 6)
        do
            fname=data/files/big_${num}.txt
            echo generating $fname
            cp data/files/big.txt ${fname}
        done
        mvn clean compile assembly:single > /dev/null
        for num in $(seq 1 10)
        do
            fname=data/sql/init_${num}.sql
            echo generating $fname
            java -cp target/app-jar-with-dependencies.jar ru.itone.beeline.database.InitDataGenerator 1000000 ${fname}
        done
        echo done
    ;;

    devenv)
        echo executing \"$1\"
        docker compose -f docker-compose.yml up
        echo done
        ;;

    *)
        echo "unknown action \"$1\". nothing to do"
        ;;
esac