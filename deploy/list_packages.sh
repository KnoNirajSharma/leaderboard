#!/bin/bash
# AUTHOR: Shubham Girdhar
# email: shubham.girdhar@knoldus.com

##################### GLOBAL VARIABLES #####################
OWNER=${OWNER:-"knoldus"} 
REPO=${REPO:-"leaderboard"}

##################### METHODS #####################
graphqlQuery() {
  local query="$1"; shift

  curl -s -H "Authorization: bearer $INPUT_TOKEN" -X POST -d '{"query":"'"$query"'"}' 'https://api.github.com/graphql'
}
listPackageVersions() {
  local query="$(cat <<EOF | sed 's/"/\\"/g' | tr '\n\r' ' '
query{
  repository(name: "$REPO", owner: "$OWNER") {
    id
    packages(last: 30, packageType: DOCKER) {
      nodes {
        versions(last: 30) {
          edges {
            node {
              id
            }
          }
        }
      }
    }
  }
}

EOF
)"
  graphqlQuery "$query"
}

##################### MAIN #####################
listPackageVersions | jq '.data.repository.packages.nodes[].versions.edges[].node.id' | awk 'NR > 1 { printf(",") } {printf "%s",$0}'
