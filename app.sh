#!/bin/bash

function stage_build() {
    docker build -t bee_app -f app.Dockerfile .
}

function stage_init() {
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
}

case $1 in

    run)
        echo initializing ...
        stage_init
        echo building ...
        stage_build
        echo let\'s start ...
        docker compose -f docker-compose.yml up -d
        echo done
    ;;

    logs)
        docker logs bee_$2
    ;;

    build)
        echo executing \"$1\"
        stage_build
        echo done
    ;;

    init)
        echo executing \"$1\"
        stage_init
        echo done
    ;;

    devenv)
        echo executing \"$1\"
        docker compose -f docker-compose.yml up bee_pg
        echo done
        ;;

    *)
        echo "unknown action \"$1\". nothing to do"
        ;;
esac