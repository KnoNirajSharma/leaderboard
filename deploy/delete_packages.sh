#!/bin/bash

##################### MAIN #####################
PACKAGES=$(bash ./list_packages.sh)
for VERSION in ${PACKAGES[@]}
do
    echo "Deleting: "$VERSION

    curl -X POST \
    -H "Accept: application/vnd.github.package-deletes-preview+json" \
    -H "Authorization: bearer $INPUT_TOKEN" \
    -d '{"query":"mutation { deletePackageVersion(input:{packageVersionId:\"'"$VERSION"'\"}) { success }}"}' \
    https://api.github.com/graphql
done
