#!/bin/zsh
baseUrl="http://localhost:8080/api";
# insert costumer
costumerLocationUrl="$(curl -H "content-type: application/json" "$baseUrl""/costumer" -d '{"name": "Mario"}' -si )"

# get new costumer
costumerGetUrl=$(echo "${costumerLocationUrl}" | grep Location | awk '{print $2}' | tr -d '\r\n')
getBodyCostumer="$(curl -s "${costumerGetUrl}")"
echo "$getBodyCostumer" | jq '.'

costumerIdentifier=$(echo "$getBodyCostumer" | jq '.identifier')

# insert product
productLocationUrl="$(curl "$baseUrl""/product" -d '{"name": "Banana", "value": 1235.45677}' -H "Content-type: application/json"  -si)"

# get new product
productGetUrl="$(echo "$productLocationUrl" | grep Location | awk '{print $2}' | tr -d '\r\n' )"
getBodyProduct="$(curl -s "$productGetUrl")"
echo "$getBodyProduct" | jq '.'

productIdentifier=$(echo "${getBodyProduct}" | jq '.identifier')

requestSaleBody=$(echo "{
                   \"status\": \"OPENED\",
                   \"costumerIdentifier\": ${costumerIdentifier},
                   \"paymentForm\": \"CREDIT_CARD\",
                   \"items\": [
                     {
                       \"productIdentifier\": ${productIdentifier},
                       \"quantity\": 1
                     }
                   ]
                 }")

saleLocationUrl="$(curl -X POST -si -H "content-type: application/json" "$baseUrl""/sale" -d "${requestSaleBody}")"
echo $saleLocationUrl
saleGetUrl="$(echo "${saleLocationUrl}" | grep Location | awk '{print $2}' | tr -d '\r\n' )"

curl -s "${saleGetUrl}" | jq '.'