#!/bin/bash

case $1 in
    init)
        echo executing \"$1\"
        for fname in one two three four five; do cp data/files/big.txt data/files/big_${fname}.txt; done
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