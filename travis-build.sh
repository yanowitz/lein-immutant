#!/bin/bash

curl -f -L https://raw.github.com/technomancy/leiningen/2.3.2/bin/lein > ./lein
chmod +x ./lein
./lein midje
