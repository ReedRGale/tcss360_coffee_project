ENDPOINT=https://shrouded-shore-30021.herokuapp.com/shops
curl -X PUT -H "Content-Type: application/json" -d @./test_shops/new_5.json $ENDPOINT
echo

